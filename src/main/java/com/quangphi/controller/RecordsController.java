package com.quangphi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.quangphi.model.RecordsDTO;
import com.quangphi.model.StaffsDTO;
import com.quangphi.service.RecordsService;
import com.quangphi.service.StaffsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
    public String feedbackStaffsRequest(@ModelAttribute("records") @Valid RecordsDTO recordsDTO,
            BindingResult bindingResult, @RequestParam int type_records, @PathVariable String idStaffs,
            ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("reason_error", "Vui lòng không để trống lý do ");
            return feedbackStaffsRequest(model, idStaffs);
        }
        StaffsDTO staffs = staffsService.getByID(idStaffs);
        recordsDTO.setType(type_records == 1);
        recordsDTO.setStaffsDTO(staffs);
        recordsService.addRecords(recordsDTO);
        return "redirect:/staffs";
    }

    @PostMapping(value = "/{idDepartment}/{idStaffs}")
    public String feedbackDepartmentRequest(@ModelAttribute("records") @Valid RecordsDTO recordsDTO,
            BindingResult bindingResult, @RequestParam int type_records, @PathVariable String idDepartment,
            @PathVariable String idStaffs, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("reason_error", "Vui lòng không để trống lý do ");
            return feedbackDepartmentRequest(model, idDepartment, idStaffs);
        }
        StaffsDTO staffs = staffsService.getByID(idStaffs);
        recordsDTO.setType(type_records == 1);
        recordsDTO.setStaffsDTO(staffs);
        recordsService.addRecords(recordsDTO);
        return "redirect:/department/infor/" + idDepartment;
    }

    @GetMapping("/infor/{idStaffs}")
    public String feedbackToStaffsInfo(ModelMap model, @PathVariable String idStaffs) {
        model.addAllAttributes(new HashMap<String, Object>() {
            {
                put(RecordsController.ACTION_KEY, "/records/infor/" + idStaffs);
                put(RecordsController.URL_KEY, "/staffs/" + idStaffs);
            }
        });
        init(model, idStaffs);
        return "records/records-form";
    }

    @PostMapping("/infor/{idStaffs}")
    public String feedbackToStaffsInfo(ModelMap model, @ModelAttribute RecordsDTO records,
            @RequestParam int type_records, @PathVariable String idStaffs) {
        StaffsDTO staffs = staffsService.getByID(idStaffs);
        records.setType(type_records == 1);
        records.setStaffsDTO(staffs);
        recordsService.addRecords(records);
        return "redirect:/staffs/" + idStaffs;
    }

}