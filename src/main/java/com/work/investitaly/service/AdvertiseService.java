package com.work.investitaly.service;

import com.work.investitaly.model.Advertise;

import java.util.List;

public interface AdvertiseService {

    Advertise save(Advertise advertise);
    Advertise findById(Long id);
    List<Advertise> findAll();
    void delete(Advertise advertise);
    void deleteById(Long id);

}
