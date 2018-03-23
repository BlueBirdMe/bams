package com.pinhuba.web.servlet.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinhuba.common.util.ConstWords;
/**
 * 生成验证码
 * @author peng.ning
 *
 */
public class CreateValidCodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1606911790429994866L;

	public CreateValidCodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpg;charset=utf-8");
		String sessionName = request.getParameter("sid");
		// 在内存中创建图象
		int width=70, height=24;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		//生成随机类
		Random random = new Random();
		//设定字体
		g2.setFont(new Font("Times New Roman",Font.PLAIN,18));
		// 设定背景色
		g2.setColor(getRandColor(200,250));
		g2.fillRect(0, 0, width, height);

		//画边框
		g2.setColor(getRandColor(160,200));
		g2.drawRect(0,0,width-1,height-1);

		// 随机产生120条干扰线，使图象中的认证码不易被其它程序探测到
		g2.setColor(getRandColor(160,200));
		for (int i=0;i<120;i++)
		{
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g2.drawLine(x,y,x+xl,y+yl);
		}

		// 取随机产生的认证码(4位数字)
		String sRand="";
		String[] selectChar = new String[]{"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","J","K","M","N","P","Q","R","S","T","U","V","W","X","Y","Z"};//所有候选组成验证码的字符，当然也可以用中文的
		String[] fontName = new String[]{"Times New Roman","Arial","宋体","黑体"};
		for (int i=0;i<4;i++){
			Random rm = new Random();
			int charIndex = (int) Math.floor(Math.random()*32);
			String rand=selectChar[charIndex];
			sRand+=rand;
			//设置字体
			g2.setFont(new Font(fontName[rm.nextInt(fontName.length)],Font.PLAIN,18));
			// 将认证码显示到图象中
			g2.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			int yy = rm.nextInt(6);
			g2.rotate(0,0,yy);
			g2.drawString(rand,14*i+8,17+yy);
		
		}
		if (sessionName==null||sessionName.length()==0) {
			sessionName = ConstWords.ValidCodeTempSession;
		}
		// 将认证码存入SESSION
		request.getSession().setAttribute(sessionName,sRand);

		// 图象生效
		g2.dispose();

		// 输出图象到页面
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	private Color getRandColor(int fc,int bc){//给定范围获得随机颜色
		Random random = new Random();
		if(fc>255) fc=255;
		if(bc>255) bc=255;
		int r=fc+random.nextInt(bc-fc);
		int g=fc+random.nextInt(bc-fc);
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}

	public void init() throws ServletException {
	}

}
