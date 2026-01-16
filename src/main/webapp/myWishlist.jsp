<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500;600;700&family=Roboto:wght@400;500;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">
<link href="lib/animate/animate.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">
<style>
/* 추가: 좋아요 아이콘 클릭 영역 커서 */
.like-toggle {
	cursor: pointer;
	color: #ff6600; /* Primary color를 임시로 주황색으로 설정 */
	transition: color 0.2s;
	font-size: 1.2rem;
}
/* 상품 이미지와 아이콘을 수직 중앙 정렬 */
.table td, .table th {
    vertical-align: middle; /* 수직 중앙 정렬 */
}

/* 좋아요 아이콘의 크기나 위치를 미세 조정할 경우 */
.like-toggle {
    font-size: 1.5rem; /* 아이콘 크기 조정 */
    color: #6F4F3A;
}

</style>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">

</head>

<body>
	<!-- header 태그 호출 -->
	<tag:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">내 관심상품</h1>
	</div>
	<!-- Single Page Header End -->

	<div class="container-fluid overflow-hidden py-5 ">
		<div class="container py-5  content item-card rounded"
			style="margin: 80px auto;">
			<!-- 
			<div class="row justify-content-center">
				<div class="col-md-12 col-lg-8 col-xl-8 text-center">
					<h1 class="mb-4 wow fadeInUp" data-wow-delay="0.1s">내 관심상품</h1>
				</div>
			</div>
 -->
			<form action="#">
				<div class="row g-5 justify-content-center">
					<div class="col-md-12 col-lg-10 col-xl-8 wow fadeInUp"
						data-wow-delay="0.3s">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr class="text-center">
										<th scope="col"><span class="text-dark fw-bold">상품</span></th>
										<!--  class="text-start" -->
										<th scope="col"><span class="text-dark fw-bold">상품 명</span></th>
										<th scope="col"><span class="text-dark fw-bold">좋아요</span></th>
										<th scope="col"><span class="text-dark fw-bold">가격</span></th>
									</tr>
								</thead>

								<!-- 관심 상품 없을 때 -->
								<c:if test="${empty itemList}">
									<tr>
										<td colspan="4" class="text-center py-5 text-muted"><i
											class="bi bi-cart-fill" style="font-size: 40px;"></i>
											<p class="mt-3 mb-0">관심 상품이 없습니다..</p></td>
									</tr>
								</c:if>

								<!-- 관심 상품 있을 때 -->
								<c:forEach var="item" items="${itemList}">
									<tr class="text-center" data-product-id="${item.itemPk}">
										<!-- 상품 이미지 -->
										<th scope="row" class="py-4 text-center"><a
											href="itemDetail.do?itemPk=${item.itemPk}"> <img
												src="${pageContext.request.contextPath }${item.itemImageUrl}"
												alt="${item.itemName}" class="img-fluid rounded"
												style="width: 100px; height: 100px;">
										</a></th>

										<!-- 상품명 -->
										<td class="py-4 text-center"><a
											href="ornamentDetailPage.do?itemPk=${item.itemPk}"
											class="text-dark fw-bold"> ${item.itemName} </a></td>

										<!-- 좋아요 -->
										<td class="py-4 text-center"><i
											class="fas fa-thumbs-up like-toggle" id="like-${item.itemPk}"
											data-item-pk="${item.itemPk}" onclick="toggleLike(this)"></i>
										</td>

										<!-- 가격 -->
										<td class="py-4 text-center">
										<span class="text-dark fw-bold"><fmt:formatNumber
												value="${item.itemPrice}" pattern="#,###" />원</span></td>
									</tr>
									<%--
									<tr class="text-center" data-product-id="${item.itemPk}">
										<!-- 상품 이미지 -->
										<th scope="row" class="py-4"><a
											href="itemDetail.do?itemPk=${item.itemPk}"> <img
												src="${pageContext.request.contextPath }${item.itemImageUrl}"
												alt="${item.itemName}" style="width: 100px; height: 100px;"
												class="img-fluid rounded">
										</a></th>

										<!-- 상품명 -->
										<td class="py-4"><a
											href="itemDetail.do?itemPk=${item.itemPk}"
											class="text-dark fw-bold"> ${item.itemName} </a></td>

										<!-- 좋아요 -->
										<td class="py-4"><i class="fas fa-thumbs-up like-toggle"
											id="like-${item.itemPk}" data-item-pk="${item.itemPk}"
											onclick="toggleLike(this)"></i></td>

										<!-- 가격 -->
										<td class="py-4"><fmt:formatNumber
												value="${item.itemPrice}" pattern="#,###" />원</td>
									</tr> --%>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>


	<!-- footer 태그 호출 -->
	<tag:footer />

	<script>
    	// 좋아요 상태 변경
    	function toggleLike(element) {
    		const accountPk = parseInt('${sessionScope.accountPk}'); 	// 세션에서 가져온 accountPk
            const itemPk = element.dataset.itemPk;						// 내가 누룬 버튼이 어떤 상품인지 식별하는 ID를 얻음
		    console.log(itemPk);
			// 좋아요 버튼 클릭 시 서버로 요청을 보냄
		    fetch('UpdateWishlistServlet', {
		        method: 'POST',
		        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		        body: `accountPk=${accountPk}&itemPk=\${itemPk}`
		        // accountPk : 로그인한 계정 번호 | itemPk : 클릭한 상품 번호
		    })
		    .then(response => response.text())
		    .then(result => {
		        if(result === 'true') {
		            // DB 업데이트 성공 시 UI 변경
		            if (element.classList.contains('fas')) {
		                // 현재 채워진 상태 (fas) -> 빈 상태 (far)로 변경
		                element.classList.remove('fas', 'fa-thumbs-up');
		                element.classList.add('far', 'fa-thumbs-up');
		            } else {
		                // 현재 빈 상태 (far) -> 채워진 상태 (fas)로 변경
		                element.classList.remove('far', 'fa-thumbs-up');
		                element.classList.add('fas', 'fa-thumbs-up');
		            }
		        } else {
		            alert('좋아요 처리 실패');
		        }
		    })
		    .catch(err => console.error(err));
		}
    </script>


</body>

</html>