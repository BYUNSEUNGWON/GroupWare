<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Submit Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jodit/3.6.1/jodit.min.css">
    <style>
        .container {
            margin-top: 50px;
        }

        .jodit_wysiwyg {
            min-height: 400px;
        }

        .small-input {
            width: auto;
            display: inline-block;
            text-align: center;
        }

        .approver-group {
            display: flex;
            align-items: center;
        }

        .approver-group .btn {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h3 class="mb-4">양식 작성</h3>
    <form action="/submitForm.ex" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="userSubmitName" class="form-label">상신자</label>
            <input type="text" class="form-control form-control-sm small-input" id="userSubmitName" name="submitter"
                   value="${user.name}" required readonly>
        </div>
        <div class="mb-3 approver-group">
            <label for="approver" class="form-label" style="margin-right: 5px;">결재자</label>
            <input type="text" class="form-control form-control-sm small-input" id="approver" name="approver" required>
            <button type="button" class="btn btn-primary btn-sm" onclick="openApproverPopup()">선택</button>
        </div>
        <div class="mb-3">
            <label for="attachment" class="form-label">첨부 파일</label>
            <input type="file" class="form-control" id="attachment" name="attachments" multiple>
        </div>
        <div class="mb-3">
            <label for="editor" class="form-label">내용</label>
            <textarea id="editor" name="editorContent"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">제출</button>
    </form>
</div>

<!-- Jodit JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jodit/3.6.1/jodit.min.js"></script>
<script>
    function openApproverPopup() {
        window.open('/apprLine.ex', '결재자 선택', 'width=800,height=800,scrollbars=yes');
    }

    function setApprover(name) {
        document.getElementById('approver').value = name;
    }

    document.addEventListener('DOMContentLoaded', function () {
        // Initialize Jodit editor
        const editor = new Jodit('#editor', {
            height: 500,
        });

        // Load formContent into Jodit editor
        const formContent = `<%= request.getAttribute("formContent") %>`;
        editor.value = formContent;

        // Set hidden textarea value on form submit
        document.querySelector('form').onsubmit = function () {
            document.querySelector('#editor').value = editor.value;
        };
    });
</script>
</body>
</html>