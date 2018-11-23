package com.jy.repository.system.user;

import java.util.List;


import com.jy.entity.system.user.UserCamear;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;

@JYBatis
public interface UserCamearDao  extends BaseDao<UserCamear> {
	
	public List<UserCamear> finddevicesafe_key_value(UserCamear o);	
	/**
	 * 获得对象列表
	 * @param o 对象       
	 * @param page 分页对象
	 * @return List
	 */
	public List<UserCamear> findemailphone(UserCamear o);	
}
