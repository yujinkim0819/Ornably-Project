<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오너블리 - 함꼐 크리스마스를 즐겨요</title>
<link rel="icon" href="images/ORNABLY.jpg">
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
	<script>
	Swal.fire({
		  title: '알림',
		  text: '${message}',      // ⭐ el을 그대로 전달
		  icon: 'info',
		  confirmButtonColor: '#6F4F3A',
		  showCancelButton: false,
		  confirmButtonText: '확인',
		  cancelButtonText: '취소',
		  allowOutsideClick: false,
		  allowEscapeKey: true
		}).then((result) => {
		  if (result.isConfirmed) {
		    location.href='${empty location ? "mainPage.do" : location}';
		  }
		});
	</script>
</body>
</html>