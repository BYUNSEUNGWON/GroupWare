<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재문서 리스트</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body {
        background-color: #f4f6f9;
        margin: 0;
        padding-top: 70px; /* Adjust this if header height is different */
    }
    .header-container {
        position: fixed;
        width: 100%;
        top: 0;
        z-index: 10;
        background: white;
        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
    }
    .content {
        flex-grow: 1;
        padding: 20px;
        background-color: #ffffff;
        margin-left: 30px; /* Minimal space between menu and content */
        border-radius: 8px;
        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
        overflow-y: auto; /* Allow content to scroll if it overflows */
    }
    .content h2 {
        margin-top: 0;
    }
    table {
        width: 100%;
        margin-top: 20px;
    }
    table, th, td {
        border: 1px solid #dee2e6;
        text-align: center;
    }
    th, td {
        padding: 15px;
    }
    .narrow-col {
    width: 10%; /* 원하는 너비를 설정합니다 */
	}
	.wide-col {
	    width: 40%; /* 원하는 너비를 설정합니다 */
	}
</style>
</head>
<body>
    <!-- 고정된 헤더 -->
    <div class="header-container">
        <%@ include file="../common/header.jsp" %>
    </div>

        <%@ include file="../common/approvalMenu.jsp" %>
        
        <div class="content">
            <h2 class="text-primary">결재하기</h2>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>No</th>
                        <th class="narrow-col">상신자명</th>
                        <th class="wide-col">제목</th>
                        <th>상신일시</th>
                        <th>문서번호</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty documents}">
                            <c:forEach var="doc" items="${documents}">
                                <tr>
                                    <td>${doc.no}</td>
                                    <td class="narrow-col">${doc.registUserId}</td>
                                    <td class="wide-col">${doc.subject}</td>
                                    <td>${doc.registDt}</td>
                                    <td>${doc.apprDocNo}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5">데이터가 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>