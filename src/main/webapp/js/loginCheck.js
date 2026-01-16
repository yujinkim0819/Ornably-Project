document.addEventListener('DOMContentLoaded', function () {
    let idInput = document.querySelector('input[name="userInputId"]');
    let pwInput = document.querySelector('input[name="userInputPassword"]');
    let loginBtn = document.getElementById('loginBtn');
	let rememberCheckbox = document.getElementById('rememberId');
	
	// 최소 문자 입력 시 경고 메세지
	
	// 1. 저장된 아이이디 있으면 불러오기 (로컬 스토리지)
	let savedId = localStorage.getItem("savedUserId");
	if(savedId) {
		idInput.value = savedId;
		rememberCheckbox.checked = true;
	}
	
	// 둘중 하나만 공백이면 버튼 비활성화 
    function checkInput() {
        let isIdFilled = idInput.value.trim() !== '';
        let isPwFilled = pwInput.value.trim() !== '';
        loginBtn.disabled = !(isIdFilled && isPwFilled);
    }

    idInput.addEventListener('input', checkInput);
    pwInput.addEventListener('input', checkInput);
	
	// 2. 로그인 버튼 클릭 시 아이디 저장/삭제 (로컬 스토리지)
	loginBtn.addEventListener("click", function () {

	    if (rememberCheckbox.checked) {
	        localStorage.setItem("savedUserId", idInput.value.trim());
	    } else {
	        localStorage.removeItem("savedUserId");
	    }
	});	
	
});