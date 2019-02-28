package com.quangphi.service;

import com.quangphi.model.RecordsDTO;

public interface RecordsService {

    RecordsDTO addRecords(RecordsDTO recordsDTO);

    Iterable<RecordsDTO> getAll();

}