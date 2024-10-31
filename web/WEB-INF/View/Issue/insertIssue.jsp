<%-- 
    Document   : insertIssue
    Created on : 28 thg 10, 2024
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Insert Issue</title>
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
        form {
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input[type="text"], input[type="date"], select, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        textarea {
            height: 100px;
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
            margin-top: 10px;
            text-decoration: none;
        }
        .button-primary {
            background-color: #4a90e2;
        }
        .button-secondary {
            background-color: #6c757d;
            display: block;
            width: 150px;
            text-align: center;
            margin: 20px auto;
        }
        .button-primary:hover, .button-secondary:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <h2>Add New Issue</h2>
    <form action="insertIssue" method="post">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required>

        <label for="typeId">Type:</label>
        <select name="typeId" id="typeId" required>
            <c:forEach var="type" items="${types}">
                <option value="${type.settingId}">${type.name}</option>
            </c:forEach>
        </select>

        <label for="reqId">Requirement:</label>
        <select name="reqId" id="reqId" required>
            <c:forEach var="requirement" items="${requirements}">
                <option value="${requirement.reqId}">${requirement.title}</option>
            </c:forEach>
        </select>

        <label for="assignerId">Assigner:</label>
        <select name="assignerId" id="assignerId" required>
            <c:forEach var="user" items="${users}">
                <option value="${user.userId}">${user.fullName}</option>
            </c:forEach>
        </select>

        <label for="assigneeId">Assignee:</label>
        <select name="assigneeId" id="assigneeId" required>
            <c:forEach var="user" items="${users}">
                <option value="${user.userId}">${user.fullName}</option>
            </c:forEach>
        </select>

        <label for="deadline">Deadline:</label>
        <input type="date" name="deadline" id="deadline" required>

        <label for="statusId">Status:</label>
        <select name="statusId" required>
            <option value="1">Pending</option>
            <option value="2">In-progress</option>
            <option value="3">Done</option>
            <option value="4">Canceled</option>
        </select>

        <label for="description">Description:</label>
        <textarea name="description" id="description"></textarea>

        <input type="hidden" name="createdById" value="${sessionScope.currentUser.userId}">

        <button type="submit" class="button button-primary">Add Issue</button>
    </form>
    <a href="listIssue" class="button button-secondary">Back to List</a>
</body>
</html>
