package com.behind.the.scene.walletway.control.service;

import com.behind.the.scene.walletway.control.entity.Money;
import com.behind.the.scene.walletway.control.exception.APIException;
import com.behind.the.scene.walletway.control.payload.MoneyDTO;
import com.behind.the.scene.walletway.control.payload.MoneyResponse;
import com.behind.the.scene.walletway.control.repositories.MoneyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    private MoneyRepository moneyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MoneyDTO createMoneyRecord(MoneyDTO moneyDTO) {

        final String isIncome = moneyDTO.getEntryType();
        if (isIncome.equalsIgnoreCase("expense")) moneyDTO.setAmount(-moneyDTO.getAmount());

        Money money = modelMapper.map(moneyDTO, Money.class);
        Money savedMoney = moneyRepository.save(money);

        return modelMapper.map(savedMoney, MoneyDTO.class);
    }

    @Override
    public MoneyResponse retrieveAllMoney(Integer pageNum, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNum, pageSize, sortByAndOrder);
        Page<Money> moneyPage = moneyRepository.findAll(page);

        List<Money> moneyList = moneyPage.getContent();

        if(moneyList.isEmpty()) throw new APIException("No money records found");

        double totalBalance = moneyList.stream()
                .mapToDouble(Money::getAmount)
                .sum();

        double totalIncome = moneyList.stream()
                .filter(money -> money.getEntryType().equalsIgnoreCase("income"))
                .mapToDouble(Money::getAmount)
                .sum();

        double totalExpense = moneyList.stream()
                .filter(money -> money.getEntryType().equalsIgnoreCase("expense"))
                .mapToDouble(Money::getAmount)
                .sum();

        List<MoneyDTO> moneyDTOSList = moneyList.stream()
                .map(money-> modelMapper.map(money, MoneyDTO.class))
                .toList();

        MoneyResponse moneyResponse = new MoneyResponse();
        moneyResponse.setContent(moneyDTOSList);
        moneyResponse.setPageSize(moneyPage.getSize());
        moneyResponse.setPageNo(moneyPage.getTotalPages());
        moneyResponse.setTotalElements(moneyPage.getTotalElements());
        moneyResponse.setTotalPages(moneyPage.getTotalPages());
        moneyResponse.setTotalBalance(totalBalance);
        moneyResponse.setTotalExpense(totalExpense);
        moneyResponse.setTotalIncome(totalIncome);

        return moneyResponse;
    }
}
