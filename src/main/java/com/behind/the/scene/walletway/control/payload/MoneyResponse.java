package com.behind.the.scene.walletway.control.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyResponse {
    private List<MoneyDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private double totalBalance;
    private double totalIncome;
    private double totalExpense;
}
