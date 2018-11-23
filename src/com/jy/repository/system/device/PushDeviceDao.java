package com.jy.repository.system.device;

import java.util.List;


import com.jy.entity.system.device.PushDevice;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;

@JYBatis
public interface PushDeviceDao  extends BaseDao<PushDevice> {
	
	public List<PushDevice> findDeviceId(PushDevice o);	
	/**
	 * 获得对象列表
	 * @param o 对象       
	 * @param page 分页对象
	 * @return List
	 */

}
