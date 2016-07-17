package com.example.web;

import com.example.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resources;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
class OAuth2Controller {
    @Autowired
    OAuth2RestTemplate restTemplate;

    @Value("${message.api:http://localhost:18080/messages}")
    String messageApi;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    String home(Model model) {
        Resources<Message> messages = restTemplate.getForObject(messageApi + "?sort=createdAt,DESC", Resources.class);
        model.addAttribute("messages", messages.getContent());
        return "index";
    }

    @RequestMapping(path = "/messages", method = RequestMethod.POST)
    String post(@RequestParam String text) {
        restTemplate.postForObject(messageApi, Collections.singletonMap("text", text), Void.class);
        return "redirect:/";
    }
}
