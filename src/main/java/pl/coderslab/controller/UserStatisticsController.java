package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.coderslab.dao.*;
import pl.coderslab.model.Goal;
import pl.coderslab.model.User;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserStatisticsController {

    private final UserDao userDao;
    private final UserDetailsDao userDetailsDao;
    private final BudgetDao budgetDao;
    private final TransactionDao transactionDao;
    private final GoalDao goalDao;
    private final CategoryDao categoryDao;

    @Autowired
    public UserStatisticsController(UserDao userDao, UserDetailsDao userDetailsDao, BudgetDao budgetDao, TransactionDao transactionDao, GoalDao goalDao, CategoryDao categoryDao) {
        this.userDao = userDao;
        this.userDetailsDao = userDetailsDao;
        this.budgetDao = budgetDao;
        this.transactionDao = transactionDao;
        this.goalDao = goalDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/stats")
    public String showUserStatistics(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userDao.getUserById(userId);

        List<Goal> currentGoals = goalDao.findCurrentGoals(userId);
        List<Goal> completedGoals = goalDao.findCompletedGoals(userId);
        List<Goal> expiredGoals = goalDao.findExpiredGoals(userId);

        //Goals
        Long countOfCurrentGoals = (long) currentGoals.size();
        Long countOfDoneGoals = (long) completedGoals.size();
        Long countOfExpiredGoals = (long) expiredGoals.size();
        Long countOfGoals = countOfCurrentGoals + countOfExpiredGoals + countOfExpiredGoals;

        model.addAttribute("countOfCurrentGoals", countOfCurrentGoals);
        model.addAttribute("countOfDoneGoals", countOfDoneGoals);
        model.addAttribute("countOfExpiredGoals", countOfExpiredGoals);
        model.addAttribute("countOfGoals", countOfGoals);

        // Transactions
        Long countOfAddAmount = transactionDao.getTransactionCountByCategory(11L);
        Long countOfAddAmountCat1 = transactionDao.getTransactionCountByCategory(1L);
        Long countOfAddAmountCat2 = transactionDao.getTransactionCountByCategory(2L);
        Long countOfAddAmountCat3 = transactionDao.getTransactionCountByCategory(3L);
        Long countOfAddAmountCat4 = transactionDao.getTransactionCountByCategory(4L);
        Long countOfAddAmountCat5 = transactionDao.getTransactionCountByCategory(5L);
        Long countOfAddAmountCat6 = transactionDao.getTransactionCountByCategory(6L);
        Long countOfAddAmountCat7 = transactionDao.getTransactionCountByCategory(7L);
        Long countOfAddAmountCat8 = transactionDao.getTransactionCountByCategory(8L);
        Long countOfAddAmountCat9 = transactionDao.getTransactionCountByCategory(9L);
        Long countOfAddAmountCat10 = transactionDao.getTransactionCountByCategory(10L);
        Long countOfAddAmountCat12 = transactionDao.getTransactionCountByCategory(12L);

        Long countOfTransactions = countOfAddAmountCat1 + countOfAddAmountCat2 + countOfAddAmountCat3 + countOfAddAmountCat4 + countOfAddAmountCat5 + countOfAddAmountCat6 + countOfAddAmountCat7 + countOfAddAmountCat8 + countOfAddAmountCat9 + countOfAddAmountCat10 + countOfAddAmountCat12;

        Double sumOfTrCat1 = transactionDao.getTransactionSumByCategory(1L);
        Double sumOfTrCat2 = transactionDao.getTransactionSumByCategory(2L);
        Double sumOfTrCat3 = transactionDao.getTransactionSumByCategory(3L);
        Double sumOfTrCat4 = transactionDao.getTransactionSumByCategory(4L);
        Double sumOfTrCat5 = transactionDao.getTransactionSumByCategory(5L);
        Double sumOfTrCat6 = transactionDao.getTransactionSumByCategory(6L);
        Double sumOfTrCat7 = transactionDao.getTransactionSumByCategory(7L);
        Double sumOfTrCat8 = transactionDao.getTransactionSumByCategory(8L);
        Double sumOfTrCat9 = transactionDao.getTransactionSumByCategory(9L);
        Double sumOfTrCat10 = transactionDao.getTransactionSumByCategory(10L);
        Double sumOfTrCat12 = transactionDao.getTransactionSumByCategory(12L);

        Double sumOfTr = sumOfTrCat1 + sumOfTrCat2 + sumOfTrCat3 + sumOfTrCat4 + sumOfTrCat5 + sumOfTrCat6 + sumOfTrCat7 + sumOfTrCat8 + sumOfTrCat9 + sumOfTrCat10 + sumOfTrCat12;
        Double amountOfAdds = transactionDao.getTransactionSumByCategory(11L);

        model.addAttribute("countOfAddAmount", countOfAddAmount);
        model.addAttribute("countOfAddAmountCat1", countOfAddAmountCat1);
        model.addAttribute("countOfAddAmountCat2", countOfAddAmountCat2);
        model.addAttribute("countOfAddAmountCat3", countOfAddAmountCat3);
        model.addAttribute("countOfAddAmountCat4", countOfAddAmountCat4);
        model.addAttribute("countOfAddAmountCat5", countOfAddAmountCat5);
        model.addAttribute("countOfAddAmountCat6", countOfAddAmountCat6);
        model.addAttribute("countOfAddAmountCat7", countOfAddAmountCat7);
        model.addAttribute("countOfAddAmountCat8", countOfAddAmountCat8);
        model.addAttribute("countOfAddAmountCat9", countOfAddAmountCat9);
        model.addAttribute("countOfAddAmountCat10", countOfAddAmountCat10);
        model.addAttribute("countOfAddAmountCat12", countOfAddAmountCat12);
        model.addAttribute("sumOfTrCat1", sumOfTrCat1);
        model.addAttribute("sumOfTrCat2", sumOfTrCat2);
        model.addAttribute("sumOfTrCat3", sumOfTrCat3);
        model.addAttribute("sumOfTrCat4", sumOfTrCat4);
        model.addAttribute("sumOfTrCat5", sumOfTrCat5);
        model.addAttribute("sumOfTrCat6", sumOfTrCat6);
        model.addAttribute("sumOfTrCat7", sumOfTrCat7);
        model.addAttribute("sumOfTrCat8", sumOfTrCat8);
        model.addAttribute("sumOfTrCat9", sumOfTrCat9);
        model.addAttribute("sumOfTrCat10", sumOfTrCat10);
        model.addAttribute("sumOfTrCat12", sumOfTrCat12);
        model.addAttribute("countOfTransactions", countOfTransactions);
        model.addAttribute("sumOfTr", sumOfTr);
        model.addAttribute("amountOfAdds", amountOfAdds);

        // %%%%%%%%
        double percentDoneGoals = (countOfGoals > 0) ? (double) countOfDoneGoals / countOfGoals * 100 : 0;
        double percentCurrentGoals = (countOfGoals > 0) ? (double) countOfCurrentGoals / countOfGoals * 100 : 0;
        double percentExpiredGoals = (countOfGoals > 0) ? (double) countOfExpiredGoals / countOfGoals * 100 : 0;
        model.addAttribute("percentDoneGoals", percentDoneGoals);
        model.addAttribute("percentCurrentGoals", percentCurrentGoals);
        model.addAttribute("percentExpiredGoals", percentExpiredGoals);

        return "stats";
    }
}