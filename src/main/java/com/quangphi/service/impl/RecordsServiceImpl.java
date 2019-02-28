package com.quangphi.service.impl;

import com.quangphi.entity.Records;
import com.quangphi.model.RecordsDTO;
import com.quangphi.repository.RecordsRepository;
import com.quangphi.service.RecordsService;
import com.quangphi.util.ConvertListSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordsServiceImpl implements RecordsService {

    @Autowired
    private RecordsRepository recordsRepository;

    @Autowired
    private ConvertListSupport<RecordsDTO, Records> convertListSupport;

    @Override
    public RecordsDTO addRecords(RecordsDTO recordsDTO) {
        Records records = recordsDTO.toRecords();
        return RecordsDTO.parseRecordsDTO(recordsRepository.save(records));
    }

    @Override
    public Iterable<RecordsDTO> getAll() {
        return convertListSupport.converting(recordsRepository.findAll(), records -> RecordsDTO.parseRecordsDTO(records));
    }
    
}