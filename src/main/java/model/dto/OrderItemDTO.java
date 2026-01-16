package model.dto;

public class OrderItemDTO {
	private int orderItemPk; // 주문 상세 pk
	private int orderPk;  // 주문내역 fk
	private int itemPk; // 상품 fk
	private int accountPk; // 사용자 fk
	private int orderItemQuantity; // 주문 개수
	private int orderItemPrice; // 상품 주문 가격
	
	private String itemName; // 상품이름
	private String itemImageUrl; // 상품 이미지
	private String condition; // 분기점
	
	private boolean isReviewed; // 사용자 리뷰 작성 유무
	
	@Override
	public String toString() {
		return "OrderItemDTO [orderItemPk=" + orderItemPk + ", orderPk=" + orderPk + ", itemPk=" + itemPk
				+ ", orderItemQuantity=" + orderItemQuantity + ", orderItemPrice=" + orderItemPrice + ", itemName="
				+ itemName + ", itemImageUrl=" + itemImageUrl + ", isReviewed=" + isReviewed + ", condition=" + condition + "]";
	}
	
	
	public int getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(int accountPk) {
		this.accountPk = accountPk;
	}
	public boolean isReviewed() {
		return isReviewed;
	}
	public void setReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}
	public int getOrderItemPk() {
		return orderItemPk;
	}
	public void setOrderItemPk(int orderItemPk) {
		this.orderItemPk = orderItemPk;
	}
	public int getOrderPk() {
		return orderPk;
	}
	public void setOrderPk(int orderPk) {
		this.orderPk = orderPk;
	}
	public int getItemPk() {
		return itemPk;
	}
	public void setItemPk(int itemPk) {
		this.itemPk = itemPk;
	}
	public int getOrderItemQuantity() {
		return orderItemQuantity;
	}
	public void setOrderItemQuantity(int orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}
	public int getOrderItemPrice() {
		return orderItemPrice;
	}
	public void setOrderItemPrice(int orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemImageUrl() {
		return itemImageUrl;
	}
	public void setItemImageUrl(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
