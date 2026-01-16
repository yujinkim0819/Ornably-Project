$(function () {
    console.log(" joinPhoneCheck.js 로드됨");
});

$(function () {
	
	let isPhoneChecked = false; // 폰 번호 중복 확인 여부
	
	$("#phone").on("input", function() {
	    let text= $(this).val();

	    // 숫자가 아닌 문자는 모두 제거
	    if (/[^0-9]/.test(text)) {
	        text = text.replace(/[^0-9]/g, "");
	        $(this).val(text);
	        
	        $("#msgPhoneCheck")
	            .text("휴대폰 번호는 숫자만 입력 가능합니다.")
	            .css("color", "red");
	    } else {
	        $("#msgPhoneCheck").text(""); // 정상 입력이면 메시지 제거
	    }
	});
	
    $("#phone").on("blur", function () {
        let phone = $("#phone").val().trim();
        $("#msgPhoneCheck").text("");
		
		console.log("phone : ", phone);
        //  빈 값 체크
        if (!phone) {
            $("#msgPhoneCheck")
                .text("휴대폰 번호를 입력하세요.")
                .css("color", "red");
           // isPhoneChecked = false;
		   // updateSubmitButton();
            return;
        }

        //  숫자만 + 010 고정 + 11자리 정규식
        const phoneRegex = /^010\d{8}$/;

        if (!phoneRegex.test(phone)) {
            $("#msgPhoneCheck")
                .text("휴대폰 번호 형식이 올바르지 않습니다. (010xxxxxxxx)")
                .css("color", "red");
           // isPhoneChecked = false;
		   // updateSubmitButton();
            return; // ❌ Ajax 호출 차단
        }

        // Ajax 중복 검사
        $.ajax({
            url: contextPath + "/CheckPhoneNumberDuplicateServlet",
            type: "POST",
			dataType: 'text',
            data: { accountPhone: phone },
            success: function (result) {
				console.log("Ajax 결과:", result);
				
                if (result === "accountPhoneDuplicate") {
                    $("#msgPhoneCheck")
                        .text("이미 사용 중인 휴대폰 번호입니다.")
                        .css("color", "red");
						isPhoneChecked = false;
                } else if (result === "accountPhoneUnique") {
                    $("#msgPhoneCheck")
                        .text("사용 가능한 휴대폰 번호입니다.")
                        .css("color", "green");
						isPhoneChecked = true;
                } else if (result === "accountPhoneEmpty") {
                    $("#msgPhoneCheck")
                        .text("휴대폰 번호를 입력하세요.")
                        .css("color", "red");
						isPhoneChecked = false;
                }
            },
            error: function () {
                console.log("휴대폰 번호 중복 체크 Ajax 오류");
                // isPhoneChecked = false;
				// updateSubmitButton();
			}
        });
    });
	
	// 회원가입 버튼 클릭 시 폰 번호 중복 확인이 되어 있는지 체크
	$("#btnSubmit").on("click", function (e) {
	    // 아이디 중복 확인이 완료되지 않았거나, 아이디가 중복된 경우
	    if (!isPhoneChecked) {
	        e.preventDefault();  // 폼 제출을 막음
		}
	});

    //  전화번호 변경 시 → 다시 검증하도록 상태 초기화
    $("#phone").on("input", function () {
		$("#msgPhoneCheck").text("");
		
		isPhoneChecked = false;
    });

});