package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class UserDetailsDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserDetails saveUserDetails(UserDetails userDetails) {
        entityManager.persist(userDetails);
        return userDetails;
    }

    @Transactional
    public void deleteUserDetails(Long userDetailsId) {
        UserDetails userDetails = entityManager.find(UserDetails.class, userDetailsId);
        if (userDetails != null) {
            entityManager.remove(userDetails);
        }
    }

    @Transactional
    public UserDetails updateUserDetails(UserDetails updatedUserDetails) {
        return entityManager.merge(updatedUserDetails);
    }

    @Transactional
    public UserDetails getUserDetailsById(Long userDetailsId) {
        return entityManager.find(UserDetails.class, userDetailsId);
    }

    public String findUsernameById(Long userId) {
        Query query = entityManager.createQuery("SELECT ud.firstName FROM UserDetails ud WHERE ud.user.id = :userId");
        query.setParameter("userId", userId);
        try {
            return (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
