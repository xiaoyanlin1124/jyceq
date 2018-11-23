package com.jy.entity.system.user;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("UserDevice")
public class UserDevice extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String device_model;
	private String device_name;
	private String admin_name;
	private String device_id;
	private String user_id;
	private String admin_password;
	private String description;
	private Integer lockOnPush;
	private Integer hijackPush;
	private Integer alarmPush;
	private Integer powerLowPush;
	private Integer SMSPush;//短讯推送
	private String pushToken;
	private String pushPlatform;
	private Date createTime;
	private Date updateTime;
	private String language;
	
	private String keyWord;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAdmin_password() {
		return admin_password;
	}
	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getLockOnPush() {
		return lockOnPush;
	}
	public void setLockOnPush(Integer lockOnPush) {
		this.lockOnPush = lockOnPush;
	}
	public Integer getHijackPush() {
		return hijackPush;
	}
	public void setHijackPush(Integer hijackPush) {
		this.hijackPush = hijackPush;
	}
	public Integer getAlarmPush() {
		return alarmPush;
	}
	public void setAlarmPush(Integer alarmPush) {
		this.alarmPush = alarmPush;
	}
	public Integer getPowerLowPush() {
		return powerLowPush;
	}
	public void setPowerLowPush(Integer powerLowPush) {
		this.powerLowPush = powerLowPush;
	}
	public String getPushToken() {
		return pushToken;
	}
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}
	public String getPushPlatform() {
		return pushPlatform;
	}
	public void setPushPlatform(String pushPlatform) {
		this.pushPlatform = pushPlatform;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Integer getSMSPush() {
		return SMSPush;
	}
	public void setSMSPush(Integer sMSPush) {
		SMSPush = sMSPush;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	@Override
	public String toString() {
		return "UserDevice [id=" + id + ", device_model=" + device_model + ", device_name=" + device_name
				+ ", admin_name=" + admin_name + ", device_id=" + device_id + ", user_id=" + user_id
				+ ", admin_password=" + admin_password + ", description=" + description + ", lockOnPush=" + lockOnPush
				+ ", hijackPush=" + hijackPush + ", alarmPush=" + alarmPush + ", powerLowPush=" + powerLowPush
				+ ", SMSPush=" + SMSPush + ", pushToken=" + pushToken + ", pushPlatform=" + pushPlatform
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", language=" + language + "]";
	}
	


}
