package com.jy.entity.system.user;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("UserCamear")
public class UserCamear extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String	id;				//	  `id` varchar(32) NOT NULL COMMENT 'id',
	private String openid;
	private String	user_id;		//	  `user_id` varchar(32) DEFAULT NULL COMMENT '会员ID',
	private String	nickname;		//	  `name` varchar(64) DEFAULT NULL COMMENT '呢称',
	private String	password;			//	  `password` varchar(64) DEFAULT NULL COMMENT '密码',
	private String	salt;			//	  `salt` varchar(64) DEFAULT NULL COMMENT '盐',
	private Date	updateTime;	//	  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
	private Date	createTime;	//	  `createTime` datetime DEFAULT NULL COMMENT '创建',
	private Date	login_Time;	//	  `loginTime` datetime DEFAULT NULL COMMENT '最后登陆',
	private String	email;			//	  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
	private String	phone;		//	  `phone` varchar(64) DEFAULT NULL,
	private Integer sex;		//	  `sex` int(1) DEFAULT NULL COMMENT '性别',
	private Integer is_enabled;			//	  `is_enabled` int(1) DEFAULT NULL COMMENT '是否可用',
	private Date	locked_Time;		//	  `locked_Time` datetime DEFAULT NULL COMMENT '锁定日期',
	private Integer login_failure_count;		//	  `login_failure_count` int(2) DEFAULT NULL COMMENT '失败登陆次数',
	private String	login_ip;		//	  `login_ip` varchar(32) DEFAULT NULL COMMENT '最近登陆IP',
	private String	language;	//	  `language` varchar(32) DEFAULT NULL COMMENT '语言',
	private String	safe_key_value;		//	  `safe_key_value` varchar(128) DEFAULT NULL COMMENT '安全密钥',
	private Date	safe_key_expire;			//	  `safe_key_expire` datetime DEFAULT NULL COMMENT '密钥到期时间',
	private String	verificationCode;		//	  `verificationCode` varchar(32) DEFAULT NULL COMMENT '验证码',
	private Date	verification_Time;		//	  `verification_Time` datetime DEFAULT NULL COMMENT '验证码失效时间',
	private Date	verification_send_Time;	//	  `verification_send_Time` datetime DEFAULT NULL COMMENT '验证码可发送时间',
	private String	description;		//	  `description` varchar(128) DEFAULT NULL COMMENT '备注',
	private String access_token;//第三方登陆用户access_token
	private String access_type;//受权类型
	private String operation_password;
	private String	wxToken;			//	  `wxToken` varchar(128) DEFAULT NULL COMMENT '微信token',
	private String	qqToken;		//	  `qqToken` varchar(128) DEFAULT NULL COMMENT '微信token',
	private String	wbToken;		//	  `wbToken` varchar(128) DEFAULT NULL COMMENT '微博token',
	private String	fbToken;		//	  `fbToken` varchar(128) DEFAULT NULL COMMENT 'fackbook',
	private String	phoneBrand;
	private Integer	smsSum;
	private Integer	smsSurplus;
	
	private String keyWord;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLogin_Time() {
		return login_Time;
	}

	public void setLogin_Time(Date login_Time) {
		this.login_Time = login_Time;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public Date getLocked_Time() {
		return locked_Time;
	}

	public void setLocked_Time(Date locked_Time) {
		this.locked_Time = locked_Time;
	}

	public Integer getLogin_failure_count() {
		return login_failure_count;
	}

	public void setLogin_failure_count(Integer login_failure_count) {
		this.login_failure_count = login_failure_count;
	}

	public String getLogin_ip() {
		return login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSafe_key_value() {
		return safe_key_value;
	}

	public void setSafe_key_value(String safe_key_value) {
		this.safe_key_value = safe_key_value;
	}

	public Date getSafe_key_expire() {
		return safe_key_expire;
	}

	public void setSafe_key_expire(Date safe_key_expire) {
		this.safe_key_expire = safe_key_expire;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getVerification_Time() {
		return verification_Time;
	}

	public void setVerification_Time(Date verification_Time) {
		this.verification_Time = verification_Time;
	}

	public Date getVerification_send_Time() {
		return verification_send_Time;
	}

	public void setVerification_send_Time(Date verification_send_Time) {
		this.verification_send_Time = verification_send_Time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getAccess_type() {
		return access_type;
	}

	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}

	public String getOperation_password() {
		return operation_password;
	}

	public void setOperation_password(String operation_password) {
		this.operation_password = operation_password;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}

	public String getQqToken() {
		return qqToken;
	}

	public void setQqToken(String qqToken) {
		this.qqToken = qqToken;
	}

	public String getWbToken() {
		return wbToken;
	}

	public void setWbToken(String wbToken) {
		this.wbToken = wbToken;
	}

	public String getFbToken() {
		return fbToken;
	}

	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public Integer getSmsSum() {
		return smsSum;
	}

	public void setSmsSum(Integer smsSum) {
		this.smsSum = smsSum;
	}

	public Integer getSmsSurplus() {
		return smsSurplus;
	}

	public void setSmsSurplus(Integer smsSurplus) {
		this.smsSurplus = smsSurplus;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Override
	public String toString() {
		return "UserCamear [id=" + id + ", openid=" + openid + ", user_id=" + user_id + ", nickname=" + nickname
				+ ", password=" + password + ", salt=" + salt + ", updateTime=" + updateTime + ", createTime="
				+ createTime + ", login_Time=" + login_Time + ", email=" + email + ", phone=" + phone + ", sex=" + sex
				+ ", is_enabled=" + is_enabled + ", locked_Time=" + locked_Time + ", login_failure_count="
				+ login_failure_count + ", login_ip=" + login_ip + ", language=" + language + ", safe_key_value="
				+ safe_key_value + ", safe_key_expire=" + safe_key_expire + ", verificationCode=" + verificationCode
				+ ", verification_Time=" + verification_Time + ", verification_send_Time=" + verification_send_Time
				+ ", description=" + description + ", access_token=" + access_token + ", access_type=" + access_type
				+ ", operation_password=" + operation_password + ", wxToken=" + wxToken + ", qqToken=" + qqToken
				+ ", wbToken=" + wbToken + ", fbToken=" + fbToken + ", phoneBrand=" + phoneBrand + ", smsSum=" + smsSum
				+ ", smsSurplus=" + smsSurplus + ", keyWord=" + keyWord + "]";
	}



}
