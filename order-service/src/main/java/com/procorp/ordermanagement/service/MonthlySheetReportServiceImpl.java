package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.dto.MonthlySheetReportDto;
import com.procorp.ordermanagement.entities.Inventory_Audit;
import com.procorp.ordermanagement.entities.MonthlySheetReport;
import com.procorp.ordermanagement.entities.Product_Inventory;
import com.procorp.ordermanagement.repositories.MonthlySheetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonthlySheetReportServiceImpl implements  MonthlySheetReportService {

    private MonthlySheetRepository monthlySheetRepository;

    private ProductInventoryService productInventoryService;

    public MonthlySheetReportServiceImpl(MonthlySheetRepository monthlySheetRepository,
                                         ProductInventoryService productInventoryService){
        this.monthlySheetRepository=monthlySheetRepository;
        this.productInventoryService=productInventoryService;
    }



    @Override
    public String saveMonthlySheetReport(MonthlySheetReportDtoWrapper wrapper){

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
        String monthYearDate= formatter.format(date);

        Map<Long,Long> warehouseWiseVolumeMap=new HashMap<>();
        List<Inventory_Audit> productInventory= productInventoryService.getInventoryAuditByOrderID(wrapper.getOrderID());
        if(productInventory!=null && !productInventory.isEmpty()){
          Map<Long,List<Inventory_Audit>> map=  productInventory.stream().collect(Collectors.groupingBy(in_audit->in_audit.getWareHouseID()));
          for(Long wareHouseID: map.keySet()){
              Long wareHouseVolume=0L;
              List<Inventory_Audit> auditList= map.get(wareHouseID);
              if(auditList!=null && !auditList.isEmpty()){
                   wareHouseVolume = auditList.stream().mapToLong(audit->audit.getQuantity()).sum();
              }
              warehouseWiseVolumeMap.put(wareHouseID,wareHouseVolume);
          }
        }

        for(MonthlySheetReportDto dto: wrapper.getMonthlySheetReportDto()){


            MonthlySheetReport existingSheet=this.monthlySheetRepository.
                    getMonthlySheetByWarehouseIdAndMonthYear(dto.getWarehouseId(),monthYearDate);

            if(existingSheet==null) {
                MonthlySheetReport report = new MonthlySheetReport();
                report.setCreatedDate(new Date().getTime());
                report.setDateCreated(LocalDate.now());
                // preparing current month date
                // Date epochDate = new Date(1697177001000L);-> preparing current month date with given input

                report.setMonthYear(monthYearDate);
                report.setWarehouseId(dto.getWarehouseId());
                report.setDeliveryFranchise(dto.getDeliveryFranchise());
                report.setWarehouseLocation(dto.getWarehouseLocation());
                // If new record -> action is returned need more details
                report.setSalesVolumes(warehouseWiseVolumeMap.get(dto.getWarehouseId()));

                if(dto.getOrderStatus().equalsIgnoreCase("Delivered")){
                    report.setNoOfOrdersReceived(1L);
                    report.setNoOfOrdersDelivered(1L);
                    report.setNoOfOrdersReturned(0L);
                }else if(dto.getOrderStatus().equalsIgnoreCase("Returned")){
                    report.setNoOfOrdersReceived(1L);
                    report.setNoOfOrdersReturned(1L);
                }

                this.monthlySheetRepository.save(report);
            }else{
                if(dto.getOrderStatus().equalsIgnoreCase("Delivered")){
                    existingSheet.setNoOfOrdersReceived(existingSheet.getNoOfOrdersReceived()+1L);
                    existingSheet.setNoOfOrdersDelivered(existingSheet.getNoOfOrdersDelivered()+1L);

                    if(existingSheet.getSalesVolumes()!=null&&existingSheet.getSalesVolumes()!=0L){
                        existingSheet.setSalesVolumes(existingSheet.getSalesVolumes()+warehouseWiseVolumeMap.get(dto.getWarehouseId()));
                    }else{
                        existingSheet.setSalesVolumes(warehouseWiseVolumeMap.get(dto.getWarehouseId()));
                    }

                }else if(dto.getOrderStatus().equalsIgnoreCase("Returned")){
                    if(existingSheet.getNoOfOrdersReturned()!=null && existingSheet.getNoOfOrdersReturned()!=0L){
                        existingSheet.setNoOfOrdersReturned(existingSheet.getNoOfOrdersReturned()+1L);
                        existingSheet.setNoOfOrdersDelivered(existingSheet.getNoOfOrdersDelivered()-1L);
                    }else{
                        existingSheet.setNoOfOrdersReturned(1L);
                        existingSheet.setNoOfOrdersDelivered(existingSheet.getNoOfOrdersDelivered()-1L);
                    }
                    if(existingSheet.getSalesVolumes()!=null&&existingSheet.getSalesVolumes()!=0L){
                        existingSheet.setSalesVolumes(existingSheet.getSalesVolumes()-warehouseWiseVolumeMap.get(dto.getWarehouseId()));
                    }
                }
                this.monthlySheetRepository.save(existingSheet);
            }
        }

        return "Monthly Sheet stored Successfully";

    }

    @Override
    public  List<MonthlySheetReport> findMonthlySheetByMonthYear(String monthYear){
       return this.monthlySheetRepository.getMonthlySheetByMonthYear(monthYear);
    }

    @Override
    public Iterable<MonthlySheetReport> getAllMonthlySheetDetails(){
        return this.monthlySheetRepository.findAll();
    }
}
