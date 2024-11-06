package com.work.investitaly.controller.client;

import com.work.investitaly.model.Advertise;
import com.work.investitaly.model.FileModel;
import com.work.investitaly.model.RealEstateType;
import com.work.investitaly.service.AdvertiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        var priceFormatMap = new HashMap<Advertise, String>();

        advertises.sort((first, second) -> {
            if (first.getDate().getTime() > second.getDate().getTime()) return 1;
            else if (first.getDate() == second.getDate()) return 0;
            else return -1;
        });

        if (type != null) {
            switch (type) {
                case RESIDENTIAL -> advertises = advertises.parallelStream().filter(advertise ->
                        advertise.getType().equals(RealEstateType.RESIDENTIAL)).toList();
                case COMMERCIAL -> advertises = advertises.parallelStream().filter(advertise ->
                        advertise.getType().equals(RealEstateType.COMMERCIAL)).toList();
            }
        }

        advertises.forEach(advertise ->
                priceFormatMap.put(advertise, new DecimalFormat("###,###.##").format(advertise.getTotalPrice()))
        );

        model.addAttribute("priceFormat", priceFormatMap);
        model.addAttribute("advertises", advertises);

        return "catalog";
    }

    @GetMapping("advertise/{id}")
    public String advertise(@PathVariable(name = "id") Advertise advertise, Model model) {
        Set<FileModel> photos = advertise.getPhotos();
        photos = photos.stream().sorted(Comparator.comparing(FileModel::getFileName))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        model.addAttribute("priceFormat",
                new DecimalFormat("###,###.##").format(advertise.getTotalPrice())
        );
        model.addAttribute("advertise", advertise);
        model.addAttribute("photos", photos);

        return "advertise";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }

}
