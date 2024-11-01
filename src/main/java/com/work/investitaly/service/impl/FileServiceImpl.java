package com.work.investitaly.service.impl;

import com.work.investitaly.model.FileModel;
import com.work.investitaly.repository.FileRepository;
import com.work.investitaly.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileModel save(FileModel file) {
        return fileRepository.save(file);
    }

    @Override
    public FileModel findById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public List<FileModel> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public void delete(FileModel file) {
        fileRepository.delete(file);
    }

    @Override
    public void deleteById(Long id) {
        fileRepository.deleteById(id);
    }
}
