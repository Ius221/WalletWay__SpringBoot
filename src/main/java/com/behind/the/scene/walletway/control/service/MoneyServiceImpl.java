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

        if (moneyList.isEmpty()) throw new APIException("No money records found");
        List<Money> moneyWithoutPage = moneyRepository.findAll();

        double totalBalance = moneyWithoutPage.stream()
                .mapToDouble(Money::getAmount)
                .sum();

        double totalIncome = moneyWithoutPage.stream()
                .filter(money -> money.getEntryType().equalsIgnoreCase("income"))
                .mapToDouble(Money::getAmount)
                .sum();

        double totalExpense = moneyWithoutPage.stream()
                .filter(money -> money.getEntryType().equalsIgnoreCase("expense"))
                .mapToDouble(Money::getAmount)
                .sum();

        List<MoneyDTO> moneyDTOSList = moneyList.stream()
                .map(money -> modelMapper.map(money, MoneyDTO.class))
                .toList();

        MoneyResponse moneyResponse = new MoneyResponse();
        moneyResponse.setContent(moneyDTOSList);
        moneyResponse.setPageSize(moneyPage.getSize());
        moneyResponse.setPageNo(moneyPage.getNumber());
        moneyResponse.setTotalElements(moneyPage.getTotalElements());
        moneyResponse.setTotalPages(moneyPage.getTotalPages());
        moneyResponse.setTotalBalance(totalBalance);
        moneyResponse.setTotalExpense(totalExpense);
        moneyResponse.setTotalIncome(totalIncome);

        return moneyResponse;
    }

    @Override
    public MoneyDTO updateMoneyRecord(MoneyDTO moneyDTO, Long moneyId) {

        Money fetchedMoneyData = moneyRepository
                .findById(moneyId)
                .orElseThrow(() -> new APIException("Money record not found"));

        fetchedMoneyData.setAmount(moneyDTO.getAmount());
        fetchedMoneyData.setDescription(moneyDTO.getDescription());
        fetchedMoneyData.setEntryType(moneyDTO.getEntryType());
        fetchedMoneyData.setEntryCategory(moneyDTO.getEntryCategory());
        fetchedMoneyData.setEntryMode(moneyDTO.getEntryMode());
        fetchedMoneyData.setNote(moneyDTO.getNote());

        Money updatedMoneyData = moneyRepository.save(fetchedMoneyData);

        return modelMapper.map(updatedMoneyData, MoneyDTO.class);
    }

    @Override
    public String deleteMoneyRecord(Long moneyId) {

        Money moneyData = moneyRepository
                .findById(moneyId)
                .orElseThrow(() -> new APIException("Data Not Found"));

        moneyRepository.delete(moneyData);

        return "Successfully Deleted the Data with data id: " + moneyId;
    }

    @Override
    public MoneyDTO getMoneyRecordById(Long moneyId) {

        Money money = moneyRepository
                .findById(moneyId)
                .orElseThrow(() -> new APIException("Money record not found"));

        return modelMapper.map(money, MoneyDTO.class);

    }

    @Override
    public MoneyResponse fetchByDesc(String description,Integer pageSize, Integer pageNum) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Money> fetchedPage = moneyRepository.searchByDescriptionOrNote("%" + description.trim() + "%",pageable);

        if (fetchedPage.isEmpty()) throw new APIException("No money records found");

        List<Money> moneyList = fetchedPage.getContent();

        List<MoneyDTO> moneyDTOS = moneyList.stream()
                .map((e) -> modelMapper.map(e, MoneyDTO.class))
                .toList();

        MoneyResponse moneyResponse = new MoneyResponse();
        moneyResponse.setContent(moneyDTOS);
        moneyResponse.setPageNo(fetchedPage.getNumber());
        moneyResponse.setPageSize(fetchedPage.getSize());
        moneyResponse.setTotalElements(fetchedPage.getTotalElements());
        moneyResponse.setTotalPages(fetchedPage.getTotalPages());

        return moneyResponse;
    }

}
