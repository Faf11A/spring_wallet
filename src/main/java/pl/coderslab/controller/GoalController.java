package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.dao.BudgetDao;
import pl.coderslab.dao.GoalDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class GoalController {

    private final GoalDao goalDao;
    private final UserDao userDao;
    private final BudgetDao budgetDao;

    @Autowired
    public GoalController(GoalDao goalDao, UserDao userDao, BudgetDao budgetDao) {
        this.goalDao = goalDao;
        this.userDao = userDao;
        this.budgetDao = budgetDao;
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
        goalDao.saveGoal(goal);
        return "redirect:/goals";
    }

    @PostMapping("/deleteGoal")
    public String deleteGoal(@RequestParam Long goalId) {
        goalDao.deleteGoal(goalId);
        return "redirect:/goals";
    }
}
