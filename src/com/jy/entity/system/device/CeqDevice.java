package com.jy.entity.system.device;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("CeqDevice")
public class CeqDevice extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 设备id,作为唯一标识
     */
    //@TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 设备产品型号
     */
    private String deviceModel;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备对接的标识名,默认为admin
     */
    private String defaultName;
    
    /**
     * 设备密码
     */
    private String devicePassword;
    /**
     * 设备标记(DID)
     */
    private String deviceId;
    /**
     * 对应的用户id
     */
    private String userId;
    /**
     * 设备描述
     */
    private String description;
    /**
     * 开启锁推送功能,0未开启,1开启
     */
    //@TableField("lockOnPush")
    private Integer lockOnPush;
    /**
     * 开启推送劫持功能,0未开启,1开启
     */
    //@TableField("hijackPush")
    private Integer hijackPush;
    /**
     * 开启推送警报功能,0未开启,1开启
     */
    //@TableField("alarmPush")
    private Integer alarmPush;
    /**
     * 1表示开启电量推送,0表示不开启
     */
   // @TableField("powerLowPush")
    private Integer powerLowPush;
    /**
     * 推送的token
     */
    //@TableField("pushToken")
    private String pushToken;
    /**
     * 手机品牌,不同的品牌有不同的推送接口
     */
    //@TableField("pushPlatform")
    private String pushPlatform;
    /**
     * 设备录入时间
     */
    //@TableField("createTime")
    private Date createTime;
    /**
     * 修改时间
     */
    //@TableField("updateTime")
    private Date updateTime;
    /**
     * 国际化语言
     */
    private String language;

    /**
     * 设备发送信息的标识,0关门 1开门 2发生警报 3被劫持 4电量低
     */
    //@TableField(exist=false)
    private Integer flag;
    
    /**
     * 用户的安全密钥,用于添加设备时校验该用户是否合法
     */
    //@TableField(exist=false)
    private String safeKeyValue;
    
    /**
     * 用户的账户,手机或者邮箱
     */
    //@TableField(exist=false)
    private String userAccount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getDevicePassword() {
		return devicePassword;
	}

	public void setDevicePassword(String devicePassword) {
		this.devicePassword = devicePassword;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getSafeKeyValue() {
		return safeKeyValue;
	}

	public void setSafeKeyValue(String safeKeyValue) {
		this.safeKeyValue = safeKeyValue;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	@Override
	public String toString() {
		return "CeqDevice [id=" + id + ", deviceModel=" + deviceModel + ", deviceName=" + deviceName + ", defaultName="
				+ defaultName + ", devicePassword=" + devicePassword + ", deviceId=" + deviceId + ", userId=" + userId
				+ ", description=" + description + ", lockOnPush=" + lockOnPush + ", hijackPush=" + hijackPush
				+ ", alarmPush=" + alarmPush + ", powerLowPush=" + powerLowPush + ", pushToken=" + pushToken
				+ ", pushPlatform=" + pushPlatform + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", language=" + language + ", flag=" + flag + ", safeKeyValue=" + safeKeyValue + ", userAccount="
				+ userAccount + "]";
	}

}
