package com.example.testtariffparser.helper;


import com.example.testtariffparser.entity.tariff.Tariff;
import com.example.testtariffparser.entity.tariff.TariffFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
public class TariffParserImpl implements TariffParser {
    @Value("${app.parser.base-url}")
    private String URL;
    private final WebDriver webDriver;

    public List<Tariff> parse() {
        webDriver.get(URL);
        WebElement seeMoreBtn = webDriver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/mts-tariffs-catalog/div[2]/div/div/div/mts-actual-tariffs-catalog/div/div[3]/mts-actual-tariffs/div[1]/mts-actual-tariffs-group/div/button"));
        seeMoreBtn.click();
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements tariffCards = doc.select(".card.card__wrapper");
        List<Tariff> tariffs = new ArrayList<>();
        for (Element tariffCard : tariffCards) {
            var cardTitle = tariffCard.select("span.card-title__link").text();
            var cardDescription = tariffCard.select(".card-description").text();
            var features = featureExtractor(tariffCard.select("span.feature-description.feature-description__text.feature-description__margin").eachText());
            Element priceDiv = tariffCard.select("div.price-main").first();
            String[] priceTokens = priceDiv.select("span.price-text").text().split(" ");
            var price = priceTokens.length > 2 ?Integer.parseInt(priceTokens[0]+priceTokens[1]):Integer.parseInt(priceTokens[0]);
            tariffs.add(Tariff.builder()
                    .title(cardTitle)
                    .description(cardDescription)
                    .tariffFeatures(features)
                    .price(price)
                    .build());
        }

        return tariffs;
    }


    private TariffFeature featureExtractor(List<String> features) {
        var tariffFeatures = new TariffFeature();
        for (var f : features) {
            String[] valueKey = f.split(" ");
            switch (valueKey[1]) {
                case "ГБ" -> tariffFeatures.setInternetLimit(Integer.parseInt(valueKey[0]));
                case "минут" -> tariffFeatures.setMinutesLimit(Integer.parseInt(valueKey[0]));
                case "Мбит/с" -> tariffFeatures.setInternetSpeed(Integer.parseInt(valueKey[0]));
                case "Гбит/с" -> tariffFeatures.setInternetSpeed(Integer.parseInt(valueKey[0]) * 1000);
                case "ТВ" -> tariffFeatures.setTvChannelLimit(Integer.parseInt(valueKey[0]));
            }
        }

        return tariffFeatures;
    }
}