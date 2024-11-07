package com.work.investitaly.api;

import com.work.investitaly.model.Request;
import com.work.investitaly.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/request")
public class RequestAPI {

    private final RequestService requestService;

    @Autowired
    public RequestAPI(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Request>> findAll() {
        return ResponseEntity.ok(requestService.findAll());
    }

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Request> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(requestService.findById(id));
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Request> createRequest(@RequestBody Request request) {
        return ResponseEntity.ok(requestService.save(request));
    }

    @DeleteMapping("{id}")
    public @ResponseBody ResponseEntity<HttpStatus> deleteRequest(@PathVariable(name = "id") Long id) {
        requestService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
