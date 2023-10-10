package com.example.testtariffparser.repository;

import com.example.testtariffparser.entity.tariff.Tariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.querydsl.core.types.Predicate;
import java.util.Optional;


public interface TariffRepository extends
        JpaRepository<Tariff, Long>,
        QuerydslPredicateExecutor<Tariff> {

    Optional<Tariff> findByTitleIgnoreCase(String title);

    @Override
    Page<Tariff> findAll(Predicate predicate, Pageable pageable);
}
