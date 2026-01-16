$(function () {
    let isPasswordValid = false; // ⭐ 비밀번호 형식 통과 여부

    //  비밀번호 형식 체크
    $("#password1").on("blur", function () {
        let pw1 = $(this).val().trim();
        $("#msgCheck1").text("");
        isPasswordValid = false; // 초기화

        if (!pw1) return;

        const pwRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&])[\S]{8,16}$/;

        if (!pwRegex.test(pw1)) {
            $("#msgCheck1")
                .text("비밀번호 형식이 올바르지 않습니다. (영문 대소문자, 숫자, 특수문자 포함, 8~16자)")
                .css("color", "red");
        } else {
            $("#msgCheck1")
                .text("비밀번호 형식이 올바릅니다.")
                .css("color", "green");
            isPasswordValid = true; // ⭐ 통과
        }
    });

    // 2 비밀번호 일치 체크
    $("#password2").on("blur", function () {
        let pw1 = $("#password1").val().trim();
        let pw2 = $(this).val().trim();
        $("#msgCheck2").text("");

        if (!pw2) return;

        // ⭐ 형식이 틀리면 일치 메시지 자체를 안 띄움
        if (!isPasswordValid) {
            return;
        }

        if (pw1 !== pw2) {
            $("#msgCheck2")
                .text("비밀번호가 일치하지 않습니다.")
                .css("color", "red");
        } else {
            $("#msgCheck2")
                .text("비밀번호가 일치합니다.")
                .css("color", "green");
        }
    });
});