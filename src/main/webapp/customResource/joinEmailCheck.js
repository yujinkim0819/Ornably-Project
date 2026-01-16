$(function () {
    console.log(" joinEmailCheck.js 로드됨");
});

$(function () {
	
	let isEmailChecked = false;
	
    let isEmailIdValid = false;
    let isEmailUnique = false;

    // 이메일 아이디 입력 시 정규식 검증
    $("#emailId").on("blur", function () {
        let id = $(this).val().trim();
        $("#msgEmail").text("");
        isEmailIdValid = false;
        isEmailUnique = false;

        if (!id) return;

        const idRegex = /^(?=.{4,30}$)[a-zA-Z0-9]+(?:_[a-zA-Z0-9]+)*$/;

        if (!idRegex.test(id)) {
            $("#msgEmail")
                .text("이메일 아이디 형식이 올바르지 않습니다.")
                .css("color", "red");
            return;
        }

        // 이메일 아이디 형식이 통과되면
        isEmailIdValid = true;
    });

    // 이메일 중복 체크
    function checkEmailDuplicate() {
        const email = $("#accountEmail").val().trim();
		
        isEmailUnique = false;

        // 이메일 아이디 형식이 유효하고, 이메일이 완성되었을 때만 중복 체크
        if (!isEmailIdValid || !email) return;
		console.log("AJAX 호출 시작:", email);
        $.ajax({
			
            url: contextPath + "/CheckEmailDuplicateServlet",  // 실제 경로에 맞게 변경 필요
            type: "POST",
            data: { accountEmail: email },
            success: function (result) {
				console.log("AJAX 응답 성공:", result);
                if (result === "accountEmailDuplicate") {
                    $("#msgEmail")
                        .text("이미 사용 중인 이메일입니다.")
                        .css("color", "red");
						isEmailChecked = false;
                } else if (result === "accountEmailUnique") {
                    $("#msgEmail")
                        .text("사용 가능한 이메일입니다.")
                        .css("color", "green");
                    isEmailUnique = true;
					isEmailChecked = true;
                }
            },
            error: function () {
                $("#msgEmail")
                    .text("이메일 확인 중 오류가 발생했습니다.")
                    .css("color", "red");
					isEmailChecked = false;
            }
        });
    }

    // 이메일 도메인 선택
    $("#emailDomain").on("change", function () {
        if ($(this).val() === "custom") {
            $(this).hide();
            $("#customDomain").show().focus();
        } else {
            $("#customDomain").hide().val("");
            $(this).show();
        }

        updateEmail(); // 이메일 도메인 변경 후 이메일 값 업데이트
        checkEmailDuplicate(); // 이메일 도메인 변경 후 이메일 중복 체크
    });

    // 직접 입력 도메인 입력 후 블러
    $("#customDomain").on("blur", function () {
		let domain = $(this).val().trim();
		$("#msgEmail").text("");
		isEmailIdValid = false;
		isEmailUnique = false;

		if (!domain) return;

		const domainRegex = /^[a-zA-Z0-9.-]+\.(com|co\.kr|net|org|edu|gov|mil|int|co)$/;

		if (!domainRegex.test(domain)) {
		    $("#msgEmail")
		        .text("이메일 도메인 형식이 올바르지 않습니다.")
		        .css("color", "red");
	     return;
		 }
		 
        updateEmail(); // 이메일 값 업데이트
        checkEmailDuplicate(); // 이메일 중복 체크
    });
	
	// 
	$("#btnSubmit").on("click", function (e) {
	    // 아이디 중복 확인이 완료되지 않았거나, 아이디가 중복된 경우
	    if (!isEmailChecked) {
	        e.preventDefault();  // 폼 제출을 막음
	    } 
	});
	
    // 이메일 값 업데이트
    function updateEmail() {
        let id = $("#emailId").val().trim();
        let domainSelect = $("#emailDomain").val();
        let customDomain = $("#customDomain").val().trim();
        let domain = "";

        // 아이디 없으면 함수 종료
        if (!id || !domainSelect) {
            $("#accountEmail").val(""); // 이메일을 비웁니다.
            return;
        }

        // 도메인 결정
        if (domainSelect === "custom") {
            domain = customDomain;
        } else {
            domain = domainSelect;
        }

        // 이메일 값 설정
        $("#accountEmail").val(id + "@" + domain);
		console.log("accountemail", accountEmail);
    }
});