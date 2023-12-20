package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.model.Goal;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GoalController {

    private List<Goal> goals = new ArrayList<>();

    @GetMapping("/goals")
    public String showGoals(Model model) {
        model.addAttribute("goals", goals);
        model.addAttribute("newGoal", new Goal());
        return "goals";
    }

    @PostMapping("/goals")
    public String addGoal(Goal newGoal) {
        goals.add(newGoal);
        return "redirect:/goals";
    }
}