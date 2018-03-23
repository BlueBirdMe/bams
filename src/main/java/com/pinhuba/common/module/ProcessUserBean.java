package com.pinhuba.common.module;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

public class ProcessUserBean {
	private User user;
	private Group group;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
}
