package com.work.investitaly.controller.admin;

import com.work.investitaly.dto.AdvertiseDTO;
import com.work.investitaly.exception.AdvertiseNotFound;
import com.work.investitaly.service.AdvertiseService;
import com.work.investitaly.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    private final AdvertiseService advertiseService;
    private final RequestService requestService;

    @Autowired
    public AdminController(AdvertiseService advertiseService, RequestService requestService) {
        this.advertiseService = advertiseService;
        this.requestService = requestService;
    }

    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("advertises", advertiseService.findAll());
        return "admin/index";
    }

    @GetMapping("add_advertise")
    public String addAdvertise(Model model) {
        model.addAttribute("advertiseForm", new AdvertiseDTO());
        return "admin/add_advertise";
    }

    @GetMapping("edit_advertise")
    public String editAdvertise(@RequestParam(name = "id") long advertiseId, Model model) {
        try {
            model.addAttribute("advertise", advertiseService.findById(advertiseId));
        } catch (AdvertiseNotFound exception) {
            model.addAttribute("advertise", null);
        }

        model.addAttribute("advertiseForm", new AdvertiseDTO());

        return "admin/edit_advertise";
    }

    @GetMapping("requests")
    public String requests(Model model) {
        model.addAttribute("requests", requestService.findAll());
        return "admin/requests";
    }
}
