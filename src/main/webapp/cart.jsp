<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ornably"%>
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
<!-- Template Stylesheet -->


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">
	
	<style>
	/* 기본 버튼 색상 (비활성화 및 활성화 상태 공통) */
	.cart-buy-btn {
	    background-color: #6F4F3A;
	    border-color: #6F4F3A;
	    opacity: 1; /* 기본적으로 불투명도 1로 설정 */
	    cursor: pointer;
	    color: white;
	}
	
	/* 비활성화 상태 */
	.cart-buy-btn.disabled {
	    opacity: 0.5; /* 비활성화 시 투명도 설정 */
	    cursor: not-allowed; /* 마우스 커서 변경 */
	}
	
	/* 활성화 상태 */
	.cart-buy-btn:not(.disabled) {
	    opacity: 1; /* 활성화 시 opacity 1로 설정 */
	}
	</style>
</head>
<body>
	<ornably:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">장바구니</h1>
	</div>
	<!-- Single Page Header End -->



	<!-- 장바구니 품목 Start -->
	<div class="container-fluid py-5 cart-section">
		<div class="container py-5 content item-card rounded">
			<div class="row">
				<!-- 왼쪽 장바구니 테이블 -->
				<div class="col-lg-8 col-md-7 col-sm-12 mb-5">
					<div class="table-responsive card cart-table">
						<table class="table">
							<thead>
								<tr>
									<th scope="col"><span style="color:black">상품명</span></th>
									<th scope="col"><span style="color:black">가격</span></th>
									<th scope="col"><span style="color:black">수량</span></th>
									<th scope="col"><span style="color:black">합계</span></th>
									<th scope="col"><span style="color:black">삭제</span></th>
								</tr>
							</thead>
							<tbody id="cart-tbody">
								<!-- 상품 출력 -->
								<c:if test="${empty cartDatas }">
									<tr>
										<td colspan="5" class="text-center py-5 text-muted"><i
											class="bi bi-cart-fill" style="font-size: 40px;"></i>
											<p class="mt-3 mb-0">장바구니가 비어있습니다..</p></td>
									</tr>
								</c:if>
								<c:forEach var="cart" items="${cartDatas}">

									<tr data-cartpk="${cart.cartPk}">
										<th scope="row">
											<p class="mb-0 py-4">${cart.itemName}</p>
										</th>

										<!-- 가격 -->
										<td class="item-price">
											<p class="mb-0 py-4">
												<fmt:formatNumber value="${cart.itemPrice}" type="number" />
												원
											</p>
										</td>

										<!-- 수량 -->
										<td>
											<div class="input-group py-4 cart-quantity"
												style="width: 100px;">
												<button type="button"
													class="btn btn-sm btn-minus rounded-circle bg-light border">-</button>
												<input type="text"
													class="form-control form-control-sm text-center border-0 item-count"
													value="${cart.count != 0 ? cart.count : 1}"
													data-cartpk="${cart.cartPk}">
												<button type="button"
													class="btn btn-sm btn-plus rounded-circle bg-light border">+</button>
											</div>
										</td>

										<!-- 합계 -->
										<td class="item-total">
											<p class="mb-0 py-4">
												<fmt:formatNumber
													value="${cart.itemPrice * (cart.count != 0 ? cart.count : 1)}"
													type="number" />
												원
											</p>
										</td>

										<!-- 삭제 -->
										<td class="py-4">
											<button
												class="btn btn-md rounded-circle bg-light border cart-remove-btn">
												<i class="fa fa-times text-danger"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<!-- 오른쪽 결제요약 박스 -->
				<div class="col-lg-4 col-md-5 col-sm-12">
					<div class="rounded cart-summary content item-card">
						<div class="p-4">
							<h2 class="display-6 mb-4">
								장바구니 <span class="fw-normal">총액</span>
							</h2>
						</div>

						<div
							class="py-4 mb-4 border-top border-bottom d-flex justify-content-between">
							<h5 class="mb-0 ps-4 me-4">총 합계</h5>
							<p class="mb-0 pe-4 total-price">${totalPrice}원</p>
						</div>

						<form action="${pageContext.request.contextPath}/paymentPage.do"
							method="get">
							<button
								class="btn rounded-pill px-4 py-3 text-uppercase mb-4 ms-4 cart-buy-btn"
								style="background-color: #6F4F3A; border-color: #6F4F3A;"
								type="submit">구매</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 장바구니 품목 End -->

	<!-- footer 태그 호출 -->
	<ornably:footer />

	<!-- jQuery -->
	<script>
	console.log('${cartDatas}');

	const buyBtn = $(".cart-buy-btn");
	const cartbody = $("#cart-tbody");
	const totalPriceElem = $(".cart-summary .total-price"); // 총액 변수

	// 버튼으로 개수 변경
	$(document).on("click", ".btn-minus, .btn-plus", function(){
	    const tr = $(this).closest("tr");
	    const cartPk = tr.data("cartpk");
	    const input = tr.find("input");
	    let count = parseInt(input.val()) || 1;

	    if($(this).hasClass("btn-minus")){
	    	count -= 1;
	    }
	    else count += 1;

	 	// 개수 변경 함수 호출
	    updateCartCount(cartPk, count, tr, input);
	});
	
	
	// 직접 입력으로 개수 변경
    $(document).on("blur", ".item-count", function(){
        const input = $(this);
        const tr = input.closest("tr");
        const cartPk = input.data("cartpk");
        let count = parseInt(input.val()) || 1;
        if(count < 1) count = 1; // 최소 1
        
        // 개수 변경 함수 호출
        updateCartCount(cartPk, count, tr, input);
    });
	
 	// AJAX : 개수변경, 총금액
    function updateCartCount(cartPk, count, tr, input){
    	// 최소/최대 범위 제한
        if(count < 1) count = 1;
        if(count > 99) count = 99;
        
        $.ajax({
            url: "${pageContext.request.contextPath}/UpdateCartItemCountServlet",
            type: "POST",
            data: { cartPk: cartPk, newCount: count },
            success: function(res){
                if(res === "true"){
                    input.val(count);

                    // 항목 합계 업데이트
                    const price = parseInt(tr.find(".item-price").text().replace(/,/g,"").replace("원",""));
			        const total = price * count;
			        tr.find(".item-total p").text(total.toLocaleString("ko-KR") + "원");


                    // 촘 금액 함수 호출
                    updateTotalPrice();
                } else {
                    alert("수량 변경 실패");
                }
            }
        });
    }

	// 삭제 버튼
	$(document).on("click", ".cart-remove-btn", function(){
	    const tr = $(this).closest("tr");
	    const cartPk = tr.data("cartpk");

	    $.ajax({
	        url: "${pageContext.request.contextPath}/DeleteCartServlet",
	        type: "POST",
	        data: { cartPk: cartPk },
	        success: function(res){
	            if(res === "true"){
	                tr.remove();		// 선택한 상품 삭제
	                
	                if ($("#cart-tbody tr").length === 0) {
                		$("#cart-tbody").append(`
                			<tr>
                				<td colspan="5" class="text-center py-5 text-muted">
                					<i class="bi bi-cart-fill" style="font-size: 40px;"></i>
                					<p class="mt-3 mb-0">장바구니가 비어있습니다..</p>
                				</td>
                			</tr>
                		`);
                	}
	                
	                updateTotalPrice();	// 총 금액 업데이트
	            } else {
	                alert("삭제 실패");
	            }
	        }
	    });
	});

	// 총 금액 계산
	function updateTotalPrice(){
	    let total = 0;
	    $("#cart-tbody tr").each(function(){
	        // 가격이 없는 행(빈 장바구니 메시지 등)은 스킵
	        if($(this).find(".item-price").length === 0) return;
	        
	        const priceText = $(this).find(".item-price").text().replace("원","").replace(/,/g,"");
	        const count = parseInt($(this).find("input").val()) || 1;
	        total += parseInt(priceText) * count;
	    });
	
	    // 총 금액 계산 후
	    if (isNaN(total) || total <= 0) {
	        total = 0;
	        buyBtn.prop("disabled", true).addClass("disabled");
	    } else {
	        buyBtn.prop("disabled", false).removeClass("disabled");
	    }
	    totalPriceElem.text(total.toLocaleString("ko-KR") + "원");
	}
		
	$(document).ready(function(){
		// 페이지 로딩 시 총 금액 계산
	    updateTotalPrice();
	});

	</script>
</body>
</html>




<!-- 입력값 검사, 1, -1, 한번에 99개 -->

