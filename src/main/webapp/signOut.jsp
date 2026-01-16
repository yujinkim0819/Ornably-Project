<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
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
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	
	<style>
		.form-check-input {
			accent-color: #22c55e; /* 체크 표시 색 */
		}
		
		.form-check-input:checked {
			background-color: #22c55e;
			border-color: #22c55e;
		}
		
		.form-check-input:focus {
    		border-color: #22c55e;
   		 	box-shadow: 0 0 0 0.25rem rgba(34, 197, 94, 0.25) !important;
		}
		
		.form-control:focus {
    		border-color: #22c55e;
    		box-shadow: 0 0 0 0.25rem rgba(34, 197, 94, 0.25) !important;
		}
	</style>
	<!-- ▼▼▼모달창 cdn ▼▼▼ -->
	<script src="
	https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.all.min.js
	"></script>
	<link href="
	https://cdn.jsdelivr.net/npm/sweetalert2@11.26.12/dist/sweetalert2.min.css
	" rel="stylesheet">
	<!-- ▲▲▲ 모달창 cdn ▲▲▲ -->
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">
	<style>
	
	</style>
</head>


<body>

<tag:header />

<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">회원 탈퇴</h1>
	</div>
	<!-- Single Page Header End -->
    <!-- Contucts Start -->
    <div class="container-fluid contact py-5">
        <div class="container py-5" >
            <div class="p-5 content item-card rounded" style="max-width: 900px; margin: 0 auto;">
                <div class="row g-4">
                    <div class="col-12">
                        <div class="text-center mx-auto wow fadeInUp" data-wow-delay="0.1s" style="max-width: 500px;">
                            <h2 class="text-dark border-bottom border-dark border-2 d-inline-block pb-2 mb-4">회원탈퇴</h2>
                      
                        </div>
                    </div>
                    <!-- 회원탈퇴 페이지 수정 부분 -->
                    <div class="col-lg-6 mx-auto">
						<form action="signOut.do" method="POST">
    						<div class="row g-4 wow fadeInUp" data-wow-delay="0.1s">
       				<!-- 비밀번호 입력 -->
        						<table class="table">
           							<tr>
                						<td>
                    						<input type="password" class="form-control" id ="password1" name="inputPassword" placeholder="비밀번호 입력" minlength="4" maxlength="25" required>             						
                							<div id ="msgCheck1"></div>
                						</td>
            						</tr>

            					<!-- 비밀번호 -->
            						<tr>
                						<td>
                    						<input type="password" class="form-control" id="password2" name="password2" placeholder="비밀번호 확인" minlength="4" maxlength="25" required>
											<div id="msgCheck2"></div>                						
                						</td>
            						</tr>
            						<tr>
                						<td colspan="2">
                    						<div class="form-check my-3">
                        						<input class="form-check-input" type="checkbox" id="agree" name="agree" required>
                        						<label class="form-check-label" for="agree">
                            						회원 탈퇴 시 계정 정보 및 서비스 이용 기록은 즉시 삭제되며, 삭제된 데이터는 복구할 수 없습니다. 이에 동의합니다. (필수)
                       			 				</label>
                    						</div>
                						</td>
            						</tr>
            					</table>

        						<!-- 회원탈퇴 버튼 -->
        						<div class="col-12 mt-4" >
            						<button id="signout" class="custom-btn btn btn-dark w-100 py-3" disabled>회원탈퇴</button>
        						</div>

    						</div>
						</form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Contuct End -->

 

	<tag:footer />


    <!-- Back to Top -->

    <!-- JavaScript Libraries -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script> -->
    <script src="lib/wow/wow.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>


    <!-- Template Javascript -->
    <script src="js/main.js"></script>
    
    <!-- Custom JavaScript (버그샌드위치) -->
    <script>
    		
    </script>
	<script >
	$(function () {
		// 비밀번호 2개 맞는지 확인하는 코드
	    $("#password1, #password2").on("input", function () {
	        let pw1 = $("#password1").val();
	        let pw2 = $("#password2").val();
	        let agree = $("#agree").is(":checked");
			console.log(pw1);
			console.log(pw2);
	        // 기본 상태
	        $("#signout").prop("disabled", true);
	        $("#msgCheck1").text("");
	        $("#msgCheck2").text("");

	        // 입력값 체크
	        if (!pw1 || !pw2) {
	            return;
	        }

	        if (pw1 !== pw2) {
	            $("#msgCheck2").text("비밀번호가 일치하지 않습니다.").css("color", "red");
	            return;
	        }
			else{
				$("#msgCheck2").text("비밀번호가 일치합니다.").css("color", "green");
			}
	     // Ajax 비동기 요청
	        $.ajax({
	            url: "${pageContext.request.contextPath}/CheckPasswordServlet",
	            type: "POST",
	            data: { inputPassword: pw1 },
	            success: function (result) {
					console.log("Ajax 결과:", result); //
					// 
	                if (result === "true") {
	                    //$("#msgCheck1").text("비밀번호가 확인되었습니다.").css("color", "green");

	                    if (agree) {
	                        $("#signout").prop("disabled", false);
	                    }
	                }
	                /*else {
	                    $("#msgCheck1").text("비밀번호가 올바르지 않습니다.").css("color", "red");
	                }*/
	            },
	            error: function () {
	                console.log("비밀번호 확인 Ajax 오류");
	            }
	        });
	    });

	    // 동의 체크박스 변경 시에도 다시 검사
	    $("#agree").on("change", function () {
	        $("#password1").trigger("input");
	    });
	});
	$(function() {

		$("form").on("submit", function(e) {
			e.preventDefault();
			
			const form = this; // 현재 form DOM

			
			// 모달창 실행시키기
			Swal.fire({
				  title: '회원 탈퇴',
				  icon: 'warning',
				  html: `
				    <label style="display:flex; align-items:center; gap:8px;">
				      <input type="checkbox" id="agree2">
				      정말로 회원 탈퇴하시겠습니까?\n탈퇴 후에는 복구할 수 없습니다.
				    </label>
				  `, // 체크박스 만들어주기
				  confirmButtonText: '탈퇴',
				  showCancelButton: true,
				  cancelButtonText: '취소',
				  allowOutsideClick: false, // UX + 취소 금지
				  allowEscapeKey: true,
				  // 
				  didOpen: () => {
				    const confirmBtn = Swal.getConfirmButton(); // 전역 객체 Swal로 부터 '탈퇴'버튼 요소 가져와주기
				    const agreeChk = document.getElementById('agree2'); // 체크박스 아이디

				    // 처음엔 비활성화
				    confirmBtn.disabled = true;

				    // 상태 바뀔때마다 버튼 상태 ON/OFF 해주기
				    agreeChk.addEventListener('change', () => {
				      confirmBtn.disabled = !agreeChk.checked;
				    });
				  }
				}).then((result) => { // '탈퇴' 버튼 눌렸을 때 탈퇴 진행해주기
				  if (result.isConfirmed) {
					  console.log('최종 회원탈퇴 진행');
				    // 탈퇴 진행시키기
				    form.submit();
				  }
				  else{
					  form.action = "myPage.do";
					  form.method = "POST";
					  form.submit();
				  }
				});
/*
			const ok = confirm(
				
			);
			if (!ok) {
				location.href="myPage.do";
				return; // 취소 → 아무것도 안 함
			}
			// ✔ 확인 → 실제 submit 진행*/
		});
	});
	</script>
</body>

</html>