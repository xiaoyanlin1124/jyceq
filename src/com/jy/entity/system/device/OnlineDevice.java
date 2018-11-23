package com.jy.entity.system.device;


import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("OnlineDevice")
public class OnlineDevice extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String deviceId;
	private String deviceName;
	private String deviceModel;
	private String moduleId;
	private String description;
	private String ipAdd;
	private Integer isValid;
	private String cityAdd;
	private Date createTime;
	private Date updateTime;
	
	private String keyWord;
	/***
	private String id;
	private String paramKey;
	private String paramName;
	private String paramValue;
	private String description;
	private Integer isValid;
	private Date createTime;
	private Date updateTime;
	
	private String keyWord;
	*****/

	public String getId() {
		return id;
	}

	public String getIpAdd() {
		return ipAdd;
	}

	public void setIpAdd(String ipAdd) {
		this.ipAdd = ipAdd;
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getCityAdd() {
		return cityAdd;
	}

	public void setCityAdd(String cityAdd) {
		this.cityAdd = cityAdd;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	@Override
	public String toString() {
		return "OnlineDevice [id=" + id + ", deviceId=" + deviceId + ", deviceName=" + deviceName + ", deviceModel="
				+ deviceModel + ", moduleId=" + moduleId + ", description=" + description + ", ipAdd=" + ipAdd
				+ ", isValid=" + isValid + ", cityAdd=" + cityAdd + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", keyWord=" + keyWord + "]";
	}
	
}
