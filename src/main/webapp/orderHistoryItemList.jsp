<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ornably"%>
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


<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/header.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/footer.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">

	<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">

</head>

<body>
	<script>
		console.log('${orderItemDatas}');
	</script>
	<!--헤드태그 호출 -->
	<ornably:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">이전 상품 주문내역 상세보기</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Checkout Page Start -->
	<!-- 이전상품주문내역 시작 -->
	<div class="container-fluid overflow-hidden py-5">
		<div class="container py-5 content item-card rounded">
			<form action="#">
				<div class="row g-5 justify-content-center">
				
					<div class="col-md-12 col-lg-6 col-xl-6 mx-auto"
						style="max-width: 1000px; width: 100%;">
						<h1><span style="color:black">주문 상세</span></h1>
						<div class="table-responsive">
							<table class="table table-bordered text-center">
								<thead>
									<tr class="text-center">
										<th scope="col">상품목록</th>
										<th scope="col">개별가격</th>
										<th scope="col">개수</th>
										<th scope="col">리뷰</th>
									</tr>

								</thead>
								<!-- 주문 내역에 대표상품 한개가 있고 상품명을 누르면 주문내역 상세 페이지로 이동 
									즉 주문내역 한개당 상세내역이 한개이거나(사용자가 상품하나만 구매할시) 두개이상이어야함
									 상품명 목록, 개별 가격, 개수, 리뷰작성 버튼 필요함 리뷰 작성 버튼 누르면 리뷰 작성으로 이동 됨
									 페이지 하단에 주문상세내역의 총금액을 보여줌 상품에 리뷰가 이미 작성되었다면 버튼 비활성화-->
								<tbody style="color: black">
									<c:forEach var="order" items="${orderItemDatas}" varStatus="st">

										<tr class="text-center">

											<!-- itemDatas 와 index 맞춰서 접근 -->
											<td class="py-4">${order.itemName}</td>

											<!-- 주문 당시 가격 -->
											<td class="py-4 price">
											<fmt:formatNumber value="${order.orderItemPrice}" type="number" groupingUsed="true"/>원
											</td>

											<!-- 주문 수량 -->
											<td class="py-4">${order.orderItemQuantity}</td>

											<!-- 리뷰 여부 -->
											<td class="align-middle"><c:choose>
													<c:when test="${order.reviewed}">
														<button class="btn custom-btn " 
														style="background-color: #6F4F3A; border-color: #6F4F3A;" disabled>
															리뷰작성 완료</button>
													</c:when>
													<c:otherwise>
														<a
															href="reviewWritePage.do?itemPk=${order.itemPk}&pageType=firstReview"
															class="btn custom-btn"> 리뷰작성 </a>
													</c:otherwise>
												</c:choose></td>

										</tr>

									</c:forEach>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</form>

			<!-- 총금액 -->
			<div class="d-flex justify-content-end mt-4"
				style="max-width: 1000px; margin: auto; font-size: 18px;">
				<c:set var="sum" value="0" />
				<c:forEach var="order" items="${orderItemDatas}">
					<c:set var="sum"
						value="${sum + (order.orderItemPrice * order.orderItemQuantity)}" />
				</c:forEach>
				<c:if test="${not empty orderItemDatas}">
					<span style="color:black"><strong>총 금액 : <fmt:formatNumber value="${sum}" type="number" groupingUsed="true"/>원</strong></span>
				</c:if>

			</div>
			
			
		</div>
	</div>
	<!-- 이전상품주문내역 끝 -->

	<!-- 풋터 태그 호출 -->
<ornably:footer />


	<!-- JavaScript Libraries -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<!-- <script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script> -->	
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>


	<!-- Template Javascript -->
	<script src="js/main.js"></script>



</body>

</html>