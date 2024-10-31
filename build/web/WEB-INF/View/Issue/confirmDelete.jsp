<%-- 
    Document   : confirmDelete
    Created on : 28 thg 10, 2024, 15:15:12
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Confirm Delete Issue</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
        }
        .confirm-container {
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            margin-top: 20px;
        }
        h2 {
            color: #d9534f;
        }
        p {
            font-size: 16px;
            margin: 10px 0;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            font-weight: bold;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            margin: 5px;
        }
        .button-danger {
            background-color: #d9534f;
        }
        .button-secondary {
            background-color: #6c757d;
        }
        .button-danger:hover, .button-secondary:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <div class="confirm-container">
        <h2>Confirm Deletion</h2>
        <p>Are you sure you want to delete the following issue?</p>
        <p><strong>Title:</strong> ${issue.title}</p>
        <p><strong>Description:</strong> ${issue.description}</p>
        <p><strong>Assigned To:</strong> ${issue.assigneeId.fullName}</p>
        <p><strong>Deadline:</strong> <fmt:formatDate value="${issue.deadline}" pattern="dd-MM-yyyy" /></p>

        <form action="deleteIssue" method="post">
            <input type="hidden" name="issueId" value="${issue.issueId}" />
            <button type="submit" class="button button-danger">Yes, Delete</button>
            <a href="listIssue" class="button button-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>
