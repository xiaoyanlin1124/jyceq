package com.jy.entity.system.device;

import java.util.Date;
import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;

@Alias("PushDevice")
public class PushDevice extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String deviceId;
	private String deviceModel;
	private Integer isValid;
	private Integer lockOnPush;
	private Integer alarmPush;
	private Integer powerLowPush;
	private Integer hijackPush;
	private Integer pushSum;
	private String pushConfig;
	private String pushPlatform;
	private String pushToken;
	private Date createTime;
	private Date updateTime;
	private String language;
	private String description;
	
	private String keyWord;

	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getLockOnPush() {
		return lockOnPush;
	}

	public void setLockOnPush(Integer lockOnPush) {
		this.lockOnPush = lockOnPush;
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

	public Integer getHijackPush() {
		return hijackPush;
	}

	public void setHijackPush(Integer hijackPush) {
		this.hijackPush = hijackPush;
	}

	public Integer getPushSum() {
		return pushSum;
	}

	public void setPushSum(Integer pushSum) {
		this.pushSum = pushSum;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public String getPushConfig() {
		return pushConfig;
	}

	public void setPushConfig(String pushConfig) {
		this.pushConfig = pushConfig;
	}

	public String getPushPlatform() {
		return pushPlatform;
	}

	public void setPushPlatform(String pushPlatform) {
		this.pushPlatform = pushPlatform;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	@Override
	public String toString() {
		return "PushDevice [id=" + id + ", deviceId=" + deviceId + ", deviceModel=" + deviceModel + ", isValid="
				+ isValid + ", lockOnPush=" + lockOnPush + ", alarmPush=" + alarmPush + ", powerLowPush=" + powerLowPush
				+ ", hijackPush=" + hijackPush + ", pushSum=" + pushSum + ", pushConfig=" + pushConfig
				+ ", pushPlatform=" + pushPlatform + ", pushToken=" + pushToken + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", language=" + language + ", description=" + description
				+ ", keyWord=" + keyWord + "]";
	}

	
}
