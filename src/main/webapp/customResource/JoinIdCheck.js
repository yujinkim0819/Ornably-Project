$(function () {
	let isIdChecked = false; // 아이디 중복 확인 여부
	
	$("#btnIdCheck").on("click", function () {
        let userId = $("#userId").val().trim();

        console.log("중복 확인 아이디:", userId);

        $("#msgIdCheck").text("");

        // 빈 값 체크
        if (!userId) {	
            $("#msgIdCheck").text("아이디를 입력하세요.").css("color", "red");
			return;
        }
		
		// ️정규식 체크
		const idRegex = /^(?=.*\d)[a-z][a-z0-9_]{3,15}$/;
		if (!idRegex.test(userId)) {
			
		    $("#msgIdCheck")
		        .text("아이디 형식이 올바르지 않습니다. (영소문자 시작, 4~16자, _, 숫자 포함)")
		        .css("color", "red");
		    return; // Ajax 호출 막음
		}
		
        $.ajax({
            url: contextPath + "/CheckIdDuplicateServlet", 
            type: "POST",
			dataType: 'text',
            data: { accountId: userId },
            success: function (result) {
                console.log("Ajax 결과:", result);

                if (result === "accountIdDuplicate") {
                    $("#msgIdCheck").text("이미 사용 중인 아이디입니다.").css("color", "red");
					isIdChecked = false;  // 아이디 중복 시 유효하지 않음
				} else if (result === "accountIdUnique") {		
                    $("#msgIdCheck").text("사용 가능한 아이디입니다.").css("color", "green");
					isIdChecked = true;  // 아이디 유효한 경우
                }
            },
            error: function () {
                console.log("아이디 중복 확인 Ajax 오류");
				isIdChecked = false;  // 요청 실패 시 유효하지 않음
            }
        });
    });
	
	// 회원가입 버튼 클릭 시 아이디 중복 확인이 되어 있는지 체크
	$("#btnSubmit").on("click", function (e) {
	    // 아이디 중복 확인이 완료되지 않았거나, 아이디가 중복된 경우
	    if (!isIdChecked) {
	        e.preventDefault();  // 폼 제출을 막음
		}
	});

    // 아이디 변경 시 다시 막기 (중요!)
    $("#userId").on("input", function () {
        $("#msgIdCheck").text(""); // 입력 중일 때 메시지 초기화
		
		isIdChecked = false;  // 아이디 확인이 완료되지 않았다고 표시
    });
});