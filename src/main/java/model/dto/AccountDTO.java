package model.dto;

public class AccountDTO {
	//멤버변수
	 private int accountPk; //회원PK
	 private String accountPassword;//회원비밀번호
	 private String accountRole;//유저 롤
	 private String accountId;//회원아이디
	 private String accountName;//회원이름
	 private String accountEmail;//회원 이메일
	 private String accountPhone;//회원 전화번호
	 private String condition; //DAO에서 분기처리용으로 사용
	 
	 //게터세터
	 public String getAccountName() {
		return accountName;
	}
	 public void setAccountName(String accountName) {
		 this.accountName = accountName;
	 }
	 public int getAccountPk() {
		 return accountPk;
	 }
	 public void setAccountPk(int accountPk) {
		 this.accountPk = accountPk;
	 }
	 public String getAccountPassword() {
		 return accountPassword;
	 }
	 public void setAccountPassword(String accountPassword) {
		 this.accountPassword = accountPassword;
	 }
	 public String getAccountRole() {
		 return accountRole;
	 }
	 public void setAccountRole(String accountRole) {
		 this.accountRole = accountRole;
	 }
	 public String getAccountId() {
		 return accountId;
	 }
	 public void setAccountId(String accountId) {
		 this.accountId = accountId;
	 }
	 public String getAccountEmail() {
		 return accountEmail;
	 }
	 public void setAccountEmail(String accountEmail) {
		 this.accountEmail = accountEmail;
	 }
	 public String getAccountPhone() {
		 return accountPhone;
	 }
	 public void setAccountPhone(String accountPhone) {
		 this.accountPhone = accountPhone;
	 }
	 public String getCondition() {
		 return condition;
	 }
	 public void setCondition(String condition) {
		 this.condition = condition;
	 }
	 @Override
	 public String toString() {
		return "AccountDTO [accountPk=" + accountPk + ", accountPassword=" + accountPassword + ", accountRole="
				+ accountRole + ", accountId=" + accountId + ", accountEmail=" + accountEmail + ", accountPhone="
				+ accountPhone + ", condition=" + condition + "]";
	 }
}
