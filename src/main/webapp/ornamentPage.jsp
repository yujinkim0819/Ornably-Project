<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">

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
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/animate/animate.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">

<!-- Custom Style (버그샌드위치) -->
<link href="customResource/ratingStar.css" rel="stylesheet">

<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/customResource/dropdown.css">


<style>


/* 링크에 커서를 올렸을 때 색상 변경 */
.product-title:hover {
	color: #FF6347; /* 원하는 색으로 변경 (여기서는 Tomoto 색상 예시) */
}

.product-title {
	font-weight: bold; /* 글씨를 더 찐하게 */
	font-size: 1.2rem; /* 글씨 크기를 조금 더 크게 */
	color: #6F4F3A; /* 더 어두운 초록색 */
}

.product-item-inner {
	border: none !important; /* 모든 테두리 제거 */
	border-color: transparent !important; /* 테두리 색을 투명으로 설정 */
}

  /* 검색창을 가운데로 정렬 */
  .input-group {
    background-color: white;  /* 배경을 하얀색으로 설정 */
    border-radius: 50px;      /* 테두리 둥글게 설정 */
    overflow: hidden;         /* 요소가 밖으로 나가지 않도록 */
    display: flex;            /* flexbox로 정렬 */
    justify-content: center;  /* 입력창과 버튼을 가운데로 정렬 */
    width: 100%;              /* 전체 너비 차지 */
  }

  /* 검색 버튼 스타일 */
  .input-group-text {
    background-color: white;  /* 버튼 배경을 하얀색으로 설정 */
    border: none;             /* 버튼 테두리 제거 */
    cursor: pointer;          /* 클릭 시 손 모양 */
    padding: 10px 15px;        /* 버튼의 좌우 여백 설정 */
  }

  .input-group-text i {
    color: #333;  /* 아이콘 색상 설정 */
  }

  /* 검색 입력창 스타일 */
  .form-control {
    background-color: white;  /* 입력창 배경을 하얀색으로 설정 */
    border: none;             /* 입력창 테두리 제거 */
    border-radius: 50px;      /* 입력창 테두리 둥글게 설정 */
    color: #333;              /* 텍스트 색상 */
    padding: 10px 15px;       /* 입력창 안의 텍스트 여백 */
    margin-right: 0;          /* 오른쪽 마진 제거 */
  }

  /* 입력창 focus 시 스타일 */
  .form-control:focus {
    border-color: transparent; /* focus 시 테두리 색을 투명으로 */
    box-shadow: none;           /* focus 시 그림자 제거 */
  }
  
  
/* 기본 페이지네이션 버튼 색상 */
.pagenation-button {
  background-color: transparent !important;  /* 기본 배경 투명 */
  color: #D1B29A !important;                 /* 기본 글씨 색상 (연한 색상) */
  border: 1px solid #D1B29A !important;      /* 테두리 색상 (연한 색상) */
  padding: 10px 15px !important;             /* 버튼 크기 */
  border-radius: 5px !important;             /* 둥근 모서리 */
  text-decoration: none !important;          /* 링크 스타일 제거 */
  margin: 0 5px !important;                  /* 버튼 간격 */
}

/* 페이지네이션 버튼 hover 시 색상 */
.pagenation-button:hover {
  background-color: #8B6A4D !important;     /* hover 시 배경색 */
  color: white !important;                   /* hover 시 글씨 색상 */
  border-color: #8B6A4D !important;          /* hover 시 테두리 색상 */
}

/* 활성화된 페이지 버튼 스타일 (현재 페이지) */
.pagenation-button.active {
  background-color: #4F3C29 !important;     /* 활성화된 페이지 색상 (진한 색상) */
  color: white !important;                   /* 활성화된 텍스트 색상 */
  border-color: #4F3C29 !important;          /* 활성화된 테두리 색상 (진한 색상) */
}

/* 상품 카드 컨테이너에 기준점 설정 */
.item-card {
    position: relative; 
    overflow: hidden;
}

/* 품절 배지 스타일 */
.sold-out-badge {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: rgba(220, 53, 69, 0.9); /* 빨간색 계열 */
    color: white;
    padding: 5px 12px;
    font-size: 0.9rem;
    font-weight: bold;
    border-radius: 5px;
    z-index: 10;
}

/* 품절 상품 이미지 흐리게 처리 (선택 사항) */
.sold-out-img {
    filter: grayscale(60%) brightness(70%);
}
</style>
</head>

<body>



	<tag:header />

	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6 wow fadeInUp"
			data-wow-delay="0.1s">오너먼트 상품</h1>
	</div>
	<!-- Single Page Header End -->



	<!-- Shop Page Start -->
	<div class="container-fluid shop py-5">
		<div class="container py-5 d-flex justify-content-center">
			<div class="col-lg-9 wow fadeInUp content" data-wow-delay="0.1s">
				
				<!-- 상품 정렬 옵션 영역 -->
				<div class="row g-4">
					<!-- 검색창 영역 -->
					<div class="col-xl-12">
						<div class="input-group w-100 mx-auto d-flex" style="cursor: pointer;">
							<input id="search-bar" type="search" class="form-control p-3"
								placeholder="검색어를 입력해주세요" 
								aria-describedby="search-icon-1">
							<span id="search-icon-1" class="input-group-text p-3"><i
								class="fa fa-search"></i></span>
								<!-- 최근 검색어 드롭다운 바 -->
						<div id="recent-search-dropdown" class="recent-search-dropdown d-none">
							<!-- 최대 5개 드롭다운 생성 -->
						</div>
						</div>
						
					</div>
					<!-- 검색창 영역 종료 -->
					
					<!-- 검색어 옆 추가 정렬 버튼 -->
					<div class="col-xl-12 d-flex justify-content-end"
						data-wow-delay="0.1s">
						<ul class="nav nav-pills d-inline-flex mb-5">
							<li class="nav-item mb-4"><a id="star-desc"
								class="d-flex mx-2 py-2 bg-light rounded-pill active text-center align-items-center"
								data-bs-toggle="pill" href="#"
								style="background-color: #6F4F3A !important; color: white; border: none;">
									<span style="width: 130px; color: white;">별점 높은순</span>
							</a></li>
						</ul>
					</div>
					<!-- 검색어 옆 추가 정렬 버튼 종료 -->
				</div>
				<!-- 상품 정렬 옵션 영역 종료 -->
				
				<!-- 상품 목록 및 페이지네이션 -->
				<div class="tab-content">
					<div id="tab-5" class="tab-pane fade show p-0 active">
						<div id="item-list-container" class="row g-4 product">
							
							<!-- 상품 목록 비동기 로딩 -->

						</div>
						<!-- 페이지네이션 부분 -->
						<div class="col-12 wow fadeInUp" data-wow-delay="0.1s">
							<div id="item-page-list"
								class="pagination d-flex justify-content-center mt-5">
								
								<!-- 페이지네이션 비동기 로딩 -->
								
							</div>
						</div>
					</div>
				</div>
				<!-- 상품 목록 및 페이지네이션 종료 -->
			</div>
		</div>
	</div>
	<!-- Shop Page End -->


	<tag:footer />



	<!-- JavaScript Libraries -->
	<!-- <script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script> -->
	<script src="lib/wow/wow.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>


	<!-- Template Javascript -->
	<script src="js/main.js"></script>

	<!-- Custom JavaScript -->
	<script src="customResource/ratingStar.js"></script>
	<script>
	
	
	const itemListContainer = $("#item-list-container");
	
	function renderItems(itemDatas) {
		console.log('renderItems');
	  	console.log("a" + itemDatas);
	  
	  let html = "";
	  $.each(itemDatas, function (index, itemData) {
		  console.log("index:["+index+"]")
		  console.log("itemData:["+itemData+"]")
		  // [수정 포인트 1] 재고에 따른 품절 처리 로직 추가
        let soldOutBadge = "";
        let imgClass = "";
        
        if (itemData.itemStock === 0) {
            // 품절 배지 HTML 생성
            soldOutBadge = `<div class="sold-out-badge">품절</div>`;
            // 이미지에 흐림 효과를 줄 클래스 추가 (선택 사항)
            imgClass += " sold-out-img";
        }
	    html += `
	    
	      <div class="col-lg-4">
	        <div class="rounded ">
	          <div class="product-item-inner border rounded item-card">
	          \${soldOutBadge}
	            <div class="product-item-inner-item ">
	              <img src="${pageContext.request.contextPath}\${itemData.itemImageUrl}"
	                   class="img-fluid w-100 rounded-top product-img \${imgClass}"
	                   alt="상품 이미지">
	              <div class="product-details"></div>
	            </div>

	            <div class="text-center rounded-bottom p-4">
	              <a href="ornamentDetailPage.do?itemPk=\${itemData.itemPk}" class="d-block h2 product-title">
	                \${itemData.itemName}

	              </a>
	              <span class="text-primary fs-7" style="color: brown !important;">
	              \${Number(itemData.itemPrice).toLocaleString('ko-KR') + '원'}
	              </span>
	            </div>

	            <div class="text-center rounded-bottom p-4">
	              <div class="rating_box">
	                <div class="rating">
	                  ★★★★★
	                  <span class="rating_star"
	                        style="width: \${itemData.itemStar * 10}%;">
	                    ★★★★★
	                  </span>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>
	      </div>
	    `;
	  });
	  itemListContainer.html(html);
	}
	
	function renderPagination(data) {
		console.log('renderPagination');
		  let html = "";

		  const currentPage = data.currentPage;
		  const startPage   = data.startPage;
		  const endPage     = data.endPage;
		  const totalPage   = data.totalPage;
			console.log(
					"currentPage:["+currentPage+"]"+
					"startPage:["+startPage+"]"+
					"endPage:["+endPage+"]"+
					"totalPage:["+totalPage+"]");
		  // 현재페이지가 보이는 페이지네이션 맨 왼쪽이 아니라면 '<<' 표시 해주기
		  if (currentPage > 1) {
		    html += `
		      <a href="#" class="rounded pagenation-button item-card" data-page="\${currentPage - 1}">
		        &laquo;
		      </a>
		    `;
		  }

		  // 페이지 번호들 출력, 현재 페이지면 active 클래스 붙여주기
		  for (let page = startPage; page <= endPage; page++) {
		    html += `
		      <a href="#"
		         class="rounded pagenation-button \${page === currentPage ? 'active' : 'item-cart'}"
		         data-page="\${page}">
		        \${page}
		      </a>
		    `;
		  }
		  
		  // 마지막 페이지에 갔을때면 '>>' 표시 없애주기
		  if (currentPage < totalPage) {
		    html += `
		      <a href="#" class="rounded pagenation-button item-card" data-page="\${currentPage + 1}">
		        &raquo;
		      </a>
		    `;
		  }
			console.log(html);
		  const paginationContainer = $("#item-page-list"); 
		  paginationContainer.html(html);
		}
	
	
	let keyword = $("#search-bar").val();
	let condition = "keyword";
	let page = "1";
	
	// 1. 키워드 검색 눌렀을 때
	const searchButton = $('#search-icon-1');
	searchButton.on('keydown click', function(){
		console.log("검색어 검색 버튼 눌림");
		
		keyword = $("#search-bar").val();
		saveRecentSearch(keyword); // ✅ 추가
		condition = "keyword";
		page = "1";
		
		$.ajax({
		  url: "${pageContext.request.contextPath}/ItemListPagenationServlet",
		  type: "GET", 
		  dataType: "json", // 서버가 JSON을 응답하면
		  contentType: "application/json; charset=UTF-8", 
		  data: { keyword: $("#search-bar").val(), condition: "keyword", page: "1"},
		  success: function (result) { 
			console.log("검색어 관련 데이터 받음");
			// itemDatas, currentPage, startPage, endPage, totalPage
		    renderItems(result.itemDatas);
		 	// currentPage, startPage, endPage, totalPage 주고 페이지네이션 밑부분 만들기
		  	renderPagination(result); 
		  },
		  error: function (xhr, status, err) {
		    console.log("error:", status, err);
		    console.log("responseText:", xhr.responseText);
		  },
		  complete: function () {
		    console.log("complete");
		  }
		});
	});
	
	
	// 2. 별점 내림차순 버튼 눌렀을 때 
	$('#star-desc').on('click', function(){
		console.log("별점 내림차순 버튼 눌림");
		
		keyword = $("#search-bar").val();
		condition = "starDesc";
		page = "1";
		
		$.ajax({
			  url: "${pageContext.request.contextPath}/ItemListPagenationServlet",
			  type: "GET", 
			  dataType: "json", // 서버가 JSON을 응답하면
			  contentType: "application/json; charset=UTF-8", 
			  data: { keyword: $("#search-bar").val(), condition: "starDesc", page: "1"},
			  success: function (result) { // itemDatas, currentPage, startPage, endPage, totalPage
			    renderItems(result.itemDatas);
			    renderPagination(result); // currentPage, startPage, endPage, totalPage 주고 페이지 네이션 밑부분 만들기
			  },
			  error: function (xhr, status, err) {
			    console.log("error:", status, err);
			    console.log("responseText:", xhr.responseText);
			  },
			  complete: function () {
			    console.log("complete");
			  }
			});
	});
	// 3. 최초 로딩 페이지
	$(document).ready(function(){
		console.log("최초 페이지 로딩");
		
		keyword = $("#search-bar").val();
		condition = "default";
		page = "1";
		
		$.ajax({
			  url: "${pageContext.request.contextPath}/ItemListPagenationServlet",
			  type: "GET", 
			  dataType: "json", // 서버가 JSON을 응답하면
			  data: { keyword: $("#search-bar").val(), condition: "default", page: "1"},
			  success: function (result) { // itemDatas, currentPage, startPage, endPage, totalPage
				console.log(result);
			    renderItems(result.itemDatas);
			    renderPagination(result); // currentPage, startPage, endPage, totalPage 주고 페이지 네이션 밑부분 만들기
			  },
			  error: function (xhr, status, err) {
			    console.log("error:", status, err);
			    console.log("responseText:", xhr.responseText);
			  },
			  complete: function () {
			    console.log("complete");
			  }
			});
	});
	
	// 4. 페이지네이션 번호 눌렀을 때
	$(document).on('click', '.pagenation-button', function(e) {
		page = $(this).data('page');
		console.log("페이지네이션 버튼 눌림:["+page+"]");
		
		$.ajax({
		  url: "${pageContext.request.contextPath}/ItemListPagenationServlet",
		  type: "GET", 
		  dataType: "json", // 서버가 JSON을 응답하면
		  contentType: "application/json; charset=UTF-8", 
		  data: { keyword: keyword, condition: condition, page: page},
		  success: function (result) { // itemDatas, currentPage, startPage, endPage, totalPage
		    renderItems(result.itemDatas);
		    renderPagination(result); // currentPage, startPage, endPage, totalPage 주고 페이지 네이션 밑부분 만들기
		  },
		  error: function (xhr, status, err) {
		    console.log("error:", status, err);
		    console.log("responseText:", xhr.responseText);
		  },
		  complete: function () {
		    console.log("complete");
		  }
		});
	});
	</script>
</body>

</html>