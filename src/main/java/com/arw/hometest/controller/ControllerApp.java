package com.arw.hometest.controller;

import com.arw.hometest.dto.OperatorTaskDto;
import com.arw.hometest.service.CourierService;
import com.arw.hometest.service.OperatorService;
import com.arw.hometest.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;


@Controller
public class ControllerApp {

    @Autowired
    private CourierService courierService;

    @Autowired
    private OperatorService operatorService;

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Start Page");
        model.addAttribute("message", "Click on courier or operator link");
        return "welcomePage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("userInfo", userInfo);
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }
        return "403Page";
    }

    @RequestMapping(value = "/courier", method = RequestMethod.GET)
    public String courierPage(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        String userName = principal.getName();
        List<String> tasks = courierService.getTasks(userName);
        model.addAttribute("tasks", tasks);

        return "courierPage";
    }

    @RequestMapping(value = "/operator", method = RequestMethod.GET)
    public String operatorPage(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        String userName = principal.getName();
        List<OperatorTaskDto> tasks = operatorService.getTasks(userName);
        model.addAttribute("tasks", tasks);

        return "operatorPage";
    }

    @RequestMapping(value="courier/notInTime", method = RequestMethod.POST)
    public String deleteUser (@Valid String orderCode) {
        if (!orderCode.isEmpty()){
            courierService.setTaskDisabled(orderCode);
        }
        return "redirect:/courier";
    }
}
