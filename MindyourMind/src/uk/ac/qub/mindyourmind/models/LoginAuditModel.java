package uk.ac.qub.mindyourmind.models;

import java.util.Calendar;

public class LoginAuditModel {

	private Calendar lastLoginTime;
	private Calendar lastOnlineRefresh;
	private Calendar numberOfLogins;
	
	public LoginAuditModel(Calendar lastLoginTime, Calendar lastOnlineRefresh, Calendar numberOfLogins) {
		this.lastLoginTime = lastLoginTime;
		this.lastOnlineRefresh = lastOnlineRefresh;
		this.numberOfLogins = numberOfLogins;
	}

	public Calendar getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Calendar lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Calendar getLastOnlineRefresh() {
		return lastOnlineRefresh;
	}

	public void setLastOnlineRefresh(Calendar lastOnlineRefresh) {
		this.lastOnlineRefresh = lastOnlineRefresh;
	}

	public Calendar getNumberOfLogins() {
		return numberOfLogins;
	}

	public void setNumberOfLogins(Calendar numberOfLogins) {
		this.numberOfLogins = numberOfLogins;
	}
	

}
