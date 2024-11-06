package com.work.investitaly.api;

import com.work.investitaly.dto.AdvertiseDTO;
import com.work.investitaly.exception.AdvertiseNotFound;
import com.work.investitaly.model.Advertise;
import com.work.investitaly.model.FileModel;
import com.work.investitaly.model.RealEstateType;
import com.work.investitaly.service.FileService;
import com.work.investitaly.service.impl.AdvertiseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/advertise")
public class AdvertiseAPI {

    private final AdvertiseServiceImpl advertiseService;
    private final FileService fileService;

    private @Value("${storage.path}") String PATH;

    @Autowired
    public AdvertiseAPI(AdvertiseServiceImpl advertiseService, FileService fileService) {
        this.advertiseService = advertiseService;
        this.fileService = fileService;
    }

    private void setValueFromDTO(Advertise advertise, AdvertiseDTO advertiseDTO) {
        advertise.setTitle(advertiseDTO.getTitle());
        advertise.setText(advertiseDTO.getDescription());
        advertise.setPlacement(advertiseDTO.getAddress());
        advertise.setSquare(advertiseDTO.getSquare());
        advertise.setPricePerSquare(advertiseDTO.getPrice());
        advertise.setBedroomCount(advertiseDTO.getBedroom());
        advertise.setBathroomCount(advertiseDTO.getBathroom());
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Advertise>> findAll() {
        return ResponseEntity.ok(advertiseService.findAll());
    }

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Advertise> findById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(advertiseService.findById(id));
        } catch (AdvertiseNotFound exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Advertise> addNewAdvertise(@RequestPart(name = "advertise") AdvertiseDTO advertiseDTO,
                                                                   @RequestPart(name = "files")MultipartFile[] files) throws IOException {
        var advertise = new Advertise();
        var addFiles = new HashSet<FileModel>();

        for (var file : files) {
            log.info(file.getContentType());
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

        setValueFromDTO(advertise, advertiseDTO);
        advertise.setType(RealEstateType.valueOf(advertiseDTO.getType()));
        advertise.setPhotos(addFiles);

        advertiseService.save(advertise);

        return null;
    }

    @PutMapping("{id}")
    public @ResponseBody ResponseEntity<Advertise> updateAdvertise(@PathVariable(value = "id") Long advertiseId,
                                                                   @RequestBody AdvertiseDTO advertiseDTO) {
        log.info(String.valueOf(advertiseId));
        log.info(advertiseDTO.toString());

        try {
            var advertise = advertiseService.findById(advertiseId);

            setValueFromDTO(advertise, advertiseDTO);
            advertiseService.update(advertise);

            return ResponseEntity.ok(advertiseService.update(advertise));
        } catch (AdvertiseNotFound exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public @ResponseBody ResponseEntity<Advertise> deleteAdvertise(@PathVariable(name = "id") Long advertiseId) {
        try {
            var advertise = advertiseService.findById(advertiseId);
            advertiseService.deleteById(advertiseId);

            return ResponseEntity.ok(advertise);
        } catch (AdvertiseNotFound exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
