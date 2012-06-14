package com.crawler.dao;

import org.springframework.stereotype.Component;

import com.crawler.dao.base.GenericDao;
import com.crawler.pojo.Address;

@Component("addressDao") 
public class AddressDao extends GenericDao<Address, Long>{
	public AddressDao(){
		super();
		this.entityClass = Address.class;
	}
}
