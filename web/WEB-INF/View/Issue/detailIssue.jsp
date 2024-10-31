<%-- 
    Document   : detailIssue
    Created on : 28 thg 10, 2024, 15:45:00
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Issue Details</title>    
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
        }
        h2 {
            color: #4a90e2;
            text-align: center;
        }
        .detail-container {
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }
        .detail-container p {
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
            text-decoration: none;
            cursor: pointer;
            background-color: #6c757d;
            text-align: center;
            display: block;
            width: 100px;
            margin: 20px auto;
        }
        .button:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <h2>Issue Details</h2>
    <div class="detail-container">
        <p><strong>Title:</strong> ${issue.title}</p>
        <p><strong>Type:</strong> ${issue.typeId.name}</p>
        <p><strong>Requirement:</strong> ${issue.reqId.title}</p>
        <p><strong>Deadline:</strong> ${issue.deadline}</p>
        <p><strong>Status:</strong> 
            <c:choose>
                <c:when test="${issue.statusId == 1}">Pending</c:when>
                <c:when test="${issue.statusId == 2}">In-progress</c:when>
                <c:when test="${issue.statusId == 3}">Done</c:when>
                <c:when test="${issue.statusId == 4}">Canceled</c:when>
                <c:otherwise>Unknown</c:otherwise>
            </c:choose>
        </p>
        <p><strong>Assigner:</strong> ${issue.assignerId.fullName}</p>
        <p><strong>Assignee:</strong> ${issue.assigneeId.fullName}</p>
        <p><strong>Status Date:</strong> ${issue.statusDate}</p>
        <p><strong>Description:</strong> ${issue.description}</p>
        <p><strong>Created At:</strong> ${issue.createdAt}</p>
        <p><strong>Created By:</strong> ${issue.createdById.fullName}</p>
        <p><strong>Updated At:</strong> ${issue.updatedAt}</p>
        <p><strong>Updated By:</strong> ${issue.updatedById.fullName}</p>
    </div>
    <a href="listIssue" class="button">Back to List</a>
</body>
</html>
