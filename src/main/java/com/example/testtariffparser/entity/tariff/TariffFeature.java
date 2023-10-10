package com.example.testtariffparser.entity.tariff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class TariffFeature {
    @ColumnDefault("0")
    private Integer internetLimit;
    @ColumnDefault("0")
    private Integer minutesLimit;
    @ColumnDefault("0")
    private Integer internetSpeed;
    @ColumnDefault("0")
    private Integer tvChannelLimit;
}
