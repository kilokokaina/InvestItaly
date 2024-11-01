package com.work.investitaly.controller.client;

import com.work.investitaly.model.Advertise;
import com.work.investitaly.model.RealEstateType;
import com.work.investitaly.service.AdvertiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class MainController {

    private final AdvertiseService advertiseService;

    @Autowired
    public MainController(AdvertiseService advertiseService) {
        this.advertiseService = advertiseService;
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("catalog")
    public String catalog(@RequestParam(name = "type", required = false) RealEstateType type, Model model) {
        var advertises = advertiseService.findAll();

        if (type != null) {
            switch (type) {
                case RESIDENTIAL -> advertises = advertises.parallelStream().filter(advertise ->
                        advertise.getType().equals(RealEstateType.RESIDENTIAL)).toList();
                case COMMERCIAL -> advertises = advertises.parallelStream().filter(advertise ->
                        advertise.getType().equals(RealEstateType.COMMERCIAL)).toList();
            }
        }

        model.addAttribute("advertises", advertises);

        return "catalog";
    }

    @GetMapping("advertise/{id}")
    public String advertise(@PathVariable(name = "id") Advertise advertise, Model model) {
        model.addAttribute(advertise);

        return "advertise";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }

}
