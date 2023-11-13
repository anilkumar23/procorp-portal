package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.entities.MonthlySheetReport;

import java.util.List;

public interface MonthlySheetReportService {

    String saveMonthlySheetReport(MonthlySheetReportDtoWrapper wrapper);

    List<MonthlySheetReport> findMonthlySheetByMonthYear(String monthYear);

    Iterable<MonthlySheetReport> getAllMonthlySheetDetails();
}
