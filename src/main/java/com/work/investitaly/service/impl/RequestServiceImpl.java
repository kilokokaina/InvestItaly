package com.work.investitaly.service.impl;

import com.work.investitaly.model.Request;
import com.work.investitaly.repository.RequestRepository;
import com.work.investitaly.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public void delete(Request request) {
        requestRepository.delete(request);
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }
}
