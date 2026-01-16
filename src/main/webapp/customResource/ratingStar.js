// ratingStar.js


// 각 요소들 선택하기
const reviewBox = $(".rating_box.range");
reviewBox.each(function(){
	const ratingInput = this.querySelector('input[type="range"]');
	const ratingStar  = this.querySelector('.rating_star');
	
	// range조절 시 활성화되는 별이 들어있는 박스의 width값 바꾸기
	ratingInput.addEventListener('input', () => {
		// console.log(ratingInput.value);
	  	ratingStar.style.width = (ratingInput.value * 10) + '%';
	  	// console.log(ratingStar.style.width); 
	});
});
// 끝
