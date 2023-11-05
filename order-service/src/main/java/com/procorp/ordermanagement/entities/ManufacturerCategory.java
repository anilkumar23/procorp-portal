package com.procorp.ordermanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class ManufacturerCategory {

    @EmbeddedId
    @JsonIgnore
    private ManufacturerCategoryPK pk;

    public ManufacturerCategory() {
        super();
    }

    public ManufacturerCategory(Manufacturer manufacturer, Category category) {
        pk = new ManufacturerCategoryPK();
        pk.setManufacturer(manufacturer);
        pk.setCategory(category);
    }

    @Transient
    public Category getCategory() {
        return this.pk.getCategory();
    }

    public ManufacturerCategoryPK getPk() {
        return pk;
    }

    public void setPk(ManufacturerCategoryPK pk) {
        this.pk = pk;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ManufacturerCategory other = (ManufacturerCategory) obj;
        if (pk == null) {
            if (other.pk != null) {
                return false;
            }
        } else if (!pk.equals(other.pk)) {
            return false;
        }

        return true;
    }
}
