package pl.coderslab.rentaapartment.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User saveUser(User user) {
        if(user != null){
            entityManager.persist(user);
        }
        return new User();
    }
}
