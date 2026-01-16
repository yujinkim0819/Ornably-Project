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
<title>결제 취소</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        text-align: center;
        margin: 0;
        padding: 0;
    }

    .cancel-box {
        background: #fff;
        width: 570px;
        margin: 340px auto;
        padding: 60px;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    }

    h1 {
        color: #d32f2f;        
        margin-bottom: 15px;
        font-weight: 800;
    }

    p {
        color: #555;
        margin-bottom: 30px;
    }

    button {
        border: none;
        border-radius: 50px;
        font-weight: 800;
        cursor: pointer;
        font-size: 16px;
        width: 230px;
        padding: 12px 0;
        margin: 10px auto;
        display: block;
    }

    .home {
        background-color: #6F4F3A;
        color: #fff;
    }
</style>
<body>

    <!-- header -->
    <ornably:header />

    <div class="cancel-box content">
        <h1>결제가 취소되었습니다</h1>
        <p>
            결제를 취소하셨습니다<br>
            감사합니다
        </p>

        <button class="home"
            onclick="location.href='<%=request.getContextPath()%>/mainPage.do'">
            메인으로 이동
        </button>
    </div>

    <!-- footer -->
    <ornably:footer />

</body>
</html>
