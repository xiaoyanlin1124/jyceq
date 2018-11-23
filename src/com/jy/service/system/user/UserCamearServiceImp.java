package com.jy.service.system.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jy.entity.system.user.UserCamear;
import com.jy.repository.system.user.UserCamearDao;
import com.jy.service.base.BaseServiceImp;


@Service("UserCamearService")
public class UserCamearServiceImp extends BaseServiceImp<UserCamear> implements UserCamearService {
	
	@Autowired
	protected UserCamearDao userCamearDao;
	
	@Override
	public List<UserCamear> finddevicesafe_key_value(UserCamear o) {
		return userCamearDao.finddevicesafe_key_value(o);
	}
	@Override
	public List<UserCamear> findemailphone(UserCamear o) {
		return userCamearDao.findemailphone(o);
	}


}