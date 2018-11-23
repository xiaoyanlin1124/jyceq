package com.jy.common.utils.base;

/**
 * 全局静态资源：
 * 正数比示成功，负数表示失败或错误
*/
public class Const {
	
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";            
	public static final String SESSION_MENULIST = "menuList";			//当前菜单
	/**
	 *数据库配置文件位置
	 */
	public static final String DB_CONFIG="/spring/dbconfig.properties";
	/**
	 *邮箱配置文件位置
	 */
	public static final String EMAIL_CONFIG="/mail.properties";
	public static final String EMAIL_CONFIG2="/mail2.properties";
	/**
	 *微信配置文件位置
	 */
	public static final String WEIXIN_CONFIG="/weixin/mp.properties";
	/**
	 *上传配置文件位置
	 */
	public static final String UPLOAD_CONFIG="upload.properties";
	/**
	 *没有权限返回的URL
	 */
	public static final String NO_AUTHORIZED_URL="/system/noAuthorized";//没有权限返回的URL
	/**
	 *没有权限返回中文说明
	 */
	public static final String NO_AUTHORIZED_MSG="当前角色没有权限";//
	/**
	 *返回值 没有权限 100
	 */
	public static final int NO_AUTHORIZED=100;
	/**
	 *返回值 成功(1)
	 */
	public static final int SUCCEED = 1;
	/**
	 *返回值 失败(0)
	 */
	public static final int FAIL = 0;
	/**
	 *菜单类型 (1)
	 */
	public static final String RESOURCES_TYPE_MENU = "1";
	/**
	 *功能类型(2)
	 */
	public static final String RESOURCES_TYPE_FUNCTION = "2";
	/**
	 *按钮类型(3)
	 */
	public static final String RESOURCES_TYPE_BUTTON = "3";
	/**
	 *保存成功
	 */
	public static final String SAVE_SUCCEED = "保存成功";
	public static final int SAVE_SUCCEED_VALUE  = 11;
	/**
	 *保存失败
	 */
	public static final String SAVE_FAIL = "保存失败";
	public static final int SAVE_FAIL_VALUE  = -11;
	/**
	 *删除成功
	 */
	public static final String DEL_SUCCEED = "删除成功";
	public static final int DEL_SUCCEED_VALUE  = 12;
	/**
	 *删除失败
	 */
	public static final String DEL_FAIL = "删除失败";
	public static final int DEL_FAIL_VALUE  = -12;
	/**
	 *修改成功
	 */
	public static final String UPDATE_SUCCEED = "修改成功";
	public static final int UPDATE_SUCCEED_VALUE  = 13;
	/**
	 *修改失败
	 */
	public static final String UPDATE_FAIL = "修改失败";
	public static final int UPDATE_FAIL_VALUE  = -13;
	/**
	 *数据获取成功
	 */
	public static final String DATA_SUCCEED = "数据获取成功";
	public static final int DATA_SUCCEED_VALUE  = 14;
	/**
	 *数据获取失败
	 */
	public static final String DATA_FAIL = "数据获取失败";
	public static final int DATA_FAIL_VALUE  = -14;
	/**
	 *设备信息收集
	 */
	public static final String DEVICE_POST = "接收设备的数据";
	public static final int DEVICE_POST_VALUE  = 15;
	/**
	 *设备信息收集数据有误
	 */
	public static final String DEVICE_POST_FAIL = "接收设备的数据错误";
	public static final int DEVICE_POST_FAIL_VALUE  = -15;
	/**
	 *IP获取地区失败
	 */
	public static final String IP_CITY_FAIL = "IP获取地区失败";
	public static final int IP_CITY_FAIL_VALUE  = -16;
	/**
	 * post接收成功
	 */
	public static final String POST_SUCCEED = "successfully";
	public static final int POST_SUCCEED_VALUE  = 17;
	/**
	 *push接收成功
	 */
	public static final String PUSH_SUCCEED = "successfully";
	public static final int PUSH_SUCCEED_VALUE  = 18;
	/**
	 *push发送失败
	 */
	public static final String PUSH_FAIL = "push fail";
	public static final int PUSH_FAIL_VALUE  = -18;
	/**
	 *APP注册成功
	 */
	public static final String APPREG_SUCCEED = "reg success";
	public static final int APPREG_SUCCEED_VALUE  = 19;
	/**国际化文本
	 *发送成功 
	 */
	public static final String sendsuccessful = "sendsuccessful";
	public static final int sendsuccessful_VALUE  = 20 ;
	/**国际化文本
	 *帐号已注册过
	 */
	public static final String Registered = "Registered";
	public static final int Registered_VALUE  = 21 ;
	/**国际化文本
	 *发送失败
	 */
	public static final String sendFail = "sendFail";
	public static final int sendFail_VALUE  = -22 ;
	/**国际化文本
	 *验证码错误
	 */
	public static final String verificationError = "verificationError";
	public static final int verificationError_VALUE  = -23 ;
	/**国际化文本
	 *验证码超时 
	 */
	public static final String verificationTimeout = "verificationTimeout";
	public static final int verificationTimeout_VALUE  = -24 ;
	/**国际化文本
	 *错误
	 */
	public static final String error = "error";
	public static final int error_VALUE  = -25 ;
	/**国际化文本
	 *注册成功
	 */
	public static final String regSuccess = "regSuccess";
	public static final int regSuccess_VALUE  = 26 ;
	/**国际化文本
	 *密码错误
	 */
	public static final String passwordFail = "passwordFail";
	public static final int passwordFail_VALUE  = -27 ;
	/**国际化文本
	 *登陆成功
	 */
	public static final String loginSuccess = "loginSuccess";
	public static final int loginSuccess_VALUE  = 28 ;
	/**国际化文本
	 *受权失败
	 */
	public static final String authorizedFail = "authorizedFail";
	public static final int authorizedFail_VALUE  = -28 ;
	
	/**
	 *三方受权成功
	 */
	public static final String ThirdSuccess = "ThirdSuccess";
	public static final int ThirdSuccess_VALUE  = 29 ;
	
	/**
	 *增加设备
	 */
	public static final String AddDeviceSuccess = "AddDeviceSuccess";
	public static final int AddDeviceSuccess_VALUE  = 30 ;
	/**
	 *没有设备数据
	 */
	public static final String NO_DEVICE ="NoDevice";
	public static final int NO_DEVICE_VALUE  = 31;

}
