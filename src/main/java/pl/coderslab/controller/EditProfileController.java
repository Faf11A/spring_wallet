package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.dao.*;
import pl.coderslab.model.User;
import pl.coderslab.model.UserDetails;

import javax.servlet.http.HttpSession;

@Controller
public class EditProfileController {

    private final GoalDao goalDao;
    private final UserDao userDao;
    private final UserDetailsDao userDetailsDao;
    private final BudgetDao budgetDao;
    private final CategoryDao categoryDao;
    private final TransactionDao transactionDao;


    @Autowired
    public EditProfileController(GoalDao goalDao, UserDao userDao, UserDetailsDao userDetailsDao, BudgetDao budgetDao, CategoryDao categoryDao, TransactionDao transactionDao) {
        this.goalDao = goalDao;
        this.userDao = userDao;
        this.userDetailsDao = userDetailsDao;
        this.budgetDao = budgetDao;
        this.categoryDao = categoryDao;
        this.transactionDao = transactionDao;
    }

    //GET for edit-profile
    @RequestMapping("/edit-profile")
    public String EditProfilePage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        User user = userDao.getUserById(userId);
        UserDetails userDetails = userDetailsDao.getUserDetailsById(userId);

        model.addAttribute("user", user);
        model.addAttribute("userDetails", userDetails);

        return "edit-profile";
    }

    //POST for edit-profile
    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute User user, @ModelAttribute UserDetails userDetails, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        User existingUser = userDao.getUserById(userId);
        UserDetails existingUserDetails = userDetailsDao.getUserDetailsById(userId);

        if (user.getLogin() != null && !user.getLogin().isBlank()) {
            existingUser.setLogin(user.getLogin());
        }
        existingUser.setPassword(user.getPassword());
        existingUserDetails.setFirstName(userDetails.getFirstName());
        existingUserDetails.setLastName(userDetails.getLastName());
        existingUserDetails.setEmail(userDetails.getEmail());

        userDao.updateUser(existingUser);
        userDetailsDao.updateUserDetails(existingUserDetails);

        session.setAttribute("user", existingUser);
        session.setAttribute("userDetails", existingUserDetails);

        model.addAttribute("success", "Profile updated successfully");
        model.addAttribute("user", existingUser);
        model.addAttribute("userDetails", existingUserDetails);

        return "edit-profile";
    }

    @PostMapping("/del-profile")
    public String deleteProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        userDetailsDao.deleteUserDetails(userId);
        transactionDao.deleteTransactionsByUserId(userId);
        goalDao.deleteGoalsByUserId(userId);
        budgetDao.deleteBudgetsByUserId(userId);

        userDao.deleteUser(userId);
        return "redirect:/logout";
    }

}