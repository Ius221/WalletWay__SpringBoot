package com.behind.the.scene.walletway.control.repositories;

import com.behind.the.scene.walletway.control.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money,Long> {
}
