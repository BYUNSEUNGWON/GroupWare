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
    table tr:hover {
        background-color: #f1f1f1; /* 배경색 변경 */
        cursor: pointer; /* 커서 스타일 변경 */
    }
</style>
</head>
<body>
    <!-- 고정된 헤더 -->
    <div class="header-container">
        <%@ include file="../common/header.jsp" %>
    </div>

    <%@ include file="../common/approvalMenu.jsp" %>

    <c:if test="${not empty result}">
        <script type="text/javascript">
            alert("${result}");
            window.close();
        </script>
    </c:if>

    <div class="content">
        <h2 class="text-primary">결재하기</h2>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>NO.</th>
                    <th class="narrow-col">상신자명</th>
                    <th class="wide-col">제목</th>
                    <th>문서번호</th>
                    <th>상신일시</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty approvalList}">
                        <c:forEach var="doc" items="${approvalList}" varStatus="status">
                            <tr>                           
                                <td>${status.index + 1}</td>
                                <td class="narrow-col">${doc.submitter}</td>
                                <td class="wide-col">${doc.subject}</td>
                                <td>${doc.documentNo}</td>
                                <td>${doc.registDt}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5">결재대기문서가 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

    <!-- 모달 -->
    <div class="modal fade" id="approvalModal" tabindex="-1" aria-labelledby="approvalModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="approvalModalLabel">결재문서 내용</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p><strong>상신자명</strong>: <span id="modalSubmitter"></span></p>
                    <p><strong>제목</strong>: <span id="modalSubject"></span></p>
                    <p><strong>첨부파일</strong>: <span id="modalFileId"></span></p>
                    <p><strong>본문</strong>: <span id="modalContents"></span></p>
                    <p><strong>요청코멘트</strong>: <span id="modalComment"></span></p>
                    <p><strong>요청일시</strong>: <span id="modalRegistDt"></span></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary">승인</button>
                    <button type="button" class="btn btn-danger">반려</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function() {
        // 각 테이블 행에 클릭 이벤트 리스너 추가
        var rows = document.querySelectorAll('table tbody tr');
        rows.forEach(function(row) {
            row.addEventListener('click', function() {
                var documentNo = this.cells[3].innerText;
                var approvalModal = new bootstrap.Modal(document.getElementById('approvalModal'));
                approvalModal.show();
                
                $.ajax({
                    url: '/detail.ex',
                    type: 'POST',
                    data: { documentNo: documentNo },
                    success: function(response) {
                        document.getElementById('modalSubmitter').innerText = response.submitter;
                        document.getElementById('modalSubject').innerText = response.subject;
                        document.getElementById('modalFileId').innerText = response.fileId;
                        document.getElementById('modalContents').innerText = response.contents;
                        document.getElementById('modalComment').innerText = response.comment;
                        document.getElementById('modalRegistDt').innerText = response.registDt;
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                    }
                });
            });
        });
    });
    </script>
</body>
</html>