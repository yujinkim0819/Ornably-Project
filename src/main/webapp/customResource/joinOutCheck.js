$(function () {
    $("#password1, #password2").on("input", function () {
        let pw1 = $("#password1").val().trim();
        let pw2 = $("#password2").val().trim();
        let agree = $("#agree").is(":checked");
		console.log(pw1);
		console.log(pw2);
        // 기본 상태
        $("#submit").prop("disabled", true);
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
            url: "checkPassword.do",
            type: "POST",
            data: { inputPassword: pw1 },
            success: function (result) {
				console.log("Ajax 결과:", result); //
				// 
                if (result === "true") {
                    $("#msgCheck1").text("비밀번호가 확인되었습니다.").css("color", "green");

                    if (agree) {
                        $("#submit").prop("disabled", false);
                    }
                }
                else {
                    $("#msgCheck1").text("비밀번호가 올바르지 않습니다.").css("color", "red");
                }
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