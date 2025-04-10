<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Team Box List</title>
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/moment@2.29.1/min/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/5.11.2/main.min.js'></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
			padding-top: 70px;
            
        }
		.header-container {
	        position: fixed;
	        width: 100%;
	        top: 0;
	        z-index: 10;
	        background: white;
	        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
   		 }
        .list-container {
            margin-top: 20px;
        }
        .list-item {
            padding: 15px;
            border: 1px solid #ddd;
            margin-bottom: 15px;
            border-radius: 5px;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .write-button {
            position: fixed;
            right: 20px;
            bottom: 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 50%;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
            font-size: 24px;
            text-align: center;
            cursor: pointer;
        }
        .write-button:hover {
            background-color: #0056b3;
        }
        .container{
        	margin-top: 20px;
        }

    </style>
</head>
<body>    <!-- 고정된 헤더 -->
    <div class="header-container">
        <%@ include file="../common/header.jsp" %>
    </div>
    <div class="container">
        <h1>Team Box List</h1>
        <div class="list-container">
            <c:forEach var="team" items="${teams}">
                <div class="list-item">
                    <a href="${team.link}" target="_blank" class="news-link">
                        ${team.title}
                    </a>
                </div>
            </c:forEach>
        </div>

        <!-- Pagination -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                        <a class="page-link" href="/teambox.ex?page=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>

        <!-- Write Button -->
        <button class="write-button" onclick="window.location.href='/write'">+</button>
    </div>
</body>
</html>