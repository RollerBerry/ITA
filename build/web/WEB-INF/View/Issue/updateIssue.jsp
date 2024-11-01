<%-- 
    Document   : updateIssue
    Created on : 28 thg 10, 2024
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Update Issue</title>
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
            }
            .button-primary {
                background-color: #4a90e2;
            }
            .button-primary:hover {
                opacity: 0.9;
            }
        </style>
    </head>
    <body>
        <h2>Update Issue</h2>
        <c:if test="${not empty errors}">
            <div style="color: red; border: 1px solid red; padding: 10px; margin-bottom: 20px;">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="updateIssue" method="post">
            <input type="hidden" name="issueId" value="${issue.issueId}" />

            <label for="title">Title:</label>
            <input type="text" name="title" id="title" value="${issue.title}" required>

            <label for="description">Description:</label>
            <textarea name="description" id="description">${issue.description}</textarea>

            <!-- Dropdown for Type Selection -->
            <label for="typeId">Type:</label>
            <select name="typeId" id="typeId">
                <c:forEach var="type" items="${types}">
                    <option value="${type.settingId}" ${type.settingId == issue.typeId.settingId ? 'selected' : ''}>${type.name}</option>
                </c:forEach>
            </select>

            <!-- Dropdown for Requirement Selection -->
            <label for="reqId">Requirement:</label>
            <select name="reqId" id="reqId">
                <c:forEach var="req" items="${requirements}">
                    <option value="${req.reqId}" ${req.reqId == issue.reqId.reqId ? 'selected' : ''}>${req.title}</option>
                </c:forEach>
            </select>

            <!-- Dropdown for Assigner Selection -->
            <label for="assignerId">Assigner:</label>
            <select name="assignerId" id="assignerId">
                <c:forEach var="user" items="${users}">
                    <option value="${user.userId}" ${user.userId == issue.assignerId.userId ? 'selected' : ''}>${user.fullName}</option>
                </c:forEach>
            </select>

            <!-- Dropdown for Assignee Selection -->
            <label for="assigneeId">Assignee:</label>
            <select name="assigneeId" id="assigneeId">
                <c:forEach var="user" items="${users}">
                    <option value="${user.userId}" ${user.userId == issue.assigneeId.userId ? 'selected' : ''}>${user.fullName}</option>
                </c:forEach>
            </select>

            <!-- Dropdown for Status Selection -->
            <label for="statusId">Status:</label>
            <select name="statusId" required>
                <option value="1" <c:if test="${issue.statusId == 1}">selected</c:if>>Pending</option>
                <option value="2" <c:if test="${issue.statusId == 2}">selected</c:if>>In-progress</option>
                <option value="3" <c:if test="${issue.statusId == 3}">selected</c:if>>Done</option>
                <option value="4" <c:if test="${issue.statusId == 4}">selected</c:if>>Canceled</option>
                </select>

                <!-- Input for Deadline -->
                <label for="deadline">Deadline:</label>
                <input type="date" name="deadline" id="deadline" value="${issue.deadline}" required>

            <button type="submit" class="button button-primary">Update Issue</button>
        </form>
    </body>
</html>
