package com.work.investitaly.service.impl;

import com.work.investitaly.exception.AdvertiseNotFound;
import com.work.investitaly.model.Advertise;
import com.work.investitaly.repository.AdvertiseRepository;
import com.work.investitaly.service.AdvertiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    private final AdvertiseRepository advertiseRepository;

    private static final String NOT_FOUND_MESSAGE = "Advertise with id %d not found";

    @Autowired
    public AdvertiseServiceImpl(AdvertiseRepository advertiseRepository) {
        this.advertiseRepository = advertiseRepository;
    }

    @Override
    public Advertise save(Advertise advertise) {
        return advertiseRepository.save(advertise);
    }

    @Override
    public Advertise update(Advertise advertise) throws AdvertiseNotFound {
        if (advertiseRepository.findById(advertise.getId()).orElse(null) == null) {
            throw new AdvertiseNotFound(String.format(NOT_FOUND_MESSAGE, advertise.getId()));
        }

        return advertiseRepository.save(advertise);
    }

    @Override
    public Advertise findById(Long id) throws AdvertiseNotFound {
        var advertise = advertiseRepository.findById(id).orElse(null);

        if (advertise == null) {
            throw new AdvertiseNotFound(String.format(NOT_FOUND_MESSAGE, id));
        }

        return advertise;
    }

    @Override
    public List<Advertise> findAll() {
        return advertiseRepository.findAll();
    }

    @Override
    public void delete(Advertise advertise) throws AdvertiseNotFound {
        var advertiseFromDB = advertiseRepository.findById(advertise.getId()).orElse(null);

        if (advertiseFromDB == null) {
            throw new AdvertiseNotFound(String.format(NOT_FOUND_MESSAGE, advertise.getId()));
        }

        for (var file : advertiseFromDB.getPhotos()) {
            try {
                Files.delete(Path.of(file.getFilePath()));
            } catch (IOException exception) {
                log.info(exception.getMessage());
            }
        }

        advertiseRepository.delete(advertise);
    }

    @Override
    public void deleteById(Long id) throws AdvertiseNotFound {
        var advertise = advertiseRepository.findById(id).orElse(null);

        if (advertise == null) {
            throw new AdvertiseNotFound(String.format(NOT_FOUND_MESSAGE, id));
        }

        for (var file : advertise.getPhotos()) {
            try {
                Files.delete(Path.of(file.getFilePath()));
            } catch (IOException exception) {
                log.info(exception.getMessage());
            }
        }

        advertiseRepository.deleteById(id);
    }
}
