<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="ornably"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500;600;700&family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/animate/animate.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<link href="css/style.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">
<style>
	/* ◀◀◀◀◀◀◀ 회원 정보 박스 START ▶▶▶▶▶▶▶ */
	.member-info {
		border: 1px solid #ccc; 	/* 테두리 */
		background-color: #fff; 	/* 배경 흰색 */
		margin-top: 50px; 			/* 위쪽 여백 조절 */ 
		max-width: 770px; 			/* 메뉴 카드 한 줄 너비와 동일 */
	    width: 100%;
	    margin: 50px auto 5px auto; /* 위:50px, 아래:10px */
	}
	
	.member-info .row {
	    margin-left: 0;   	 /* row 기본 좌우 여백 제거 */
	    margin-right: 0;
	    padding-left: 15px;  /* 좌우 안쪽 여백 */
	    padding-right: 15px;
	}
	
	.member-info .col-4,
	.member-info .col-8 {
	    padding-left: 10px;  /* 좌우 여백 조금 더 */
	    padding-right: 10px;
	}
	
	/* 글자 스타일 */
	.member-info .col-4 strong,
	.member-info .col-8 {
	    color: #000;          /* 진한 검정 */
	}
	/* ◀◀◀◀◀◀◀ 회원 정보 박스 END ▶▶▶▶▶▶▶ */
	
	
	
	/* ◀◀◀◀◀◀◀ 마이 메뉴 박스 START ▶▶▶▶▶▶▶*/
	.menu-container {
	    margin-bottom: 130px; /* 밑에 여백 */
	}
	
	/* 메뉴 카드 감싸는 row */
	.menu-row {
	    display: flex;
	    flex-wrap: wrap;
	    justify-content: center; /* 가운데 정렬 */
	    gap: 5px; 				 /* 카드 사이 간격 */
	}
	
	/* 카드 하나 감싸는 col */
	.menu-col {
	    flex: 0 0 auto; /* 카드 고정 크기 */
	}
	
	/* 카드 자체 스타일 */
	.menu-card {
	    display: flex;
	    flex-direction: column;
	    align-items: center;
	    justify-content: center;
	    width: 150px;  /* 정사각형 크기 */
	    height: 150px; 
	    background-color: #ffffff;
	    border: 1px solid #ddd;
	    border-radius: 8px;
	    text-decoration: none;
	    color: #000;
	    transition: 0.3s;
	}
	
	/* 마우스 올렸을 때 스타일 */
	.menu-card:hover {
	    background-color: #f8f9fa;
	    border-color: #aaa;
	    transform: translateY(-3px);
	}
	
	/* 메뉴 아이콘 색상 지정 */
	.menu-card i {
	    color: #000;
	}
	
	.menu-card h6 {
	    margin: 5px 0;		/* 제목 위,아래 여백 */
	    font-weight: 600;	/* 글자 두께 */
	}
	
	/* p태그의 설명 텍스트 스타일 */
	.menu-card p {
	    margin: 0;
	    font-size: 0.85rem;
	    color: #555;
	}
	/* ◀◀◀◀◀◀◀ 마이 메뉴 박스 END ▶▶▶▶▶▶▶ */
</style>
</head>

<body>
	<ornably:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">마이페이지</h1>
	</div>
	<!-- Single Page Header End -->
	

	<!-- 회원정보 Start -->
	<div class="container pt-5 pb-3 mt-4">
	    <div class="row justify-content-center content">
	        <div class="col-md-10 col-lg-9">
	            <div class="member-info p-4 rounded mb-2">
					<h2 class="mb-4 text-center">회원 정보</h2>
	
					<div class="row mb-3">
						<div class="col-4">
							<strong>ID</strong>
						</div>
						<div class="col-8">${accountData.accountId}</div>
					</div>
					<div class="row mb-3">
						<div class="col-4">
							<strong>Name</strong>
						</div>
						<div class="col-8">${accountData.accountName}</div>
					</div>
	
					<div class="row mb-3">
						<div class="col-4">
							<strong>Mobile</strong>
						</div>
						<div class="col-8">
							<c:set var="phone" value="${accountData.accountPhone }" />
							${fn:substring(phone,0,3)}-${fn:substring(phone,3,7)}-${fn:substring(phone,7,11)}
						</div>
					</div>
	
					<div class="row mb-3">
						<div class="col-4">
							<strong>Email</strong>
						</div>
						<div class="col-8">${accountData.accountEmail}</div>
					</div>
				</div>
				<!-- 테두리 박스 End -->
			</div>
		</div>
	</div>
	

	<!-- 마이페이지 메뉴 Start -->
	<div class="container menu-container mt-3">
		<div class="menu-row text-center content">
			<!-- 내 배송지 목록 보기 -->
	        <div class="menu-col">
	            <a href="deliveryAddressListPage.do" class="menu-card"> 
	                <i class="fas fa-map-marker-alt fa-2x mb-2"></i>
	                <h6>Address</h6>
	                <p>배송지 목록보기</p>
	            </a>
	        </div>
	
	        <!-- Order -->
	        <div class="menu-col">
	            <a href="orderHistoryListPage.do" class="menu-card"> 
	                <i class="fas fa-file-alt fa-2x mb-2"></i>
	                <h6>Order</h6>
	                <p>주문내역 조회</p>
	            </a>
	        </div>
	
	        <!-- My Reviews -->
	        <div class="menu-col">
	            <a href="myReviewListPage.do" class="menu-card"> 
	                <i class="fas fa-comment-dots fa-2x mb-2"></i>
	                <h6>Reviews</h6>
	                <p>작성한 리뷰</p>
	            </a>
	        </div>
	
	        <!-- Like it -->
	        <div class="menu-col">
	            <a href="wishlistPage.do" class="menu-card"> 
	                <i class="fas fa-thumbs-up fa-2x mb-2"></i>
	                <h6>Like it</h6>
	                <p>좋아요</p>
	            </a>
	        </div>
	
	        <!-- 회원 탈퇴 -->
	        <div class="menu-col">
	            <a href="signOutPage.do" class="menu-card"> 
	                <i class="fas fa-user-slash fa-2x mb-2"></i>
	                <h6>회원 탈퇴</h6>
	                <p>계정 삭제</p>
	            </a>
	        </div>
	    </div>
	</div>
	<!-- 마이페이지 메뉴 End -->

	<!-- footer 태그 호출 -->
	<ornably:footer />
</body>
</html>