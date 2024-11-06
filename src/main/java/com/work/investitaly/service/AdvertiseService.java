package com.work.investitaly.service;

import com.work.investitaly.exception.AdvertiseNotFound;
import com.work.investitaly.model.Advertise;

import java.util.List;

public interface AdvertiseService {

    Advertise save(Advertise advertise);
    Advertise update(Advertise advertise) throws AdvertiseNotFound;
    Advertise findById(Long id) throws AdvertiseNotFound;
    List<Advertise> findAll();
    void delete(Advertise advertise) throws AdvertiseNotFound;
    void deleteById(Long id) throws AdvertiseNotFound;

}
