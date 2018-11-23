package com.jy.controller.monitor;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.monitor.MonitorUitl;
import com.jy.controller.base.BaseController;
/**
 * 系统监控
 */
@Controller
@RequestMapping("/backstage/monitor/sys/")
public class SysMonitorController extends BaseController<Object>{

	private static final String SECURITY_URL ="/backstage/monitor/sys/index";
	
	/**
	 * 数据库管理页面
	 */
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "system/monitor/sys/index";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	/**
	 * 获取系统环境信息
	 * @return
	 */	
	@RequestMapping(value="getSysEnvironment", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getSysEnvironment(){
		AjaxRes ar=getAjaxRes();	
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {	
				Map<String, Object> p=MonitorUitl.getSysEnvironment();
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}	
		}
		return ar;
	}
	
	/**
	 * 获取CPU信息
	 * @return
	 */	
	@RequestMapping(value="getCPUInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getCPUInfo(){
		AjaxRes ar=getAjaxRes();	
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {	
				Map<String, Object> p=MonitorUitl.getCPUInfo();
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}	
		}
		return ar;
	}
	
	/**
	 * 获取硬盘状况
	 * @param uni  显示单位(g：GB,m：MB,k：KB)
	 * @return
	 */
	@RequestMapping(value="getDiskInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getDiskInfo(String uni){
		AjaxRes ar=getAjaxRes();	
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {	
				int storageUni=MonitorUitl.STORAGE_UNI_GB;
				if("m".equals(uni)){
					storageUni=MonitorUitl.STORAGE_UNI_MB;
				}else if("k".equals(uni)){
					storageUni=MonitorUitl.STORAGE_UNI_KB;
				}
				List<Map<String, Object>> p=MonitorUitl.getDiskInfo(storageUni);
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}	
		}
		return ar;
	}
	
	
	/**
	 * 获取内存状况
	 * @param uni 显示单位(g：GB,m：MB,k：KB)
	 * @return
	 */
	@RequestMapping(value="getMemInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getMemInfo(String uni){
		AjaxRes ar=getAjaxRes();	
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {	
				int storageUni=MonitorUitl.STORAGE_UNI_GB;
				if("m".equals(uni)){
					storageUni=MonitorUitl.STORAGE_UNI_MB;
				}else if("k".equals(uni)){
					storageUni=MonitorUitl.STORAGE_UNI_KB;
				}
				Map<String, Object> p=MonitorUitl.getMemInfo(storageUni);
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}	
		}
		return ar;
	}
}
