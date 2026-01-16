package model.dto;

public class WishlistDTO {
	//멤버변수
	private int accountPk; //회원PK
	private int wishlistPk;//위시리스트PK
	private int itemPk;//아이템pk
	private String condition;//DAO 분기처리용으로 사용
	
	
	//게터세터
	public int getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(int accountPk) {
		this.accountPk = accountPk;
	}
	public int getWishlistPk() {
		return wishlistPk;
	}
	public void setWishlistPk(int wishlistPk) {
		this.wishlistPk = wishlistPk;
	}
	public int getItemPk() {
		return itemPk;
	}
	public void setItemPk(int itemPk) {
		this.itemPk = itemPk;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	@Override
	public String toString() {
		return "wishlistDTO [accountPk=" + accountPk + ", wishlistPk=" + wishlistPk + ", itemPk=" + itemPk
				+ ", condition=" + condition + "]";
	}
	
	

}
