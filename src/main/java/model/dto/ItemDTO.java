package model.dto;

public class ItemDTO {
	private int accountPk;
	
	private int itemPk; // 아이템 pk
	private String itemName; // 아이템 이름
	private String itemImageUrl; // 아이템 이미지
	private String itemContent; // 상품 설명
	private int itemPrice; // 가격
	private int itemStock; // 재고
	private int itemCount;
	
	private double itemStar; // 별점 평점
	private String keyword; // 검색어
	private String condition;  // 분기
	
	//페이지네이션
	private int startItemNum;
	private int endItemNum;
	private int itemTotalCount;
	
	
	
	@Override
	public String toString() {
		return "ItemDTO [accountPk=" + accountPk + ", itemPk=" + itemPk + ", itemName=" + itemName + ", itemImageUrl="
				+ itemImageUrl + ", itemContent=" + itemContent + ", itemPrice=" + itemPrice + ", itemStock="
				+ itemStock + ", itemCount=" + itemCount + ", itemStar=" + itemStar + ", keyword=" + keyword
				+ ", condition=" + condition + ", startItemNum=" + startItemNum + ", endItemNum=" + endItemNum
				+ ", itemTotalCount=" + itemTotalCount + "]";
	}
	public int getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(int accountPk) {
		this.accountPk = accountPk;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getItemTotalCount() {
		return itemTotalCount;
	}
	public int getItemPk() {
		return itemPk;
	}
	public void setItemPk(int itemPk) {
		this.itemPk = itemPk;
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
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	public int getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getItemStock() {
		return itemStock;
	}
	public void setItemStock(int itemStock) {
		this.itemStock = itemStock;
	}
	public double getItemStar() {
		return itemStar;
	}
	public void setItemStar(double itemStar) {
		this.itemStar = itemStar;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public int getStartItemNum() {
		return startItemNum;
	}
	public void setStartItemNum(int startItemNum) {
		this.startItemNum = startItemNum;
	}
	public int getEndItemNum() {
		return endItemNum;
	}
	public void setEndItemNum(int endItemNum) {
		this.endItemNum = endItemNum;
	}
	public int getItemTotealCount() {
		return itemTotalCount;
	}
	public void setItemTotalCount(int itemTotalCount) {
		this.itemTotalCount = itemTotalCount;
	}
	
}
