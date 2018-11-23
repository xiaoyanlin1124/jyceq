package com.jy.entity.system.device;

import java.util.Date;
import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;

@Alias("PushRecord")
public class PushRecord extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String deviceId;
	private String deviceModel;
	private Integer isValid;
	private Date createTime;
	private String userPushToken;
	private String pushKey;
	private String message;
	
	private String keyWord;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserPushToken() {
		return userPushToken;
	}

	public void setUserPushToken(String userPushToken) {
		this.userPushToken = userPushToken;
	}

	public String getPushKey() {
		return pushKey;
	}

	public void setPushKey(String pushKey) {
		this.pushKey = pushKey;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PushRecord [id=" + id + ", deviceId=" + deviceId + ", deviceModel=" + deviceModel + ", isValid="
				+ isValid + ", createTime=" + createTime + ", userPushToken=" + userPushToken + ", pushKey=" + pushKey
				+ ", message=" + message + ", keyWord=" + keyWord + "]";
	}

	
	
}
