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

    @RequestMapping("/edit-profile")
    public String EditProfilePage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        User user = userDao.getUserById(userId);
        UserDetails userDetails = userDetailsDao.getUserDetailsById(userId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(userDetails.getBirthDate());

        model.addAttribute("user", user);
        model.addAttribute("userDetails", userDetails);

        return "edit-profile";
    }

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

        // Update email
        existingUserDetails.setEmail(userDetails.getEmail());

        // Save changes to the database
        userDao.updateUser(existingUser);
        userDetailsDao.updateUserDetails(existingUserDetails);

        // Refresh the user and userDetails objects in the session
        session.setAttribute("user", existingUser);
        session.setAttribute("userDetails", existingUserDetails);

        // Set success message
        model.addAttribute("success", "Profile updated successfully");

        // Add updated user and userDetails to the model for displaying in the form
        model.addAttribute("user", existingUser);
        model.addAttribute("userDetails", existingUserDetails);

        return "edit-profile";
    }


}