package com.behind.the.scene.walletway.control.repositories;

import com.behind.the.scene.walletway.control.entity.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MoneyRepository extends JpaRepository<Money,Long> {
    @Query("""
        SELECT m
        FROM Money m
        WHERE LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(m.note) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Money> searchByDescriptionOrNote(@Param("keyword") String keyword, Pageable pageable);
//    List<Money> findByDescriptionLikeIgnoreCase(String description);
}
