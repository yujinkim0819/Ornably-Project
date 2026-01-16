<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<!DOCTYPE html>
<html lang="en">
<!-- 카카오 로그인 버튼 이미지 및 맨 아래에 카카오 OAuth js 추가함 -->
<head>
<meta charset="utf-8">
<title>오너블리 - 함께 크리스마스를 즐겨요</title>
<link rel="icon" href="images/ORNABLY.jpg">
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


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/kakaoLogin.css">

<style>
.form-check-input {
	accent-color: #004d00; /* 체크 표시 색 (어두운 초록색) */
}

.form-check-input:checked {
	background-color: #004d00; /* 체크된 상태 배경색 */
	border-color: #004d00; /* 체크된 상태 테두리 색 */
}

.form-check-input:focus {
	border-color: #004d00; /* 포커스 상태 테두리 색 */
	box-shadow: 0 0 0 0.25rem rgba(0, 77, 0, 0.25) !important;
	/* 포커스 상태 그림자 */
}

.form-control:focus {
	border-color: #004d00; /* 포커스 상태 테두리 색 */
	box-shadow: 0 0 0 0.25rem rgba(0, 77, 0, 0.25) !important;
	/* 포커스 상태 그림자 */
}
</style>


</head>

<body>
	<tag:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">로그인</h1>
	</div>
	<!-- Single Page Header End -->

	<!-- Contucts Start -->
	<div class="container-fluid contact py-5 content">
		<div class="container py-5">
			<div class="p-5 bg-light rounded mx-auto" style="max-width: 720px;">
				<div class="row g-4">
					<div class="col-12">
						<div class="text-center mx-auto wow fadeInUp"
							data-wow-delay="0.1s" style="max-width: 900px;">
							<h2 class="d-inline-block pb-2 mb-4"
								style="color: #004d00; border-bottom: 2px solid #004d00;">로그인</h2>

						</div>
					</div>

					<!-- 로그인 페이지 수정 부분 -->
					<div class="col-lg-8 mx-auto">
						<form action="login.do" method="POST">
							<div class="row g-4 wow fadeInUp" data-wow-delay="0.1s">
								<!-- 아이디 입력 -->
								<div class="col-lg-12">
									<div class="form-floating">
										<input type="text" class="form-control" id="id"
											name="userInputId" minlength="4" maxlength="30"
											placeholder="아이디를 입력해주세요" required> <label for="id">아이디</label>
									</div>
								</div>

								<!-- 비밀번호 입력 -->
								<div class="col-lg-12">
									<div class="form-floating">
										<input type="password" class="form-control" id="password"
											name="userInputPassword" minlength="4" maxlength="25"
											placeholder="비밀번호를 입력해주세요" required> <label
											for="password">비밀번호</label>
									</div>
								</div>

								<!-- 아이디 기억하기 체크박스 -->
								<div class="col-lg-12">
									<input class="form-check-input" type="checkbox" id="rememberId">
									<label class="form-check-label" for="rememberId">아이디
										기억하기</label>
								</div>

								<!-- 로그인 버튼 -->
								<div class="col-12">
									<button type="submit" id="loginBtn" class="btn w-100 py-3"
										disabled
										style="background-color: #006400; color: white; border: 2px solid #228B22;">
										로그인</button>
								</div>

								<!-- 카카오 로그인 이미지 버튼-->
									<div class="col-12">
										<img
											id="kakao-login"
											src="images/kakao_login_large_wide.png" alt="카카오계정으로 로그인" class="kakao-img-btn w-100" >
									</div>
							</div>
						</form>

						<!-- 회원가입 페이지로 이동 -->
						<div class="row mt-2">
							<div class="col-12">
								<a href="signInPage.do" id="join-btn" class="btn w-100 py-3">회원가입</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Contuct End -->

	<tag:footer />

	<!-- Back to Top -->
	<a href="#" class="btn btn-primary btn-lg-square back-to-top"><i
		class="fa fa-arrow-up"></i></a>
	<script>
		console.log("contextPath: " + "${pageContext.request.contextPath}"); // 콘솔에 출력
		console.log($().jquery); // jQuery 버전 출력
	</script>

	<!-- JavaScript Libraries -->
	<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script> -->
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>


	<!-- Template Javascript -->
	<script src="js/main.js"></script>

	<!-- 버그샌드위치 js -->

	<script>
		document.addEventListener('DOMContentLoaded', function() {
			let idInput = document.querySelector('input[name="userInputId"]');
			let pwInput = document
					.querySelector('input[name="userInputPassword"]');
			let loginBtn = document.getElementById('loginBtn');
			let rememberCheckbox = document.getElementById('rememberId');

			// 최소 문자 입력 시 경고 메세지

			// 1. 저장된 아이디 있으면 불러오기 (로컬 스토리지)
			let savedId = localStorage.getItem("savedUserId");
			if (savedId) {
				idInput.value = savedId;
				rememberCheckbox.checked = true;
			}

			// 둘중 하나만 공백이면 버튼 비활성화 
			function checkInput() {
				let isIdFilled = idInput.value.trim() !== '';
				let isPwFilled = pwInput.value.trim() !== '';
				loginBtn.disabled = !(isIdFilled && isPwFilled);
			}

			idInput.addEventListener('input', checkInput);
			pwInput.addEventListener('input', checkInput);

			// 2. 로그인 버튼 클릭 시 아이디 저장/삭제 (로컬 스토리지)
			loginBtn.addEventListener("click", function() {

				if (rememberCheckbox.checked) {
					localStorage.setItem("savedUserId", idInput.value.trim());
				} else {
					localStorage.removeItem("savedUserId");
				}
			});

		});
		
		//카카오 로그인 OAuth JS
		$("#kakao-login").on('click',function kakaoLogin(){
	     //이동할 페이지
			location.href = "requestKakaoLogin.do";
		});

	
	</script>
</body>

</html>