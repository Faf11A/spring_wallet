package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.dao.CategoryDao;
import pl.coderslab.dao.TransactionDao;
import pl.coderslab.model.Category;
import pl.coderslab.model.Transaction;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HistoryController {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping("/history")
    public String getTransactionHistory(Model model,
                                        HttpSession session,
                                        @RequestParam(name = "sortBy", required = false, defaultValue = "date") String sortBy,
                                        @RequestParam(name = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
                                        @RequestParam(name = "category", required = false, defaultValue = "0") Long categoryId) {


        Long userId = (Long) session.getAttribute("userId");

        // Получаем транзакции текущего пользователя
        List<Transaction> transactions = transactionDao.getAllTransactionsByUser(userId);

        // Фильтруем транзакции по категории
        Category selectedCategory = (categoryId != 0) ? categoryDao.findById(categoryId) : null;
        transactions = transactionDao.filterByCategory(transactions, selectedCategory);

        // Сортируем транзакции
        transactions = transactionDao.sortTransactions(transactions, sortBy, sortOrder);

        // Получаем список всех категорий
        List<Category> categories = categoryDao.findAllCategories();

        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("selectedCategory", categoryId);

        return "history";
    }

    @PostMapping("/history")
    public String postTransactionHistory(Model model,
                                         HttpSession session,
                                         @RequestParam(name = "sortBy", required = false, defaultValue = "date") String sortBy,
                                         @RequestParam(name = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
                                         @RequestParam(name = "category", required = false, defaultValue = "0") Long categoryId) {

        // Получаем ID текущего пользователя из сессии
        Long userId = (Long) session.getAttribute("userId");

        // Получаем транзакции текущего пользователя
        List<Transaction> transactions = transactionDao.getAllTransactionsByUser(userId);

        // Фильтруем транзакции по категории
        Category selectedCategory = (categoryId != 0) ? categoryDao.findById(categoryId) : null;
        transactions = transactionDao.filterByCategory(transactions, selectedCategory);

        // Сортируем транзакции
        transactions = transactionDao.sortTransactions(transactions, sortBy, sortOrder);

        // Получаем список всех категорий
        List<Category> categories = categoryDao.findAllCategories();

        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("selectedCategory", categoryId);

        return "history";
    }
}
