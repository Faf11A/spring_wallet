package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.Goal;
import pl.coderslab.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class GoalDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void saveGoal(Goal goal) {
        entityManager.merge(goal);
    }

    public List<Goal> getAllGoals() {
        return entityManager.createQuery("SELECT g FROM Goal g", Goal.class)
                .getResultList();
    }

    public List<Goal> getGoalsByUser(User user) {
        return entityManager.createQuery("SELECT g FROM Goal g WHERE g.user = :user", Goal.class)
                .setParameter("user", user)
                .getResultList();
    }

    public Goal getGoalById(Long goalId) {
        return entityManager.find(Goal.class, goalId);
    }

    public List<Goal> getAllGoalsForUser(long userId) {
        String jpql = "SELECT g FROM Goal g WHERE g.user.id = :userId";
        TypedQuery<Goal> query = entityManager.createQuery(jpql, Goal.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<Goal> findCurrentGoals(Long userId) {
        String hql = "FROM Goal g WHERE g.user.id = :userId AND ((g.currentAmount / g.targetAmount) * 100) < 100";
        return entityManager.createQuery(hql, Goal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Goal> findCompletedGoals(Long userId) {
        String hql = "FROM Goal g WHERE g.user.id = :userId AND ((g.currentAmount / g.targetAmount) * 100) >= 100";
        return entityManager.createQuery(hql, Goal.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    public void deleteGoal(Long goalId) {
        Optional<Goal> goalOptional = Optional.ofNullable(entityManager.find(Goal.class, goalId));

        goalOptional.ifPresent(goal -> entityManager.remove(goal));
    }

    public List<Goal> findExpiredGoals(Long userId) {
        LocalDate currentDate = LocalDate.now();

        List<Goal> expiredGoals = new ArrayList<>();
        List<Goal> allGoals = getAllGoalsForUser(userId);


        for (Goal goal : allGoals) {
            LocalDate targetDate = goal.getTargetDate();

            BigDecimal amountCur = goal.getCurrentAmount();
            BigDecimal amountTar = goal.getTargetAmount();
            int comparison = amountCur.compareTo(amountTar);

            if (targetDate.isBefore(currentDate) && comparison < 0) {
                expiredGoals.add(goal);
            }
        }

        return expiredGoals;
    }

    public void deleteGoalsByUserId(Long userId) {
        entityManager.createQuery("DELETE FROM Goal t WHERE t.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
