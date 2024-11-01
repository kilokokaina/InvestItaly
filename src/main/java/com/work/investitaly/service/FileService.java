package com.work.investitaly.service;

import com.work.investitaly.model.FileModel;

import java.util.List;

public interface FileService {

    FileModel save(FileModel file);
    FileModel findById(Long id);
    List<FileModel> findAll();
    void delete(FileModel file);
    void deleteById(Long id);

}
