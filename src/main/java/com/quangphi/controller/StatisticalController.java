package com.quangphi.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.quangphi.model.DepartmentDTO;
import com.quangphi.model.StaffsDTO;
import com.quangphi.service.DepartmentService;
import com.quangphi.service.StaffsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/statistical")
public class StatisticalController {

    @Autowired StaffsService staffsService;

    @Autowired DepartmentService departmentService;

    @GetMapping
    public String statisticalPage(ModelMap model, @RequestParam(required=false) String type_Statistical) {
        if(type_Statistical != null && type_Statistical.equals("department")) {
            model.addAttribute("status", false);
            model.addAttribute("allDepartments", getAllDepartmentStatistical());
            return "statistical/statistical-index";
        }
        model.addAttribute("status", true);
        model.addAttribute("allStaffs", staffsService.getALLStaffs());
        return "statistical/statistical-index";
    }

    public Iterable<DepartmentStatistical> getAllDepartmentStatistical() {
        List<DepartmentStatistical> allDepartmentStatistical = new ArrayList<>();
        departmentService.getAllDepartments().forEach(
            departmentDTO -> allDepartmentStatistical.add(new DepartmentStatistical(departmentDTO))
        );
        Collections.sort(allDepartmentStatistical);
        return allDepartmentStatistical;
    }

    class DepartmentStatistical  implements Comparable<DepartmentStatistical>{

        private DepartmentDTO department;

        public DepartmentStatistical(DepartmentDTO department) {
            this.department = department;
        }

        public DepartmentDTO getDepartment() {
            return department;
        }

        public void setDepartment(DepartmentDTO department) {
            this.department = department;
        }

        public StaffsDTO getTopStaffs() {
            return !department.getAllStaffs().isEmpty() ? department.getAllStaffs().get(0) : new StaffsDTO();
        }

        public int getTotalAchievement() {
            int achievement = 0;
            for(StaffsDTO staffs : department.getAllStaffs()) {
                achievement += staffs.getAchievement();
            }
            return achievement;
        }

        public int getToTalDiscipline() {
            int discipline = 0;
            for(StaffsDTO staffs : department.getAllStaffs()) {
                discipline += staffs.getDiscipline();
            }
            return discipline;
        }

        public int getRewardPoint() {
            return getTotalAchievement() - getToTalDiscipline();
        }

        @Override
        public int compareTo(DepartmentStatistical o) {
            int rewardPoint_this = this.getRewardPoint();
            int rewardPoint_o = o.getRewardPoint();
            if(rewardPoint_this > rewardPoint_o) {
                return -1;
            } else if (rewardPoint_this < rewardPoint_o) {
                return 1;
            }
            int achievement_this = this.getTotalAchievement();
            int achievement_o = o.getTotalAchievement();
            if(achievement_this > achievement_o) {
                return -1;
            } else if (achievement_this < achievement_o) {
                return 1;
            }
            int discipline_this = this.getToTalDiscipline();
            int discipline_o = o.getToTalDiscipline();
            if(discipline_this > discipline_o) {
                return 1;
            } else if (discipline_this < discipline_o) {
                return -1;
            }
            return 0;
        }

    }
    
}