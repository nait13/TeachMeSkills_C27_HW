package org.lesson41.controller;

import org.lesson41.DTO.User;
import org.lesson41.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public ModelAndView getUserForm(@RequestParam(name = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView();
        if (id == null || id < 1) {
            mav.setViewName("getUserForm");
            return mav;
        } else {
            User user = userService.getUserById(id);
            if (user != null) {
                mav.addObject("body", "Name: " + user.getName() + ", Login: " + user.getLogin());
            } else {
                mav.addObject("body", "Not found user!");
            }
            mav.setViewName("information");
        }
        return mav;
    }

    @RequestMapping("/create")
    public String createUserPage() {
        return "createUserForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createUserDB(@RequestParam("nameInput") String name, @RequestParam("login") String login) {
        ModelAndView mav = new ModelAndView();
        String userName = name.trim();
        String userLogin = login.trim();

        if (userName == null || userName.isEmpty() || userLogin == null || userLogin.isEmpty()) {
            mav.setViewName("createUserForm");
            return mav;
        } else {
            boolean isCreate = userService.createUser(userName, userLogin);
            if (isCreate) {
                mav.addObject("body", "Success insert");
            } else {
                mav.addObject("body", "Error insert");
            }
            mav.setViewName("information");
            return mav;
        }
    }

    @RequestMapping("/delete")
    public String getDeleteUserPage() {
        return "deleteUserForm";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView getDeleteUserInDB(@RequestParam("userId") String id) {
        ModelAndView mav = new ModelAndView();
        String userId = id.trim();

        if (userId == null || userId.isEmpty()) {
            mav.setViewName("deleteUserForm");
            return mav;
        } else {
            boolean isDelete = userService.deleteUser(Integer.parseInt(userId));
            if (isDelete) {
                mav.addObject("body", "User id :" + userId + " Success delete");
            } else {
                mav.addObject("body", "Error delete");
            }
        }
        mav.setViewName("information");
        return mav;
    }

    @GetMapping("/change")
    public String changeLoginPage() {
        return "updateLogin";
    }

    @PostMapping("/change")
    public ModelAndView changeLoginInDB(@RequestParam("userId") String id, @RequestParam("login") String login) {
        ModelAndView mav = new ModelAndView();
        String userId = id.trim();
        String userLogin = login.trim();

        if (id == null || userId.isEmpty() || login == null || userLogin.isEmpty()) {
            mav.setViewName("updateLogin");
            return mav;
        } else {
            boolean isUpdate = userService.updateUserLogin(Integer.parseInt(userId), userLogin);
            if (isUpdate) {
                mav.addObject("body", "Success change login");
            } else {
                mav.addObject("body", "Error change login ");
            }
            mav.setViewName("information");
            return mav;
        }

    }
//    @ExceptionHandler(RuntimeException.class)
//    public String handleException(Exception e){
//        e.printStackTrace();
//        return "error";
//    }
}
