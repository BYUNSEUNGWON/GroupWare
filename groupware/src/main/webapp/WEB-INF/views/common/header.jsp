<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
    body {
        margin: 0;
        font-family: Arial, Helvetica, sans-serif;
    }
    .navbar {
        background-color: #333;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 20px;
    }
    .navbar-menu {
        display: flex;
        justify-content: center;
        flex: 1;
    }
    .navbar a {
        display: block;
        color: #f2f2f2;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
        margin: 0 50px;
    }
    .navbar a:hover {
        background-color: #ddd;
        color: black;
    }
    .user-button-container {
        position: relative;
    }
    #user-button {
        background-color: #4CAF50;
        color: white;
        padding: 14px 16px;
        text-decoration: none;
        border: none;
        cursor: pointer;
    }
    .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f9f9f9;
        min-width: 160px;
        boxShadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
        z-index: 1;
        top: 100%;
        right: 0;
    }
    .dropdown-content a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        font-size: small;
        width: 100px;
    }
    .dropdown-content a:hover {
        background-color: #ddd;
    }
</style>
</head>
<body>
    <div class="navbar">
        <div class="navbar-menu">
            <a href="#home">Home</a>
            <a href="#services">Services</a>
            <a href="#about">About</a>
            <a href="#contact">Contact</a>
        </div>
        <div class="user-button-container">
            <button id="user-button">
                <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1"/>
                </svg>
            </button>
            <div class="dropdown-content">
                <a href="#profile">내 정보 변경</a>
                <a href="/logout.ex">로그아웃</a>
            </div>
        </div>
    </div>

    <script type="text/javascript">
    $(document).ready(function() {
        $("#user-button").click(function() {
            console.log("버튼 클릭");
            $(this).next(".dropdown-content").slideToggle("fast");
        });
        $(window).click(function(e) {
            if (!$(e.target).closest('.user-button-container').length) {
                $(".dropdown-content").slideUp("fast");
            }
        });
    });
    </script>
</body>
</html>