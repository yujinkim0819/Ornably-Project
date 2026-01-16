<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

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
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">



<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">

<!-- Custom Stylesheet (버그샌드위치) -->
<link href="customResource/ratingStar.css" rel="stylesheet">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">
<!-- ▼▼▼모달창 cdn ▼▼▼ -->
<script
	src="
			https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.all.min.js
			"></script>
<link
	href="
			https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.min.css
			"
	rel="stylesheet">
<!-- ▲▲▲ 모달창 cdn ▲▲▲ -->
</head>

<body>

	<!-- Spinner Start -->
	<div id="spinner"
		class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
		<div class="spinner-border text-primary"
			style="width: 3rem; height: 3rem;" role="status">
			<span class="sr-only">Loading...</span>
		</div>
	</div>
	<!-- Spinner End -->

	<tag:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">내가 작성한 리뷰내역</h1>
	</div>
	<!-- Single Page Header End -->


	<div class="container-fluid shop py-5">
		<div class="container py-5 item-card content rounded">

			<!-- 
		reviewPk, itemPk, reviewTitle, reviewStar, reviewContent, itemName, itemImageUrl, itemPrice
		받아서 모두 출력해주기
		 -->

			<!-- 리뷰 세트 반복 -->
			<c:if test="${empty reviewDatas }">
				<div class="text-center py-5 text-muted">
					<i class="bi bi-chat-dots" style="font-size: 40px;"></i>
					<p class="mt-3 mb-0">작성한 리뷰가 없습니다.</p>
				</div>
			</c:if>
			<c:forEach var="reviewData" items="${reviewDatas }">
				<div class="row mb-5 align-items-start">

					<%-- 상품 정보 (한 세트로 묶음)--%>
					<div class="col-lg-3 text-center">

						<img
							src="${pageContext.request.contextPath }${reviewData.itemImageUrl }"
							class="img-fluid rounded mb-2" style="width: 120px;" alt="상품 이미지">

						<h6 class="mb-1">${reviewData.itemName }</h6>

						<h5 class="fw-bold mb-0">
							가격:
							<fmt:formatNumber
								value="${empty reviewData.itemPrice ? 0 : reviewData.itemPrice}"
								pattern="#,###" />
							원
						</h5>
					</div>


					<%--리뷰 내용  --%>
					<div class="col-lg-9">
						<!-- <p class="mb-1 text-muted" style="font-size: 14px;">리뷰날짜 (생략)</p> -->
						<span style="font-size: 25px; font-weight: bold; color: black"
							class="mb-2 d-flex align-items-center gap-2">
							${reviewData.reviewTitle}
							<div class="rating_box" style="margin-left: 10px;">
								<div class="rating">
									★★★★★ <span class="rating_star"
										style="width: ${reviewData.reviewStar*10}%;">★★★★★</span>
								</div>
							</div>
						</span><br>


						<p class="mb-3">${reviewData.reviewContent}</p>
						<button data-reviewpk="${reviewData.reviewPk }"
							class="btn btn-outline-danger btn-sm me-2 review-delete-button">삭제</button>
						<a
							href="reviewWritePage.do?pageType=updateReview&reviewPk=${reviewData.reviewPk }&itemPk=${reviewData.itemPk}"
							class="btn btn-outline-success btn-sm review-update-button">수정</a>

					</div>

				</div>

				<hr>
			</c:forEach>

		</div>
	</div>

	<tag:footer />

	<!-- JavaScript Libraries -->
	<!-- <script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>-->
	<!-- <script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script> -->
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/counterup/counterup.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="lib/lightbox/js/lightbox.min.js"></script>


	<!-- Template Javascript -->
	<script src="js/main.js"></script>

	<!-- Custom Javascript -->
	<script src="customResource/ratingStar.js"></script>
	<script>
		$(window).on('load',function(){
			// 리뷰 삭제 버튼 .review-delete-button
			$('.review-delete-button').each(function(index, element){
				$(element).on('click', function(e){
					console.log('삭제 버튼 눌림');
					// 비동기 상품 삭제 요청 보내기
					console.log('reviewpk: ' + $(this).data('reviewpk'));
					Swal.fire({
						  title: '알림',
						  text: '정말 리뷰를 삭제하시겠습니까?', 
						  icon: 'info',
						  showCancelButton: true,
						  confirmButtonText: '확인',
						  cancelButtonText: '취소',
						  allowOutsideClick: false,
						  allowEscapeKey: true,
						}).then((result) => {
						  if (result.isConfirmed) {
							  $.ajax({
									url: "${pageContext.request.contextPath}/DeleteReviewServlet",
									type: "POST", // 데이터 수정 요청이므로 POST 사용
									dataType: "text", // true/false으로 구분
									data: { reviewPk: $(this).data('reviewpk') }, // 자신의 data-reviewpk의 값 꺼내오기
									success: function(result){
										if(result === 'true'){
											Swal.fire({
												  title: '알림',
												  text: '리뷰가 삭제되었습니다', 
												  icon: 'success',
												  confirmButtonText: '확인',
												  allowOutsideClick: false,
												  allowEscapeKey: true
												});
											location.reload(); // 화면 새로고침
										}
										else if(result === 'false'){
											Swal.fire({
												  title: '알림',
												  text: '리뷰 삭제 실패', 
												  icon: 'error',
												  confirmButtonText: '확인',
												  allowOutsideClick: false,
												  allowEscapeKey: true
												});
										}
										else{
											Swal.fire({
												  title: '알림',
												  text: '원인 모를 오류 발생', 
												  icon: 'error',
												  confirmButtonText: '확인',
												  allowOutsideClick: false,
												  allowEscapeKey: true
												});
										}
									},
									error: function(error){
										console.log('[로그] 에러 발생');
										console.log(error);
									}
								});
						  }
						  });
						
				});
			});
			
			// 리뷰 수정 버튼 .review-update-button
			$('.review-update-button').each(function(index, element){
				console.log(index);
				$(element).on('click', function(e){
					console.log('리뷰 수정 버튼 눌림');
					// 비동기 상품 삭제 요청 보내기
					console.log('reviewpk: ' + $(this).data('reviewpk'));
					
					
				});
			});
			
		});
	</script>
</body>

</html>