package com.ichi.inspection.app.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Palak on 07-06-2017.
 */

public class OrderUpdateContainer {

    private List<OrderUpdates> orderUpdatesList;

    public OrderUpdateContainer() {
        this.orderUpdatesList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "OrderUpdateContainer{" +
                "orderUpdatesList=" + orderUpdatesList +
                '}';
    }

    public List<OrderUpdates> getOrderUpdatesList() {
        return orderUpdatesList;
    }

    public void setOrderUpdatesList(List<OrderUpdates> orderUpdatesList) {
        this.orderUpdatesList = orderUpdatesList;
    }
}
