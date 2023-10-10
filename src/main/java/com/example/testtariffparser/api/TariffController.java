package com.example.testtariffparser.api;

import com.example.testtariffparser.service.TariffService;
import com.example.testtariffparser.dto.PageResponse;
import com.example.testtariffparser.dto.TariffFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TariffController {
    private final TariffService tariffService;

    @GetMapping("/tariffs")
    public String getTariffs(
            Model model,
            TariffFilter filter,
            Pageable pageable) {
        model.addAttribute("tariffs", PageResponse.of(tariffService.findAll(filter, pageable)));
        model.addAttribute("filter", filter);
        return "tariffs";
    }

    @PostMapping("/tariffs/parse")
    public String updateTariffs() {
        tariffService.update();
        return "redirect:/tariffs";
    }
}
