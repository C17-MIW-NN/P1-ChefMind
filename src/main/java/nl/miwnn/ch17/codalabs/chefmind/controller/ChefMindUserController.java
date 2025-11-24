package nl.miwnn.ch17.codalabs.chefmind.controller;

import nl.miwnn.ch17.codalabs.chefmind.dto.NewChefMindUserDTO;
import nl.miwnn.ch17.codalabs.chefmind.service.ChefMindUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nelleke Jansen
 * Handles requests regarding ChefMind users.
 */

@Controller
@RequestMapping("/user")
public class ChefMindUserController {
    private final ChefMindUserService chefMindUserService;

    public ChefMindUserController(ChefMindUserService chefMindUserService) {
        this.chefMindUserService = chefMindUserService;
    }

    @GetMapping("/all")
    private String showUserOverview(Model datamodel) {
        datamodel.addAttribute("allUsers", chefMindUserService.getAllUsers());
        datamodel.addAttribute("formUser", new NewChefMindUserDTO());
        datamodel.addAttribute("formModalHidden", true);

        return "userOverview";
    }

    @PostMapping("/save")
    private String saveOrUpdateUser(@ModelAttribute("formUser") NewChefMindUserDTO userDtoToBeSaved,
                                    BindingResult result, Model datamodel) {
        if (chefMindUserService.usernameInUse(userDtoToBeSaved.getUsername())) {
            result.rejectValue("username", "duplicate", "This username already exists");
        }

        if (!userDtoToBeSaved.getPassword().equals(userDtoToBeSaved.getConfirmPassword())) {
            result.rejectValue("password", "no.match", "The passwords do not match");
        }

        if (result.hasErrors()) {
            datamodel.addAttribute("allUsers", chefMindUserService.getAllUsers());
            datamodel.addAttribute("formModalHidden", false);
            return "userOverview";
        }

        chefMindUserService.save(userDtoToBeSaved);
        return "redirect:/user/all";
    }
}
