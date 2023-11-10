package com.procorp.ordermanagement.dto;

public class OrderDeliveryPartnerDto {

    private String deliveryAgentName;
    private String deliveryPartner;
    private String deliveryOn;

    public String getDeliveryAgentName() {
        return deliveryAgentName;
    }

    public void setDeliveryAgentName(String deliveryAgentName) {
        this.deliveryAgentName = deliveryAgentName;
    }

    public String getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(String deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public String getDeliveryOn() {
        return deliveryOn;
    }

    public void setDeliveryOn(String deliveryOn) {
        this.deliveryOn = deliveryOn;
    }
}
