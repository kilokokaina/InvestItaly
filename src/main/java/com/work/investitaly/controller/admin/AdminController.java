package com.work.investitaly.controller.admin;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
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
    public String addAdvertise() {
        return "admin/add_advertise";
    }

    @PostMapping("add_advertise")
    public String addAdvertisePost(@RequestParam(name = "title") String title, @RequestParam(name = "address") String address,
                                   @RequestParam(name = "type") int type, @RequestParam(name = "square") float square,
                                   @RequestParam(name = "price") float price, @RequestParam(name = "description") String description,
                                   @RequestParam(name = "files") MultipartFile[] files, @RequestParam(name = "isThumbnail") String thumbName) throws IOException {
        var advertise = new Advertise();
        var addFiles = new HashSet<FileModel>();

        for (var file : files) {
            var dbFile = new FileModel();

            String fileUUID = UUID.randomUUID() + "." + file.getOriginalFilename();
            String filePath = PATH + fileUUID;

            file.transferTo(Path.of(filePath));

            dbFile.setFileName(fileUUID);
            dbFile.setFilePath(filePath);

            fileService.save(dbFile);
            addFiles.add(dbFile);

            if (Objects.equals(file.getOriginalFilename(), thumbName)) {
                advertise.setThumbnail(dbFile);
            }

            log.info(file.getOriginalFilename());
        }

        advertise.setTitle(title);
        advertise.setText(description);
        advertise.setPlacement(address);
        advertise.setSquare(square);
        advertise.setPricePerSquare(price);

        switch (type) {
            case 1 -> advertise.setType(RealEstateType.RESIDENTIAL);
            case 2 -> advertise.setType(RealEstateType.COMMERCIAL);
        }

        advertise.setPhotos(addFiles);

        advertiseService.save(advertise);

        return "redirect:/admin/add_advertise";
    }

}
