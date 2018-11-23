package com.jy.controller.system.device;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.device.CopyProperties;
import com.jy.common.device.DevRes;
import com.jy.common.device.IpAdrressUtil;
import com.jy.common.device.AppPush.miPush;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.controller.system.user.AppLoginController;
import com.jy.entity.system.device.OnlineDevice;
import com.jy.entity.system.device.PushDevice;
import com.jy.entity.system.user.UserCamear;
import com.jy.entity.system.user.UserDevice;
import com.jy.entity.system.user.UserDeviceApp;
import com.jy.service.system.device.OnlineDeviceService;
import com.jy.service.system.device.PushDeviceService;
import com.jy.service.system.user.UserCamearService;
import com.jy.service.system.user.UserDeviceService;

import net.sf.json.JSONArray;
/**
 * 
 * @author lamaq@126.com
 * @date 2018-6-22
 * 摄像头设备发过来的信息
 */
@Configuration  
@EnableAsync
@Controller
@RequestMapping("/device/")
public class DeviceLoginPushController extends BaseController<OnlineDevice>{
	
	   /* 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(DeviceLoginPushController.class);

//	private static final int List = 0;
	
	@Autowired
	public OnlineDeviceService onlineDeviceService;
	
	@Autowired
	public PushDeviceService pushDeviceService;
	
	@Autowired
	public UserCamearService userCamearService;
	
	@Autowired
	public UserDeviceService userDeviceService;
	
	/**
	 * 收集摄像头上线信息
	 * 摄像头启动POST发送数据
	 * @param request
	 * @return {"res":1,"resMsg":"保存成功","obj":null}
	 */
	@RequestMapping(value="run", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes deviceAdd(OnlineDevice o , HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
		try {
			if( o.getDeviceId() == null || o.getDeviceId().length() != 17 ){
				return null ;
			}
			String remoteAddr = IpAdrressUtil.getIpAdrress(request);
			List<OnlineDevice> list=onlineDeviceService.find(o);
			if( !list.isEmpty() ){
				OnlineDevice obj=list.get(0);
				obj.setUpdateTime(new Date());
				if( obj.getIpAdd().equalsIgnoreCase(remoteAddr))
				{
					onlineDeviceService.update(obj);
					ar.setSucceedMsg(Const.POST_SUCCEED);
				}else
				{
					obj.setIpAdd(remoteAddr);
					getCityUpdate( obj );
				}
			}else
			{
				o.setIpAdd(remoteAddr);
				o.setId(get32UUID());
				o.setIsValid(1);
				o.setCreateTime(new Date());
				onlineDeviceService.insert(o);
				//测试
				//LOG.info(XingeApp.pushTokenAndroid(2100297185, "af20bdea534492c9de400248b0475de6", "test", "测试", "3d72a1e063cffba94fcda8ad61fe26bf87a2611a").toString());
//				addPushDevice(o);
				ar.setSucceedMsg(Const.POST_SUCCEED);
				getCityUpdate( o );
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.SAVE_FAIL);
		}
	return ar;
}
	
	
	/**
	 * APP端推送配置
	 * POST发送数据
	 * @param request
	 * @return {"res":1,"resMsg":"保存成功","obj":null}
	 */
	@RequestMapping(value="pushconfig", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes devicePushConfig(PushDevice o ){
		AjaxRes ar=getAjaxRes();
			try {
				if( o.getDeviceId() == null || o.getDeviceId().length() != 17 ){
					return null ;
				}
				List<PushDevice> list=pushDeviceService.find(o);
				if( !list.isEmpty() ){
					PushDevice obj=list.get(0);
					CopyProperties.copyPropertiesIgnoreNull(o, obj);
					obj.setUpdateTime(new Date());
					pushDeviceService.update(obj);
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				}else
				{
					o.setId(get32UUID());
					o.setIsValid(1);
					o.setUpdateTime(new Date());
					pushDeviceService.insert(o);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
	
	/**
	 * 固件推送消息接收
	 * POST发送数据
	 * @param request
	 * @return {"res":1,"resMsg":"保存成功","obj":null}
	 */
	@RequestMapping(value="push", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes devicePush(PushDevice o ){
		AjaxRes ar=getAjaxRes();
			try {
				if( o.getDeviceId() == null || o.getDeviceId().length() != 17 ){
					return null ;
				}
				List<PushDevice> list=pushDeviceService.findDeviceId(o);
				
//				OnlineDevice onlineDevice = new OnlineDevice();
//				CopyProperties.copyPropertiesIgnoreNull(o, onlineDevice);
//				List<OnlineDevice> list=onlineDeviceService.find(onlineDevice);
				if( !list.isEmpty() ){
					for(PushDevice  pushDeviceTemp:list ){
						LOG.info( pushDeviceTemp.toString() );
						
						
						
						
					}
					
					 miPush m = new miPush();
					m.sendMessage();

					
//					System.out.println(PushI18n.getI18nString("lockOnPush", null));
//					System.out.println(PushI18n.getI18nString("lockOnPush", new Locale("en-us", "en-us")));
//					System.out.println(PushI18n.getI18nString("lockOnPush",new Locale("zh", "CN")));
					
					ar.setSucceedMsg(Const.PUSH_SUCCEED);
				}else
				{
					ar.setFailMsg(Const.PUSH_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
	
	/**
	 * 根据did（device_id）删除设备
	 * POST发送数据
	 * 参数1 phone email openid safeKeyValue
	 * @param request
	 * @return {"res":1,"resMsg":"保存成功","obj":null}
	 */
	@RequestMapping(value="delDeviceByDid", method=RequestMethod.POST)
	@ResponseBody
	public DevRes DelDeviceByDid(UserCamear u ,  UserDeviceApp d  ){
		
		DevRes ar=getDevRes();
		ar.setFailMsg(Const.DATA_FAIL ,Const.DATA_FAIL_VALUE );
//		LOG.info("findDeviceByKey"+ u.toString() );
//		LOG.info("findDeviceByKey"+ d.getDevice_id() );
			try {
				
				UserCamear user = new AppLoginController().LoginConfirm(u,userCamearService);
//				LOG.info( "findDeviceByKey"+ user.toString() );	
				if( user!=null && d.getDevice_id()!=null ){
					
					UserDevice userDevice = new UserDevice();
					
					userDevice.setDevice_id(d.getDevice_id());
					userDevice.setUser_id(user.getUser_id());
					
					List<UserDevice> UserDevicelist = userDeviceService.find(userDevice );
					
					if( !UserDevicelist.isEmpty() ){
						userDeviceService.delete(UserDevicelist.get(0));
//						ar.setObj(JSONArray.fromObject(UserDeviceJsonlist ));
						ar.setSucceedMsg(Const.DEL_SUCCEED , Const.DEL_SUCCEED_VALUE);
					}
				}
				
			} catch (Exception e) {
				logger.error(e.toString(),e);
				//ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
	/**
	 * 查找用户关联的设备信息
	 * POST发送数据
	 * 参数1 phone email openid safeKeyValue
	 * @param request
	 * @return {"res":1,"resMsg":"保存成功","obj":null}
	 */
	@RequestMapping(value="findDeviceByKey", method=RequestMethod.POST)
	@ResponseBody
	public DevRes FindDeviceByKey(UserCamear u ){
		
		LOG.info(u.toString());
		DevRes ar=getDevRes();
		ar.setFailMsg(Const.DATA_FAIL ,Const.DATA_FAIL_VALUE );

			try {
				UserCamear user = new AppLoginController().LoginConfirm(u,userCamearService);
				
				if( user!=null){
					
					UserDevice userDevice = new UserDevice();
					
					userDevice.setUser_id(user.getId());
					
					List<UserDevice> UserDevicelist = userDeviceService.find(userDevice );
					
					if( !UserDevicelist.isEmpty() ){
						userDeviceService.find(UserDevicelist.get(0));
						List<UserDeviceApp>   UserDeviceJsonlist = new ArrayList<>();
						
						for(UserDevice tmp:UserDevicelist ){
							UserDeviceApp userDeviceApp = new UserDeviceApp();
							CopyProperties.copyPropertiesIgnoreNull(tmp, userDeviceApp);
							UserDeviceJsonlist.add(userDeviceApp);
						}
						
						ar.setObj(JSONArray.fromObject(UserDeviceJsonlist ));
						ar.setSucceedMsg(Const.DATA_SUCCEED , Const.DATA_SUCCEED_VALUE);
					}
//					else{
//						ar.setSucceedMsg(Const.NO_DEVICE , Const.NO_DEVICE_VALUE);
					}
				} catch (Exception e) {
				logger.error(e.toString(),e);
				//ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
	
	/**
	 * 根据did（device_id）删除设备
	 * POST发送数据
	 * 参数1 phone email openid safeKeyValue
	 * @param request
	 * @return {"res":1,"resMsg":"保存成功","obj":null}
	 */
	@RequestMapping(value="addDeviceByDid", method=RequestMethod.POST)
	@ResponseBody
	public DevRes AddDeviceByDid(UserCamear u ,  UserDeviceApp d  ){
		
		DevRes ar=getDevRes();
		ar.setFailMsg(Const.DATA_FAIL ,Const.DATA_FAIL_VALUE );
//		LOG.info("findDeviceByKey"+ u.toString() );
//		LOG.info("findDeviceByKey"+ d.getDevice_id() );
			try {
				
				UserCamear user = new AppLoginController().LoginConfirm(u,userCamearService);
//				LOG.info( "findDeviceByKey"+ user.toString() );	
				if( user!=null && d.getDevice_id()!=null ){
					
					UserDevice userDevice = new UserDevice();
					
					CopyProperties.copyPropertiesIgnoreNull(d, userDevice);
					
					userDevice.setId(get32UUID());
					userDevice.setUser_id(user.getId());
					userDevice.setCreateTime(new Date());
					userDevice.setDevice_model("CEQP2"); //型号，后期更新
					userDevice.setLanguage(user.getLanguage());
					LOG.info( "addDeviceByKey"+ userDevice.toString() );
					List<UserDevice> UserDevicelist = userDeviceService.find(userDevice );
					
					if( UserDevicelist.isEmpty() ){
						userDeviceService.insert(userDevice);
//						ar.setObj(JSONArray.fromObject(UserDeviceJsonlist ));
						ar.setSucceedMsg(Const.AddDeviceSuccess , Const.AddDeviceSuccess_VALUE);
					}
				}
				
			} catch (Exception e) {
				logger.error(e.toString(),e);
				//ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}

	/**
	 * 异步获取地区并更新
	 * @throws Exception 
	 */
    @Async
    public AjaxRes getCityUpdate(OnlineDevice obj ) throws InterruptedException  {
    	AjaxRes ar=getAjaxRes();
    	IpAdrressUtil ipAdrressUtil = new IpAdrressUtil();
    	String cityAdd = null;
    	try {
			 cityAdd = ipAdrressUtil.getAddress(obj.getIpAdd());
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.IP_CITY_FAIL);
		}
    	//LOG.info( cityAdd );
    	if(cityAdd!=null){
    		obj.setCityAdd(cityAdd);
    		onlineDeviceService.update(obj);
    		ar.setSucceedMsg(Const.UPDATE_SUCCEED);
    	}
        return ar;
    }
    
	/**
	 * 插入推送设备数据
	 * @throws Exception 
	 */
    @Async
    public AjaxRes addPushDevice(OnlineDevice o ) throws InterruptedException  {
    	AjaxRes ar=getAjaxRes();
    	PushDevice pushDevice = new PushDevice() ;
    	try {
			pushDevice.setId(get32UUID());
			pushDevice.setDeviceId(o.getDeviceId());
			pushDevice.setDeviceModel(o.getDeviceModel());
			pushDevice.setCreateTime(new Date());	
			pushDevice.setIsValid(1);
			pushDevice.setAlarmPush(1);
			pushDevice.setHijackPush(1);
			pushDevice.setPowerLowPush(1);
			pushDevice.setLockOnPush(1);
			pushDevice.setPushSum(0);
			pushDeviceService.insert(pushDevice);
			ar.setSucceedMsg(Const.UPDATE_SUCCEED);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.IP_CITY_FAIL);
		}
        return ar;
    }
    
    /**
     * 登陆首页记录脚本
     * 
     */
//	public void loginIPadd(PushDict o){
//		//AjaxRes ar=getAjaxRes();
//		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
//		
//			try {
//				o.setId(get32UUID());
//				o.setCreateTime(new Date());	
//				service.insert(o);
//				ar.setSucceedMsg(Const.SAVE_SUCCEED);
//			} catch (Exception e) {
//				logger.error(e.toString(),e);
//				ar.setFailMsg(Const.SAVE_FAIL);
//			}
//	//	}	
//		//return ar;
//	}
//    
    
	
	
	/**
	 * 系统字典首页
	 */
	@RequestMapping(value="index", method=RequestMethod.POST)
	public String index(Model model) {	
		LOG.info("running device");
	//	if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
	//		model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
	//		return "/system/device/online/list";
	//	}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<OnlineDevice> page,OnlineDevice o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/backstage/OnlineDevice/index"))){
			try {
				Page<OnlineDevice> result=onlineDeviceService.findByPage(o,page);
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				p.put("list",result);		
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	

	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(OnlineDevice o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){		
			try {
				List<OnlineDevice> list=onlineDeviceService.find(o);
				OnlineDevice obj=list.get(0);
				ar.setSucceed(obj);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(OnlineDevice o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){		
			try {
				o.setUpdateTime(new Date());
				onlineDeviceService.update(o);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(OnlineDevice o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				onlineDeviceService.delete(o);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				String[] chk =chks.split(",");
				List<OnlineDevice> list=new ArrayList<OnlineDevice>();
				for(String s:chk){
					OnlineDevice sd=new OnlineDevice();
					sd.setId(s);
					list.add(sd);
				}
				onlineDeviceService.deleteBatch(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	
	

	
}

