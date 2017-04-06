package com.ichi.inspection.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderResponse implements Parcelable {

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

	protected OrderResponse(Parcel in) {
		if (in.readByte() == 0x01) {
			pay = new ArrayList<PayItem>();
			in.readList(pay, PayItem.class.getClassLoader());
		} else {
			pay = null;
		}
		if (in.readByte() == 0x01) {
			orderList = new ArrayList<OrderListItem>();
			in.readList(orderList, OrderListItem.class.getClassLoader());
		} else {
			orderList = null;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (pay == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(pay);
		}
		if (orderList == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(orderList);
		}
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<OrderResponse> CREATOR = new Parcelable.Creator<OrderResponse>() {
		@Override
		public OrderResponse createFromParcel(Parcel in) {
			return new OrderResponse(in);
		}

		@Override
		public OrderResponse[] newArray(int size) {
			return new OrderResponse[size];
		}
	};
}