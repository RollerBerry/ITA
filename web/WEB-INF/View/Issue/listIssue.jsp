<%-- 
    Document   : listIssue
    Created on : 28 thg 10, 2024, 14:13:12
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of Issues</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        h2 {
            color: #4a90e2;
            text-align: center;
        }
        form {
            margin: 20px 0;
        }
        label {
            font-weight: bold;
            margin-right: 10px;
        }
        input[type="text"], select {
            padding: 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-right: 10px;
        }
        .button {
            display: inline-block;
            padding: 8px 12px;
            font-size: 14px;
            font-weight: bold;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .button-primary {
            background-color: #4a90e2;
        }
        .button-secondary {
            background-color: #6c757d;
        }
        .button-danger {
            background-color: #d9534f;
        }
        .button-warning {
            background-color: #f0ad4e;
        }
        .button:hover {
            opacity: 0.9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
    <h2>List of Issues</h2>

    <!-- Search Form -->
    <form action="searchIssue" method="get">
        <label for="searchTitle">Search by Title:</label>
        <input type="text" name="title" id="searchTitle" placeholder="Enter title to search" />
        <button type="submit" class="button button-primary">Search</button>
    </form>

    <!-- Filter Form -->
    <form action="filterIssue" method="get">
        <!-- Filter by Type -->
        <label for="typeId">Filter by Type:</label>
        <select name="typeName" id="typeId">
            <option value="" ${selectedTypeName == null ? 'selected' : ''}>All</option>
            <c:forEach var="type" items="${types}">
                <option value="${type.name}" ${type.name == selectedTypeName ? 'selected' : ''}>${type.name}</option>
            </c:forEach>
        </select>

        <!-- Filter by Status -->
        <label for="statusId">Filter by Status:</label>
        <select name="statusId" id="statusId">
            <option value="" ${selectedStatus == null ? 'selected' : ''}>All</option>
            <option value="1" <c:if test="${selectedStatus == 1}">selected</c:if>>Pending</option>
            <option value="2" <c:if test="${selectedStatus == 2}">selected</c:if>>In-progress</option>
            <option value="3" <c:if test="${selectedStatus == 3}">selected</c:if>>Done</option>
            <option value="4" <c:if test="${selectedStatus == 4}">selected</c:if>>Canceled</option>
        </select>

        <!-- Filter by Requirement -->
        <label for="reqId">Filter by Requirement:</label>
        <select name="reqTitle" id="reqId">
            <option value="" ${selectedReqTitle == null ? 'selected' : ''}>All</option>
            <c:forEach var="requirement" items="${requirements}">
                <option value="${requirement.title}" ${requirement.title == selectedReqTitle ? 'selected' : ''}>${requirement.title}</option>
            </c:forEach>
        </select>

        <button type="submit" class="button button-warning">Filter</button>
    </form>

    <br>

    <!-- Table of Issues -->
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Type</th>
            <th>Requirement</th>
            <th>Deadline</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="issue" items="${issues}">
            <tr>
                <td>${issue.issueId}</td>
                <td>${issue.title}</td>
                <td>${issue.typeId.name}</td>
                <td>${issue.reqId.title}</td>
                <td>${issue.deadline}</td>
                <td>
                    <c:choose>
                        <c:when test="${issue.statusId == 1}">Pending</c:when>
                        <c:when test="${issue.statusId == 2}">In-progress</c:when>
                        <c:when test="${issue.statusId == 3}">Done</c:when>
                        <c:when test="${issue.statusId == 4}">Cancelled</c:when>
                        <c:otherwise>Unknown</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form action="detailIssue" method="get" style="display:inline;">
                        <input type="hidden" name="issueId" value="${issue.issueId}" />
                        <button type="submit" class="button button-secondary">View</button>
                    </form>
                    <form action="updateIssue" method="get" style="display:inline;">
                        <input type="hidden" name="issueId" value="${issue.issueId}" />
                        <button type="submit" class="button button-primary">Edit</button>
                    </form>
                    <form action="deleteIssue" method="post" style="display:inline;">
                        <input type="hidden" name="issueId" value="${issue.issueId}" />
                        <button type="submit" class="button button-danger" onclick="return confirm('Are you sure you want to delete this issue?')">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <a href="insertIssue" class="button button-primary">Add New Issue</a>
</body>
</html>
