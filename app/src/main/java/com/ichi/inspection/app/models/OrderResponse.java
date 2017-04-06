package com.ichi.inspection.app.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderResponse{

	@SerializedName("Pay")
	@Expose
	private List<PayItem> pay;

	@SerializedName("OrderList")
	@Expose
	private List<OrderListItem> orderList;

	public void setPay(List<PayItem> pay){
		this.pay = pay;
	}

	public List<PayItem> getPay(){
		return pay;
	}

	public void setOrderList(List<OrderListItem> orderList){
		this.orderList = orderList;
	}

	public List<OrderListItem> getOrderList(){
		return orderList;
	}

	@Override
 	public String toString(){
		return 
			"OrderResponse{" + 
			"pay = '" + pay + '\'' + 
			",orderList = '" + orderList + '\'' + 
			"}";
		}
}