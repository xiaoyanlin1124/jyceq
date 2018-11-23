package com.jy.service.system.user;

import java.util.List;

import com.jy.entity.system.user.UserCamear;
import com.jy.service.base.BaseService;

public interface UserCamearService extends BaseService<UserCamear> {
	
	public List<UserCamear> finddevicesafe_key_value(UserCamear o);	
	/**
	 * 获得对象列表
	 * @param o 对象       
	 * @param page 分页对象
	 * @return List
	 */
	public List<UserCamear> findemailphone(UserCamear o);	

}
