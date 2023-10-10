package com.example.testtariffparser.service;

import com.example.testtariffparser.dto.TariffFilter;
import com.example.testtariffparser.entity.tariff.Tariff;
import com.example.testtariffparser.helper.TariffParserImpl;
import com.example.testtariffparser.querydsl.QPredicates;
import com.example.testtariffparser.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.testtariffparser.entity.tariff.QTariff.tariff;

@Service
@Transactional()
@RequiredArgsConstructor
public class TariffService {

    private final TariffRepository tariffRepository;

    private final TariffParserImpl tariffParser;

    public Page<Tariff> findAll(TariffFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.title(), tariff.title::containsIgnoreCase)
                .add(filter.description(), tariff.description::containsIgnoreCase)
                .add(filter.internetLimit(), tariff.tariffFeatures.internetLimit::goe)
                .add(filter.minutesLimit(), tariff.tariffFeatures.minutesLimit::goe)
                .add(filter.internetSpeed(), tariff.tariffFeatures.internetSpeed::goe)
                .add(filter.tvChannelLimit(), tariff.tariffFeatures.tvChannelLimit::goe)
                .add(filter.price(), tariff.price::loe)
                .build();
        return tariffRepository.findAll(predicate, pageable);
    }

    public void update() {
        List<Tariff> tariffs = tariffParser.parse();
        for (var tariff : tariffs) {
            tariffRepository.findByTitleIgnoreCase(tariff.getTitle())
                    .ifPresentOrElse(
                            alreadyExistTariff -> tariffRepository.saveAndFlush(updateMapper(tariff, alreadyExistTariff)),
                            () -> tariffRepository.saveAndFlush(tariff));
        }
    }

    private Tariff updateMapper(Tariff s, Tariff t) {
        t.setTitle(s.getTitle());
        t.setDescription(s.getDescription());
        t.setPrice(s.getPrice());
        t.setTariffFeatures(s.getTariffFeatures());
        return t;
    }
}