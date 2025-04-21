<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Team Memo</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
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
    .editor-container {
        background: white;
        border: 1px solid #ced4da;
        border-radius: 0.25rem;
        height: 400px; /* Adjust this value for editor height */
    }
    .ql-editor {
        height: 100%; /* Ensure the text editor takes full height */
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
            <h4 class="card-title">New TeamBox</h4>
            <form action="/teambox/save.ex" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="title" class="form-label">제목</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>
                <div class="mb-3">
                    <label for="content" class="form-label">본문</label>
                    <div id="editor" class="editor-container"></div>
                    <input type="hidden" id="content" name="content">
                </div>
                <div class="mb-3">
                    <label for="file" class="form-label">첨부파일</label>
                    <input type="file" class="form-control" id="file" name="file">
                </div>
                <button type="button" class="btn btn-primary" onclick="saveContent()">저장</button>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
<script>
    var quill = new Quill('#editor', {
        theme: 'snow',
        placeholder: 'Write your memo here...',
    });

    function saveContent() {
        var content = document.querySelector('#editor .ql-editor').innerHTML;
        document.querySelector('#content').value = content;
        document.forms[0].submit();
    }
</script>
</body>
</html>