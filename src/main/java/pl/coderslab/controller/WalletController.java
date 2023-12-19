package pl.coderslab.controller;

import com.sun.jdi.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dao.BudgetDao;
import pl.coderslab.dao.CategoryDao;
import pl.coderslab.dao.TransactionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Budget;
import pl.coderslab.model.Category;
import pl.coderslab.model.Transaction;
import pl.coderslab.model.User;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WalletController {
    private final BudgetDao budgetDao;
    private final TransactionDao transactionDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Autowired
    public WalletController(BudgetDao budgetDao, TransactionDao transactionDao, UserDao userDao, CategoryDao categoryDao) {
        this.budgetDao = budgetDao;
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/wallet")
    public String showWallet(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        Long userId = (Long) session.getAttribute("userId");

        Optional<Budget> budgetOptional = budgetDao.getBudgetByUserId(userId);
        Optional<Transaction> transactionOptional = transactionDao.getLastTransaction(userId);


        if (budgetOptional.isPresent() && transactionOptional.isPresent()) {
            Budget budget = budgetOptional.get();
            Transaction transaction = transactionOptional.get();
            Category category = transaction.getCategory();

            model.addAttribute("budget", budget);
            model.addAttribute("name_wall", budget.getName());
            model.addAttribute("balance", budget.getAmount());
            model.addAttribute("username", username);
            model.addAttribute("lastTrAmount", transaction.getAmount());
            model.addAttribute("lastTrDate", transaction.getDate());
            model.addAttribute("lastTrCategory", (category != null) ? category.getCategory_name() : "No Category");

            return "wallet";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/wallet")
    public String addFunds(HttpSession session, BigDecimal amount) {
        Long userId = (Long) session.getAttribute("userId");

        budgetDao.updateBalance(userId, amount);

        Transaction newTransaction = new Transaction();

        newTransaction.setUser(userDao.getUserById(userId));
        newTransaction.setAmount(amount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(formatter);
        newTransaction.setDate(LocalDate.parse(formattedDate, formatter));

        newTransaction.setDescription("Deposit by main page");
        newTransaction.setCategory(categoryDao.findById(11L));

        transactionDao.save(newTransaction);
        return "redirect:/wallet";
    }

    @PostMapping("/add-transaction")
    public String addTransaction(@RequestParam("amount") Double amount,
                                 @RequestParam("date") String date,
                                 @RequestParam("category") Long categoryId,
                                 @RequestParam("description") String description,
                                 HttpSession session,
                                 Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userDao.getUserById(userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate transactionDate = LocalDate.parse(date, formatter);


        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(BigDecimal.valueOf(amount));
        newTransaction.setDate(transactionDate);

        Category category = categoryDao.findById(categoryId);
        newTransaction.setCategory(category);

        newTransaction.setDescription(description);
        newTransaction.setUser(user);

        if (categoryId.equals(11L)) {
            budgetDao.updateBalance(userId, BigDecimal.valueOf(amount));
        } else {
            BigDecimal negativeAmount = BigDecimal.valueOf(amount).negate();
            budgetDao.updateBalance(userId, negativeAmount);
        }

        transactionDao.save(newTransaction);

        return "redirect:/wallet";
    }
}
