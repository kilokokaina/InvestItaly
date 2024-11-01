package com.work.investitaly.service.impl;

import com.work.investitaly.model.Advertise;
import com.work.investitaly.repository.AdvertiseRepository;
import com.work.investitaly.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    private final AdvertiseRepository advertiseRepository;

    @Autowired
    public AdvertiseServiceImpl(AdvertiseRepository advertiseRepository) {
        this.advertiseRepository = advertiseRepository;
    }

    @Override
    public Advertise save(Advertise advertise) {
        return advertiseRepository.save(advertise);
    }

    @Override
    public Advertise findById(Long id) {
        return advertiseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Advertise> findAll() {
        return advertiseRepository.findAll();
    }

    @Override
    public void delete(Advertise advertise) {
        advertiseRepository.delete(advertise);
    }

    @Override
    public void deleteById(Long id) {
        advertiseRepository.deleteById(id);
    }
}
