package com.pinhuba.web.filter.dwrRemoter;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.Calls;
import org.directwebremoting.extend.Replies;
import org.directwebremoting.impl.DefaultRemoter;
import org.directwebremoting.proxy.dwr.Util;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.web.listener.OnlineUserBindingListener;

/**
 * 调用dwr方法时验证用户信息
 * 
 * @author peng.ning
 * @date Apr 28, 2010
 */
public class DWRRemoter extends DefaultRemoter {
	private Logger log = Logger.getLogger(this.getClass());

	public Replies execute(Calls calls) {

		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();// 获取request对象
		ServletContext context = WebContextFactory.get().getServletContext();
		Object obj = LoginContext.getSessionValueByLogin(request);
		
		if (obj == null) {
			this.loginOut("loginOut()");//执行页面方法
			return super.execute(new Calls());
		} else {
			boolean bl;
			try {
				//读取配置文件，判断系统中，用户是否可以重复登录
				bl = Boolean.parseBoolean(SystemConfig.getParam("erp.sys.LoginSingle"));
				if(bl){
					SessionUser user = (SessionUser) obj;
					String sid = request.getSession().getId();
					bl = OnlineUserBindingListener.isEqualsSessionId(context, user.getEmployeeInfo().getPrimaryKey(), sid);
					if (!bl) {
						//释放session
						LoginContext.removeSessionByLogin(request);
						log.info("用户" + user.getEmployeeName() + "已登录");
						this.loginOut("warningmsg()");//执行页面方法
						return super.execute(new Calls());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.execute(calls);
	}

	private void loginOut(String jsmethod) {
		WebContext wct = WebContextFactory.get();
		Util util = new Util(wct.getScriptSession());
		util.addScript(new ScriptBuffer(jsmethod));
	}
}
