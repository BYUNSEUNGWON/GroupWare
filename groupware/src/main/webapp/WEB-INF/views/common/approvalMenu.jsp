<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<style>
    .main-content {
        display: flex;
        width: 100%;
        height: calc(100vh - 70px);
    }
    .menu {
        width: 250px;
        padding: 20px;
        background-color: #f8f9fa;
        box-shadow: 2px 0 5px -2px rgba(0, 0, 0, 0.1);
        position: sticky;
        top: 70px;
    }
    .menu button {
        width: 100%;
        margin-bottom: 15px;
    }
        body {
        background-color: #f4f6f9;
    }
    .modal-container {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }
    .modal-content {
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.3);
        width: 600px; /* 원하는 크기로 조정 (가로) */
        max-width: 90%; /* 화면의 90%를 넘지 않도록 설정 */
        height: 80vh; /* 원하는 크기로 조정 (세로) */
        max-height: 90%; /* 화면의 90%를 넘지 않도록 설정 */
        overflow-y: auto; /* 세로 스크롤 생성 */
    }
    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;
    }
    .close-btn {
        border: none;
        background: none;
        font-size: 20px;
        cursor: pointer;
    }
    .list-group-item {
        display: flex;
        justify-content: space-between;
    }
    .list-group-item > span {
        flex: 1;
        text-align: left;
        cursor: pointer;
        transition: background-color 0.3s, color 0.3s;
    }
	.list-group-item>span:hover {
        background-color: #eaeaea; /* 마우스를 올렸을 때 연한 회색 배경색 변경 */
    	color: black; /* 글자색 (원래 색상 유지) */
    	border-radius: 8px; /* 모서리를 둥글게 변경 */
    	transition: background-color 0.3s, color 0.3s, border-radius 0.3s; /* 부드러운 전환을 위해 추가 */
    }
    .list-group-item-header {
        font-weight: bold;
        background-color: #f8f9fa;
    }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	function openNewDoc() {
	    console.log("Fetching approval forms...");
	    // AJAX 요청을 보내서 데이터를 가져옴
	    $.ajax({
	        url: '/getApprovalForms.ex', // 서버의 엔드포인트
	        type: 'POST',
	        dataType: 'json',
	        success: function(data) {
	        	console.log(data);
	            let listHTML = '<li class="list-group-item"><strong>양식명</strong></li>'; // Header
	            var i = 1;
	            data.forEach(function(item) {
	                listHTML += '<li class="list-group-item">';
	                listHTML += '<span onclick="submit(\''+ item.formId + '\');">' + i + '. ' + item.formNm + '</span>';
	                listHTML += '</li>';
	                i++;
	            });
	            document.getElementById('approval-list').innerHTML = listHTML;
	            document.getElementById('modal-container').style.display = 'flex';
	        },
	        error: function(xhr, status, error) {
	            console.error("Error fetching data: ", error);
	        }
	    });
	}
	
    function closeNewDoc() {
        document.getElementById('modal-container').style.display = 'none';
    }
    
    function submit(formId){
        window.open('/submit.ex?formId=' + formId, 'PopupWindow', 'width=1500,height=1000,scrollbars=yes');
        closeNewDoc();
    }
</script>
<body>
<!-- 고정된 메뉴와 콘텐츠를 감싸는 main-content -->
    <div class="main-content">
        <div class="menu">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <button class="btn btn-outline-primary" onclick="openNewDoc()">상신하기</button>
                </li>
                <li class="nav-item">
                    <button class="btn btn-outline-primary" onclick="window.location.href='/approveDocuments.ex'">결재하기</button>
                </li>
                <li class="nav-item">
                    <button class="btn btn-outline-primary" onclick="window.location.href='/approvedDocuments.ex'">결재완료</button>
                </li>
                <li class="nav-item">
                    <button class="btn btn-outline-primary" onclick="window.location.href='/rejectedDocuments.ex'">결재반려</button>
                </li>
                <li class="nav-item">
                    <button class="btn btn-outline-primary" onclick="window.location.href='/canceledDocuments.ex'">결재취소</button>
                </li>
            </ul>
        </div>
        
        <!-- 모달 -->
	    <div id="modal-container" class="modal-container">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">결재 양식</h5>
	                <button class="close-btn" onclick="closeNewDoc()">&times;</button>
	            </div>
	            <div class="modal-body">
	                <ul id="approval-list" class="list-group">
	                </ul>
	            </div>
	        </div>
	    </div>
</body>
</html>