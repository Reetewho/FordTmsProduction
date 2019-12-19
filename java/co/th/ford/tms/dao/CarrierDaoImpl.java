package co.th.ford.tms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import co.th.ford.tms.model.Carrier;


@Repository("carrierDao")
public class CarrierDaoImpl extends AbstractDao<Integer, Carrier> implements CarrierDao {

	/*public User findByUsername(String user) {
		return getByKey(user);
	}*/

	public void saveCarrier(Carrier carrier) {
		persist(carrier);
	}

	public void deleteCarrierByID(int carrierID) {
		Query query = getSession().createSQLQuery(
				  " delete tb_carrier,tb_load,tb_loadstop,tb_setstopeta "
				+ " from tb_carrier left join tb_load on tb_carrier.carrierID=tb_load.carrierID "
				+ " left join tb_loadstop on tb_load.loadID=tb_loadstop.loadID "
				+ " left join tb_setstopeta  on tb_loadstop.id=tb_setstopeta.loadStopID   "
				+ " where tb_carrier.carrierID = :carrierID ");		
		query.setInteger("carrierID", carrierID);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Carrier> findAllCarriers() {
		Criteria criteria = createEntityCriteria();
		return (List<Carrier>) criteria.list();
	}

	public Carrier findCarrierByID(int carrierID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("carrierID", carrierID));
		return (Carrier) criteria.uniqueResult();
	}
	
}
