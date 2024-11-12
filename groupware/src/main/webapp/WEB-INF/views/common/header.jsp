<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<link rel="stylesheet" type="text/css" href="styles.css">
<style>
    body {
        margin: 0;
        font-family: Arial, Helvetica, sans-serif;
    }
    .navbar {
        overflow: hidden;
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
        margin: 0 30px;
    }
    .navbar a:hover {
        background-color: #ddd;
        color: black;
    }
    .navbar .user-button {
        background-color: #4CAF50;
        color: white;
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
        <a href="#user" class="user-button">User</a>
    </div>
</body>
</html>