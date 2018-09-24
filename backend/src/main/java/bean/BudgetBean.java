package bean;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ManagedBean
public class BudgetBean
{
	@PersistenceContext(unitName = "postgres")
	public EntityManager entityManager;


}
