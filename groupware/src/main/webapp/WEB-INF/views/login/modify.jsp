<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>내정보변경</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .signup-container {
            background-color: white;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 90%;
            max-width: 400px;
        }
        .signup-container h2 {
            margin-bottom: 20px;
        }
        .signup-container input[type="text"], .signup-container input[type="password"], .signup-container input[type="email"], .signup-container input[type="tel"] {
            width: calc(100% - 55px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        
        .signup-container input[type="email"], .signup-container input[type="tel"] {
            width: calc(100% - 150px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        
        .signup-container .btn {
            background-color: #007BFF;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            width: 100%;
            box-sizing: border-box;
        }
        .signup-container .btn:hover {
            background-color: #0056b3;
        }
        .signup-container .verify-btn, .signup-container .duplicate-btn {
            width: 80px; /* Smaller width for buttons */
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-left: 5px;
            box-sizing: border-box;
        }
        .signup-container .verify-btn:hover, .signup-container .duplicate-btn:hover {
            background-color: #0056b3;
        }
        .signup-container .form-row {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .signup-container .full-width {
            width: 100%;
        }
        .signup-container .error-msg, .error-msg-pw, .signup-container .available-msg {
            font-size: 12px;
            margin-top: -10px;
            margin-bottom: 10px;
        }
        .signup-container .error-msg {
            color: red;
        }
        .signup-container .error-msg-pw {
            color: red;
        }
        .signup-container .available-msg {
            color: blue;
        }
        .signup-container .input-group {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            width: 100%;
        }
        .signup-container .input-group input {
            width: calc(100% - 90px); /* Adjusted width */
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>

    var checkUserId = false;
    var checkMail = true;
    var checkMailDetail = true;
    var checkPhone = true;
    var checkPhoneDetail = true;
    
	function validatePassword() {
	    const password = document.querySelector('input[name="password"]').value;
	    const errorMsg = document.querySelector('.error-msg-pw');
	    const passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{6,}$/;
	    
	    if (!passwordRegex.test(password)) {
	        errorMsg.textContent = "비밀번호는 대문자와 숫자, 특수문자를 포함한 6글자 이상이어야 합니다.";
	    } else {
	        errorMsg.textContent = "";
	    }
	}
	
	function mailCheckVal(){
		console.log("메일 인증 로직");
        checkMail = true; // 이메일이 변경되지 않았으므로 인증 불필요
        checkMailDetail = true;
	}
	
	function phoneCheckVal(){
		console.log("폰 인증 로직");
	    checkPhone = true;
	    checkPhoneDetail = true;
	}
	
	function handleEmailVerify() {
	    const email = document.querySelector('input[name="email"]').value;
	    if (email) {
	        const emailVerificationBox = document.querySelector('.email-verification-box');
	        emailVerificationBox.innerHTML = `
	            <div class="form-row input-group">
	                <input type="text" placeholder="인증 코드">
	                <button type="button" onclick="mailCheckVal()" class="verify-btn verify-btn-small email-confirm-btn">인증 완료</button>
	            </div>
	        `;
	    } else {
	        alert('이메일을 입력해 주세요.');
	    }
	}
	
	function handlePhoneVerify() {
	    const phone = document.querySelector('input[name="phone"]').value;
	    if (phone) {
	        const phoneVerificationBox = document.querySelector('.phone-verification-box');
	        phoneVerificationBox.innerHTML = `
	            <div class="form-row input-group">
	                <input type="text" placeholder="인증 코드">
	                <button type="button" onclick="phoneCheckVal()" class="verify-btn verify-btn-small phone-confirm-btn">인증 완료</button>
	            </div>
	        `;
	    } else {
	        alert('휴대폰 번호를 입력해 주세요.');
	    }
	}
	
	
$(document).ready(function() {
	    $('input[name="password"]').on('input', validatePassword);
	    
	    $('.email-verify-btn').on('click', function(){
	    	handleEmailVerify();
	    	checkMail = true;
    	});
    	
	    $('.phone-verify-btn').on('click', function(){
    		handlePhoneVerify();
    		checkPhone = true;
	    }); 
	    
	    $('.duplicate-btn').on('click', function() {
	        const userId = $('input[name="user_id"]').val();
	        handleDuplicateCheck(userId);
	    });


	    var initialEmail = $('input[name="email"]').val();
	    var initialPhone = $('input[name="phone"]').val();
	    
	    $('input[name="email"]').on('change', function() {
	        var currentEmail = $(this).val();
	        if (currentEmail != initialEmail) {
	            checkMail = false; // 이메일이 변경되었으므로 인증 필요
	            checkMailDetail = false;
	            alert('이메일이 변경되었습니다. 인증을 완료해주세요.');
	        } else {
	            checkMail = true; // 이메일이 변경되지 않았으므로 인증 불필요
	            checkMailDetail = true;
	        }
	    });

	    $('input[name="phone"]').on('change', function() {
	        var currentPhone = $(this).val();
	        if (currentPhone != initialPhone) {
	            checkPhone = false; // 휴대폰 번호가 변경되었으므로 인증 필요
	            checkPhoneDetail = false;
	            alert('휴대폰 번호가 변경되었습니다. 인증을 완료해주세요.');
	        } else {
	            checkPhone = true; // 휴대폰 번호가 변경되지 않았으므로 인증 불필요
	            checkPhoneDetail = true;
	        }
	    });
	
	    $('form').submit(function(event) {
	        // 이메일 인증 확인
	        if (!checkMail) {
	            alert("메일 인증을 완료해주세요.");
	            $('input[name="email"]').focus();
	            event.preventDefault();
	            return;
	        }

	        if (!checkMailDetail) {
	            alert("메일 인증번호를 입력해주세요.");
	            $('button[name="mailDetail"]').focus();
	            event.preventDefault();
	            return;
	        }

	        // 휴대폰 인증 확인
	        var phone = $('input[name="phone"]').val();
	        var cleanedPhone = phone.replace(/[^\d]/g, '');
	        var phoneRegex = /^[0-9]{11}$/;

	        if (cleanedPhone == "") {
	            alert("전화번호를 입력하세요.");
	            $('input[name="phone"]').focus();
	            event.preventDefault();
	            return;
	        }

	        if (!phoneRegex.test(cleanedPhone)) {
	            alert("전화번호는 숫자 11자리여야 합니다.");
	            $('input[name="phone"]').focus();
	            event.preventDefault();
	            return;
	        } else {
	            $('input[name="phone"]').val(cleanedPhone);
	        }

	        if (!checkPhone) {
	            alert("휴대폰 인증을 완료해주세요.");
	            $('input[name="phone"]').focus();
	            event.preventDefault();
	            return;
	        }

	        if (!checkPhoneDetail) {
	            alert("휴대폰 인증번호를 입력해주세요.");
	            $('button[name="phoneDetail"]').focus();
	            event.preventDefault();
	            return;
	        }
	    });

});
    </script>
</head>
<body>
    <div class="signup-container">
        <h2>내정보변경</h2>
        <form action="/modify.ex" method="post">
            <div class="form-row">
			    <label for="user_id">ID</label>
			    <input type="text" name="user_id" id="user_id" placeholder="아이디" readonly="readonly" value="${user.user_id}">
			</div>
			<div class="form-row">
			    <span class="error-msg"></span>
			</div>
			<div class="form-row">
			    <label for="password">PW</label>
			    <input type="password" name="password" id="password" placeholder="비밀번호" readonly="readonly" value="${user.password}">
			</div>
			<div class="form-row">
			    <span class="error-msg-pw"></span>
			</div>
			<div class="form-row">
			    <label for="name">이름</label>
			    <input type="text" name="name" id="name" placeholder="이름" readonly="readonly" value="${user.name}">
			</div>
			<div class="form-row">
			    <label for="nickname">닉네임</label>
			    <input type="text" name="nickname" id="nickname" placeholder="닉네임" required value="${user.nickname}">
			</div>
			<div class="form-row">
			    <label for="dept">부서명</label>
			    <input type="text" name="dept" id="dept" placeholder="부서명" required value="${user.dept}">
			</div>
			<div class="form-row">
			    <label for="position">직책</label>
			    <input type="text" name="position" id="position" placeholder="직책" required value="${user.position}">
			</div>
            <div class="form-row">이메일
                <input type="email" name="email" placeholder="이메일" required value="${user.email}">
                <button type="button" name="mailDetail" class="verify-btn email-verify-btn">인증</button>
            </div>
            <div class="email-verification-box"></div>
            <div class="form-row">휴대폰
                <input type="tel" name="phone" placeholder="휴대폰(번호만 입력)" required value="${user.phone}">
                <button type="button" name="phoneDetail" class="verify-btn phone-verify-btn">인증</button>
            </div>
            <div class="phone-verification-box"></div>
            <div class="form-row">
                <button type="submit" class="btn full-width">변경요청</button>
            </div>
        </form>
    </div>
</body>
</html>