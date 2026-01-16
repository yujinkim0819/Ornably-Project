<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
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

<!-- Custom Style (버그샌드위치) -->
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
	<div class="container-fluid page-header py-5 ">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">상품 상세보기</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Single Products Start -->
	<div class="container-fluid shop py-5 ">
		<div class="container py-5 ">
			<div class="row g-4  d-flex justify-content-center">

				<div class="col-lg-7 col-xl-9 wow fadeInUp" data-wow-delay="0.1s">
					<div class="row g-4 single-product item-card content rounded">
						<!-- 상품 이미지 출력 -->
						<div class="col-xl-6">
							<div class="single-item"
								data-dot="<img class='img-fluid' src='${itemData.itemImageUrl }' alt='상품 이미지'>">
								<div class="single-inner bg-light rounded">
									<img src="${pageContext.request.contextPath }${itemData.itemImageUrl }" class="img-fluid rounded"
										alt="Image">
								</div>
							</div>
						</div>
						<!-- 상품 이미지 출력 종료 -->

						<!-- 상품 이미지 우측 정보 및 구매 영역 -->
						<div class="col-xl-6">
							<!-- 상품 정보 개요 (이름, 찜, 별점, 가격, 설명) -->
							<div
								class="d-flex justify-content-between align-items-center mb-3">
								<h4 class="fw-bold mb-0">상품 이름: ${itemData.itemName}</h4>

								<a href="#" id="wish-btn"
									data-item-pk="${empty itemData.itemPk ? 0 : itemData.itemPk }"
									data-wished="${isWished}"
									class="wish-pill d-flex align-items-center justify-content-center bg-light rounded-pill px-3 py-2">
									<i class="far fa-heart"></i>
								</a>

							</div>
							<h5 class="fw-bold mb-3">
								가격:
								<fmt:formatNumber
									value="${empty itemData.itemPrice ? 0 : itemData.itemPrice}"
									pattern="#,###" />
								원
							</h5>
							<div class="d-flex mb-4">
								<div class="rating_box">
									<div class="rating">
										★★★★★ <span class="rating_star"
											style="width: ${itemData.itemStar*10}%;">★★★★★</span>
									</div>
								</div>
							</div>
							<h6>상품설명</h6>
							<p class="mb-4">${itemData.itemContent }</p>
							<!-- 상품 정보 영역 종료 -->

							<!-- 상품 개수 선택 영역 -->
							<div class="input-group mb-5" style="width: 100px;">
								<!-- 상품 개수 선택영역 -->
								<div class="">
									<button id="dec-item-count"
										class="btn-minus rounded-circle bg-light border">
										<i class="fa fa-minus"></i>
									</button>
								</div>
								<input id="item-count" type="text"
									class="form-control form-control-sm text-center border-0"
									value="1" pattern="^[1-9][0-9]?$">
								<div class="">
									<button id="add-item-count"
										class="btn-plus rounded-circle bg-light border">
										<i class="fa fa-plus"></i>
									</button>
								</div>
								<!-- 상품 개수 선택영역 종료 -->
							</div>
							<!-- 상품 개수 선택 영역 종료 -->

							<!-- 장바구니/즉시구매 버튼 -->
							<a href="#" id="add-to-cart"
								class="btn btn-primary border border-secondary rounded-pill px-4 py-2 mb-4 text-primary">
								<i class="fa fa-shopping-cart me-2 text-white"></i>장바구니에 추가
							</a> 
							<a href="#" id="buy-now"
								class="btn btn-primary border border-secondary rounded-pill px-4 py-2 mb-4 text-primary">
								<i class="fa fa-credit-card me-2 text-white"></i>즉시 구매하기
							</a>
							<!-- 장바구니/즉시구매 버튼 종료 -->
						</div>
						<!-- 상품 이미지 우측 정보란 종료 -->

						<!-- 리뷰 상단 안내바 생성 -->
						<div class="col-lg-12">
							<nav>
								<div class="nav nav-tabs mb-3">
									<button class="nav-link border-white border-bottom-0 active"
										type="button" role="tab" id="nav-mission-tab"
										data-bs-toggle="tab" data-bs-target="#nav-mission"
										aria-controls="nav-mission" aria-selected="true">리뷰</button>
								</div>
							</nav>
							<div class="tab-content mb-5">
								<div class="tab-pane active" id="review-list" role="tabpanel"
									aria-labelledby="nav-about-tab">

									<!-- 리뷰 동적 생성 부분 -->

								</div>
							</div>
						</div>
						<!-- 리뷰 상단 안내바 생성 종료 -->



						<!-- 페이지네이션 부분 -->
						<div class="col-12 wow fadeInUp" data-wow-delay="0.1s">
							<div id="review-page-list"
								class="pagination d-flex justify-content-center mt-5">

								<!-- 비동기로 페이지네이션 버튼 생성 -->

							</div>
						</div>
						<!-- 페이지네이션 영역 종료 -->
					</div>
				</div>
			</div>
		</div>
		<!-- Single Products End -->
	</div>

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

		<!-- Custom JavaScript -->
		<script>
	
	// DOM 로딩이 끝난 뒤 실행
	$(function () {
	  // 찜(하트) 버튼 객체
	  const btn = $("#wish-btn");

	  /* =========================
	     1. 초기 상태 세팅
	     =========================
	     - JSP에서 data-wished="true / false" 로 내려준 값을 기준으로
	       화면에 활성/비활성 상태를 먼저 반영
	  */
	  if (btn.data("wished") === true || btn.data("wished") === "true") {
	    // 활성 상태면 active 클래스 추가
	    btn.addClass("active");

	    // 하트 아이콘을 채워진 하트로 변경
	    btn.find("i")
	        .removeClass("far")
	        .addClass("fas");
	  }
	  /* =========================
	     2. 클릭 이벤트 처리
	     ========================= */
	     
	  btn.on("click", function (e) {
	    e.preventDefault(); // a태그 기본 이동(#) 방지
		
	    if(${empty accountPk}){
			Swal.fire({
				  title: '알림',
				  text: '찜하기를 이용하시려면 로그인을 하셔야합니다.', 
				  icon: 'info',
				  confirmButtonText: '확인',
				  allowOutsideClick: false,
				  allowEscapeKey: true
				});
		  return;
	  }

	    // 현재 활성 상태인지 확인
	    const isActive = btn.hasClass("active");

	    // 2️ 서버에 찜 토글 요청 (비동기)
	    $.ajax({
	      url: "${pageContext.request.contextPath}/UpdateWishlistServlet", // 찜 토글 처리 서블릿
	      type: "POST",
	      // 서버로 보낼 데이터
	      data: {
	        itemPk: btn.data("item-pk") // 어떤 상품인지
	      },
		  success: function(result){
			  console.log("result:["+result+"]");
			  if(result==='true'){
				  const next = !isActive;
				  // ui 바꾸기
				  toggleUI(next);
			  }
		  },
	      // 서버 통신 실패 시
	      error: function () {
	        // UI를 원래 상태로 되돌림
	        toggleUI(isActive);
	        alert("찜 처리 실패");
	      }
	    });
	  });
	  /* =========================
	     3. UI 토글 함수
	     =========================
	     - 활성/비활성 상태에 따라
	       클래스와 아이콘만 변경
	  */
	  function toggleUI(on) {

	    // 버튼 활성 상태 토글
	    btn.toggleClass("active", on);

	    // 아이콘 모양 변경
	    btn.find("i")
	        .toggleClass("fas", on)   // 활성 → 채운 하트
	        .toggleClass("far", !on); // 비활성 → 빈 하트
	  }
	   
	// 아이템 개수 텍스트를 통한 선택 영역 1~99 숫자만 입력되게 하기
	  const itemCount = $("#item-count");

	  // 1~99로 정규화하는 공통 함수
	  function normalizeCount(val) {
	    let v = parseInt(val, 10);

	    if (isNaN(v)) v = 1;     // 숫자 아니면 1
	    if (v < 1) v = 1;        // 최소 1
	    if (v > 99) v = 99;      // 최대 99

	    return v;
	  }

	  // input이 포커스 잃었을 때(엔터/다른곳 클릭 등) 값 보정
	  itemCount.on("blur", function () {
	    const v = normalizeCount(itemCount.val());
	    itemCount.val(v);
	  });

	  // + 버튼
	  $("#add-item-count").on("click", function (e) {
	    itemCount.val(normalizeCount(parseInt(itemCount.val(),10)+1));
	  });

	  // - 버튼
	  $("#dec-item-count").on("click", function (e) {
	    itemCount.val(normalizeCount(parseInt(itemCount.val(),10)-1));
	  });
	  
	  // 장바구니에 추가하기 버튼 눌렸을 때
	  $('#add-to-cart').on('click', function(e){
		  e.preventDefault();
		  // 로그인 되어있지 않다면 로그인 먼저 하라고 하기
		  
		  if(${empty accountPk}){
				Swal.fire({
					  title: '알림',
					  text: '장바구니를 이용하시려면 로그인을 하셔야합니다.', 
					  icon: 'info',
					  confirmButtonText: '확인',
					  allowOutsideClick: false,
					  allowEscapeKey: true
					});
			  return;
		  }
		  
		  $.ajax({
			 url: "${pageContext.request.contextPath}/AddCartServlet",
			 type: "POST",
			 dataType: "json", // 서버로부터 받는 데이터 형식
			 //contentType: "application/json; charset=UTF-8", // 서버에 주는 데이터 형식 
			 data: {
				itemPk: ${empty itemData.itemPk ? 1 : itemData.itemPk},
				count: parseInt(itemCount.val(),10)
			 },
			 success: function(result){
				 if(result===true){
					 Swal.fire({
						  title: '알림',
						  text: '상품이 장바구니에 추가되었습니다!', 
						  icon: 'info',
						  confirmButtonText: '확인',
						  allowOutsideClick: false,
						  allowEscapeKey: true
						});
				 }
				 else{
					 Swal.fire({
						  title: '알림',
						  text: '오류로 인해 장바구니에 추가에 실패하였습니다!', 
						  icon: 'info',
						  confirmButtonText: '확인',
						  allowOutsideClick: false,
						  allowEscapeKey: true
						});
				 }
			 },
			// 장바구니 추가 실패
		      error: function () {
		        alert("장바구니 추가 실패");
		      }
		  });
	  });
	  // 즉시 구매시 form 동적 생성하여 보내기
	  $('#buy-now').on('click', function (e) {
		  e.preventDefault();
			
		  if(${empty accountPk}){
				Swal.fire({
					  title: '알림',
					  text: '상품을 구매하시려면 로그인을 하셔야합니다.', 
					  icon: 'info',
					  confirmButtonText: '확인',
					  allowOutsideClick: false,
					  allowEscapeKey: true
					});
			  return;
		  }
		  
		  const itemPk    = ${empty itemData.itemPk ? 0 : itemData.itemPk};
		  const itemCount = parseInt($('#item-count').val(), 10);
		  const itemPrice = ${empty itemData.itemPrice ? 0 : itemData.itemPrice};

		  const form = $('<form>', {
		    method: 'POST',
		    action: '${pageContext.request.contextPath}/paymentPage.do'
		  });

		  form.append(`<input type="hidden" name="itemPk" value="\${itemPk}">`);
		  form.append(`<input type="hidden" name="itemCount" value="\${itemCount}">`);
		  form.append(`<input type="hidden" name="itemPrice" value="\${itemPrice}">`);
		  form.append(`<input type="hidden" name="condition" value="buyNow">`);

		  $('body').append(form);
		  form.submit();
		});
	// 리뷰 렌더링 함수
	function renderReviews(reviewDatas) {
		console.log('renderReviews');
	  	console.log("reviewDatas: ", reviewDatas);
	  
		if(typeof reviewDatas == "undefined" || reviewDatas.length===0){
			$('#review-list').html(`
			      <div class="text-center py-5 text-muted">
			        <i class="bi bi-chat-dots" style="font-size: 40px;"></i>
			        <p class="mt-3 mb-0">아직 등록된 리뷰가 없습니다.</p>
			      </div>
			    `);
			return;
		}
	  
		  let html="";
	  	  $.each(reviewDatas, function(index, reviewData){
	  		  html +=`
	  			<div class="d-flex">
					<img src="img/avatar.jpg" class="img-fluid rounded-circle p-3"
						style="width: 100px; height: 100px;" alt="사용자 이미지">
					<div class="">
						<p class="mb-2" style="font-size: 14px;">\${reviewData.reviewAccountName}</p>
						<div class="d-flex justify-content-between" style="align-items: center;">
							<h5>\${reviewData.reviewTitle}</h5>
							<div class="d-flex mb-3">
								<div class="rating_box">
									<div class="rating" style="line-height: 1; font-size: 20px;">
										★★★★★ <span class="rating_star" style="width: \${reviewData.reviewStar * 10}%;">★★★★★</span>
									</div>
								</div>
							</div>
						</div>
						<p>\${reviewData.reviewContent }</p>
					</div>
				</div>
				`;
			
	  	  });
	  $('#review-list').html(html);
	}
	// 페이지네이션 렌더링 함수 
	function renderPagination(data) {
		console.log('renderPagination');
		  let html = "";

		  const currentPage = data.currentPage;
		  const startPage   = data.startPage;
		  const endPage     = data.endPage;
		  const totalPage   = data.totalPage;

		  // 현재페이지가 보이는 페이지네이션 맨 왼쪽이 아니라면 '<<' 표시 해주기
		  if (currentPage > 1) {
		    html += `
		      <a href="#" class="rounded pagenation-button" data-page="\${currentPage - 1}">
		        &laquo;
		      </a>
		    `;
		  }

		  // 페이지 번호들 출력, 현재 페이지면 active 클래스 붙여주기
		  for (let page = startPage; page <= endPage; page++) {
		    html += `
		      <a href="#"
		         class="rounded pagenation-button \${page === currentPage ? 'active' : ''}"
		         data-page="\${page}">
		        \${page}
		      </a>
		    `;
		  }
		  
		  // 마지막 페이지에 갔을때면 '>>' 표시 없애주기
		  if (currentPage < totalPage) {
		    html += `
		      <a href="#" class="rounded pagenation-button" data-page="\${currentPage + 1}">
		        &raquo;
		      </a>
		    `;
		  }
			$("#review-page-list").html(html);
		}
		let page = "1";
		
		// 리뷰 페이지네이션 번호 눌렀을 때
		$(document).on('click', '.pagenation-button', function(e) {
			e.preventDefault();
			
			page = $(this).data('page');
			console.log("페이지네이션 버튼 눌림:["+page+"]");
			
			$.ajax({
			  url: "${pageContext.request.contextPath}/ReviewListPagenationServlet",
			  type: "GET", 
			  dataType: "json", // 서버가 JSON을 응답하면
			  data: { 
				  itemPk: ${empty itemData.itemPk ? 0 : itemData.itemPk}, 
				  page: page
				},
			  success: function (result) { // reviewDatas, currentPage, reviewAccountName, startPage, endPage, totalPage
			  	  // reviewDatas : reviewPk, itmePk, reviewStar, reviewTitle, reviewContent
			  	 console.log("비동기 요청 성공");
			  console.log(result);
			  	 renderReviews(result.reviewDatas);
			  		renderPagination(result);
			  	
			  },
			  error: function (xhr, status, err) {
			    console.log("error:", status, err);
			    console.log("responseText:", xhr.responseText);
			  }
			});
		});
		// 초기의 리뷰 첫 페이지 불러오는 코드
		$.ajax({
			  url: "${pageContext.request.contextPath}/ReviewListPagenationServlet",
			  type: "GET", 
			  dataType: "json", // 서버가 JSON을 응답하면
			  data: { 
				  itemPk: ${itemData.itemPk}, 
				  page: 1
				},
			  success: function (result) { // reviewDatas, currentPage, reviewAccountName, startPage, endPage, totalPage
			  	  // reviewDatas : reviewPk, itmePk, reviewStar, reviewTitle, reviewContent
			  	 console.log("비동기 요청 성공");
			  console.log(result);
			  	 renderReviews(result.reviewDatas);
			  	renderPagination(result);
			  	
			  },
			  error: function (xhr, status, err) {
			    console.log("error:", status, err);
			    console.log("responseText:", xhr.responseText);
			  }
			});
	}); // 로딩 완료시 수행 동작 종료
	
	</script>
</body>

</html>