<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>
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
        .login-container {
            background-color: white;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 90%;
            max-width: 400px;
        }
        .login-container h2 {
            margin-bottom: 20px;
        }
        .login-container input[type="text"], .login-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .login-btn {
            background-color: #007BFF;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
            box-sizing: border-box;
        }
        .login-btn:hover {
            background-color: #0056b3;
        }
        .signup-btn {
            background-color: #6c757d;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
            box-sizing: border-box;
        }
        .signup-btn:hover {
            background-color: #5a6268;
        }
        .naver-btn {
            background-color: #1ec800;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
            box-sizing: border-box;
        }
        .naver-btn:hover {
            background-color: #1ba600;
        }
        .kakao-btn {
            background-color: #fee500;
            color: black;
            padding: 10px;
            border: none;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
            box-sizing: border-box;
        }
        .kakao-btn:hover {
            background-color: #ecd600;
        }
    </style>
    <script>
        function handleLogin() {
            document.querySelector('form').submit();
        }

        function handleSignup() {
            window.location.href = 'signup.ex';
        }

        function handleNaverLogin() {
        	var naverUrl = "${naverUrl}";
            window.location.href = naverUrl;
        }

        function handleKakaoLogin() {
            // 카카오 로그인 로직 추가
            alert('카카오 로그인 기능은 구현 중입니다.');
        }

        document.addEventListener("DOMContentLoaded", function() {
            document.querySelector('.login-btn').addEventListener('click', handleLogin);
            document.querySelector('.signup-btn').addEventListener('click', handleSignup);
            document.querySelector('.naver-btn').addEventListener('click', handleNaverLogin);
            document.querySelector('.kakao-btn').addEventListener('click', handleKakaoLogin);
        });
    </script>
</head>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

<body>
    <c:if test="${not empty message}">
        <script type="text/javascript">
            var message = "${message}";
            if (message) {
                alert(message);
            }
        </script>
    </c:if>
    <div class="login-container">
        <h2>로그인</h2>
        <form action="LoginServlet" method="post">
            <input type="text" name="username" placeholder="아이디" required>
            <input type="password" name="password" placeholder="비밀번호" required>
        </form>
        
        <div class="social-login">
            <button type="button" class="login-btn">로그인</button>
            <button type="button" class="signup-btn">회원가입</button>
            <button type="button" class="naver-btn">네이버 로그인</button>
            <button type="button" class="kakao-btn">카카오 로그인</button>
        </div>
    </div>
</body>
</html>