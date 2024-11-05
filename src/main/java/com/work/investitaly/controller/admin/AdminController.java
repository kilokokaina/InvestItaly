package com.work.investitaly.controller.admin;

import com.work.investitaly.dto.AdvertiseDTO;
import com.work.investitaly.model.Advertise;
import com.work.investitaly.model.FileModel;
import com.work.investitaly.model.RealEstateType;
import com.work.investitaly.service.AdvertiseService;
import com.work.investitaly.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    private final AdvertiseService advertiseService;
    private final FileService fileService;

    private @Value("${storage.path}") String PATH;

    @Autowired
    public AdminController(AdvertiseService advertiseService, FileService fileService) {
        this.advertiseService = advertiseService;
        this.fileService = fileService;
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

    @PostMapping("add_advertise")
    public String addAdvertiseProcess(@ModelAttribute AdvertiseDTO advertiseDTO) throws IOException {
        var advertise = new Advertise();
        var addFiles = new HashSet<FileModel>();

        for (var file : advertiseDTO.getFiles()) {
            var dbFile = new FileModel();

            String fileUUID = UUID.randomUUID() + "." + file.getOriginalFilename();
            String filePath = PATH + fileUUID;

            file.transferTo(Path.of(filePath));

            dbFile.setFileName(fileUUID);
            dbFile.setFilePath(filePath);

            fileService.save(dbFile);
            addFiles.add(dbFile);

            if (Objects.equals(file.getOriginalFilename(), advertiseDTO.getThumbName())) {
                advertise.setThumbnail(dbFile);
            }

            log.info(file.getOriginalFilename());
        }

        advertise.setTitle(advertiseDTO.getTitle());
        advertise.setText(advertiseDTO.getDescription());
        advertise.setPlacement(advertiseDTO.getAddress());
        advertise.setSquare(advertiseDTO.getSquare());
        advertise.setPricePerSquare(advertiseDTO.getPrice());
        advertise.setBedroomCount(advertiseDTO.getBedroom());
        advertise.setBathroomCount(advertiseDTO.getBathroom());

        advertise.setType(RealEstateType.valueOf(advertiseDTO.getType()));

        advertise.setPhotos(addFiles);

        advertiseService.save(advertise);

        return "redirect:/admin/add_advertise";
    }

    @GetMapping("edit_advertise")
    public String editAdvertise(@RequestParam(name = "id") long advertiseId, Model model) {
        model.addAttribute("advertise", advertiseService.findById(advertiseId));
        model.addAttribute("advertiseForm", new AdvertiseDTO());

        return "admin/edit_advertise";
    }

    @PostMapping("edit_advertise")
    public String editAdvertiseProcess(@RequestParam(name = "id") Long advertiseId, @ModelAttribute AdvertiseDTO advertiseDTO) {
        log.info(String.valueOf(advertiseId));
        log.info(advertiseDTO.toString());

        var advertise = advertiseService.findById(advertiseId);

        advertise.setTitle(advertiseDTO.getTitle());
        advertise.setText(advertiseDTO.getDescription());
        advertise.setPlacement(advertiseDTO.getAddress());
        advertise.setSquare(advertiseDTO.getSquare());
        advertise.setPricePerSquare(advertiseDTO.getPrice());
        advertise.setBedroomCount(advertiseDTO.getBedroom());
        advertise.setBathroomCount(advertiseDTO.getBathroom());

        advertiseService.save(advertise);

        return String.format("redirect:/admin/edit_advertise?id=%d", advertiseId);
    }

}
