package com.jy.service.system.device;

import java.util.List;

import com.jy.entity.system.device.PushDevice;
import com.jy.service.base.BaseService;

public interface PushDeviceService extends BaseService<PushDevice> {
	
	public List<PushDevice> findDeviceId(PushDevice o);	
	/**
	 * 获得对象列表
	 * @param o 对象       
	 * @param page 分页对象
	 * @return List
	 */
	

}
