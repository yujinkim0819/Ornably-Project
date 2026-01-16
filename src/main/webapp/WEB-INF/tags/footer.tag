<%@ tag language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/footer.css">
<style>
/* 기본(비활성) */
.wish-pill {
	border: 1px solid #ddd;
	transition: all 0.2s ease;
}

.wish-pill i {
	color: #bbb;
	font-size: 18px;
}

/* 활성 상태 */
.wish-pill.active {
	background-color: #fddede;
	border-color: #e74c3c;
}

.wish-pill.active i {
	color: #e74c3c;
}

/* hover */
.wish-pill:hover {
	transform: scale(1.05);
}
</style>
<style> /*헤더 푸터 스타일*/
/*** 브랜드 로고 텍스트 ***/
.brand-logo {
	font-size: 2.4rem; /* 글자 크기 */
	font-weight: 600; /* 두께 */
	color: #2c2c2c; /* 글자 색 */
	letter-spacing: 1px; /* 자간 */
}

/* 아이콘 */
.brand-icon {
	color: #8b8b8b; /* 아이콘 색 */
	font-size: 2rem;
}

/* hover 효과 */
.navbar-brand:hover .brand-logo {
	color: #000;
}

.navbar-brand:hover .brand-icon {
	color: #555;
}

/*** 홈페이지 상단 메뉴 ***/
.simple-nav-link {
	padding: 8px 12px;
	border-radius: 4px;
}

.simple-nav-link:hover {
	background-color: #f8f9fa;
}

/*** 홈페이지 메인 이미지  ***/
.carousel-bg {
	background-image: url("/BugSandwichOrnamentMall/images/backTree.jpg");
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	min-height: 900px;
}

/* 홈페이지 가운데 이미지*/
.btn-custom {
	background-color: #555555; /* 조금 진한 회색 */
	color: #ffffff; /* 글자 색 흰색 */
	padding: 8px 20px; /* 버튼 크기 조정 */
	font-weight: 500;
	border-radius: 8px; /* 살짝 둥근 모서리 */
	text-decoration: none;
	transition: 0.3s;
}

.btn-custom:hover {
	background-color: #333333; /* 호버 시 더 진한 회색 */
	color: #ffffff;
}

/* 홈페이지 가운데 글씨 정렬 & 크기 등 */
.carousel-content h1 {
	font-size: 3rem; /* 화면에 맞게 글자 크기 조정 */
	line-height: 1.2;
}

.carousel-content p {
	font-size: 1.2rem;
	color: #f0f0f0;
}

.btn-custom {
	background-color: #555555; /* 진한 회색 */
	color: #ffffff;
	padding: 10px 25px;
	font-weight: 500;
	border-radius: 10px;
	text-decoration: none;
	transition: 0.3s;
}

.btn-custom:hover {
	background-color: #333333;
	color: #ffffff;
}

기존에 있는거 수정 /*** Topbar Start ***/   
.topbar {
	padding: 2px 10px 2px 20px;
	background: #f5f5f5 !important; /* 연한 회색 */
}

.topbar a, .topbar a i, .topbar small {
	color: #6c757d !important; /* 연한 글자 색 (text-muted) */
	transition: 0.5s;
}

.topbar a:hover, .topbar a i:hover {
	color: #343a40 !important; /* 호버 시 조금 진하게 */
}

@media ( max-width : 768px) {
	.topbar {
		display: none;
	}
}
/*** Topbar End ***/

/*** Footer Start ***/
/* 푸터 전체 배경 밝게 */
.footer {
	background-color: #f9f9f9 !important; /* 밝은 배경 */
	color: #000000 !important; /* 글씨 검정 */
	font-family: 'Roboto', sans-serif;
}

/* 푸터 안 제목, 텍스트, 링크 */
.footer h4, .footer p, .footer a, .footer .footer-item {
	color: #000000 !important; /* 검정 글씨 */
}

/* 링크 hover */
.footer a:hover {
	color: #222222 !important; /* 살짝 진한 검정 */
	text-decoration: none;
}

/* 아이콘 색상 통일 */
.footer .fas, .footer .far, .footer .fab {
	color: #000000 !important; /* 아이콘 검정 */
	transition: color 0.3s;
}

.footer .fas:hover, .footer .far:hover, .footer .fab:hover {
	color: #222222 !important; /* hover 시 진한 검정 */
}

/* 폼 컨트롤 */
.footer .form-control {
	background-color: #ffffff !important; /* 밝은 배경 */
	color: #000000 !important;
	border: 1px solid #ccc !important;
}

/* 버튼 – 기본/hover */
.footer .btn, .footer .btn-primary, .footer .btn-secondary, .footer .btn-custom
	{
	background-color: #e0e0e0 !important; /* 밝은 회색 버튼 */
	color: #000000 !important; /* 글씨 검정 */
	border: none !important;
	transition: background-color 0.3s, color 0.3s;
}

.footer .btn:hover, .footer .btn-primary:hover, .footer .btn-secondary:hover,
	.footer .btn-custom:hover {
	background-color: #d0d0d0 !important; /* hover 시 약간 더 진한 회색 */
	color: #000000 !important;
}

/* copyright */
.copyright {
	border-top: 1px solid rgba(0, 0, 0, 0.08) !important;
	background-color: #f9f9f9 !important; /* 밝은 배경 */
	color: #000000 !important;
	text-align: center;
	padding: 15px 0;
	font-size: 0.9rem;
}

/* 제목 */
.footer-dark .footer-title {
	color: #000000; /* 제목 검정 */
	margin-bottom: 1rem;
	font-weight: 600;
}

/* 링크 */
.footer-dark a {
	display: block;
	color: #000000; /* 검정 */
	text-decoration: none;
	margin-bottom: 0.4rem;
	transition: 0.3s;
}

.footer-dark a:hover {
	color: #222222;
}

/* 소셜 아이콘 */
.footer-dark .social-icons a {
	display: inline-block;
	width: 35px;
	height: 35px;
	margin-right: 8px;
	text-align: center;
	line-height: 35px;
	border-radius: 50%;
	background-color: #e0e0e0; /* 밝은 버튼 배경 */
	color: #000000;
	transition: 0.3s;
}

.footer-dark .social-icons a:hover {
	background-color: #d0d0d0;
	color: #222222;
}

/* CS CENTER / info */
.footer-dark .text-light {
	color: #000000 !important;
	font-size: 0.85rem;
}
/*** Footer End ***/
.back-to-top {
	position: fixed;
	right: 30px;
	bottom: 30px;
	display: flex;
	width: 45px;
	height: 45px;
	align-items: center;
	justify-content: center;
	transition: 0.5s;
	z-index: 99;
	/* 색상 변경 */
	background-color: #f0f0f0 !important; /* 밝은 회색 배경 */
	color: #000000 !important; /* 아이콘 검정 */
	border: none !important;
	transition: background-color 0.3s, color 0.3s;
}

.back-to-top:hover {
	background-color: #e0e0e0 !important; /* hover 시 약간 진한 회색 */
	color: #000000 !important; /* 아이콘 검정 유지 */
}

/* copyright */
.copyright {
	background-color: #333333 !important; /* 진한 회색 배경 */
	color: #ffffff !important; /* 글씨 흰색 */
	text-align: center;
	padding: 15px 0;
	font-size: 0.9rem;
}

/* 내부 링크 */
.copyright a {
	color: #ffffff !important; /* 링크 글씨도 흰색 */
	text-decoration: none;
	border-bottom: 1px solid #ffffff; /* 원하면 하단 밑줄 흰색으로 */
	transition: color 0.3s, border-color 0.3s;
}

.copyright a:hover {
	color: #ffeb99 !important; /* hover 시 연노랑 */
	border-bottom-color: #ffeb99 !important;
}

/* 아이콘 */
.copyright i {
	color: #ffffff !important; /* 저작권 아이콘 흰색 */
}

/*** div.con Start ***/
/* copyright 안의 container만 덮어쓰기 */
.copyright .container {
	background-color: #333333 !important; /* 어두운 회색 */
	border-top: 1px solid rgba(255, 255, 255, 0.08);
}
</style>

<style>
.rainbow {
	background: linear-gradient(45deg, red, orange, yellow, green, blue, indigo, violet, red); 
	background-clip: text; 
	color: transparent; 
	font-size: 15px;
}
</style>
<!-- Footer Start -->
<div class="container-fluid footer py-5 wow fadeIn content"
	data-wow-delay="0.2s">
	<div class="container py-5">
		<!-- Footer Start -->
		<div class="container-fluid footer-dark py-5">
			<div class="container py-5">

				<div class="row g-4">

					<!-- 주소 -->
					<div class="col-md-4">
						<div class="p-4 h-100">
							<div
								class="rounded-circle d-flex align-items-center justify-content-center mb-4"
								style="width: 70px; height: 70px; background-color: #cccccc;">
								<i class="fas fa-map-marker-alt fa-2x" style="color: #ffffff;"></i>
							</div>
							<h5 class="footer-title">주소</h5>
							<p class="mb-1">서울 강남구 테헤란로 146</p>
							<p>현익빌딩 3, 4층</p>
						</div>
					</div>

					<!-- 블로그 -->
					<div class="col-md-4">
						<h5 class="footer-title">블로그</h5>

						<p>
							<strong>유진</strong> : <a href="https://bobaejin.tistory.com/"
								target="_blank">
								<span class="rainbow">유진개발일기</span></a>
						</p>
						<p>
							<strong>변희인</strong> : <a
								href="https://blog.naver.com/qusakfdl111" target="_blank">
								<span class="rainbow">JAVA_STUDY_변희인</span></a>
						</p>
						<p>
							<strong>송이</strong> : <a href="https://kkobug2.tistory.com/"
								target="_blank" class="rainbow"><span class="rainbow">꼬부기의 코딩수련소</span></a>
						</p>
						<p>
							<strong >허완</strong> : <a
								href="https://www.notion.so/272117a41d4d80b1b0faffde0630da77/"
								target="_blank"><span class="rainbow">개인활동</span></a>
						</p>
					</div>

					<!-- 기타 정보 -->
					<div class="col-md-4">
						<h5 class="footer-title">기타 정보</h5>
						<p>BugSandwich</p>
						<p>김유진 · 변희인 · 정송이 · 허완</p>
						<p>코리아IT아카데미 강남점 : 02-538-0021</p>

						<div class="social-icons mt-3">
							<a href="https://www.instagram.com/koreaitacademy_/"
								target="_blank"><i class="fab fa-instagram"></i></a> <a
								href="https://gangnam.koreaisacademy.com/" target="_blank"><i
								class="fas fa-home"></i></a> <a
								href="https://blog.naver.com/koreaisit" target="_blank"><i
								class="fas fa-globe"></i></a> <a
								href="https://www.youtube.com/@koreaitacademy_" target="_blank"><i
								class="fab fa-youtube"></i></a>
						</div>
					</div>

				</div>
			</div>
		</div>
		<!-- Footer End -->

	</div>
</div>
<!-- Footer End -->




<!-- Back to Top -->
<a href="#" class="btn btn-lg-square back-to-top"><i
	class="fa fa-arrow-up"></i></a>

<!-- JavaScript Libraries -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="lib/wow/wow.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>


<!-- Template Javascript -->
<script src="js/main.js"></script>

</html>