<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Ornably 회원가입</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500;600;700&family=Roboto:wght@400;500;700&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/animate/animate.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">


<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">

<style>
.form-control:focus {
	border-color: #6F4F3A;
	box-shadow: 0 0 0 0.25rem rgba(34, 197, 94, 0.25) !important;
}
</style>

<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		console.log("Context Path from JSP: " + contextPath); // 콘솔에서 확인
	</script>

</head>

<body>
	<tag:header />

	<!-- 회원가입 페이지 수정 부분 -->
	<div class="container-fluid bg-light overflow-hidden py-5">
		<div class="container py-5">
			<h1 class="row g-5 justify-content-center" data-wow-delay="0.1s">회원가입</h1>

			<form action="join.do" method="POST">
				<div class="row g-2 justify-content-center">
					<div class="col-12 col-sm-8 col-md-6 col-lg-4"
						data-wow-delay="0.1s">
						<div class="form-item">
							<label class="form-label my-3"> 아이디 입력<sup>*</sup>
							</label>

							<div class="input-group">
								<input type="text" class="form-control" name="accountId"
									id="userId" required pattern="^(?=.*\d)[a-z][a-z0-9_]{3,15}$"
									minlength="4" maxlength="16"
									title="아이디는 숫자 포함 영소문자로 시작 , 영문/숫자/_만 가능 (4~16자)"
									placeholder="아이디는 숫자 포함 영(4~16자)">
								<button class="btn btn-outline-success" type="button"
									id="btnIdCheck"
									style="background-color: #6F4F3A; color: white; border: 2px solid #6F4F3A;">
									중복확인</button>
							</div>

							<div id="msgIdCheck" class="mt-1"></div>
						</div>

						<div class="form-item">
							<label class="form-label my-3">비밀번호 입력<sup>*</sup></label> <input
								type="password" class="form-control" name="accountPassword"
								id="password1" required
								pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&])[\S]{8,16}$"
								maxlength="20" placeholder="대소문자,특수문자, 숫자 포함"
								title="비밀번호 형식이 올바르지 않습니다. (영문 대소문자, 숫자, 특수문자 포함, 8~16자)">
							<div id="msgPasswordCheck1"></div>
						</div>

						<div class="form-item">
							<label class="form-label my-3">비밀번호 확인<sup>*</sup></label> <input
								type="password" class="form-control" name="checkPassword"
								id="password2" required
								pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&])[\S]{8,16}$"
								maxlength="20" placeholder="비밀번호 확인">
							<div id="msgPasswordCheck2"></div>
						</div>

						<hr class="mt-5">
						<div class="form-item">
							<label class="form-label my-3">이름<sup>*</sup></label> <input
								type="text" class="form-control" id="accountName"
								name="accountName" minlength="2" maxlength="20" required
								pattern="^[a-zA-Z가-힣\s]+$" title="이름은 한글, 영문 대소문자만 허용됩니다."
								placeholder="이름은 한글, 영문 대소문자만 허용됩니다.">
						</div>
						<div id="msgNameCheck"></div>


						<div class="form-item">
							<label class="form-label my-3">휴대폰 번호<sup>*</sup></label>
							<div class="input-group">
								<input type="tel" class="form-control" name="accountPhone"
									id="phone" required pattern="^010\d{8}$" maxlength="11"
									placeholder="01012345678 (-는 제외)"
									title="휴대폰 번호는 010으로 시작하는 11자리 숫자만 입력하세요">
								<button class="btn btn-outline-success" type="button"
									id="btnSendSms"
									style="background-color: #6F4F3A; color: white; border: 2px solid #6F4F3A;"
									disabled>
									인증번호 발송</button>
							</div>
							<div id="msgPhoneCheck" class="mt-1"></div>
						</div>

						<div class="form-item" id="smsVerifyArea" style="display: none;">
							<label class="form-label my-3">인증번호 입력<sup>*</sup> <span
								id="smsTimer" style="color: red;"></span></label>
							<div class="input-group">
								<input type="text" class="form-control" id="smsVerifyCode"
									placeholder="인증번호 4자리 입력" maxlength="4">
								<button class="btn btn-outline-success" type="button"
									id="btnVerifySms"
									style="background-color: #6F4F3A; color: white; border: 2px solid #6F4F3A;">
									인증확인</button>
							</div>
							<div id="msgSmsVerify" class="mt-1"></div>
						</div>

						<div class="form-item">
							<label class="form-label my-3">이메일<sup>*</sup></label>

							<div class="d-flex gap-2">
								<!-- 이메일 아이디 -->
								<input type="text" class="form-control" id="emailId"
									pattern="^(?=.{4,30}$)[a-zA-Z0-9]+(?:_[a-zA-Z0-9]+)*$"
									title="이메일 아이디 형식이 올바르지 않습니다." placeholder="이메일 아이디"> <span
									class="align-self-center">@</span>

								<!-- 도메인 select -->
								<select class="form-select" id="emailDomain">
									<option value="" disabled selected hidden>선택</option>
									<option value="gmail.com">gmail.com</option>
									<option value="naver.com">naver.com</option>
									<option value="kakao.com">kakao.com</option>
									<option value="daum.com">daum.com</option>
									<option value="custom">직접입력</option>
								</select>

								<!-- 직접입력 도메인 -->
								<input type="text" class="form-control" id="customDomain"
									placeholder="도메인 입력" style="display: none;" maxlength="20"
									pattern="^[a-zA-Z0-9.-]+\.(com|net|org|co\.kr|io|co)$"
									title="도메인은 .com, .net, .co.kr, .org, .io 등의 확장자를 포함해야 합니다."
									placeholder="예) abc.com">
							</div>
							<div id="msgEmail"></div>

							<!-- 숨겨진 이메일 값 (최종 결합된 이메일을 이곳에 설정) -->
							<input type="hidden" id="accountEmail" name="accountEmail">
						</div>
						<!-- 배송지 명 -->
						<div class="form-item">
							<label class="form-label my-3">배송지 명<sup>*</sup></label> <select
								class="form-select" id="addressName" required>
								<option value="" disabled selected hidden>선택</option>
								<option value="집">집</option>
								<option value="회사">회사</option>
								<option value="customAddress">직접입력</option>
							</select>
							<!-- 직접입력 입력창, 필요 시 보여주기 -->
							<input type="text" class="form-control mt-2"
								id="customAddressName" placeholder="배송지 명 입력"
								style="display: none;" maxlength="20"
								pattern="^[a-zA-Z가-힣0-9]{3,30}$"
								title="배송지 명은 한글, 영문 대소문자, 숫자만 포함할 수 있으며, 3~30자 이내여야 합니다.">
							<div id="msgAddressName"></div>
						</div>

						<!-- 우편번호 및 주소 -->
						<div class="form-item">
							<label class="form-label my-3">주소</label>
							<div class="d-flex gap-2">
								<input type="text" class="form-control" id="sample6_postcode"
									name="postalCode" placeholder="우편번호" readonly required>

								<input type="button" class="btn btn-outline-success"
									id="postcodeBtn"
									style="background-color: #6F4F3A; color: white; border: 2px solid #6F4F3A;"
									value="우편번호 찾기">
							</div>
							<input type="text" class="form-control mt-2" id="sample6_address"
								name="address1" placeholder="주소" readonly required>
							<div id="msgAddress"></div>

							<input type="text" class="form-control mt-2"
								id="sample6_detailAddress" name="address2" placeholder="상세주소"
								required maxlength="20" pattern="^[a-zA-Z가-힣0-9\s]{3,20}$"
								title="상세주소는 한글, 영문 대소문자, 숫자만 포함할 수 있으며, 3~50자 이내여야 합니다.">
							<div id="msgAddress2"></div>
						</div>
					</div>
				</div>
				<div class="row justify-content-center mt-5">
					<div class="col-5">
						<button type="submit" class="btn btn-success w-100 py-3"
							id="btnSubmit"
							style="background-color: #6F4F3A; color: white; border: 2px solid #6F4F3A;">회원가입</button>
					</div>
				</div>
			</form>

		</div>
	</div>

	<!-- 회원가입 페이지 부분 수정 완료-->


	<!-- Back to Top -->
	<a href="#" class="btn btn-primary btn-lg-square back-to-top"><i
		class="fa fa-arrow-up"></i></a>

	<tag:footer />
	<!-- JavaScript Libraries -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>


	<!-- Template Javascript -->
	<script src="js/main.js"></script>

	<!-- Custom JavaScript (버그샌드위치) -->
	<script
		src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="customResource/JoinIdCheck.js"></script>
	<script src="customResource/joinPasswordCheck.js"></script>
	<script src="customResource/joinPhoneCheck.js"></script>
	<script src="customResource/joinEmailCheck.js"></script>
	<script src="customResource/joinAddressAPI.js"></script>
	<script src="customResource/joinAddressCheck.js"></script>
	<script src="customResource/joinNameCheck.js"></script>
</body>

</html>