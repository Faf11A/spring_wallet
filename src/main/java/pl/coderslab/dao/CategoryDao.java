package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.Category;
import pl.coderslab.model.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Category findById(Long categoryId) {
        return entityManager.find(Category.class, categoryId);
    }

    public List<Category> findAllCategories() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Transactional
    public Category findCategoryById(Long id) {
        return entityManager.find(Category.class, id);
    }
}