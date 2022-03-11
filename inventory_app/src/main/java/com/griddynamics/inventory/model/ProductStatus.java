package com.griddynamics.inventory.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "inventory")
public class ProductStatus {
    
    @Id
    private String uniq_id;
    private String status;
}
