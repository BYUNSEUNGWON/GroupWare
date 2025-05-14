<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Team Memo Detail</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body {
        background-color: #f8f9fa;
    }
    .content-container {
        margin-top: 100px;
        padding: 20px;
    }
    .custom-header {
        position: fixed;
        width: 100%;
        top: 0;
        z-index: 10;
        background: white;
        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
    }
    .card-title, .card-content {
        margin-bottom: 20px;
    }
</style>
</head>
<body>

<!-- 고정된 헤더 -->
<div class="custom-header">
    <%@ include file="../common/header.jsp" %>
</div>

<!-- 본문 -->
<div class="container content-container">
    <div class="card shadow-sm">
        <div class="card-body">
            <h4 class="card-title">${teamsVO.title}</h4>
            <div class="card-content">
                <div>${teamsVO.contents}</div>
            </div>
                <c:if test="${not empty fileMetadatas}">
			        <div class="mb-3">
			            <label for="attachments" class="form-label">첨부 파일</label>
			            <ul>
			                <c:forEach var="fileMetadata" items="${fileMetadatas}">
			                    <li>
			                        <a href="${pageContext.request.contextPath}/downloadFile.ex?fileId=${fileMetadata.fileId}&fileNm=${fileMetadata.originalFilename}">
			                            ${fileMetadata.originalFilename}
			                        </a>
			                    </li>
			                </c:forEach>
			            </ul>
			        </div>
    			</c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>