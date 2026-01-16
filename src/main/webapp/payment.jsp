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
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

  <!-- Libraries Stylesheet -->
  <link href="lib/animate/animate.min.css" rel="stylesheet">
  <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


  <!-- Customized Bootstrap Stylesheet -->
  <link href="css/bootstrap.min.css" rel="stylesheet">

  <!-- Template Stylesheet -->
  <link href="css/style.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"
    integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">
	
</head>

<body>
  <tag:header />

  <!-- Single Page Header start -->
  <div class="container-fluid page-header py-5">
    <h1 class="text-center text-white display-6 wow fadeInUp" data-wow-delay="0.1s">최종결제 페이지</h1>
  </div>
  <!-- Single Page Header End -->


  <!-- Checkout Page Start -->
  <div class="container-fluid bg-light overflow-hidden py-5">
    <div class="container py-5">

      <!-- <form id="payment-form" action="payment.do" method="POST"> -->
      <form id="payment-form" action="kakaoPayReady.do" method="POST">
        <div class="row g-5 align-items-stretch">

          <!-- 배송지 선택 공간 시작 -->
          <div class="col-12 col-lg-6 wow fadeInUp" data-wow-delay="0.1s">
            <div class="bg-light rounded p-4 h-100 d-flex flex-column align-items-center text-center">
              <h1 class="display-6 mb-4">배송지 선택</h1>

              <select id="select-address" name="addressPk" 
              class="form-select text-dark border-0 border-start rounded-0 p-3"
                style="width: 200px;">
                <!-- 배송지가 js에 의해 삽입되는 공간 -->
              </select>

              <div class="mt-auto pt-4">
                <button id="regist-address" class="btn btn-primary rounded-pill px-4 py-3 text-uppercase ms-4"
                  type="button">배송지 등록하기</button>
              </div>
            </div>
          </div>
          <!-- 배송지 선택 공간 끝 -->
          <!-- 결제하기 버튼 공간 -->
          <div class="col-12 col-lg-6 wow fadeInUp" data-wow-delay="0.1s">
            <div class="d-flex justify-content-lg-end h-100">
              <div class="bg-light rounded p-4 h-100 d-flex flex-column" style="max-width:420px; width:100%;">
                <h1 class="display-6 mb-4">총 <span class="fw-normal">주문금액</span></h1>

                <div class="py-4 mb-4 border-top border-bottom d-flex justify-content-between">
                  <h5 class="mb-0">총 금액</h5>
                  <p class="mb-0">
                  <fmt:formatNumber value="${totalAmount}" pattern="#,###" />원
                  </p>
                </div>

                <div class="mt-auto">
                  <!-- <button id="pay-submit" class="btn btn-primary rounded-pill px-4 py-3 text-uppercase ms-4"
                    type="button">결제하기</button>-->
					    <input type="hidden" name="totalAmount" value="${totalAmount}">
					    <input type="hidden" name="addressPk" value=""> <!-- JS에서 선택된 배송지 PK 넣음 -->
					    <button id="pay-submit" type="submit"
					        class="btn rounded-pill px-4 py-3 text-uppercase"
					        style="background:#6F4F3A; color:white; font-weight:700;">
					        결제하기
					    </button>
					</form>
                
                <!-- 결제하기 버튼 -->
				<!-- <div class="mt-auto">
					<form id="kakaopay-form" action="${pageContext.request.contextPath}/KakaoPayReadyAction.do" method="POST">
						<input type="hidden" name="orderId" value="${orderId}">
						<input type="hidden" name="accountPk" value="${loginAccount.accountPk}">
						<input type="hidden" name="addressPk">
						<input type="hidden" name="totalAmount" value="${totalAmount}">
				  		<button id="pay-submit" class="btn btn-primary rounded-pill px-4 py-3 text-uppercase ms-4"
				      			type="submit">결제하기</button>
					</form>
				</div> -->
              </div>
            </div>
          </div>
          <!-- 결제버튼 공간 끝 -->
        </div>

      </form>
    </div>
  </div>
  <!-- Checkout Page End -->

  <tag:footer />


  <!-- JavaScript Libraries -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script> -->
  <script src="lib/wow/wow.min.js"></script>
  <script src="lib/owlcarousel/owl.carousel.min.js"></script>


  <!-- Template Javascript -->
  <script src="js/main.js"></script>
  <script>
    /*
    <select id="select-address" class="form-select text-dark border-0 border-start rounded-0 p-3" style="width: 200px;">
      <option value="기본배송지">기본배송지</option>
    </select>
    */
    // 배송지 select에 넣어주기

    const select = $("#select-address");
    // 주소 데이터를 받으면
    console.log('${addressDatas}');
    if (typeof ${addressDatas} !== 'undefined') {
    	${addressDatas}.forEach(address => {
    		console.log(address);
    		$("<option>", {
          value: address.addressPk,
          text: address.addressName,
          selected: address.isDefaultAddress === true
        }).appendTo(select);
      });
    }
    // 주소 데이터가 호옥시라도 들어오지 않는다면
    else {
      $("<option>", {
        text: "주소를 로드하지 못했습니다"
      }).appendTo(select);
    }

    // 결제하기 버튼 누를 때 submit 시켜주기
    /*$('#pay-submit').click(function () {
      $('#payment-form').submit();
    });*/
    // 결제 버튼 클릭 시 선택된 배송지 PK 전달
	$('#pay-submit').click(function (e) {
	    e.preventDefault(); // 기본 submit 막기
	    const selectedAddress = $('#select-address').val();
	    $('input[name="addressPk"]').val(selectedAddress);  // 선택된 주소PK 넣기
	    $('#payment-form').submit(); // form 제출
	});

    // 배송지 등록 버튼을 눌렀을 때 
    $('#regist-address').click(function () {
      location.href = '${pageContext.request.contextPath}/registDiliveryAddressPage.do?condition=payment';
    });
  </script>
</body>

</html>