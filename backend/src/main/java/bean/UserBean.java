package bean;

import domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    public String createUser(User user) {
        entityManager.persist(user);

        return String.valueOf(user.getId());
    }
}
