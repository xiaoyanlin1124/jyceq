package com.jy.controller.system.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.jy.common.device.CopyProperties;
import com.jy.common.device.DevRes;
import com.jy.common.device.IpAdrressUtil;
import com.jy.common.device.PushI18n;
import com.jy.common.user.AccountValidatorUtil;
import com.jy.common.user.SmsTool;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.user.UserCamearApp;
import com.jy.entity.system.user.UserIP;
import com.jy.entity.system.user.UserCamear;
import com.jy.service.system.user.UserCamearService;
import com.jy.service.system.user.UserIPService;

/*
 * 上线记录
 */
@Controller
@RequestMapping("/appLogin/")
public class AppLoginController extends BaseController<UserCamear>{
	
	   /* 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(AppLoginController.class);
	
//	private static final int loginErrorCount = 10 ; //APP登陆错误次数
	
	@Autowired
	public UserCamearService userCamearService;
	
	@Autowired
	public UserIPService userIPService;

	/**
	 * 注册入口，发送验证码
	 * @param userCamear
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="reg", method=RequestMethod.POST)
	@ResponseBody
	public DevRes AppReg(UserCamear userCamear ,HttpServletRequest request){
		DevRes ar=getDevRes();
//		logger.error(e.toString(),e);
//		LOG.error(userCamear.toString());
		try {
			if( AccountValidatorUtil.isEmail(userCamear.getEmail()) || AccountValidatorUtil.isPhone(userCamear.getPhone()))
			{
				List<UserCamear> result=userCamearService.findemailphone(userCamear);
				if(result.isEmpty()){
					List<UserCamear> result2=userCamearService.find(userCamear);
					if(result2.isEmpty()){
						UserCamear userCamear2 = sendSMS(userCamear);
						userCamear2.setId(get32UUID());
						userCamear2.setCreateTime(getDate());	
						userCamearService.insert(userCamear2);
						inputIP(getIPAdd(request) , userCamear2.getId() );
					}else if((getDate()).getTime() - result2.get(0).getVerification_send_Time().getTime()  > SmsTool.verificationaInterval ){
						 result2.get(0).setLanguage(userCamear.getLanguage());
						UserCamear userCamear2 = sendSMS(result2.get(0));
						userCamearService.update(userCamear2);
						inputIP(getIPAdd(request) , userCamear2.getId());
					}
					ar.setSucceedMsg(PushI18n.getI18nString( Const.sendsuccessful , userCamear.getLanguage()) , Const.sendsuccessful_VALUE );
				}else{
//					LOG.debug("已经注册过"+userCamear.getEmail());
					ar.setFailMsg( PushI18n.getI18nString( Const.Registered , userCamear.getLanguage()),Const.Registered_VALUE);
				}
			}
			inputIP(getIPAdd(request));
//				ar.setSucceedMsg(Const.POST_SUCCEED);
			} catch (Exception e) {
//				logger.error(e.toString(),e);
				ar.setFailMsg( PushI18n.getI18nString( Const.sendFail , userCamear.getLanguage()),Const.sendFail_VALUE);
			}
		return ar;
	}
	
	/**
	 * 注册入口，验证码验证
	 * @param userCamear
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="regConfirm", method=RequestMethod.POST)
	@ResponseBody
	public DevRes AppRegConfirm(UserCamear userCamear  ,HttpServletRequest request ){
		DevRes ar=getDevRes();
//		LOG.debug("-----------------"+userCamear.toString());
		try {
				if((AccountValidatorUtil.isEmail(userCamear.getEmail()) 
						|| AccountValidatorUtil.isPhone(userCamear.getPhone()))
							&& userCamear.getVerificationCode()!=null?userCamear.getVerificationCode().length() == SmsTool.verificationDigits:false )
				{
					List<UserCamear> result = userCamearService.find(userCamear);
	//				System.out.println(result.get(0).getIs_enabled().toString());
					if(!result.isEmpty() && userCamear.getPassword() != null &&  result.get(0).getIs_enabled() == null  ){
						if( (result.get(0).getVerification_send_Time().getTime()+SmsTool.verificationTime ) > (getDate()).getTime() ){
							if(result.get(0).getVerificationCode().equals(userCamear.getVerificationCode())){//正确
//								LOG.debug("OKOKOKOKOK"+userCamear.getEmail());
								//SHA1加密
								String sha1Password=DigestUtils.sha256Hex(userCamear.getPassword());
								userCamear.setId(result.get(0).getId());
								userCamear.setPassword(sha1Password);
								userCamear.setIs_enabled(1);
								userCamear.setUpdateTime(getDate());//authorizedCodeDay
								userCamear.setCreateTime(result.get(0).getCreateTime());
						        Calendar c = Calendar.getInstance();
						        c.setTime(getDate());
						        c.add(Calendar.DAY_OF_MONTH,  SmsTool.authorizedCodeDay);// 今天+最长时间
								userCamear.setSafe_key_expire(c.getTime());
								userCamear.setVerificationCode(null);
								userCamear.setSafe_key_value(get32UUID());
								userCamear.setNickname("CEQ_"+SmsTool.verifierCode(8));
								userCamearService.update(userCamear);
								inputIP(getIPAdd(request) , userCamear.getId() );
								ar.setSucceedMsg(PushI18n.getI18nString( Const.regSuccess , userCamear.getLanguage()) , Const.regSuccess_VALUE  );
							}else{//验证码错误
								ar.setFailMsg( PushI18n.getI18nString( Const.verificationError , userCamear.getLanguage()),Const.verificationError_VALUE);
							}
						}else{//验证码超时
							ar.setFailMsg( PushI18n.getI18nString( Const.verificationTimeout , userCamear.getLanguage()), Const.verificationTimeout_VALUE);
						}
					}else{//没找到数据
						ar.setFailMsg( PushI18n.getI18nString( Const.error , userCamear.getLanguage()), Const.error_VALUE);
					}
				}
				inputIP(getIPAdd(request));
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg( PushI18n.getI18nString( Const.error , userCamear.getLanguage()), Const.error_VALUE);
//				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}
	
	/**
	 * APP登陆，可获得受权码，每次登陆受权码更新
	 * @param userCamear
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value={"login","checkLogin"}, method=RequestMethod.POST)
	@ResponseBody
	public DevRes ApploginConfirm(UserCamear userCamear  ,HttpServletRequest request){
		DevRes ar=getDevRes();
//		LOG.warn(userCamear.toString());
		ar.setFailMsg(Const.DATA_FAIL ,Const.DATA_FAIL_VALUE );
		try {
			userCamear.setLogin_ip(getIPAdd(request));
			
			UserCamear user = LoginConfirm(userCamear,userCamearService);
			
			if( user!=null ){
//				LOG.warn("-----------------------------"+user.toString());
				user.setLogin_ip(getIPAdd(request));
					if(userCamear.getPassword() != null){
						user.setSafe_key_value(get32UUID());
						user.setSafe_key_expire(getAuthorizedTime());
					}
					userCamearService.update(user);
					
					UserCamearApp userAppLogin = new UserCamearApp();//APP登际后发送用户名，key，等消息
					CopyProperties.copyPropertiesIgnoreNull(user, userAppLogin);
					ar.setObj(userAppLogin);
					ar.setSucceedMsg(Const.loginSuccess , Const.loginSuccess_VALUE);
			}

		}catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg( PushI18n.getI18nString( Const.error , userCamear.getLanguage()), Const.error_VALUE);
		}
		return ar;
	}

	
	/**
	 * 查找第三方登陆写入信息，支持facebook QQ 微信
	 * POST发送数据
	 * 参数 openid access_token nickname access_type language safe_key_value
	 * @param request
	 * @return {"res":1,"resMsg":"保存成功","obj":null}
	 */
	@RequestMapping(value="thirdLogin", method=RequestMethod.POST)
	@ResponseBody
	public DevRes ThirdLogin(UserCamear o ,HttpServletRequest request){
		DevRes ar=getDevRes();
		ar.setFailMsg(Const.DATA_FAIL ,Const.DATA_FAIL_VALUE );
		//LOG.info( o.toString() );
			try {
				if( o.getOpenid() == null || o.getAccess_token() == null ){
					inputIP( IpAdrressUtil.getIpAdrress(request)  );
					return null ;
				}
				
				List<UserCamear> list=userCamearService.find(o);
				o.setSafe_key_value(get32UUID());
				o.setSafe_key_expire(getAuthorizedTime());
				//这里以后要做个安全检测，即检测微信QQfacebook用户openid和accessTokey是否合法
				UserCamearApp userAppLogin = new UserCamearApp();//APP登际后发送用户名，key，等消息
				if( list.isEmpty()){
					CopyProperties.copyPropertiesIgnoreNull(o, userAppLogin);
					o.setId(get32UUID());
					o.setIs_enabled(1);
					o.setCreateTime(getDate());
					userCamearService.insert(o);
					inputIP( IpAdrressUtil.getIpAdrress(request) , o.getId() );
				}else{
					list.get(0).setAccess_token(o.getAccess_token());
					list.get(0).setSafe_key_value(get32UUID());
					list.get(0).setSafe_key_expire(getAuthorizedTime());
					CopyProperties.copyPropertiesIgnoreNull(list.get(0), userAppLogin);
					userCamearService.update(list.get(0));
					inputIP( IpAdrressUtil.getIpAdrress(request)  , list.get(0).getId());
				}
				ar.setObj(userAppLogin);
				ar.setSucceedMsg( Const.ThirdSuccess , Const.ThirdSuccess_VALUE);
			//	LOG.info( o.toString() );
			} catch (Exception e) {
				logger.error(e.toString(),e);
				//ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
	
	/**
	 * 验证用户登陆
	 * @param userCamear
	 */
	public UserCamear LoginConfirm( UserCamear userCamear , UserCamearService usercamearService ){
		
		try {
			UserIP userip = new UserIP();
			userip.setIp(userCamear.getLogin_ip());
			if( getEmailPhoneOpenid(userCamear)!=null ){
				
				List<UserCamear> result = usercamearService.find(userCamear);
				if( !result.isEmpty() && CheckUser(userCamear,result.get(0)) ){
					userip.setUser_id(result.get(0).getId());
					inputIP(userip);
					return result.get(0);
				}
			}
			inputIP(userip);
		}catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return null;
	}
		
	
	/**
	 * 发验证码
	 * @param userCamear
	 * @return
	 */
	private UserCamear sendSMS( UserCamear userCamear  )
	{
		String verifier = SmsTool.verifierCode();
		userCamear.setVerification_send_Time(getDate());
		userCamear.setVerificationCode(verifier);
		if( AccountValidatorUtil.isEmail(userCamear.getEmail()) ){
			SmsTool.snedMial(userCamear.getEmail() , 
					userCamear.getLanguage() != null?new Locale( (String) userCamear.getLanguage().subSequence(0,2) , 
							(String) userCamear.getLanguage().subSequence(3,5) ):null,  verifier );
		}else{
			try {
				SmsTool.sendSms(userCamear.getPhone() , 
						userCamear.getLanguage() != null?new Locale( (String) userCamear.getLanguage().subSequence(0,2) , 
								(String) userCamear.getLanguage().subSequence(3,5) ):null,  verifier );
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
		
		Calendar Verification_Time = Calendar.getInstance();
		Verification_Time.setTimeInMillis((getDate()).getTime() + SmsTool.verificationTime ); 
		Date vdate = Verification_Time.getTime();
		userCamear.setVerification_Time(vdate);
		return userCamear;
	}
	
	/**
	 * 工具类，用于计算受权key到期时间
	 * @return
	 */
	private Date getAuthorizedTime(){
        Calendar c = Calendar.getInstance();
        c.setTime(getDate());
        c.add(Calendar.DAY_OF_MONTH,  SmsTool.authorizedCodeDay);// 今天+最长时间
		return c.getTime();
	}
	
	
	/**
	 * 一些工具
	 */

	/**
	 * 判断合法登陆信息
	 * @param o 收到的字段
	 * @param targer 数据库字段
	 * @return
	 */
	 private  boolean CheckUser(UserCamear o, UserCamear targer ){
		 
		 try{
			 boolean EmailPhoneOpenidcheck = false;
			 boolean Safe_key_valuecheck = false;
			 boolean Safe_key_expirecheck = false;
			 boolean Passwordcheck = false;
			 boolean Access_tokencheck = false;
			 
			 if(getEmailPhoneOpenid(o)!=null && getEmailPhoneOpenid(targer)!=null)EmailPhoneOpenidcheck = getEmailPhoneOpenid(o).equalsIgnoreCase(getEmailPhoneOpenid(targer));
			 if(o.getSafe_key_value()!=null)Safe_key_valuecheck = o.getSafe_key_value().equalsIgnoreCase(targer.getSafe_key_value());
			 if(o.getPassword()!=null)Passwordcheck = targer.getPassword().equals(DigestUtils.sha256Hex(o.getPassword()));
			 if( targer.getSafe_key_expire()!=null )Safe_key_expirecheck = (targer.getSafe_key_expire().getTime() > (getDate()).getTime())?true:false;
			 if(o.getAccess_token()!=null)Access_tokencheck = o.getAccess_token().equalsIgnoreCase(targer.getAccess_token());
			 
//			 LOG.info(EmailPhoneOpenidcheck+" "+ Safe_key_valuecheck + " "+ Safe_key_expirecheck + " "+ Passwordcheck +" "+ Access_tokencheck);
			 
				 if( getEmailPhoneOpenid(o)!=null 
						 && EmailPhoneOpenidcheck
						 	&& (( Safe_key_valuecheck && Safe_key_expirecheck)
							 		|| Passwordcheck
							 		  || Access_tokencheck )
							 			){
						 					return true;
				 }
		} catch (Exception e) {
			//logger.error(e.toString(),e);
		}
		 return false;
	 }
		//判断用户名是邮箱还是电话号、“"aa="";
	 private  String getEmailPhoneOpenid(UserCamear u ){
			if( u.getEmail()!=null ){
				return u.getEmail();
			}if( u.getPhone() !=null ){
				return u.getPhone();
			}if( u.getOpenid() !=null ){
				return u.getOpenid();
			}
			return null;
		}
	
	 /**
	  * IP 录入
	  */
	 public boolean inputIP(String IP){
		 UserIP userip = new UserIP();
		 userip.setIp(IP);
		 return inputIP(userip);
	 }
	 
	 public boolean inputIP(String IP, String user_id){
		 UserIP userip = new UserIP();
		 userip.setUser_id(user_id);
		 userip.setIp(IP);
		 return inputIP(userip);
	 }
	 
	 public boolean inputIP(UserIP  ip){
		 boolean ar= false;
		 try{
			 LOG.warn( "ipadd="+ ip.getIp());
			 if( ip.getIp() != null ){
				  List<UserIP> result = userIPService.find(ip);
				  if( result.isEmpty() ){
					  UserIP userIP = new UserIP();
					  userIP.setId(get32UUID());
					  userIP.setIp(ip.getIp());
					  userIP.setCreate_ip(ip.getIp());
					  userIP.setCreateTime(getDate());
					  userIP.setUpdateTime(getDate());
					  userIP.setUser_id(ip.getUser_id());
					  userIP.setCount(1);
					  userIPService.insert(userIP);
				  }else{
					  result.get(0).setIp(ip.getIp());
					  result.get(0).setUpdateTime(getDate());
					  result.get(0).setCount( result.get(0).getCount()+1);
					  userIPService.update( result.get(0));
				  }
				  ar = true;
			 } 
		} catch (Exception e) {
			//logger.error(e.toString(),e);
		}
		 return ar;
	 }
	 
	 
	 class newpassword{
		 private String newpass ;

		public String getNewpass() {
			return newpass;
		}

		public void setNewpass(String newpass) {
			this.newpass = newpass;
		}
		 
	 }

	
}

