<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
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

	<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">
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

	<tag:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">리뷰</h1>
	</div>
	<!-- Single Page Header End -->
	<!-- 
		/*	받는 데이터 : 
		 * [공통] : 
		 * 		itemData[itemPk, itemImageUrl, itemName, itemPrice, itemStar]
		 * 		reviewType : firstReview / updateReview
		 * [firstReview] : 없음
		 * [updateReview] : 
		 * 		reviewData[reviewPk, accountName, reviewTitle, reviewStar, reviewContent]
		 */
				 -->

	<!-- Single Products Start -->
	<!-- 리뷰작성하기 시작 -->
	<div class="container-fluid shop py-5 ">
		<div class="container py-5">

			<!-- 여기부터: 상품 + 리뷰 전체 감싸는 row -->
			<div class="row g-5 align-items-center rounded content item-card">

				<!--  LEFT: 상품 영역 -->
				<div class="col-md-4 text-center">

					<div style="width: 100%; max-width: 240px; margin: auto;">
						<div class="single-item">
							<div class="single-inner bg-light rounded">
								<img src="${pageContext.request.contextPath }${itemData.itemImageUrl }" class="img-fluid rounded"
									alt="상품 이미지">
							</div>
						</div>
					</div>

					<!--  상품명 & 평점 -->

					<h4 class="fw-bold mt-4">${itemData.itemName }</h4>
					<h5 class="fw-bold mb-3">
						가격:
						<fmt:formatNumber
							value="${empty itemData.itemPrice ? 0 : itemData.itemPrice}"
							pattern="#,###" />
						원
					</h5>
					<div class="rating_box">
						<div class="rating">
							★★★★★ <span class="rating_star"
								style="width: ${itemData.itemStar*10}%;">★★★★★</span>
						</div>
					</div>
				</div>

				<!--  RIGHT: 리뷰 영역 -->
				<!-- 문법 -->
				<c:choose>
					<c:when test="${reviewType eq 'firstReview'}">

						<%-- 리뷰 작성일 때  --%>
						<div class="col-md-8">
				


							<h4 class="fw-bold mb-4 text-center">리뷰작성하기</h4>

							<form action="reviewWrite.do" method="GET" class="review-form">
								<input type="hidden" name="condition" value="firstReview">
								<input type="hidden" name="itemPk" value="${itemData.itemPk }">

								<div class="border-bottom mb-4">
									<input type="text" class="form-control border-0"
										name="reviewTitle" 
										placeholder="15자 이내의 제목을 입력해주세요">
								</div>

								<div class="border-bottom mb-4">
									<textarea class="form-control border-0" rows="12"
										name="reviewContent"
										style="resize: none;" placeholder="600자 이내의 내용을 입력해주세요"></textarea>
								</div>

								<div class="d-flex justify-content-between align-items-center" style="margin-bottom: 25px;">
									<div class="rating_box range">
										<div class="rating" style="font-size: 20px;">
											★★★★★ <span class="rating_star" style="width: 100%;">★★★★★</span>
											<input type="range" name="reviewStar" value="10" step="1" min="2"
												max="10">
										</div>
									</div>
									<input type="submit" class="btn custom-btn rounded-pill px-4 py-2" 
									style="background-color: #6F4F3A; border-color: #6F4F3A;" value="등록하기">
								</div>
								<%--  reviewPk itemPk reviewTitle reviewContent reviewStar 반환 --%>
							</form>
						</div>

					</c:when>

					<%-- 리뷰 수정일 때 --%>

					<c:otherwise>
						<div class="col-md-8">

							<%-- reviewData[reviewPk, accountName, reviewTitle, reviewStar, reviewContent]  --%>

							<h4 class="fw-bold mb-4 text-center">리뷰수정하기</h4>

							<form action="reviewWrite.do" method="GET" class="review-form">
								<input type="hidden" name="condition" value="updateReview">
								<input type="hidden" name="reviewPk"
									value="${reviewData.reviewPk}"> 
								<input type="hidden" name="reviewStar" value="${ reviewData.reviewStar}">
								<input type="hidden" name="itemPk" value="${itemData.itemPk }"> 
								<!-- reviewType : firstReview OR updateReview -->


								<div class="border-bottom mb-4">
									<input type="text" class="form-control border-0"
										name="reviewTitle" 
										placeholder="15자 이내의 제목을 입력해주세요"
										value="${reviewData.reviewTitle }">
								</div>

								<div class="border-bottom mb-4">
									<textarea class="form-control border-0" rows="12"
										style="resize: none;" placeholder="600자 이내의 내용을 작성해주세요"
										name="reviewContent"
										>${reviewData.reviewContent }</textarea>
								</div>

								<div class="d-flex justify-content-between align-items-center"  style="margin-bottom: 25px;">
									<div class="rating_box">
										<div class="rating" style="font-size: 20px;"> 
											★★★★★ <span class="rating_star" style="width: ${reviewData.reviewStar*10}%;">★★★★★</span>
										</div>
									</div>


									<%--<a href="#" class="btn btn-primary rounded-pill px-4 py-2">
										등록하기 </a> --%>
									<input type="submit" class="btn custom-btn rounded-pill px-4 py-2" 
									style="background-color: #6F4F3A; border-color: #6F4F3A;" value="등록하기">
								</div>
								<%-- condition reviewPk itemPk reviewTitle reviewContent reviewStar 반환  --%>
							</form>
						</div>

					</c:otherwise>
				</c:choose>

			</div>
			<!--  여기까지가 변경된 구조 -->

		</div>
	</div>

	<!--  상품사진 밑에 동그라미 제거 -->
	<style>
.owl-dots {
	display: none !important;
}
</style>
	<!-- Single Products End -->
	<!-- 리뷰작성하기 끝 -->
	<tag:footer />



	<!-- JavaScript Libraries -->
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

	<!-- Custom JavaScript (버그샌드위치) -->
	<script src="customResource/ratingStar.js"></script>
	<script>
		// 리뷰 작성하기 눌렀을때 
		// 1) 리뷰제목, 리뷰 내용 유효성 검사 해주기
		// 2) condition에 따라 reviewPk도 함께 보내주기
		$('.review-form').on('submit', function (e) {
			console.log("등록 버튼 눌림");
			  const title =  $(this).find('input[name="reviewTitle"]').val().trim();
			  const content =  $(this).find('textarea[name="reviewContent"]').val().trim();
			
			  // 제목 검사 (1~15자)
			  if (title.length <= 0 || title.length > 15) {
			      Swal.fire({
					  title: '알림',
					  text: '리뷰 제목은 1~15자 입력해주세요.',  
					  icon: 'info',
					  confirmButtonText: '확인',
					  allowOutsideClick: false,
					  allowEscapeKey: true,
					});
			      e.preventDefault();
			      return;
			  }
			
			  // 내용 검사 (1~600자)
			  if (content.length === 0 || content.length > 600) {
			      Swal.fire({
					  title: '알림',
					  text: '리뷰 내용은 1~600자 입력해주세요.',  
					  icon: 'info',
					  confirmButtonText: '확인',
					  allowOutsideClick: false,
					  allowEscapeKey: true,
					});
			      e.preventDefault();
			      return;
			  }
			  $(this).find('input[name="reviewTitle"]').val(title);
			  $(this).find('textarea[name="reviewContent"]').val(content);
			  // 통과 → 그대로 submit
		});
		
	</script>
</body>

</html>