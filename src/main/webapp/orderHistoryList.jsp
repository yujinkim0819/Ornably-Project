<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">
<link href="css/style.css" rel="stylesheet">
</head>

<body>
	<!--헤드태그 호출 -->
	<tag:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">이전 상품 주문내역</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Checkout Page Start -->
	<!-- 이전상품주문내역 시작 -->
	<div class="container-fluid overflow-hidden py-5">
		<div class="container py-5 col-lg-8">
			<form action="#">
				<div class="row g-5 justify-content-center content item-card rounded">
					<div class="col-md-12 col-lg-6 col-xl-6 mx-auto"
						style="max-width: 1000px; width: 100%;">
						<div class="table-responsive">
						<h1><span style="color:black">주문 내역</span></h1>
							<table class="table table-bordered text-center">
								<thead>
									<!-- 제목 -->
									<tr class="text-center">
										<th scope="col">날짜</th>
										<th scope="col">현재상태</th>
										<th scope="col">배송지</th>
										<th scope="col">상품명</th>
										<th scope="col">가격</th>
									</tr>
								</thead>
								<tbody style="color: black;">
									<!-- 주문 내역	보기  -->
									<c:choose>
										<c:when test="${empty orderDatas}">
											<tr>
												<td colspan="5" class="text-center py-5 text-muted"><i
											class="bi bi-cart-fill" style="font-size: 40px;"></i>
											<p class="mt-3 mb-0">주문 내역이 없습니다</p></td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach var="order" items="${orderDatas}">
												<tr class="text-center">
													<td class="py-4">${order.orderDate}</td>
													<td class="py-4">${order.orderStatus}</td>
													<td class="py-4">${order.addressName}</td>
													<td class="py-4"><a
														href="orderHistoryDetailPage.do?orderPk=${order.orderPk}" style="color: #6F4F3A; font-weight: bold">
															${order.orderSignatureItemName} </a></td>
													<td class="py-4">
													<fmt:formatNumber value="${order.orderTotalAmount}" type="number" groupingUsed="true"/>원
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- 이전상품주문내역 끝 -->

	<!-- 풋터 태그 호출 -->
	<tag:footer />

	<!-- JavaScript Libraries -->
	<!-- <script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script> -->
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>


	<!-- Template Javascript -->
	<script src="js/main.js"></script>
</body>

</html>