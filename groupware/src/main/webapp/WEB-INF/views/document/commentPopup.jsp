<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>결재 요청 코멘트</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 100%;
            padding: 20px;
        }
        .modal-body {
            padding: 10px;
        }
        #comment {
            resize: none;
            height: 200px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="modal-body">
        <h4>결재 요청 의견</h4>
        <div class="mb-3">
            <label for="submitterName" class="form-label">상신자</label>
            <input type="text" class="form-control small-input" id="submitterName" readonly
                   value="${submitter} (${submitterId})">
        </div>
        <div class="mb-3">
            <label for="comment" class="form-label">코멘트</label>
            <textarea class="form-control" id="comment" rows="4" placeholder="코멘트를 입력해주세요"></textarea>
        </div>
        <div class="d-flex justify-content-end">
        <button class="btn btn-primary me-2" onclick="submitComment()">확인</button>
        <button class="btn btn-secondary" onclick="closePopup()">취소</button>
        </div>
    </div>
</div>

<script>
    function submitComment() {
        var comment = document.getElementById('comment').value;
        if (window.opener && !window.opener.closed) {
            window.opener.submitWithComment(comment);
        }
        window.close();
    }

    function closePopup() {
        window.close();
    }
</script>
</body>
</html>