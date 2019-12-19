package co.th.ford.tms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;


import co.th.ford.tms.model.City;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao<Integer, City> implements CityDao {
	
	@SuppressWarnings("unchecked")
	public List<City> findAllCity() {
		Criteria criteria = createEntityCriteria();
		return (List<City>) criteria.list();
	}
	
}
