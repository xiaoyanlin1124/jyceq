package com.jy.controller.system.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.device.IpAdrressUtil;
import com.jy.common.utils.IPUtil;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.common.utils.webpage.PageData;
import com.jy.controller.base.BaseController;
import com.jy.controller.system.device.DeviceLoginPushController;
import com.jy.entity.system.dict.PushDict;
import com.jy.entity.system.log.LoginLog;
import com.jy.service.system.dict.PushDictService;
import com.jy.service.system.log.LoginLogService;

@Configuration  
@EnableAsync
@Controller
public class LoginController extends BaseController<Object>{
	
	   /* 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(DeviceLoginPushController.class);
	
	@Autowired
	private LoginLogService loginLogService;
	/*
	 * 暂时作为记录访问IP
	 * 正式使用删除
	 */
	@Autowired
	public PushDictService service;
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/loginIndex")
	@ResponseBody
	public ModelAndView toLogin(HttpServletRequest request)throws Exception{
		ModelAndView mv =  new ModelAndView();
		//暂时检测登录页面连接
		
		try{
			PushDict o = new PushDict();
			String remoteAddr = IpAdrressUtil.getIpAdrress(request);
			o.setParamKey(remoteAddr);
			o.setId(get32UUID());
			o.setCreateTime(new Date());	
			service.insert(o);
			getCityUpdate(o);
		} catch (Exception e) {
			LOG.error("login ip");
		}

		mv.setViewName("system/login/login");
		return mv;
	}
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/system_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> login()throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").split(",jy,");
		if(null != KEYDATA && KEYDATA.length == 3){
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码		
			String code = KEYDATA[2];
			String username = KEYDATA[0];
			String password  = KEYDATA[1];	
			if(null == code || "".equals(code)){
				errInfo = "nullcode"; //验证码为空
			}else if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
				errInfo = "nullup";	//缺少用户名或密码
			}else{
				if(StringUtils.isNotEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){										
					// shiro加入身份验证
					UsernamePasswordToken token = new UsernamePasswordToken(username, password.toUpperCase());
					token.setRememberMe(true);
					try {
						if (!currentUser.isAuthenticated()) {
							currentUser.login(token);
						}		
						//记录登录日志
						String accountId=AccountShiroUtil.getCurrentUser().getAccountId();
						String loginIP=IPUtil.getIpAddr(getRequest());//获取用户登录IP
						LoginLog loginLog=new LoginLog(accountId,loginIP);
						loginLogService.saveLoginLog(loginLog);
					} catch (UnknownAccountException uae) {
						errInfo = "usererror";// 用户名或密码有误
					} catch (IncorrectCredentialsException ice) {
						errInfo = "usererror"; // 密码错误
					} catch (LockedAccountException lae) {
						errInfo = "inactive";// 未激活
					} catch (ExcessiveAttemptsException eae) {
						errInfo = "attemptserror";// 错误次数过多
					} catch (AuthenticationException ae) {
						errInfo = "codeerror";// 验证未通过
					}
					// 验证是否登录成功
					if (!currentUser.isAuthenticated()) {
						token.clear();
					}
				}else{
					errInfo = "codeerror";				 	//验证码输入有误
				}
				if(StringUtils.isEmpty(errInfo)){
					errInfo = "success";					//验证成功
					session.removeAttribute(Const.SESSION_SECURITY_CODE);//移除SESSION的验证
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}	
		map.put("result", errInfo);
		return map;
	}		
	  /**
     * 帐号注销
     * @return
     */
    @RequestMapping("/system_logout")
    public String logout(HttpServletRequest request,HttpSession session) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        session = request.getSession(true);
        session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_MENULIST);
        return "redirect:loginIndex.html";
    }
    
	/**
	 * 异步获取地区并更新
	 * @throws Exception 
	 */
    @Async
    public AjaxRes getCityUpdate(PushDict obj ) throws InterruptedException  {
    	AjaxRes ar=getAjaxRes();
    	IpAdrressUtil ipAdrressUtil = new IpAdrressUtil();
    	String cityAdd = null;
    	try {
			 cityAdd = ipAdrressUtil.getAddress(obj.getParamKey());
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.IP_CITY_FAIL);
		}
    	//LOG.info( cityAdd );
    	if(cityAdd!=null){
    		obj.setParamName(cityAdd);
    		service.update(obj);
    		ar.setSucceedMsg(Const.UPDATE_SUCCEED);
    	}
        return ar;
    }
    
   
}
