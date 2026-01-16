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

<link href="css/style.css" rel="stylesheet">


<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">

<!-- ◀◀◀ 모달창 cdn ▶▶▶ -->
<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.all.min.js">
</script>
<link
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.min.css"
	rel="stylesheet">
<!-- ◀◀◀ 모달창 cdn ▶▶▶ -->

</head>

<style>
	/* ◀◀◀◀◀◀◀ 배송지 목록 박스 START ▶▶▶▶▶▶▶ */
	#addressTable th, #addressTable td {
	    padding: 12px 15px; /* 위아래 12px, 좌우 15px */
	}

	#addressTable th, #addressTable td {
	    color: #000; /* 진한 검정 */
	    font-weight: 500; /* 살짝 굵게 */
	}
	
	/* 전체 컨테이너 여백 */
	.container.item-card {
	    padding-left: 20px;   /* 왼쪽 여백 */
	    padding-right: 20px;  /* 오른쪽 여백 */
	}
	/* 배송지 목록 제목 왼쪽 여백 */
	.container.item-card h2 {
	    padding-left: 30px; /* 원하는 만큼 조정 */
	}
	
	/* 기본 배송지 설정 버튼 */
	.set-default-address {
	    background-color: #6F4F3A;
	    color: #fff;
	    border: none;
	    padding: 8px 16px; /* 위아래 8px, 좌우 16px */
    	font-weight: 500; /* 글자 굵기 조절 */
	}
	.set-default-address:hover {
	    background-color: #5a3d2f;
	}
	
	/* 삭제 버튼 */
	.delete-address {
	    background-color: #6F4F3A;
	    color: #fff;
	    border: none;
	    padding: 8px 16px; 
    	font-weight: 500;
	}
	.delete-address:hover {
	    background-color: #5a3d2f;
	}
	
	/* 배송지 등록 버튼 */
	#regist-address {
	    background-color: #6F4F3A;
	    color: #fff;
	    border: none;
	    padding: 10px 20px; 
    	font-weight: 500;
   	    width: 100%;  /* 버튼을 부모 요소 전체 너비로 */
	    max-width: 200px; /* 최대 넓이 설정 (원하면 조정 가능) */
	}
	/* Hover 효과 */
	.set-default-address:hover,
	.delete-address:hover,
	#regist-address:hover {
	    background-color: #5a3d2f;
	    color: #fff;
	}
	
	#regist-address:hover {
	    background-color: #5a3d2f;
	}
	/* ◀◀◀◀◀◀◀ 배송지 목록 박스 END ▶▶▶▶▶▶▶ */
</style>

<body>
	<tag:header />
	
	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">배송지 목록</h1>
	</div>
	<!-- Single Page Header End -->

	<div class="container-fluid overflow-hidden py-5">
		<div class="container py-5  content item-card rounded">
			<h2 class="mb-4 wow fadeInUp" data-wow-delay="0.1s">배송지 목록</h2>
			<form action="#">
				<div class="row g-5">
					<div class="col-md-12 col-lg-12 col-xl-12 wow fadeInUp"
						data-wow-delay="0.3s">
						<div class="table-responsive">
							<table class="table" id="addressTable">
								<thead>
									<tr class="text-center">
										<th scope="col" class="text-start" style="width: 25%;">배송지 이름</th>
										<th scope="col" class="text-start">우편번호</th>
										<th scope="col" class="text-start">주소</th>
										<th scope="col" class="text-start">상세주소</th>
										<th scope="col" style="width: 200px;">관리</th>
										<th scope="col" style="width: 200px;">배송지 삭제</th>
									</tr>
								</thead>
								<tbody>
									<!-- [addressPk, postalCode, isDefaultAddress, addressName, regionAddress, detailAddress] -->
									<c:forEach var="addressData" items="${addressDatas }">
										<tr class="text-center">
											<th scope="row" class="text-start py-4">${addressData.addressName }</th>
											<td class="py-4 text-start"> ${addressData.postalCode }</td>
											<td class="py-4 text-start"> ${addressData.regionAddress }</td>
											<td class="py-4 text-start"> ${addressData.detailAddress }</td>
											<td class="py-4 text-center"><c:choose>
												<c:when test="${addressData.defaultAddress==true}">
													<span class="text-muted">기본 설정됨</span>
												</c:when>
												<c:otherwise>
													<button type="button"
														class="set-default-address btn rounded-pill py-1 px-2"
														data-addresspk="${addressData.addressPk }">기본 배송지로 설정
													</button> 
												</c:otherwise>
												</c:choose></td>
											<td class="py-4 text-center">
												<button type="button"
													class="delete-address btn rounded-pill py-1 px-2"
													data-addresspk="${addressData.addressPk }"
													data-isdefault="${addressData.defaultAddress }">삭제
												</button> 
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="text-end mt-3">
							<c:if test="${not empty addressDatas and addressDatas.size() < 10 }">
							<div style="margin:35px;">
							<a type="button" id="regist-address"
								href="registDiliveryAddressPage.do?condition=addressList"
								class="btn rounded-pill px-4 py-2">배송지 등록하기</a>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<tag:footer />

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>
	
	<script>
	$(document).ready(function(){
		// 기본 배송지 설정
		$('.set-default-address').each(function(index, element){
			$(element).on('click', function(e){
				const addressPk = $(this).data('addresspk');
				console.log('기본 배송지 설정 눌림 addresspk:['+addressPk+']');
				$.ajax({
					url: "${pageContext.request.contextPath}/ChangeDefaultDeliveryAddressServlet",
					type: "POST",
					dataType: "text",
					data: { addressPk: addressPk },
					success: function(result){
						// 업데이트가 되면 새로고침 해주기
						if(result==='true'){
							alert('업데이트 성공');
							location.href='deliveryAddressListPage.do';
						}
						else if(result==='false'){
							console.log('업데이트 실패');
						}
						else {
							console.log('result: ['+result+']');
						}
					},
					error: function(error){
						console.log("error: ["+error+"]");
					}
				});
			});
		});
		// 배송지 삭제
		$('.delete-address').on('click', function(e){
			const addressPk = $(this).data('addresspk');
			const isDefault = $(this).data('isdefault');
			console.log('배송지 삭제 버튼 눌림 addresspk:['+addressPk+']');
			console.log('해당 배송지의 기본 배송지 여부 :['+isDefault+']');
			console.log(typeof(isDefault));
			if(isDefault===true){
				Swal.fire({
					title: '알림',
					text: '기본 배송지는 삭제할 수 없습니다.',  
					icon: 'info',
					confirmButtonText: '확인',
					allowOutsideClick: false,
					allowEscapeKey: true,
				});
				return;
			}
			
			Swal.fire({
				  title: '알림',
				  text: '정말 배송지를 삭제하시겠습니까?', 
				  icon: 'info',
				  showCancelButton: true,
				  confirmButtonText: '확인',
				  cancelButtonText: '취소',
				  allowOutsideClick: false,
				  allowEscapeKey: true,
				}).then((result) => {
				if (result.isConfirmed) {
					$.ajax({
						url: "${pageContext.request.contextPath}/DeleteDeliveryAddressServlet",
						type: "POST",
						dataType: "text",
						data: { addressPk: addressPk },
						success: function(result){
							console.log('result:['+result+']');
							
							// 업데이트가 되면 새로고침 해주기
							if(result==='true'){
								alert('삭제 성공');
								location.href='deliveryAddressListPage.do';
							}
							else if(result==='false'){
								Swal.fire({
									title: '오류',
									text: '배송지 삭제에 실패하였습니다.관리자에게 문의해주세요', 
									icon: 'info',
									confirmButtonText: '확인',
									allowOutsideClick: false,
									allowEscapeKey: true,
								});
							}
							else{
								alert('예외 발생');
								console.log('result: ['+result+']');
								console.log(typeof(result));
							}
						},
						error: function(error){
							console.log("error: ["+error+"]");
						}
					}); 
				}
			});
		});
	});
	</script>
</body>
</html>