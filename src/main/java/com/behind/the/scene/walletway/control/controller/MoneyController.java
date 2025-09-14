package com.behind.the.scene.walletway.control.controller;

import com.behind.the.scene.walletway.control.config.AppConstants;
import com.behind.the.scene.walletway.control.payload.MoneyDTO;
import com.behind.the.scene.walletway.control.payload.MoneyResponse;
import com.behind.the.scene.walletway.control.service.MoneyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    @GetMapping("/public/money")
    public ResponseEntity<MoneyResponse> retrieveAllMoney(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        MoneyResponse retrievedMoneyResponse = moneyService.retrieveAllMoney(pageNum, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(retrievedMoneyResponse, HttpStatus.FOUND);
    }

    @PostMapping("/public/money")
    public ResponseEntity<MoneyDTO> createMoneyRecord(@Valid @RequestBody MoneyDTO moneyDTO) {
        MoneyDTO moneyDTO1 = moneyService.createMoneyRecord(moneyDTO);
        return new ResponseEntity<>(moneyDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/public/money/{moneyId}")
    public ResponseEntity<MoneyDTO> updateMoneyRecord(@Valid @RequestBody MoneyDTO moneyDTO, @PathVariable Long moneyId) {
        MoneyDTO moneyDTO1 = moneyService.updateMoneyRecord(moneyDTO,moneyId);
        return new ResponseEntity<>(moneyDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/public/money/{moneyId}")
    public ResponseEntity<String> deleteMoneyRecord(@PathVariable Long moneyId) {
        String message = moneyService.deleteMoneyRecord(moneyId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
