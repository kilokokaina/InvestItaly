package com.work.investitaly.service;

import com.work.investitaly.model.Request;

import java.util.List;

public interface RequestService {

    Request save(Request request);
    Request findById(Long id);
    List<Request> findAll();
    void delete(Request request);
    void deleteById(Long id);

}
