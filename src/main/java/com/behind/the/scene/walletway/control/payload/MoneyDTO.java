package com.behind.the.scene.walletway.control.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDTO {
    private Long id;
    private String description;
    private String entryType;
    private String entryCategory;
    private String entryMode;
    private String note;
    private Double amount;
    private LocalDateTime createdAt;
}

