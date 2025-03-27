<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Document Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 50px;
        }

        .content-container {
            border: 1px solid #ccc;
            padding: 20px;
            min-height: 500px; /* 원하는 높이 설정 */
            max-height: 500px; /* 원하는 높이 설정 */
            overflow-y: auto; /* 세로 스크롤 추가 */
        }

        h1, h2 {
            text-align: center;
        }
        
        .readonly-container {
        }
    </style>
</head>
<body>
<div class="container">
    <h1>결재 상세</h1>
    <div class="mb-3">
        <label for="subject" class="form-label">제목</label>
        <input type="text" class="form-control" id="subject" name="subject" value="${document.subject}" readonly>
    </div>
    <div class="mb-3">
        <label for="submitter" class="form-label">상신자</label>
        <input type="text" class="form-control" id="submitter" name="submitter" value="${document.submitter}&nbsp;&nbsp;&nbsp; [ 결재요청내용 : ${document.comment} ]" readonly>
    </div>
    <div class="mb-3">
        <label for="approver" class="form-label">결재자</label>
        <input type="text" class="form-control" id="approver" name="approver" value="${document.approver}" readonly>
    </div>
    <div class="mb-3">
        <label for="registDt" class="form-label">등록일</label>
        <input type="text" class="form-control" id="registDt" name="registDt" value="${document.registDt}" readonly>
    </div>
    <div class="mb-3">
        <label for="contents" class="form-label">내용</label>
        <div id="contents" class="content-container ${action == 'approved' ? 'readonly-container' : ''}">
            <c:out value="${document.contents}" escapeXml="false"/>
        </div>
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
    
    <div class="mb-3">
        <label for="approverComment" class="form-label">결재자 코멘트</label>
        <textarea class="form-control" id="approverComment" name="approverComment" rows="3" ${action == 'approved' ? 'readonly' : ''}>${document.approverComment}</textarea>
    </div>
    
    <c:choose>
        <c:when test="${action == 'proceed'}">
            <button type="button" class="btn btn-danger" onclick="cancelDocument()">취소</button>
            <button type="button" class="btn btn-secondary" onclick="window.close()">닫기</button>
        </c:when>
        <c:when test="${action == 'approval'}">
            <button type="button" class="btn btn-primary" onclick="approveDocument()">승인</button>
            <button type="button" class="btn btn-danger" onclick="rejectDocument()">반려</button>
        </c:when>
        <c:otherwise>
            <button type="button" class="btn btn-secondary" onclick="window.close()">닫기</button>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 추가 -->
<script>

	function cancelDocument(){
		if(confirm("취소처리하시겠습니까?")){
			var approverComment = $('#approverComment').val();
		    var documentNo = "${document.documentNo}";
		    
		    $.ajax({
		        url: '<c:url value="/canceld.ex"/>',
		        method: 'POST',
		        data: {
		            documentNo: documentNo,
		            approverComment: approverComment
		        },
		        success: function(response) {
		            alert('문서가 취소되었습니다.');                
		            if (window.opener) {
	                    window.opener.location.reload();
	                }
		            window.close();
		        },
		        error: function(xhr, status, error) {
		            alert('문서 취소 중 오류가 발생했습니다. 오류 코드: ' + xhr.status + ', 메시지: ' + error);
		        }
		    });	
		}
	}

	function approveDocument() {
		if(confirm("승인처리하시겠습니까?")){
			var approverComment = $('#approverComment').val();
		    var documentNo = "${document.documentNo}";
		    
		    $.ajax({
		        url: '<c:url value="/approved.ex"/>',
		        method: 'POST',
		        data: {
		            documentNo: documentNo,
		            approverComment: approverComment
		        },
		        success: function(response) {
		            alert('문서가 승인되었습니다.');                
		            if (window.opener) {
	                    window.opener.location.reload();
	                }
		            window.close();
		        },
		        error: function(xhr, status, error) {
		            alert('문서 승인 중 오류가 발생했습니다. 오류 코드: ' + xhr.status + ', 메시지: ' + error);
		        }
		    });	
		}
	}
	
	function rejectDocument() {
		if(confirm("반려처리하시겠습니까?")){
		    var approverComment = $('#approverComment').val();
		    var documentNo = "${document.documentNo}";
		    
		    $.ajax({
		        url: '<c:url value="/returned.ex"/>',
		        method: 'POST',
		        data: {
		            documentNo: documentNo,
		            approverComment: approverComment
		        },
		        success: function(response) {
		            alert('문서가 반려되었습니다.');                
		            if (window.opener) {
	                    window.opener.location.reload();
	                }
		            window.close();
		        },
		        error: function(xhr, status, error) {
		            alert('문서 반려 중 오류가 발생했습니다. 오류 코드: ' + xhr.status + ', 메시지: ' + error);
		        }
		    });
		}
	}
</script>
</body>
</html>