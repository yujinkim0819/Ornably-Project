$(function() {

	$("form").on("submit", function(e) {
		// submit 가로채기
		e.preventDefault();

		const ok = confirm(
			"정말로 회원 탈퇴하시겠습니까?\n탈퇴 후에는 복구할 수 없습니다."
		);

		if (!ok) {
			return; // 취소 → 아무것도 안 함
		}

		// ✔ 확인 → 실제 submit 진행
		this.submit();
	});

});