package com.jy.service.system.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jy.entity.system.device.PushDevice;
import com.jy.repository.system.device.PushDeviceDao;
import com.jy.service.base.BaseServiceImp;


@Service("PushDeviceService")
public class PushDeviceServiceImp extends BaseServiceImp<PushDevice> implements PushDeviceService {
	
	@Autowired
	protected PushDeviceDao pushDeviceDao;
	
	@Override
	public List<PushDevice> findDeviceId(PushDevice o) {
		return pushDeviceDao.findDeviceId(o);
	}


}