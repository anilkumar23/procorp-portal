package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.MonthlySheetReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonthlySheetRepository extends CrudRepository<MonthlySheetReport,Long> {

    @Query(name = "select * from monthlysheetreport where warehouse_id=:warehouseId and month_year=':monthYear'",nativeQuery = true)
    MonthlySheetReport getMonthlySheetByWarehouseIdAndMonthYear(@Param("warehouseId")Long warehouseId,
                                                                 @Param("monthYear")String monthYear);

    @Query(name = "select * from monthlysheetreport where month_year=':monthYear'",nativeQuery = true)
    List<MonthlySheetReport> getMonthlySheetByMonthYear(@Param("monthYear")String monthYear);


}
