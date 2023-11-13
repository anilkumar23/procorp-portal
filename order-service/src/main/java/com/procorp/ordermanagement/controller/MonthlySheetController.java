package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.service.MonthlySheetReportDtoWrapper;
import com.procorp.ordermanagement.service.MonthlySheetReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monthly-sheet-service")
public class MonthlySheetController {

    private MonthlySheetReportService monthlySheetReportService;

    public MonthlySheetController(MonthlySheetReportService monthlySheetReportService){
        this.monthlySheetReportService=monthlySheetReportService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> saveMonthlySheets(@RequestBody MonthlySheetReportDtoWrapper wrapper){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("monthly-sheets created successfully")
                        .responseObj(monthlySheetReportService.saveMonthlySheetReport(wrapper))
                        .build());
    }

    @GetMapping(path = "/getMonthlySheetByMonthYear")
    public ResponseEntity<?> getMonthlySheetByMonthYear(@RequestParam(name = "monthYear")String monthYear){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got monthly-sheets successfully By monthYear: "+monthYear)
                        .responseObj(monthlySheetReportService.findMonthlySheetByMonthYear(monthYear))
                        .build());
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getAllMonthlySheetDetails(){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got All the monthly-sheets details successfully")
                        .responseObj(monthlySheetReportService.getAllMonthlySheetDetails())
                        .build());
    }

}
