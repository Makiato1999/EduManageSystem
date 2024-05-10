package com.easybytes.easyschool.controller;

import com.easybytes.easyschool.model.Contact;
import com.easybytes.easyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {
    // 如果使用lambok，那就不需要下面这个了
    // private static Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value={"/contact"})
    public String displayContactPage(Model model) {
        //model.addAttribute("username", "Shawn Xie");
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

//    @RequestMapping(value = "/saveMsg", method = POST)
//    //@PostMapping(value = "/saveMsg")
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum,
//                                    @RequestParam String email, @RequestParam String subject,
//                                    @RequestParam String message) {
//        log.info("Name : "+name);
//        log.info("mobileNum : "+mobileNum);
//        log.info("email : "+email);
//        log.info("subject : "+subject);
//        log.info("message : "+message);
//        return new ModelAndView("redirect:/contact");
//    }
    @RequestMapping(value = "/saveMsg", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Contact from validation failed due to : "+errors.toString());
            return "contact.html";
        } // catch the errors and display to end user所以我们需要去修改contact的前端

        contactService.saveMessageDetails(contact);

        /*
        contactService.setCounter(contactService.getCounter() + 1);
        log.info("Number of times the Contact form is submitted : "+contactService.getCounter());

         */

        return "redirect:/contact";
    }

    /* before example 43
    @RequestMapping(value = "/displayMessages", method = GET)
    public ModelAndView displayMessage(Model model) {
        List<Contact> contactMsgs = contactService.findMsgWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs", contactMsgs);
        return modelAndView;
    }

     */
    @RequestMapping(value = "/displayMessages/page/{pageNum}")
    public ModelAndView displayMessage(@PathVariable(name = "pageNum") int pageNum,
                                       @RequestParam("sortField") String sortField,
                                       @RequestParam("sortDir") String sortDir) {
        Page<Contact> msgPage = contactService.findMsgWithOpenStatus(pageNum, sortField, sortDir);

        List<Contact> contactMsgs = msgPage.getContent();

        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("currentPage", pageNum);
        modelAndView.addObject("totalPages", msgPage.getTotalPages());
        modelAndView.addObject("totalMsgs", msgPage.getTotalPages());
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs", contactMsgs);
        return modelAndView;
    }


    @RequestMapping(value = "/closeMsg", method = GET)
    public String closeMsg(@RequestParam int id/*, Authentication authentication*/) {
        contactService.updateMsgStatus(id);
        /* before example 36, at that time we didn't use audit
        contactService.updateMsgStatus(id, authentication.getName());

         */
        // 使用Authentication获取当前用户：Authentication authentication参数允许方法访问当前认证的用户的信息。
        // 在这个上下文中，authentication.getName()会获取当前用户的用户名，这通常用于确定是哪个用户执行了关闭消息的操作。
       // return "redirect:/displayMessages";

        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }
}
