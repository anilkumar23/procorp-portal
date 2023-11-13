package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.InventoryAuditDto;
import com.procorp.ordermanagement.dto.Product_InventoryDto;
import com.procorp.ordermanagement.dto.Product_warehouse_inventory;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Inventory_Audit;
import com.procorp.ordermanagement.entities.Product_Inventory;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import com.procorp.ordermanagement.repositories.Inventory_AuditRepository;
import com.procorp.ordermanagement.repositories.Product_InventoryRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductInventoryServiceImpl implements ProductInventoryService{

    private Product_InventoryRepository product_inventoryRepository;

    private JdbcTemplate jdbcTemplate;

    private Inventory_AuditRepository inventory_auditRepository;

    public ProductInventoryServiceImpl(Product_InventoryRepository product_inventoryRepository,
                                       JdbcTemplate jdbcTemplate,
    Inventory_AuditRepository inventory_auditRepository){
        this.product_inventoryRepository=product_inventoryRepository;
        this.jdbcTemplate=jdbcTemplate;
        this.inventory_auditRepository=inventory_auditRepository;
    }

    @Override
    public Product_Inventory create(Product_InventoryDto dto) {
        Product_Inventory productInventory=new Product_Inventory();
        productInventory.setProductId(dto.getProductId());
        productInventory.setWarehouseId(dto.getWarehouseId());
        productInventory.setQuantity(dto.getQuantity());
        productInventory.setIntialStock(dto.getIntialStock());
        return this.product_inventoryRepository.save(productInventory);
    }

    @Override
    public Optional<Product_Inventory> update(Long id, Product_InventoryDto dto) {
        Optional<Product_Inventory> existingProductInventory = this.product_inventoryRepository.findById(id);
        if(existingProductInventory.isPresent()){
            Product_Inventory productInventory=existingProductInventory.get();
            productInventory.setProductId(dto.getProductId());
            productInventory.setWarehouseId(dto.getWarehouseId());
            productInventory.setQuantity(dto.getQuantity());
            productInventory.setIntialStock(dto.getIntialStock());
            return Optional.of(this.product_inventoryRepository.save(productInventory));
        }else{
            throw new ResourceNotFoundException("Product Inventory was not found with given ID: "+id);
        }
    }

    @Override
    public Optional<Product_Inventory> getProductInventoryById(Long id){
        Optional<Product_Inventory> product_Inventory = this.product_inventoryRepository.findById(id);
        if(product_Inventory.isPresent()){
            return product_Inventory;
        }else{
            throw new ResourceNotFoundException("productInventory was not found with given ID: "+id);
        }
    }

    @Override
    public Iterable<Product_Inventory> getAllProductInventoryDetails(){
        return  this.product_inventoryRepository.findAll();
    }

    @Override
    public void deleteProductInventoryById(Long id){
        this.product_inventoryRepository.deleteById(id);
    }

    @Override
    public Map<String,List<Product_warehouse_inventory>> getProductInventoryDetails(String pincode, List<Long> productIds){

        Map<String,List<Product_warehouse_inventory>> warehouseMap = new HashMap<>();
        List<Product_warehouse_inventory> inventoryList = getProductInventoryDetailsFromWarehouses(pincode,productIds);
        if(inventoryList!=null && !inventoryList.isEmpty()){
          warehouseMap = inventoryList.stream().collect(Collectors.groupingBy(Product_warehouse_inventory::getWarehouseName));
        }
        return warehouseMap;
    }

    public List<Product_warehouse_inventory> getProductInventoryDetailsFromWarehouses(String pincode, List<Long> productIds){

        String citiesCommaSeparated = productIds.stream().map(p->String.valueOf(p))
                .collect(Collectors.joining(","));
        String sql="select w.name as warehouse_name,w.pincode as warehouse_pincode,\n" +
                "        w.status as warehouse_status,p.id as product_id,p.name as product_name,\n" +
                "        pi.quantity as available_quantity from warehouse w\n" +
                "        left join product_inventory pi on pi.warehouse_id=w.id\n" +
                "        left join Product p on p.id=pi.product_id\n" +
                "        where w.pincode='"+pincode+"' and p.id in ("+citiesCommaSeparated+") and w.status='Active'";

        System.out.println(" sql "+sql);
        List<Product_warehouse_inventory> inventory = new ArrayList<>();
        try {
            inventory = jdbcTemplate.query(sql, new RowMapper<Product_warehouse_inventory>() {
                @Override
                public Product_warehouse_inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product_warehouse_inventory inventoryDto=new Product_warehouse_inventory();
                    inventoryDto.setProductId(rs.getLong("product_id"));
                    inventoryDto.setProductName(rs.getString("product_name"));
                    inventoryDto.setWarehouseName(rs.getString("warehouse_name"));
                    inventoryDto.setWarehousePincode(rs.getString("warehouse_pincode"));
                    inventoryDto.setWarehouseStatus(rs.getString("warehouse_status"));
                    inventoryDto.setAvailableQuantity(rs.getLong("available_quantity"));
                    return inventoryDto;
                }
            });

            return inventory;
        }catch (DataAccessException de){

        }catch (Exception e){

        }
        return inventory;
    }

    public boolean isDeliveryPartnerNameValid(String name){
        boolean isValid=false;
        try {
        String sql = "select count(*) from deliverypartner where name='"+name+"'";
        int count =  jdbcTemplate.queryForObject(sql,Integer.class);
        if(count>0){
            return true;
        }
        }catch (DataAccessException e){

        }
        return isValid;

    }

    @Override
    public String saveInventoryAuditDetails(List<InventoryAuditDto> inventoryAuditDetails){

        if(inventoryAuditDetails!=null&&!inventoryAuditDetails.isEmpty()){
            inventoryAuditDetails.stream().forEach(inventoryAuditDto -> {
                Inventory_Audit inventoryAudit = new Inventory_Audit();
                inventoryAudit.setOrderID(inventoryAuditDto.getOrderID());
                inventoryAudit.setProductID(inventoryAuditDto.getProductID());
                inventoryAudit.setWareHouseID(inventoryAuditDto.getWareHouseID());
                inventoryAudit.setWareHouseLocation(inventoryAuditDto.getWareHouseLocation());
                inventoryAudit.setDeliveryPartner(inventoryAuditDto.getDeliveryPartner());
                inventoryAudit.setManufacturerID(inventoryAuditDto.getManufacturerID());
                inventoryAudit.setQuantity(inventoryAuditDto.getQuantity());
                inventoryAudit= this.inventory_auditRepository.save(inventoryAudit);
                if(inventoryAudit.getId()!=0L){
                 Product_Inventory productInventory = this.product_inventoryRepository.getProductInventoryByProductIdAndWareHouseId(inventoryAudit.getProductID(),inventoryAudit.getWareHouseID());
                  if(productInventory.getAvailableStock()==null || productInventory.getAvailableStock()==0L){
                      productInventory.setAvailableStock(productInventory.getIntialStock()-inventoryAudit.getQuantity());

                  }else{
                      Long newAvailableStock= productInventory.getAvailableStock()-inventoryAudit.getQuantity();
                      productInventory.setAvailableStock(newAvailableStock);
                  }
                  this.product_inventoryRepository.save(productInventory);
                }
            });
        }


        return "Saved Data To Inventory Audit and Updated latest available stock in Product Inventory";
    }

    @Override
    public List<Inventory_Audit> getInventoryAuditByOrderID(Long orderID){
        return this.inventory_auditRepository.getInventoryAuditByOrderID(orderID);
    }


       /* select * from warehouse

        select * from product_inventory
        select * from product
        select w.name as warehouse_name,w.pincode as warehouse_pincode,
        w.status as warehouse_status,p.id as product_id,p.name as prodct_name,
        pi.quantity as available_quantity from warehouse w
        left join product_inventory pi on pi.warehouse_id=w.id
        left join Product p on p.id=pi.product_id
        where w.pincode='500039' and p.id in (1) and w.status='Active'*/



}
