package com.pinhuba.common.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.security.Base64;

/**
 * 接收邮件解析
 * 
 * @author peng.ning
 * @date Jul 14, 2010
 */
public class MailParse {
	private Logger log = Logger.getLogger(MailParse.class);

	private String dateformat = "yyyy-MM-dd HH:mm:ss";

	private MimeMessage mimeMessage = null;

	private StringBuffer bodyText = new StringBuffer();// 存放邮件正文

	private StringBuffer fileresult = new StringBuffer();// 附件

	private String saveFilePath = "";

	public MailParse(MimeMessage mimeMessage, String saveFilePath) {
		super();
		this.mimeMessage = mimeMessage;
		this.saveFilePath = saveFilePath;
		
		FileTool.checkDirAndCreate(saveFilePath);
	}

	/**
	 * 解析邮件
	 * 
	 * @param mimeMessage
	 * @return
	 */
	public MailReceiveBean getMailReceive() {
		MailReceiveBean mb = null;
		try {
			mb = new MailReceiveBean();
			mb.setMailFromPerson(getMailPerson());
			mb.setMailFrom(getMailFrom());
			mb.setMailTOAdder(getMailAddressByType(Message.RecipientType.TO));
			mb.setMailCCAdder(getMailAddressByType(Message.RecipientType.CC));
			mb.setMailBCCAdder(getMailAddressByType(Message.RecipientType.BCC));
			mb.setMailSubject(getSubject());
			mb.setSentDate(getSentDate());
			mb.setBodyText(getBodyText());
			mb.setReplySign(getReplySign());
			mb.setMessageId(getMessageId());
			mb.setIsRead(getMailIsRead());
			mb.setUrgent(getXPriority());
			mb.setAttachMent(getAttachMent());
		} catch (Exception e) {
			log.error("解析邮件异常" + e.getMessage());
			e.printStackTrace();
		}

		return mb;
	}

	/**
	 * 收件人名称 
	 * @return
	 * @throws Exception
	 */
	private String getMailPerson() throws Exception{
		InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
		String personal = address[0].getPersonal();
		String pname="";
		if (personal != null){
			String[] asd=mimeMessage.getHeader("From");
			if (asd!=null) {
				String tmp = asd[0];
				if(tmp.indexOf("=?x-unknown?")>=0){
					tmp = tmp.replaceAll("x-unknown","GBK"); // 将编码方式的信息由x-unkown改为gbk 
				}
				if (tmp.indexOf("=?")>=0) {
					pname = MimeUtility.decodeText(personal);
				}else{
					pname = new String(personal.getBytes("ISO-8859-1"),"GBK");
				}
				
				if (pname.indexOf("=?")>=0) {
					pname = MimeUtility.decodeWord(pname);
				}
			}
		}
		
		return pname;
	}
	
	/**
	 * 发件人地址
	 * 
	 * @param mimeMessage
	 * @return
	 * @throws Exception
	 */
	private String getMailFrom() throws Exception {
		InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
		String from = address[0].getAddress();
		String fromaddr="";
		if (from != null){
			fromaddr = MimeUtility.decodeText(from);
		}
		return fromaddr;
	}

	/**
	 * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
	 */
	private String getMailAddressByType(RecipientType type) throws Exception {
		String mailaddr = "";
		InternetAddress[] address = null;
		address = (InternetAddress[]) mimeMessage.getRecipients(type);
		if (address != null) {
			for (int i = 0; i < address.length; i++) {
				String email = address[i].getAddress();
				if (email == null)
					email = "";
				else {
					email = MimeUtility.decodeText(email);
				}
				String personal = address[i].getPersonal();
				if (personal == null)
					personal = "";
				else {
					personal = MimeUtility.decodeText(personal);
				}
				String compositeto = personal + "<" + email + ">";
				mailaddr += "," + compositeto;
			}
			if (mailaddr.length()>0) {
				mailaddr = mailaddr.substring(1);
			}
		}
		return mailaddr;
	}

	/**
	 * 获得邮件主题
	 */
	private String getSubject() throws Exception {
		String subject = mimeMessage.getSubject();
		String tmp="";
		if (subject != null) {
			String[] asd=mimeMessage.getHeader("Subject");
			if (asd!=null) {
				String sub = asd[0];
				if(sub.indexOf("=?x-unknown?")>=0){
					sub = sub.replaceAll("x-unknown","GBK"); // 将编码方式的信息由x-unkown改为gbk 
				}
				if (sub.indexOf("=?")>=0) {
					tmp = MimeUtility.decodeText(subject);
				}else{
					tmp = new String(subject.getBytes("ISO-8859-1"),"GBK");
				}
				
				if (tmp.indexOf("=?")>=0) {
					tmp = MimeUtility.decodeWord(tmp);
				}
			}
		}
		return tmp;
	}

	/**
	 * 获得邮件发送日期
	 */
	private String getSentDate() throws Exception {
		Date sentdate = mimeMessage.getSentDate();
		if (sentdate==null) {
			sentdate = new Date();
		}
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.format(sentdate);
	}

	/**
	 * 获得邮件正文内容
	 */
	private String getBodyText() throws Exception {
		getMailContent((Part) mimeMessage);
		return bodyText.toString();
	}

	/**
	 * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
	 */
	private void getMailContent(Part part) throws Exception {
		String contenttype = part.getContentType();
		int nameindex = contenttype.indexOf("name");
		String txt="";
		boolean conname = false;
		if (nameindex != -1)
			conname = true;
		if (part.isMimeType("text/plain") && !conname) {
			txt = part.getContent().toString();
		} else if (part.isMimeType("text/html") && !conname) {
			txt = part.getContent().toString();
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				getMailContent(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType("message/rfc822")) {
			getMailContent((Part) part.getContent());
		}
		
		if (txt.indexOf("=?")>=0) {
			txt = MimeUtility.decodeText(txt);
			if (txt.indexOf("=?")>=0) {
				txt = MimeUtility.decodeWord(txt);
			}
		}
		bodyText.append(txt);
	}

	/**
	 * 是否需要回执
	 * 
	 * @return
	 * @throws Exception
	 */
	private Integer getReplySign() throws Exception {
		Integer replysign = EnumUtil.OA_MAIL_RECEIPT.TWO.value;
		String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = EnumUtil.OA_MAIL_RECEIPT.ONE.value;
		}
		return replysign;
	}

	/**
	 * 获得此邮件的Message-ID
	 */
	private String getMessageId() throws Exception {
		return mimeMessage.getMessageID();
	}

	/**
	 * 是否已读(pop3 不支持flags状态，imap可以)
	 * 
	 * @return
	 * @throws Exception
	 */
	private Integer getMailIsRead() throws Exception {
		Integer isread = EnumUtil.OA_SMS_INBOX_ISREAD.two.value;
		return isread;
	}

	/**
	 * 邮件优先级
	 * 
	 * @return
	 * @throws Exception
	 */
	private Integer getXPriority() throws Exception {
		Integer xpri = EnumUtil.OA_CALENDER_LEVEL.four.value;
		String pris[] = mimeMessage.getHeader("X-Priority");
		if (pris != null && pris.length > 0) {
			String tmp = pris[0];
			xpri = EnumUtil.OA_CALENDER_LEVEL.one.value;
			if ("1".equals(tmp)) {
				xpri = EnumUtil.OA_CALENDER_LEVEL.one.value;
			} else if ("3".equals(tmp)) {
				xpri = EnumUtil.OA_CALENDER_LEVEL.two.value;
			} else if ("5".equals(tmp)) {
				xpri = EnumUtil.OA_CALENDER_LEVEL.three.value;
			}else{
				xpri = EnumUtil.OA_CALENDER_LEVEL.one.value;
			}
		}
		return xpri;
	}

	/**
	 * 判断此邮件是否包含附件
	 */
	@SuppressWarnings("unused")
	private boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
					attachflag = true;
				else if (mpart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) mpart);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1)
						attachflag = true;
					if (contype.toLowerCase().indexOf("name") != -1)
						attachflag = true;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	/**
	 * 【保存附件】
	 */
	private void saveAttachMent(Part part){
		String fileName = "";
		try {
			if (part.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mpart = mp.getBodyPart(i);
					String disposition = mpart.getDisposition();
					if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
						fileName = mpart.getFileName();
						if(fileName != null){
							if (fileName.indexOf("=?")>=0) {
								fileName = MimeUtility.decodeText(fileName);
							}else{
								fileName = new String(fileName.getBytes("ISO-8859-1"),"GB2312");
							}
							saveFile(fileName, mpart.getInputStream());
						}
					} else if (mpart.isMimeType("multipart/*")) {
						saveAttachMent(mpart);
					} else {
						fileName = mpart.getFileName();
						if (fileName != null){
							if(fileName.indexOf("=?")>=0) {
								fileName = MimeUtility.decodeText(fileName);
							}else{
								fileName = new String(fileName.getBytes("ISO-8859-1"),"GB2312");
							}
							saveFile(fileName, mpart.getInputStream());
						}
					}
				}
			} else if (part.isMimeType("message/rfc822")) {
				saveAttachMent((Part) part.getContent());
			}
		} catch (Exception e) {
			log.error("保存附件时异常:"+e.getMessage());
		}
	}

	// 返回一个当前时间的字符串表示
	private String getDateStr() {
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateStr = sdf.format(new java.util.Date());

		return dateStr;
	}
	
	
	// 分离完整文件名的文件名和后缀,并在中间加入字符串后返回
	private String addInNameExt(String fullName, String add) {
		String name = "";// 文件名
		String ext = ""; // 后缀
		char point = '.';
		int index = fullName.lastIndexOf(point);

		if (index != -1) {// 如果有后缀
			name = fullName.substring(0, index);
			ext = fullName.substring(index + 1);
		} else {
			name = fullName;
		}

		return name.trim() + "_" + add + point + ext;
	}
	
	/**
	 * 【真正的保存附件到指定目录里】
	 */
	private void saveFile(String fileName, InputStream in) throws Exception {
		if (fileName.indexOf("=?")>=0) {
			fileName = MimeUtility.decodeWord(fileName);
		}
		
		String newfile  = addInNameExt(fileName.replaceAll(",", ""), getDateStr());//文件重命名存放
				
		String fullPath = saveFilePath + newfile;
		
		File storefile = new File(fullPath);
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(storefile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
			fileresult.append(fileName+"|"+Base64.getBase64FromString(fullPath)+",");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存邮件附件异常:"+e.getMessage());
		} finally {
			if(bos!=null){
				bos.close();
			}
			if(bis!=null){
				bis.close();
			}
		}
	}
	/**
	 * 邮件附件
	 * @return
	 * @throws Exception
	 */
	private String getAttachMent() throws Exception{
		saveAttachMent((Part)mimeMessage);
		String tmp = fileresult.toString();
		if (tmp.length()>0) {
			if (tmp.charAt(tmp.length() - 1) == ',') {
				tmp = tmp.substring(0, tmp.length() - 1);
			}
		}
		return tmp;
	}
}
