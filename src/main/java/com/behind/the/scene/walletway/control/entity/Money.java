package com.behind.the.scene.walletway.control.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Money {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String description;

    @NotBlank
    private String entryType;

    @NotBlank
    @Size(min=2, max=50)
    private String entryCategory;

    @NotBlank
    @Size(min = 2)
    private String entryMode;

    @NotBlank
    @Size(min = 5, max=100)
    private String note;

    @NotNull
    private Double amount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
