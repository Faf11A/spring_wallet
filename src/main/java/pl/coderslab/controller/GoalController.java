package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dao.*;
import pl.coderslab.model.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class GoalController {

    private final GoalDao goalDao;
    private final UserDao userDao;
    private final BudgetDao budgetDao;
    private final CategoryDao categoryDao;
    private final TransactionDao transactionDao;


    @Autowired
    public GoalController(GoalDao goalDao, UserDao userDao, BudgetDao budgetDao, CategoryDao categoryDao, TransactionDao transactionDao) {
        this.goalDao = goalDao;
        this.userDao = userDao;
        this.budgetDao = budgetDao;
        this.categoryDao = categoryDao;
        this.transactionDao = transactionDao;
    }

    @GetMapping("/goals")
    public String showGoals(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        List<Goal> currentGoals = goalDao.findCurrentGoals(userId);
        List<Goal> completedGoals = goalDao.findCompletedGoals(userId);
        List<Goal> expiredGoals = goalDao.findExpiredGoals(userId);

        Optional<Budget> budgetOptional = budgetDao.getBudgetByUserId(userId);
        if (budgetOptional.isPresent()) {
            Budget budget = budgetOptional.get();
            model.addAttribute("balance", budget.getAmount());
            model.addAttribute("currentGoals", currentGoals);
            model.addAttribute("completedGoals", completedGoals);
            model.addAttribute("expiredGoals", expiredGoals);
            return "goals";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/goals")
    public String addGoal(@RequestParam String goalName,
                          @RequestParam BigDecimal targetAmount,
                          @RequestParam String targetDate,
                          HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        Goal goal = new Goal();
        goal.setGoalName(goalName);
        goal.setCurrentAmount(BigDecimal.valueOf(0));
        goal.setTargetAmount(targetAmount);
        goal.setTargetDate(LocalDate.parse(targetDate));

        User user = userDao.getUserById(userId);
        goal.setUser(user);

        goalDao.saveGoal(goal);
        return "redirect:/goals";
    }

    @PostMapping("/addAmountToGoal")
    public String addAmountToGoal(@RequestParam Long goalId,
                                  @RequestParam BigDecimal amount,
                                  HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userDao.getUserById(userId);

        Goal goal = goalDao.getGoalById(goalId);
        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));

        BigDecimal negativeAmount = amount.negate();
        budgetDao.updateBalance(userId, negativeAmount);


        Transaction transaction = new Transaction();
        Category category = categoryDao.findById(12L);
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setDescription("Amount added to your goals");
        transaction.setCategory(category);
        transaction.setDate(LocalDate.now());

        transactionDao.save(transaction);
        goalDao.saveGoal(goal);


        return "redirect:/goals";
    }

    @PostMapping("/deleteGoal")
    public String deleteGoal(@RequestParam Long goalId) {
        goalDao.deleteGoal(goalId);
        return "redirect:/goals";
    }

    @PostMapping("/editGoalDate")
    public String editGoalDate(@RequestParam String newDate,
                               @RequestParam Long goalId,
                               HttpSession session){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newGoalDate = LocalDate.parse(newDate, formatter);

        Long userId = (Long) session.getAttribute("userId");
        Goal goal = goalDao.getGoalById(goalId);
        goal.setTargetDate(newGoalDate);

        goalDao.saveGoal(goal);

        return "redirect:/goals";
    }

    @GetMapping("/edit/{goalId}")
    public String showEditGoalPage(@PathVariable Long goalId, Model model) {
        Goal goal = goalDao.getGoalById(goalId);
        if (goal != null) {
            model.addAttribute("goalId", goal.getId());
            model.addAttribute("goalName", goal.getGoalName());
            model.addAttribute("targetAmount", goal.getTargetAmount());
            model.addAttribute("targetDate", goal.getTargetDate());
            return "edit-goal";
        } else {
            return "redirect:/goals";
        }
    }

    @PostMapping("/updateGoal")
    public String updateGoal(@ModelAttribute("goalId") Long goalId,
                             @ModelAttribute("goalName") String goalName,
                             @ModelAttribute("targetAmount") BigDecimal targetAmount,
                             @ModelAttribute("targetDate") String targetDate) {
        Goal goal = goalDao.getGoalById(goalId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newGoalDate = LocalDate.parse(targetDate, formatter);

        if (goal != null) {
            goal.setGoalName(goalName);
            goal.setTargetAmount(targetAmount);
            goal.setTargetDate(newGoalDate);
            goalDao.saveGoal(goal);
        }
        return "redirect:/goals";
    }
}
