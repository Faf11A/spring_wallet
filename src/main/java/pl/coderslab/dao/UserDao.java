package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    public User saveUser(User user) {
        entityManager.persist(user);
        return user;
    }

    public void deleteUser(Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public User updateUser(User updatedUser) {
        return entityManager.merge(updatedUser);
    }

    public User getUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }

    public String findPasswordByLogin(String login) {
        try {
            return entityManager.createQuery("SELECT u.password FROM User u WHERE u.login = :login", String.class).setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long findUserIdByLogin(String login) {
        Query query = entityManager.createQuery("SELECT u.id FROM User u WHERE u.login = :login");
        query.setParameter("login", login);
        try {
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}