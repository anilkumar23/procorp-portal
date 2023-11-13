package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.MonthlySheetReportDto;

import java.util.List;

public class MonthlySheetReportDtoWrapper {

    private List<MonthlySheetReportDto> monthlySheetReportDto;

    private Long orderID;

    public List<MonthlySheetReportDto> getMonthlySheetReportDto() {
        return monthlySheetReportDto;
    }

    public void setMonthlySheetReportDto(List<MonthlySheetReportDto> monthlySheetReportDto) {
        this.monthlySheetReportDto = monthlySheetReportDto;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }
}
