package com.pinhuba.common.email;

import java.io.File;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.pojo.OaNetmailSet;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

/**
 * 邮件接收工具类
 * @author peng.ning
 * @date   Jul 13, 2010
 */
public class MailTool {
	private Logger log = Logger.getLogger(this.getClass());
	
	private Set<String> toAdderSet = new HashSet<String>();//收件地址
	
	private String mailTitle;//主题
	
	private String mailContent;//内容
	
	private Integer mailPrj;//优先级 1、紧急 3、普通 5、缓慢 
	
	private Integer mailRec;//回执 1、要求回执 2、不用回执
	
	private OaNetmailSet oaNetmailSet;//邮件服务器
	
	private String protocol="pop3";
	
	private String recFilePath;//接收邮件附件存储位置
	
	
	private Map<String, String> files = new HashMap<String, String>();//附件

	public Set<String> getToAdderSet() {
		return toAdderSet;
	}

	public void setToAdderSet(Set<String> toAdderSet) {
		this.toAdderSet = toAdderSet;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getRecFilePath() {
		return recFilePath;
	}

	public void setRecFilePath(String recFilePath) {
		this.recFilePath = recFilePath;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public Integer getMailPrj() {
		return mailPrj;
	}

	public void setMailPrj(Integer mailPrj) {
		this.mailPrj = mailPrj;
	}

	public Integer getMailRec() {
		return mailRec;
	}

	public void setMailRec(Integer mailRec) {
		this.mailRec = mailRec;
	}

	public OaNetmailSet getOaNetmailSet() {
		return oaNetmailSet;
	}

	public void setOaNetmailSet(OaNetmailSet oaNetmailSet) {
		this.oaNetmailSet = oaNetmailSet;
	}

	public Map<String, String> getFiles() {
		return files;
	}

	public void setFiles(Map<String, String> files) {
		this.files = files;
	}

	public MailTool() {
		super();
	}

	
	
	public MailTool(OaNetmailSet oaNetmailSet) {
		super();
		this.oaNetmailSet = oaNetmailSet;
	}

	public MailTool(Set<String> toAdderSet, String mailTitle, String mailContent, Integer mailPrj, Integer mailRec, OaNetmailSet oaNetmailSet, Map<String, String> files) {
		super();
		this.toAdderSet = toAdderSet;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
		this.mailPrj = mailPrj;
		this.mailRec = mailRec;
		this.oaNetmailSet = oaNetmailSet;
		this.files = files;
	}
	
	/**
	 * 发送邮件
	 * @return 是否成功
	 */
	public boolean sendMail() throws Exception{
		if (toAdderSet.size()==0) {
			return false;
		}
		String pwd = Base64.getStringFromBase64(oaNetmailSet.getOaNetmailPassword());
		boolean bl = false;
		log.info("发送邮件开始:"+UtilWork.getNowTime());
		Properties props = new Properties();
		String name = oaNetmailSet.getOaNetmailUser();
		String smtp = oaNetmailSet.getOaNetmailSmtp();
		props.put("mail.smtp.host", smtp);// 指定SMTP服务器
		props.put("mail.smtp.auth", oaNetmailSet.getOaNetmailIsverify());// 指定是否需要SMTP验证
		String prot = oaNetmailSet.getOaNetmailSmtpProt();
		if (oaNetmailSet.getOaNetmailIsSSL()==EnumUtil.SYS_ISACTION.Vaild.value) {//启用SSL
			if(prot==null||prot.length()==0){
				prot = "465";
			}
			if (name.toLowerCase().indexOf("hotmail")>0 || name.toLowerCase().indexOf("msn")>0 || smtp.toLowerCase().indexOf("live")>0) {
				this.handleTLSByPop(props, prot);
			}else{
				this.handleSSLBySmtp(props, prot);
			}
		}else{
			if(prot==null||prot.length()==0){
				prot = "25";
			}
			props.put("mail.smtp.port", prot);//指定连接端口
		}
		SmtpAuth auth = new SmtpAuth(name,pwd);
		
		Transport transport = null;
		Session mailSession = Session.getInstance(props,auth);
		
		//mailSession.setDebug(true);//输出跟踪日志
		try {
			MimeMessage message = new MimeMessage(mailSession);
			
			message.setFrom(new InternetAddress(oaNetmailSet.getOaNetmailFrom(),MimeUtility.encodeText(oaNetmailSet.getOaNetmailName(), "utf-8", "B")));// 发件人
			message.setHeader("X-Priority", String.valueOf(mailPrj==null?"3":mailPrj));//邮件优先级
			if (mailRec!=null&&mailRec==EnumUtil.OA_MAIL_RECEIPT.ONE.value) {
				message.setHeader("Disposition-Notification-To","1");//要求阅读回执
			}
			int c = 0; 
			InternetAddress[] address = new InternetAddress[toAdderSet.size()];
			Iterator<String> it = toAdderSet.iterator();
			while (it.hasNext()) {
				String mail = (String) it.next();
				address[c] = new InternetAddress(mail);
				c++;
			}
			message.addRecipients(Message.RecipientType.TO,address);//收件人
			
			message.setSentDate(new Date());//发送日期
			
			message.setSubject(MimeUtility.encodeText(mailTitle, "utf-8", "B"));// 邮件主题
			Multipart mm = new MimeMultipart();
			BodyPart md = new MimeBodyPart();
			md.setContent(mailContent,"text/html;charset=gb2312");//
			mm.addBodyPart(md);// 邮件内容
			if(files.size()>0){
				// 邮件附件
				Set<String> keyset = files.keySet();
				Iterator<String> fit = keyset.iterator();
				while (fit.hasNext()) {
					String fname = (String) fit.next();
					BodyPart mdp = new MimeBodyPart();
					File file = new File(files.get(fname));
					FileDataSource fds = new FileDataSource(file);
					DataHandler dh = new DataHandler(fds);
					String ffname =  new String(fname.getBytes("gb2312"), "ISO8859-1");// 处理文件名是中文的情况
					mdp.setFileName(ffname);// 可以和原文件名不一致,但最好一样
					mdp.setDataHandler(dh);
					mm.addBodyPart(mdp);
				}
			}
			
			message.setContent(mm);
			message.saveChanges();
			transport = mailSession.getTransport("smtp");
			transport.connect(oaNetmailSet.getOaNetmailSmtp(), oaNetmailSet.getOaNetmailUser(), pwd);
			transport.sendMessage(message, message.getAllRecipients());

			transport.close();
			bl = true;
		} catch (Exception e) {
			bl = false;
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (transport != null) {
				transport.close();
			}
		}
		log.info("邮件发送成功..."+UtilWork.getNowTime());
		return bl;
	}
	
	/**
	 * 接收邮件
	 * @param dataList 数据库中已存放邮件
	 * @return 新邮件集合
	 * @throws Exception
	 */
	public ArrayList<MailReceiveBean> receiveMail(Set<String> dataList) throws Exception{
		ArrayList<MailReceiveBean> recList = new ArrayList<MailReceiveBean>();
		
		String pwd = Base64.getStringFromBase64(oaNetmailSet.getOaNetmailPassword());
		String prot = oaNetmailSet.getOaNetmailPop3Prot();
		int maxcount = oaNetmailSet.getOaNetmailCount();//最大接收邮件数量
		int tmpcount = 0;
		Properties props = new Properties();
		if (oaNetmailSet.getOaNetmailIsSSL()==EnumUtil.SYS_ISACTION.Vaild.value) {//启用SSL
			this.handleSSLByPop(props, oaNetmailSet.getOaNetmailPop3Prot());
			if(prot==null||prot.length()==0){
				prot = "995";
			}
		}else{
			if(prot==null||prot.length()==0){
				prot = "110";
			}
		}
		//创建session
		Session mailSession = Session.getInstance(props,null);
		//mailSession.setDebug(true);//输出跟踪日志
		Store store =null;
		Folder fd = null;
		try {
			URLName urln = new URLName(protocol,oaNetmailSet.getOaNetmailPop3(),Integer.parseInt(prot),null,oaNetmailSet.getOaNetmailUser(),pwd);
			
			//创建store，建立连接
			store  = mailSession.getStore(urln);
			store.connect();
			
			//打开收件箱
			fd = store.getFolder("INBOX");
			fd.open(Folder.READ_ONLY);
			if (fd instanceof POP3Folder) {
				POP3Folder pfd = (POP3Folder) fd;
				Message[] message = pfd.getMessages();
				FetchProfile fp=new FetchProfile();  //预提取
				fp.add(FetchProfile.Item.ENVELOPE);
				fp.add(FetchProfile.Item.FLAGS);   
		        fp.add("X-Mailer");
				pfd.fetch(message, fp);
				for (Message mg : message) {
					MimeMessage mimeMessage = (MimeMessage) mg;
					String uid = pfd.getUID(mimeMessage);
					if (!dataList.contains(uid)) {
						tmpcount++;
						MailParse mp = new MailParse(mimeMessage,recFilePath);
						MailReceiveBean mb = mp.getMailReceive();
						if (mb!=null) {
							mb.setMailUid(uid);
							recList.add(mb);
						}
					}
					if (tmpcount>=maxcount) {
						break;
					}
				}
			}else if (fd instanceof IMAPFolder) {
				IMAPFolder ipfd = (IMAPFolder) fd;
				Message[] message = ipfd.getMessages();
				for (Message mg : message) {
					MimeMessage mimeMessage = (MimeMessage) mg;
					String uid = String.valueOf(ipfd.getUID(mimeMessage));
					if (!dataList.contains(uid)) {
						tmpcount++;
						MailParse mp = new MailParse(mimeMessage,recFilePath);
						MailReceiveBean mb =  mp.getMailReceive();
						if (mb!=null) {
							mb.setMailUid(uid);
							recList.add(mb);
						}
					}
					if (tmpcount>=maxcount) {
						break;
					}
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}finally{
			if (fd!=null&&fd.isOpen()) {
				fd.close(true);
				fd=null;
			}
	    	if (store!=null) {
				store.close();
				store=null;
			}
		}
		
		return recList;
	}
	
	/**  
     * 处理安全套接层协议  
     */  
    private void handleSSLByPop(Properties props,String prot) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());   
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";   
        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);   
        props.setProperty("mail.pop3.socketFactory.fallback", "false");   
        props.setProperty("mail.pop3.port", prot);   
        props.setProperty("mail.pop3.socketFactory.port", prot);   //prot默认 995 imap 993
        //props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);   
        //props.setProperty("mail.imap.socketFactory.fallback", "false");   
        //props.setProperty("mail.imap.port", "993");   
        //props.setProperty("mail.imap.socketFactory.port", "993");   
    } 
    
    /**
     * hotmail msn
     * @param props
     * @param prot
     */
    private void handleTLSByPop(Properties props,String prot){
    	 Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());   
         final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";   
         //props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);   
         props.setProperty("mail.smtp.socketFactory.fallback", "false");   
         props.setProperty("mail.smtp.port", prot);   
         props.setProperty("mail.smtp.socketFactory.port", prot);   //prot默认 465
         props.setProperty("mail.smtp.starttls.enable","true");
    }
    
    /**  
     * 处理安全套接层协议  
     */  
    private void handleSSLBySmtp(Properties props,String prot) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());   
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";   
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);   
        props.setProperty("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.port", prot);   
        props.setProperty("mail.smtp.socketFactory.port", prot);   //prot默认 465
        props.setProperty("mail.smtp.starttls.enable","true");
    } 
    
    /**
     * 发送设置测试
     * @return
     * @throws Exception
     */
    public boolean mailSendTest() throws Exception{
    	boolean bl = false;
    	String pwd = oaNetmailSet.getOaNetmailPassword();
    	String name = oaNetmailSet.getOaNetmailUser();
    	String smtp = oaNetmailSet.getOaNetmailSmtp();
		log.info("测试邮件发送:"+UtilWork.getNowTime());
		Properties props = new Properties();
		props.put("mail.smtp.host", smtp);// 指定SMTP服务器
		props.put("mail.smtp.auth", oaNetmailSet.getOaNetmailIsverify());// 指定是否需要SMTP验证
		String prot = oaNetmailSet.getOaNetmailSmtpProt();
		if (oaNetmailSet.getOaNetmailIsSSL()==EnumUtil.SYS_ISACTION.Vaild.value) {//启用SSL
			if(prot==null||prot.length()==0){
				prot = "465";
			}
			if (name.toLowerCase().indexOf("hotmail")>0 || name.toLowerCase().indexOf("msn")>0 || smtp.toLowerCase().indexOf("live")>0) {
				this.handleTLSByPop(props, prot);
			}else{
				this.handleSSLBySmtp(props, prot);
			}
		}else{
			if(prot==null||prot.length()==0){
				prot = "25";
			}
			props.put("mail.smtp.port", prot);//指定连接端口
		}
		SmtpAuth auth = new SmtpAuth(name,pwd);
		
		Transport transport = null;
		Session mailSession = Session.getInstance(props,auth);
		try {
			transport = mailSession.getTransport("smtp");
			transport.connect(oaNetmailSet.getOaNetmailSmtp(), oaNetmailSet.getOaNetmailUser(), pwd);
			if (transport.isConnected()) {
				bl= true;
				transport.close();
			}
		} catch (Exception e) {
			bl= false;
		}finally {
			if (transport != null) {
				transport.close();
			}
		}
		return bl;
    }
    
    /**
     * 接收邮件测试
     * @return
     * @throws Exception
     */
    public boolean mailReceiveTest() throws Exception{
    	boolean bl = false;
    	log.info("测试邮件接收:"+UtilWork.getNowTime());
    	String pwd = oaNetmailSet.getOaNetmailPassword();
		String prot = oaNetmailSet.getOaNetmailPop3Prot();
		
		Properties props = new Properties();
		if (oaNetmailSet.getOaNetmailIsSSL()==EnumUtil.SYS_ISACTION.Vaild.value) {//启用SSL
			this.handleSSLByPop(props, oaNetmailSet.getOaNetmailPop3Prot());
			if(prot==null||prot.length()==0){
				prot = "995";
			}
		}else{
			if(prot==null||prot.length()==0){
				prot = "110";
			}
		}
		//创建session
		Session mailSession = Session.getInstance(props,null);
		Store store =null;
		try {
			URLName urln = new URLName(protocol,oaNetmailSet.getOaNetmailPop3(),Integer.parseInt(prot),null,oaNetmailSet.getOaNetmailUser(),pwd);
			//创建store，建立连接
			store  = mailSession.getStore(urln);
			store.connect();
			if (store.isConnected()) {
				bl = true;
				store.close();
			}
		} catch (Exception e) {
			bl = false;
		}finally{
	    	if (store!=null) {
				store.close();
				store=null;
			}
		}
		return bl;
    }
		
}
