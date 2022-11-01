package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.model.FileDetails;

@Repository
public interface FileStorageRepository extends JpaRepository<FileDetails, Integer>{

}
