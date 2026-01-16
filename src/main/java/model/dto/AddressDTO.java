package model.dto;

public class AddressDTO {
	//멤버변수
	private int accountPk;//회원pk
	private int addressPk; //배송지pk
	private String postalCode;//우편번호
	private boolean isDefaultAddress;//기본 배송지
	private String addressName; //배송지 이름
	private String regionAddress; //지역 배송지
	private String detailAddress; //세부 배송지
	private String condition;//DAO 분기처리용으로 사용
	
	
	//게터세터 
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public int getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(int accountPk) {
		this.accountPk = accountPk;
	}
	public int getAddressPk() {
		return addressPk;
	}
	public void setAddressPk(int addressPk) {
		this.addressPk = addressPk;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public boolean isDefaultAddress() {
		return isDefaultAddress;
	}
	public void setDefaultAddress(boolean isDefaultAddress) {
		this.isDefaultAddress = isDefaultAddress;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getRegionAddress() {
		return regionAddress;
	}
	public void setRegionAddress(String regionAddress) {
		this.regionAddress = regionAddress;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	@Override
	public String toString() {
		return "AddressDTO [accountPk=" + accountPk + ", addressPk=" + addressPk + ", postalCode=" + postalCode
				+ ", isDefaultAddress=" + isDefaultAddress + ", addressName=" + addressName + ", regionAddress="
				+ regionAddress + ", detailAddress=" + detailAddress + ", condition=" + condition + "]";
	} 
	
}
