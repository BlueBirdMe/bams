package com.pinhuba.common.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuth extends Authenticator {
	private String userName;
	private String userPwd;

	public SmtpAuth(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName,userPwd);
	}

}
