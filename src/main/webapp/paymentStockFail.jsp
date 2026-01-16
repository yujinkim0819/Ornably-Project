<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>재고 부족</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<script>
    let outOfStockItems = [
        <c:forEach var="item" items="${outOfStockItems}" varStatus="status">
            '${item}'<c:if test="${!status.last}">, </c:if>
        </c:forEach>
    ];

    Swal.fire({
        title: '이용에 불편을 드려 죄송합니다',
        html: '재고가 부족한 상품: <br>' + outOfStockItems.join('<br>'),
        icon: 'warning',
        confirmButtonText: '확인'
    }).then(() => {
        location.href = 'cartPage.do';
    });
</script>
</body>
</html>
