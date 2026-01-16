<!-- 메인 페이지 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ornably"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>오너블리 - 함꼐 크리스마스를 즐겨요</title>
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
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/header.css"> -->
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/footer.css"> -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">

</head>

<body>




	<!-- header 태그 호출 -->
	<ornably:header />

	<!-- Carousel Start -->
	<div class="container-fluid carousel bg-light px-0">
		<div class="row g-0 justify-content-end">
			<div class="col-12">

				<!-- Owl Carousel -->
				<div class="header-carousel owl-carousel">

					<!-- Carousel Item -->
					<div class="header-carousel-item carousel-bg"
						onclick="location.href='ornamentListPage.do'">
						<div class="container">
							<div class="row align-items-center justify-content-center"
								style="min-height: 600px;">
								<div
									class="col-md-8 col-lg-6 carousel-content text-center text-md-start p-4 p-md-5">

									<h1 class="display-4 fw-bold mb-3 hero-title"
    style="font-size: 72px; font-weight: bold; 
    background-image: linear-gradient(to right, #BF953F, #FCF6BA, #B38728, #FBF5B7); 
    -webkit-background-clip: text; background-clip: text; color: transparent; -webkit-text-fill-color: transparent;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.1);">
    감성자극 오너먼트
</h1>

									<p class="lead mb-4 hero-subtitle" style="font-weight: bold">감성 크리스마스를 위한 오너먼트</p>

									<a class="btn btn-custom btn-lg hero-cta"
										href="ornamentListPage.do" style="background-color: #BF953F; color: black;"> 바로가기 </a>

								</div>
							</div>
						</div>
					</div>
					<!-- Carousel Item End -->

				</div>
			</div>
		</div>
	</div>
	<!-- Carousel End -->

	<!-- footer 태그 호출 -->
	<ornably:footer />

</body>

</html>