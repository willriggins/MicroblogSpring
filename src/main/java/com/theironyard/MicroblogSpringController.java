package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by will on 6/20/16.
 */
@Controller
public class MicroblogSpringController {

    @Autowired
    MessageRepository messages;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {

        String username = (String) session.getAttribute("name");

        if (username == null) {
            return "login";
        }
        else {
            Iterable<Message> msgs = messages.findAll();
            model.addAttribute("messages", msgs);
            return "home";
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String name, String password, HttpSession session) throws Exception {

        User user = users.findByName(name);
        if (user == null) {
            user = new User(name, password);
            users.save(user);
        }
        else if (!user.password.equals(password)) {
            throw new Exception("Wrong password!");
        }

        session.setAttribute("name", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
    public String addMessage(String text) {
        Message msg = new Message(text);
        messages.save(msg);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.POST)
    public String deleteMessage(int id) {
        messages.delete(id);
        return "redirect:/";
    }

//    @RequestMapping(path = "/edit-message", method = RequestMethod.POST);
//    public String editMessage(String text, int id, HttpSession session) {
//        String editId = (String) session.getAttribute("editId");
//        id = Integer.valueOf(editId);
//        Message msg = new Message(text);
//        messages.save(msg);
//    }

}
