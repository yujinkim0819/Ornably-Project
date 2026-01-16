<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ornably"%>
<!DOCTYPE html>
<html lang="ko">
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
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/header.css"> -->
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/footer.css"> -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/customResource/style.css">
<head>
    <meta charset="UTF-8">
    <title>결제 완료</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            text-align: center;
            padding-top: 100px;
            margin: 0;
    		padding: 0;
        }
        .success-box {
            background: #fff;
            width: 570px;
            margin: 340px auto;  /* 위 100px, 아래 100px */
            padding: 60px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2e7d32;
            margin-bottom: 15px;
            font-weight: 800;           /* 글씨 두껍게 */
        }
        p {
            color: #555;
            margin-bottom: 30px;
        }
        button {
            padding: 10px 20px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

		.button-container {
		    display: flex;           /* flex 컨테이너로 만들기 */
		    justify-content: center; /* 가로 중앙 정렬 */
		    gap: 20px;               /* 버튼 사이 간격 */
		}

        .order, .home {
            background-color: #6F4F3A; /* 버튼 배경색 */
		    color: white;               /* 글씨 색상 */
		    padding: 12px 0;            /* 위아래 패딩 12px, 좌우 0 */
		    width: 230px;          		/* 버튼 가로 길이 고정 */
		    border-radius: 50px;        /* 둥근 모서리 */
		    font-weight: 800;           /* 글씨 두껍게 */
		    cursor: pointer;            /* 마우스 올리면 포인터로 표시 */
		    font-size: 16px;            /* 필요하면 글씨 크기 지정 */
		    border: none;               /* 테두리 제거 */
			margin: 5px auto;     /* 상하 여백 10px, 좌우 중앙 정렬 */
		    display: block;        /* margin auto 적용을 위해 block으로 변경 */
		}
    </style>
</head>
<body>
	<!-- header 태그 호출 -->
	<ornably:header />
	
    <div class="success-box content">
        <h1>결제가 완료되었습니다 🎉</h1>
        <p>
            주문이 정상적으로 접수되었습니다<br>
            이용해 주셔서 감사합니다
        </p>

        <div class="button-container">
		    <button class="order"
		        onclick="location.href='<%=request.getContextPath()%>/orderHistoryListPage.do'">
		        주문 상세 보기
		    </button>
		
		    <button class="home"
		        onclick="location.href='<%=request.getContextPath()%>/mainPage.do'">
		        메인으로 이동
		    </button>
		</div>
    </div>
    
    <!-- footer 태그 호출 -->
	<ornably:footer />

</body>
</html>
