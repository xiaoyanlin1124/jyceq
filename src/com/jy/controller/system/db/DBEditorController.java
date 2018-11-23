package com.jy.controller.system.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.db.DBUtil;
import com.jy.common.utils.poi.ObjectExcelView;
import com.jy.controller.base.BaseController;
/**
 * DB编辑器
 */
@Controller
@RequestMapping("/backstage/db/dbEditor/")
public class DBEditorController extends BaseController<Object>{

	private static final String SECURITY_URL ="/backstage/db/dbEditor/index";
	
	/**
	 * 查询编辑器页面
	 */
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "system/db/dbEditor/index";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	
	@RequestMapping(value="executeSQL", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes executeSQL(String sqlStr,Page<List<Object>> page){
		AjaxRes ar=getAjaxRes();	
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {	
				Map<String, Object> p=new HashMap<String, Object>();
				//默认只执行第一句
				String[] sql=sqlStr.split(";");
				Map<String,Object> result=DBUtil.executeSQL(sql[0],page);					
				p.put("result",result);
				ar.setSucceed(result);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}	
		}
		return ar;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportExl")
	public ModelAndView exportExl(String sqlStr) {
		ModelAndView mv = this.getModelAndView();
		if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL)) {
			try {
				Map<String, Object> model = new HashMap<String, Object>();
				List<Map<String, String>> varList = new ArrayList<Map<String, String>>();			
				String[] sql=sqlStr.split(";");
				Map<String,Object> result=DBUtil.executeSQL(sql[0]);
				String type=(String) result.get("type");
				if("query"==type){
					List<List<Object>> list=(List<List<Object>>) result.get("list");
					for(int i=0;i<list.size();i++){
						List<Object> l=list.get(i);
						if(i==0){
							List<String> titles = new ArrayList<String>();
							for(Object obj:l){
								titles.add(String.valueOf(obj));
							}
							model.put("titles", titles);// 设置标题们
						}else{
							Map<String, String> vpd = new HashMap<String, String>();						
							for(int j=0;j<l.size();j++){
								Object obj=l.get(j);
								if(obj==null){
									vpd.put("var"+(j+1)," ");
								}else{
									vpd.put("var"+(j+1),String.valueOf(obj));
								}
							}
							varList.add(vpd);
						}
					}
					String dateStr=DateUtils.getDate(DateUtils.parsePatterns[0]);
					int count=(int) result.get("count");
					model.put("fileName", dateStr+"("+count+")");// 设置文件名
					model.put("varList", varList);// 设置内容们
					ObjectExcelView erv = new ObjectExcelView(); // 执行excel操作
					mv = new ModelAndView(erv, model);
				}	
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}
		} else {
			mv.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mv;
	}
}
