package com.example.testtariffparser.dto;

public record TariffFilter(
        String title,
        String description,
        Integer price,
        Integer internetLimit,
        Integer minutesLimit,
        Integer internetSpeed,
        Integer tvChannelLimit) {
}

