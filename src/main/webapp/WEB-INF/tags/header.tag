<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/header.css">
	<style>
	.brand-logo-img {
  width: 60px;
  height: 60px;
  object-fit: contain;
  vertical-align: middle;
}
/* 헤더를 배경과 분리해서 흰색으로 */
header.container-fluid {
  background: #fff !important;   /* 완전 흰색 */
  position: sticky;              /* 스크롤 따라오게(원치 않으면 삭제) */
  top: 0;
  z-index: 9999;                 /* 배경/카드 위로 */
}

/* 필요하면 헤더 아래쪽 경계선 */
header.container-fluid {
  border-bottom: 1px solid rgba(0,0,0,0.08);
}

	</style>
<header class="container-fluid px-5 py-4 d-none d-lg-block">
	<div class="row gx-0 align-items-center text-center">

		<div class="col-md-4 col-lg-4 text-center text-lg-start">
			<div class="d-inline-flex align-items-center">
				<a href="mainPage.do" class="navbar-brand p-0">
					<h1 class="display-5 m-0 brand-logo">
						<img
							src="images/ORNABLY.jpg"
							alt="Ornably logo" class="brand-logo-img me-2" /><span style="color:black">오너블리</span>
					</h1>
				</a>
			</div>
		</div>

		<!-- 가운데 메뉴 -->
		<nav class="col-lg-5 d-none d-lg-flex gap-4">
			<a class="text-muted fw-medium text-decoration-none wow fadeInRight"
				data-wow-delay="0.7s"
				href="${pageContext.request.contextPath}/mainPage.do"> <h3><span style="color:black"><strong>메인</strong></span></h3> </a> <a
				class="text-muted fw-medium text-decoration-none wow fadeInRight"
				data-wow-delay="0.7s"
				href="${pageContext.request.contextPath}/ornamentListPage.do">
				<h3><span style="color:black"><strong>쇼핑</strong></span></h3></a>
		</nav>

		<!-- 맨 오른쪽 메뉴 -->
		<div
			class="col-md-4 col-lg-3 d-flex justify-content-end align-items-center">
			<div class="d-inline-flex align-items-center">
				<div class="d-inline-flex align-items-center" style="height: 45px;">
				<!-- 비로그인 상태라면 -->
				<c:if test="${empty accountPk }">
					<a href="loginPage.do"
						class="text-muted d-flex flex-column align-items-center justify-content-center"
						title="로그인"> <img src="images/login.png" 
						style="width: 45px" alt="로그인">
					</a>
				</c:if>
				<!-- 로그인 상태라면 -->
				<c:if test="${not empty accountPk }">
					<a href="myPage.do"
						class="text-muted d-flex flex-column align-items-center justify-content-center"
						title="로그인"> <img src="images/mypage.png" 
						style="width: 45px" alt="마이페이지">
					</a>
					
					 &nbsp;&nbsp;&nbsp; 
					 <a href="cartPage.do"
						class="text-muted d-flex flex-column align-items-center justify-content-center"
						title="장바구니"> <img src="images/cart.png" 
						style="width: 45px" alt="장바구니">
					</a>
				</c:if>
				&nbsp;&nbsp;&nbsp;
				<div class="dropdown">
					
					<a href="#" class="dropdown-toggle text-muted ms-2"
						data-bs-toggle="dropdown"> <img src="images/list.png" 
						style="width: 45px" alt="메뉴바">
					</a>
					<!-- <small> -->
					<div class="dropdown-menu rounded">
						<!-- 비로그인 상태라면 -->
						<c:if test="${empty accountPk }">
							<a href="${pageContext.request.contextPath}/loginPage.do"
								class="dropdown-item"><span style="color:black"><strong>로그인</strong></span></a>
							<a href="${pageContext.request.contextPath}/signInPage.do"
								class="dropdown-item"><span style="color:black"><strong>회원가입</strong></span></a>
						</c:if>
						<!-- 로그인 상태라면 -->
						<c:if test="${not empty accountPk }">
							<a href="${pageContext.request.contextPath}/myPage.do"
								class="dropdown-item"><span style="color:black"><strong>마이페이지</strong></span></a>
							<a href="${pageContext.request.contextPath}/cartPage.do"
								class="dropdown-item"><span style="color:black"><strong>장바구니</strong></span></a>
							<a href="${pageContext.request.contextPath}/wishlistPage.do"
								class="dropdown-item"><span style="color:black"><strong>좋아요 목록</strong></span></a>
							<a href="${pageContext.request.contextPath}/logout.do"
								class="dropdown-item"><span style="color:black"><strong>로그아웃</strong></span></a>
						</c:if>
					</div>
				</div>
			</div>

			</div>
		</div>
	</div>
</header>


