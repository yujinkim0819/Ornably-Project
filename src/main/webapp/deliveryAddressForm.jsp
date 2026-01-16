<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500;600;700&family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<link href="lib/animate/animate.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">
	<!-- ◀◀◀ 모달창 cdn ▶▶▶ -->
	<script src=" https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.all.min.js"></script>
	<link href=" https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.min.css" rel="stylesheet">
	<!-- ◀◀◀ 모달창 cdn ▶▶▶ -->
</head>
<style>
	/* ◀◀◀◀◀◀◀ 배송지 등록 START ▶▶▶▶▶▶▶ */
	/* 배송지 등록 박스 흰색 영역 */
	/* 큰 흰색 박스 너비 줄이기 */
	.address-container {
	    max-width: 800px;
	    margin: 80px auto 80px auto; 
	}

	/* 우편 번호 버튼 */
	#postcodeBtn {
	    color: #6F4F3A;
	    border-color: #6F4F3A;
	    background-color: transparent;
	    transition: background-color 0.3s, color 0.3s;
	}
	#postcodeBtn:hover {
	    color: white;
	    background-color: #6F4F3A;
	    border-color: #6F4F3A;
	}
	
	/* 등록 버튼 */
	.custom-btn {
	    background-color: #6F4F3A;	    
    	color: white;
	    padding: 12px 0;
	    width: 100%;
	    border-radius: 50px;
	    font-weight: 800; /* 더 굵은 글씨 */
	}
	
	.custom-btn:hover {
	    background-color: #5a3d2f; /* 마우스 오버 색상 */
	    border-color: #5a3d2f;
    	color: white;
	}
	/* ◀◀◀◀◀◀◀ 배송지 등록 START ▶▶▶▶▶▶▶ */
</style>

<body>
	<tag:header />
	
	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">배송지 등록
		</h1>
	</div>
	<!-- Single Page Header End -->
	
	<!-- 큰 흰색 박스에 address-container 적용 -->
	<div class="container-fluid bg-light overflow-hidden rounded address-container content">
	    <div class="container py-5">
	        <h1 class="mb-4 wow fadeInUp text-center" data-wow-delay="0.1s">배송지 추가</h1>
	        <div class="row g-2 justify-content-center">
	            <div class="col-md-8 col-lg-6 col-xl-6">
	                <form id="regist-address-form" action="registDiliveryAddress.do" method="POST">
	                    <input type="hidden" name="condition" id="condition" value="${condition}">
	                    <!-- 배송지 명 -->
	                    <div class="form-item">
	                        <label class="form-label my-3">배송지 명<sup>*</sup></label> 
	                        <input type="text" class="form-control mt-2"
	                               id="customAddressName"
	                               name="addressName"
	                               placeholder="배송지 명 입력"
	                               maxlength="15"
	                               required>
	                    </div>
	                    <!-- 우편번호 및 주소 -->
	                    <div class="form-item">
	                        <label class="form-label my-3">주소</label>
	                        <div class="d-flex gap-2">
	                            <input type="text" class="form-control" id="sample6_postcode"
	                                   name="postalCode" placeholder="우편번호" readonly required>
	                            <input type="button" class="btn btn-outline-success"
	                                   id="postcodeBtn" value="우편번호 찾기">
	                        </div>
	                        <input type="text" class="form-control mt-2"
	                               id="sample6_address" name="regionAddress" placeholder="주소"
	                               readonly> 
	                        <input type="text" class="form-control mt-2"
	                               id="sample6_detailAddress" name="detailAddress"
	                               placeholder="상세주소" required>
	                    </div>
	                    <!-- 등록 버튼 -->
	                    <div class="row justify-content-center mt-5">
	                        <div class="col-6">
	                            <button id="add-address" type="submit" class="custom-btn">등록</button>
	                        </div>
	                    </div>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
	<tag:footer />
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
	$(function () {
		let isAddressRegionChecked = false;
		
		// 다음 주소 검색
		function sample6_execDaumPostcode() {
		  new daum.Postcode({
		    oncomplete: function(data) {
		      let addr = '';
		
		      if (data.userSelectedType === 'R') {
		        addr = data.roadAddress;
		      } else {
		        addr = data.jibunAddress;
		      }
		
		      $("#sample6_postcode").val(data.zonecode);
		      $("#sample6_address").val(addr);
		      $("#sample6_detailAddress").focus();
		
		      isAddressRegionChecked = true;
		    }
		  }).open();
		}
		
		$("#postcodeBtn").on("click", sample6_execDaumPostcode);
		
		$("#regist-address-form").on("submit", function (e) {
		
		  const addressName = $("#customAddressName").val().trim();
		  const postalCode  = $("#sample6_postcode").val().trim();
		  const address     = $("#sample6_address").val().trim();
		  const detailAddr  = $("#sample6_detailAddress").val().trim();
		
		  if (addressName === "") {
		    Swal.fire({
		  title: '알림',
		  text: '배송지 명을 입력하세요.', 
		  icon: 'info',
		  confirmButtonText: '확인',
		  allowOutsideClick: false,
		  allowEscapeKey: true
		});
		    $("#customAddressName").focus();
		    e.preventDefault();
		    return;
		  }
		
		  if (!isAddressRegionChecked || postalCode === "" || address === "") {
		Swal.fire({
			  title: '알림',
			  text: '우편번호 찾기를 통해 주소를 선택하세요.', 
			  icon: 'info',
			  confirmButtonColor: '#6F4F3A',
			  confirmButtonText: '확인',
			  allowOutsideClick: false,
			  allowEscapeKey: true
			});
		    e.preventDefault();
		    return;
		  }
		
		  if (detailAddr === "") {
		    Swal.fire({
		  title: '알림',
		  text: '상세주소를 입력하세요.', 
		  icon: 'info',
		  confirmButtonText: '확인',
		  allowOutsideClick: false,
		  allowEscapeKey: true
		});
		    $("#sample6_detailAddress").focus();
		    e.preventDefault();
		    return;
		  }
		
		  // 여기까지 오면 정상 submit
		});
	});
	</script>
</body>
</html>

