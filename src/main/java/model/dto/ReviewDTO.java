package model.dto;

public class ReviewDTO {
	//멤버변수
	//pk/fk
	private int accountPk; //리뷰작성할 회원pk(fk)
	private int itemPk; // 리뷰작성에 들어갈 상품pk(fk)
	private int reviewPk; //리뷰pk
	//리뷰
	private boolean reviewExists;//리뷰존재여부확인
	private int reviewStar;// 리뷰 별점
	private String reviewContent;//리뷰 내용
	private String reviewTitle;//리뷰 제목
	private int totalCount; //리뷰할 상품의 전체 가격
	private String itemName; // 리뷰할 상품 이름
	private String itemImageUrl;//리뷰할 상품 이미지
	private int itemPrice;//리뷰할 상품 가격
	private String condition;//DAO 분기처리용으로 사용
	private int reviewTotalCount;
	//페이지네이션
	private int startReviewNum;//리뷰 페이지네이션 시작 
	private int endReviewNum;//리뷰 페이지네이션 끝
	private String reviewAccountName;//리뷰 페이지네이션시 사용할 사용자이름
	
	
	public int getReviewTotalCount() {
		return reviewTotalCount;
	}
	public void setReviewTotalCount(int reviewTotalCount) {
		this.reviewTotalCount = reviewTotalCount;
	}
	//게터세터
	public int getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(int accountPk) {
		this.accountPk = accountPk;
	}
	public int getItemPk() {
		return itemPk;
	}
	public void setItemPk(int itemPk) {
		this.itemPk = itemPk;
	}
	public int getReviewPk() {
		return reviewPk;
	}
	public void setReviewPk(int reviewPk) {
		this.reviewPk = reviewPk;
	}
	public boolean isReviewExists() {
		return reviewExists;
	}
	public void setReviewExists(boolean reviewExists) {
		this.reviewExists = reviewExists;
	}
	public int getReviewStar() {
		return reviewStar;
	}
	public void setReviewStar(int reviewStar) {
		this.reviewStar = reviewStar;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public String getReviewTitle() {
		return reviewTitle;
	}
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStartReviewNum() {
		return startReviewNum;
	}
	public void setStartReviewNum(int startReviewNum) {
		this.startReviewNum = startReviewNum;
	}
	public int getEndReviewNum() {
		return endReviewNum;
	}
	public void setEndReviewNum(int endReviewNum) {
		this.endReviewNum = endReviewNum;
	}
	public String getReviewAccountName() {
		return reviewAccountName;
	}
	public void setReviewAccountName(String reviewAccountName) {
		this.reviewAccountName = reviewAccountName;
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
	public int getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	@Override
	public String toString() {
		return "ReviewDTO [accountPk=" + accountPk + ", itemPk=" + itemPk + ", reviewPk=" + reviewPk + ", reviewExists="
				+ reviewExists + ", reviewStar=" + reviewStar + ", reviewContent=" + reviewContent + ", reviewTitle="
				+ reviewTitle + ", totalCount=" + totalCount + ", itemName=" + itemName + ", itemImageUrl="
				+ itemImageUrl + ", itemPrice=" + itemPrice + ", condition=" + condition + ", reviewTotalCount="
				+ reviewTotalCount + ", startReviewNum=" + startReviewNum + ", endReviewNum=" + endReviewNum
				+ ", reviewAccountName=" + reviewAccountName + "]";
	}
	

}
