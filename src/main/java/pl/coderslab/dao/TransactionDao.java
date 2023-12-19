package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.Category;
import pl.coderslab.model.Transaction;
import pl.coderslab.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
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

    public List<Transaction> getAllTransactionsByUser(Long userId) {
        String queryString = "SELECT t FROM Transaction t WHERE t.user.id = :userId";
        TypedQuery<Transaction> query = entityManager.createQuery(queryString, Transaction.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<Transaction> sortTransactions(List<Transaction> transactions, String sortBy, String sortOrder) {
        Comparator<Transaction> comparator;

        switch (sortBy) {
            case "amount":
                comparator = Comparator.comparing(Transaction::getAmount);
                break;
            case "date":
                comparator = Comparator.comparing(Transaction::getDate);
                break;
            case "category":
                comparator = Comparator.comparing(t -> t.getCategory().getCategory_name());
                break;
            default:
                return transactions;
        }

        transactions.sort(comparator);

        if ("desc".equals(sortOrder)) {
            Collections.reverse(transactions);
        }

        return transactions;
    }

    public List<Transaction> filterByCategory(List<Transaction> transactions, Category selectedCategory) {
        return transactions.stream()
                .filter(transaction -> {
                    if (selectedCategory == null) {
                        return true;
                    }

                    Category transactionCategory = transaction.getCategory();
                    return transactionCategory != null && transactionCategory.getId().equals(selectedCategory.getId());
                })
                .collect(Collectors.toList());
    }

    public void addFirstTransaction(Category category, User user) {
        Transaction transaction = new Transaction();

        transaction.setAmount(BigDecimal.valueOf(0));
        transaction.setDate(LocalDate.of(1996, 1, 21));
        transaction.setDescription("Java was shown on this day");
        transaction.setCategory(category);
        transaction.setUser(user);

        entityManager.persist(transaction);
    }
}
