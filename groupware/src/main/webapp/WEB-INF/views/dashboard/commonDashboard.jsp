<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<style>
    .dashboard-container {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        padding: 20px;
    }
    .dashboard-item {
        background-color: #f4f4f4;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 20px;
        text-align: center;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        height: 260px;
    }
    .dashboard-item h2 {
        margin: 0 0 10px;
        font-size: 24px;
    }
    .dashboard-item p {
        margin: 0;
        font-size: 16px;
    }
</style>
</head>
<body>
        
    <div class="dashboard-container">
        <div class="dashboard-item">
            <h2>메뉴 1</h2>
            <p>내용 1</p>
        </div>
        <div class="dashboard-item">
            <h2>메뉴 2</h2>
            <p>내용 2</p>
        </div>
        <div class="dashboard-item">
            <h2>메뉴 3</h2>
            <p>내용 3</p>
        </div>
        <div class="dashboard-item">
            <h2>메뉴 4</h2>
            <p>내용 4</p>
        </div>
        <div class="dashboard-item">
            <h2>메뉴 5</h2>
            <p>내용 5</p>
        </div>
        <div class="dashboard-item">
            <h2>메뉴 6</h2>
            <p>내용 6</p>
        </div>
    </div>
</body>
</html>