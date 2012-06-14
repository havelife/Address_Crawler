package com.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crawler.dao.AddressDao;
import com.crawler.pojo.Address;

@Component("addressMgr")
public class AddressMgr {
	@Autowired
	private AddressDao addressDao;
	
	public Address save(Address address){
		return addressDao.save(address);
	}
}
