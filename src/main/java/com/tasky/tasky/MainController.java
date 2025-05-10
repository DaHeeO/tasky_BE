package com.tasky.tasky;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    @ResponseBody
    public String main() {
        return "mainRoute ";
    }

    @GetMapping("/my")
    @ResponseBody
    public String my() {
        return "My Route";
    }
}
