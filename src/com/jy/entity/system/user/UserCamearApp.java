package com.jy.entity.system.user;


public class UserCamearApp {
	
	private String	name;		//	  `name` varchar(64) DEFAULT NULL COMMENT '呢称',
	private String	email;			//	  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
	private String	phone;		//	  `phone` varchar(64) DEFAULT NULL,
//	private Integer sex;		//	  `sex` int(1) DEFAULT NULL COMMENT '性别',
	private String openid;
	private String	nickname;		//	  `name` varchar(64) DEFAULT NULL COMMENT '呢称',
	private String	safe_key_value;		//	  `safe_key_value` varchar(128) DEFAULT NULL COMMENT '安全密钥',
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
//	public Integer getSex() {
//		return sex;
//	}
//	public void setSex(Integer sex) {
//		this.sex = sex;
//	}
	public String getSafe_key_value() {
		return safe_key_value;
	}
	public void setSafe_key_value(String safe_key_value) {
		this.safe_key_value = safe_key_value;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


}
