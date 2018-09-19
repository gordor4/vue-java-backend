package bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.Consumption;

@Stateless
public class BudgetBean
{
	@PersistenceContext(unitName = "postgres")
	public EntityManager entityManager;

	public List<Consumption> getAllUserConsumption(int id) {
		return null;
		//entityManager.find()
	}
}
