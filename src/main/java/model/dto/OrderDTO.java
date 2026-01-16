package model.dto;

public class OrderDTO {
	private int orderPk; // 주문내역pk
	private int accountPk; // 사용자 Fk
	private String orderStatus; // 주문현황
	private int orderTotalAmount; // 주문 총 가격
	private int addressPk; // 배송지주소 fk
	private String orderSignatureItemName; // 주문내역 대표 아이템 이름
	private java.util.Date orderDate; // 주문날짜
	private String condition; // 분기점
	
	
	


	@Override
	public String toString() {
		return "OrdersDTO [orderPK=" + orderPk + ", accountPK=" + accountPk + ", orderStatus=" + orderStatus
				+ ", orderTotalAmount=" + orderTotalAmount + ", addressPK=" + addressPk + ", orderSignatureItemName="
				+ orderSignatureItemName + ", orderDate=" + orderDate + ", condition=" + condition + "]";
	}
	
	
	public int getOrderPk() {
		return orderPk;
	}
	public void setOrderPk(int orderPk) {
		this.orderPk = orderPk;
	}
	public int getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(int accountPk) {
		this.accountPk = accountPk;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getOrderTotalAmount() {
		return orderTotalAmount;
	}
	public void setOrderTotalAmount(int orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}
	public int getAddressPk() {
		return addressPk;
	}
	public void setAddressPk(int addressPk) {
		this.addressPk = addressPk;
	}
	public String getOrderSignatureItemName() {
		return orderSignatureItemName;
	}
	public void setOrderSignatureItemName(String orderSignatureItemName) {
		this.orderSignatureItemName = orderSignatureItemName;
	}
	public java.util.Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(java.util.Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}


	

}
