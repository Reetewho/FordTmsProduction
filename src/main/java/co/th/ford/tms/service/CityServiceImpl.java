package co.th.ford.tms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.th.ford.tms.dao.CityDao;
import co.th.ford.tms.model.City;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {
	@Autowired
	private CityDao dao;
	
	
	public List<City> findAllCity() {
		return dao.findAllCity();
	}
}
