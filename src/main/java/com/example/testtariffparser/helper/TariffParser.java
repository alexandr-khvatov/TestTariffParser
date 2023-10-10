package com.example.testtariffparser.helper;

import com.example.testtariffparser.entity.tariff.Tariff;

import java.util.List;

public interface TariffParser {
    List<Tariff> parse();
}
