package com.quangphi.service.impl;

import com.quangphi.entity.Records;
import com.quangphi.model.RecordsDTO;
import com.quangphi.repository.RecordsRepository;
import com.quangphi.service.RecordsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordsServiceImpl implements RecordsService {

    @Autowired
    private RecordsRepository recordsRepository;

    @Override
    public RecordsDTO addRecords(RecordsDTO recordsDTO) {
        Records records = recordsDTO.toRecords();
        return RecordsDTO.parseRecordsDTO(recordsRepository.save(records));
    }
    
}