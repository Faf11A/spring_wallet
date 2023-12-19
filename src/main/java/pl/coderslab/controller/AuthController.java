package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.dao.*;
import pl.coderslab.model.Category;
import pl.coderslab.model.User;
import pl.coderslab.model.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Controller
public class AuthController {

    private final UserDao userDao;
    private final UserDetailsDao userDetailsDao;
    private final BudgetDao budgetDao;
    private final TransactionDao transactionDao;
    private final CategoryDao categoryDao;


    @Autowired
    public AuthController(UserDao userDao, UserDetailsDao userDetailsDao, BudgetDao budgetDao, TransactionDao transactionDao, CategoryDao categoryDao) {
        this.userDao = userDao;
        this.userDetailsDao = userDetailsDao;
        this.budgetDao = budgetDao;
        this.transactionDao = transactionDao;
        this.categoryDao = categoryDao;
    }

    //show login page
    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(value = "mode", defaultValue = "login") String mode) {
        model.addAttribute("mode", mode);
        return "login";
    }

    //signing in
    @PostMapping("/login")
    public String processLogin(@RequestParam("login") String login,
                               @RequestParam("password") String password,
                               HttpSession session,
                               RedirectAttributes attributes) {
        String pswd = userDao.findPasswordByLogin(login);

        if (pswd != null && Objects.equals(pswd, password)) {
            Long userId = userDao.findUserIdByLogin(login);
            String username = userDetailsDao.findUsernameById(userId);
            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            return "redirect:/wallet";
        } else {
            attributes.addFlashAttribute("error", "Invalid login or password");
            return "redirect:/login";
        }
    }

    //registration
    @PostMapping("/register")
    public String processRegistration(@RequestParam("firstname") String firstName,
                                      @RequestParam("lastname") String lastName,
                                      @RequestParam("email") String email,
                                      @RequestParam("login") String login,
                                      @RequestParam("password") String password,
                                      @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setEmail(email);
        userDetails.setBirthDate(dob);
        userDetails.setUser(user);

        userDao.saveUser(user);
        userDetailsDao.saveUserDetails(userDetails);
        budgetDao.createFirstBudgetForNewUser(user);

        Category category = categoryDao.findById(100L);
        transactionDao.addFirstTransaction(category, user);


        return "redirect:/login?mode=login";
    }

    //logging out
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
