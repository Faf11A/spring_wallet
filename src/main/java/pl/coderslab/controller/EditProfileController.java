package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.dao.UserDao;
import pl.coderslab.dao.UserDetailsDao;
import pl.coderslab.model.User;
import pl.coderslab.model.UserDetails;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EditProfileController {

    private final UserDao userDao;
    private final UserDetailsDao userDetailsDao;

    @Autowired
    public EditProfileController(UserDao userDao, UserDetailsDao userDetailsDao) {
        this.userDao = userDao;
        this.userDetailsDao = userDetailsDao;
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
}