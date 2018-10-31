package bean;

import domain.User;
import filter.JWTTokenNeededFilter;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    public User findUser(HttpRequest request) {
        String username = (String) request.getAttribute(JWTTokenNeededFilter.USER);
        if(username != null) {
            return findUser(username);
        }

        return null;
    }

    public User findUser(String username) {
        TypedQuery query = entityManager.createNamedQuery(User.FIND_USER, User.class);
        query.setParameter("username", username);

        return (User) query.getSingleResult();
    }

    public User findUserWithEmail(String email) {
        TypedQuery query = entityManager.createNamedQuery(User.FIND_USER_WITH_EMAIL, User.class);
        query.setParameter(User.EMAIL_PARAM, email);

        return (User) query.getSingleResult();
    }

}
