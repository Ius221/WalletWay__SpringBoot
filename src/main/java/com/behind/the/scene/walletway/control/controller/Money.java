package com.behind.the.scene.walletway.control.controller;

import com.behind.the.scene.walletway.control.payload.MoneyDTO;
import com.behind.the.scene.walletway.control.service.MoneyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Money {

    @Autowired
    private MoneyService moneyService;

    @PostMapping("/public/money")
    public ResponseEntity<MoneyDTO> getMoney(@Valid @RequestBody MoneyDTO moneyDTO) {
        MoneyDTO moneyDTO1 = moneyService.createMoneyRecord(moneyDTO);
        return new ResponseEntity<>(moneyDTO1, HttpStatus.OK);
    }
}
