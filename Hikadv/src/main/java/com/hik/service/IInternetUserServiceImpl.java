package com.hik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hik.dao.BaseService;
import com.hik.dao.IInternetUserDao;
import com.hik.framework.utils.Page;

@Service
@Transactional
public class IInternetUserServiceImpl  extends BaseService  implements IInternetUserService{

	@Autowired
	private IInternetUserDao iInternetUserDao;

	@Override
	public Page queryInternetUser(int start, int limit, String searchStr) {
		// TODO Auto-generated method stub
		return iInternetUserDao.queryInternetUser(start, limit, searchStr);
	}

}
