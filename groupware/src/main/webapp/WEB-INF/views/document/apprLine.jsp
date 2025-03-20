<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>결재자 선택</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 50px;
        }

        tbody tr {
            cursor: pointer;
            transition: background-color 0.3s;
            border-radius: 4px;
        }

        tbody tr:hover td {
            background-color: #f1f1f1; /* 연한 회색 */
            border-radius: 10px; /* 둥근 모서리 처리 */
        }

        tbody td {
            padding: 15px;
            vertical-align: middle;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h3 class="mb-4">결재자 선택</h3>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>사번</th>
                    <th>성명</th>
                    <th>직책</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="approver" items="${apprLine}">
                    <tr onclick="selectApprover('${approver.name}', '${approver.user_id}')">
                        <td>${approver.user_id}</td>
                        <td>${approver.name}</td>
                        <td>${approver.position}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
        function selectApprover(name, user_id) {
            // 부모 창으로 데이터 전달
            if (window.opener && !window.opener.closed) {
                window.opener.setApprover(name, user_id);
            }
            window.close();
        }
    </script>
</body>
</html>