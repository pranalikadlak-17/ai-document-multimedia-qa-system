package com.pranali.ai_doc_qa.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {
	@GetMapping("/test")
    public String test() {
        return "Backend Running Successfully";
    }
}
