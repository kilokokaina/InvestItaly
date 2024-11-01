package com.work.investitaly.repository;

import com.work.investitaly.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileModel, Long> {
}
