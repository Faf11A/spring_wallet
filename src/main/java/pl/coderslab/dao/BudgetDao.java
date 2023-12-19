package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.Budget;
import pl.coderslab.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
@Transactional
public class BudgetDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Budget budget) {
        entityManager.persist(budget);
    }

    public void update(Budget budget) {
        entityManager.merge(budget);
    }

    public Optional<Budget> getBudgetByUserId(Long userId) {
        String jpql = "SELECT b FROM Budget b WHERE b.user.id = :userId";
        try {
            Budget budget = entityManager.createQuery(jpql, Budget.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return Optional.of(budget);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public void updateBalance(Long userId, BigDecimal amount) {
        String jpql = "UPDATE Budget b SET b.amount = b.amount + :amount WHERE b.user.id = :userId";
        entityManager.createQuery(jpql)
                .setParameter("amount", amount)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    public void createFirstBudgetForNewUser(User user) {
        Budget budget = new Budget();
        budget.setName("My wallet");
        budget.setAmount(BigDecimal.valueOf(0));

        budget.setUser(user);
        entityManager.persist(budget);
    }
}
