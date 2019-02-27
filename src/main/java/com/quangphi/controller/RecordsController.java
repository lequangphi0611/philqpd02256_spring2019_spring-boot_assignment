package com.quangphi.controller;

import java.util.HashMap;
import java.util.Map;

import com.quangphi.model.RecordsDTO;
import com.quangphi.model.StaffsDTO;
import com.quangphi.service.RecordsService;
import com.quangphi.service.StaffsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/records")
public class RecordsController {

    @Autowired
    private StaffsService staffsService;

    @Autowired
    private RecordsService recordsService;

    private final static String ACTION_KEY = "action";
    private final static String URL_KEY = "url_records";

    private Map<String, Object> getAttributeStaffsRequest(String idStaffs) {
        return new HashMap<String, Object>() {
            {
                put(RecordsController.ACTION_KEY, "/records/" + idStaffs);
                put(RecordsController.URL_KEY, "/staffs");
            }
        };
    }

    private Map<String, Object> getAttributeDepartmentRequest(String idStaffs, String idDepartment) {
        return new HashMap<String, Object>() {
            {
                put(RecordsController.ACTION_KEY, "/records/" + idDepartment + "/" + idStaffs);
                put(RecordsController.URL_KEY, "/department/infor/" + idDepartment);
            }
        };
    }

    private ModelMap init(ModelMap model, String idStaffs) {
        model.addAllAttributes(new HashMap<String, Object>() {
            {
                put("staffs", staffsService.getByID(idStaffs));
                put("records", new RecordsDTO());
            }
        });
        return model;
    }

    @GetMapping(value = "/{idStaffs}")
    public String feedbackStaffsRequest(ModelMap model, @PathVariable String idStaffs) {
        init(model, idStaffs);
        model.addAllAttributes(getAttributeStaffsRequest(idStaffs));
        return "records/records-form";
    }

    @GetMapping(value = "/{idDepartment}/{idStaffs}")
    public String feedbackDepartmentRequest(ModelMap model, @PathVariable String idDepartment,
            @PathVariable String idStaffs) {
        init(model, idStaffs);
        model.addAllAttributes(getAttributeDepartmentRequest(idStaffs, idDepartment));
        return "records/records-form";
    }

    @PostMapping(value = "/{idStaffs}")
    public String feedbackStaffsRequest(@ModelAttribute RecordsDTO recordsDTO, @RequestParam int type_records,
            @PathVariable String idStaffs) {
        StaffsDTO staffs = staffsService.getByID(idStaffs);
        recordsDTO.setType(type_records == 1);
        recordsDTO.setStaffsDTO(staffs);
        recordsService.addRecords(recordsDTO);
        return "redirect:/staffs";
    }

    @PostMapping(value = "/{idDepartment}/{idStaffs}")
    public String feedbackDepartmentRequest(@ModelAttribute RecordsDTO recordsDTO, @RequestParam int type_records,
            @PathVariable String idDepartment, @PathVariable String idStaffs) {
                StaffsDTO staffs = staffsService.getByID(idStaffs);
        recordsDTO.setType(type_records == 1);
        recordsDTO.setStaffsDTO(staffs);
        recordsService.addRecords(recordsDTO);
        return "redirect:/department/infor/" + idDepartment;
    }

}