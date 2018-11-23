package com.jy.controller.system.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.user.UserCamear;
import com.jy.entity.system.user.UserIP;
import com.jy.service.system.user.UserCamearService;
import com.jy.service.system.user.UserIPService;
/*
 * 上线记录
 */
@Controller
@RequestMapping("/backstage/UserCamear/")
public class UserCamearController extends BaseController<UserCamear>{
	
	   /* 日志对象 */
//	private static final Logger LOG = LoggerFactory.getLogger(PushDeviceController.class);
	
	@Autowired
	public UserCamearService service;
	
	@Autowired
	public UserIPService userIPService;
	
	/**
	 * 用户管理首页
	 */
	@RequestMapping("index")
	public String index(Model model) {
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/system/user/usercamear/list";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<UserCamear> page,UserCamear o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/backstage/UserCamear/index"))){
			try {
				Page<UserCamear> result = service.findByPage(o,page);
				//获取IP 这里会形响效率
				for(int i = 0 ; i < result.getResults().size() ; i++) {
					UserIP userip = new UserIP();
					userip.setUser_id(result.getResults().get(i).getId());
					List<UserIP> ipadd = userIPService.find( userip );
					if(!ipadd.isEmpty()){
						result.getResults().get(i).setLogin_ip( ipadd.get(0).getIp() );
						result.getResults().get(i).setUpdateTime(ipadd.get(0).getUpdateTime());
					}else{
						result.getResults().get(i).setUpdateTime(null);
					}
				}
				
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
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(UserCamear o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				o.setId(get32UUID());
				o.setCreateTime(new Date());	
				service.insert(o);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(UserCamear o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){		
			try {
				List<UserCamear> list=service.find(o);
				UserCamear obj=list.get(0);
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
	public AjaxRes update(UserCamear o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){		
			try {
				o.setUpdateTime(new Date());
				service.update(o);
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
	public AjaxRes del(UserCamear o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				service.delete(o);
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
				List<UserCamear> list=new ArrayList<UserCamear>();
				for(String s:chk){
					UserCamear sd=new UserCamear();
					sd.setId(s);
					list.add(sd);
				}
				service.deleteBatch(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	
}

