package com.behind.the.scene.walletway.control.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Money {
    @GetMapping
    public String getMoney() {
        return "Money";
    }
}
