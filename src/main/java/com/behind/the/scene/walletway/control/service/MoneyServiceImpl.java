package com.behind.the.scene.walletway.control.service;

import com.behind.the.scene.walletway.control.entity.Money;
import com.behind.the.scene.walletway.control.payload.MoneyDTO;
import com.behind.the.scene.walletway.control.repositories.MoneyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyServiceImpl implements MoneyService{

    @Autowired
    private MoneyRepository moneyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MoneyDTO createMoneyRecord(MoneyDTO moneyDTO) {
        System.out.println(moneyDTO+"<-------");
        Money money = modelMapper.map(moneyDTO, Money.class);
        System.out.println(money+"<-------");
//        log.info("Incoming MoneyDTO: {}", moneyDTO);

        Money savedMoney = moneyRepository.save(money);
        System.out.println(savedMoney+"<-------");

        return modelMapper.map(savedMoney, MoneyDTO.class);
    }
}
