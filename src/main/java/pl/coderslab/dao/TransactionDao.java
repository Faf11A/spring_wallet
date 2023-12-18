package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class TransactionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }

    public Optional<Transaction> getLastTransaction(Long userId) {
        String jpql = "SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.id DESC";
        TypedQuery<Transaction> query = entityManager.createQuery(jpql, Transaction.class)
                .setParameter("userId", userId)
                .setMaxResults(1);

        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Transaction> getAllTransactions() {
        String queryString = "SELECT t FROM Transaction t";
        TypedQuery<Transaction> query = entityManager.createQuery(queryString, Transaction.class);
        return query.getResultList();
    }

    public List<Transaction> filterByType(List<Transaction> transactions, String transactionType) {
        return transactions.stream()
                .filter(transaction -> {
                    if ("all".equals(transactionType)) {
                        return true;
                    }

                    Long categoryId = transaction.getCategory().getId();
                    return ("income".equals(transactionType) && categoryId == 11) ||
                            ("expense".equals(transactionType) && categoryId >= 1 && categoryId <= 10);
                })
                .collect(Collectors.toList());
    }

    public List<Transaction> sortTransactions(List<Transaction> transactions, String sortBy, String sortOrder) {
        transactions.sort((t1, t2) -> {
            int result;
            switch (sortBy) {
                case "amount":
                    result = t1.getAmount().compareTo(t2.getAmount());
                    break;
                case "date":
                    result = t1.getDate().compareTo(t2.getDate());
                    break;
                case "category":
                    result = t1.getCategory().getCategory_name().compareTo(t2.getCategory().getCategory_name());
                    break;
                default:
                    result = 0;
            }
            return ("asc".equals(sortOrder)) ? result : -result;
        });

        return transactions;
    }
}
