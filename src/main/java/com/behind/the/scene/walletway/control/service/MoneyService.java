package com.behind.the.scene.walletway.control.service;

import com.behind.the.scene.walletway.control.payload.MoneyDTO;
import com.behind.the.scene.walletway.control.payload.MoneyResponse;

public interface MoneyService {
    public MoneyDTO createMoneyRecord(MoneyDTO moneyDTO);
    public MoneyResponse retrieveAllMoney(Integer pageNum, Integer pageSize, String sortBy, String sortOrder);
    public MoneyDTO updateMoneyRecord(MoneyDTO moneyDTO, Long moneyId);
    public String deleteMoneyRecord(Long moneyId);
    public MoneyDTO getMoneyRecordById(Long moneyId);
    public MoneyResponse fetchByDesc(String description,Integer pageSize, Integer pageNum);
}
