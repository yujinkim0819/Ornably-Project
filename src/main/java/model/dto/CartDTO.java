package model.dto;

public class CartDTO {
	private int CartPk; // 장바구니 pk;
	private int accountPk; // 사용자fk
	private int itemPk; // 아이템 fk
	private String itemImageUrl; // 상품 이미지
	private String itemName; // 상품 이름
	private int itemPrice;
	private String condition; //분기점
	
	private int count; // 상품이 장바구니에 존재하면 +담을 변수
	private int totalCount; // 장바구니에 담긴 총 상품수량
	
	private int newCount; //  장바구니 안에서 상품개수 변경시
	private int totalPrice; // 장바구니에 담긴 상품 가격
	
	private int totalAmount; // 사용자 장바구니의 총 금액
	
	

	
	
	
	
	@Override
	public String toString() {
		return "CartDTO [CartPk=" + CartPk + ", accountPk=" + accountPk + ", itemPk=" + itemPk + ", itemImageUrl="
				+ itemImageUrl + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", condition=" + condition
				+ ", count=" + count + ", totalCount=" + totalCount + ", newCount=" + newCount + ", totalPrice="
				+ totalPrice + ", totalAmount=" + totalAmount + "]";
	}





	public int getItemPrice() {
		return itemPrice;
	}





	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}





	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemPk() {
		return itemPk;
	}
	public void setItemPk(int itemPk) {
		this.itemPk = itemPk;
	}
	public int getCartPk() {
		return CartPk;
	}
	public void setCartPk(int cartPk) {
		CartPk = cartPk;
	}
	public int getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(int accountPk) {
		this.accountPk = accountPk;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNewCount() {
		return newCount;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
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
