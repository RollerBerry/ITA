<%-- 
    Document   : confirmDelete
    Created on : 28 thg 10, 2024, 14:55:12
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Confirm Delete Requirement</title>
    <style>
        .confirm-container {
            width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            font-family: Arial, sans-serif;
        }
        h2 {
            color: #d9534f;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            font-size: 16px;
            font-weight: bold;
            text-decoration: none;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .button-danger {
            background-color: #d9534f;
        }
        .button-secondary {
            background-color: #6c757d;
        }
        .button-secondary:hover, .button-danger:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <div class="confirm-container">
        <h2>Confirm Deletion</h2>
        <p>Are you sure you want to delete the following requirement?</p>
        <p><strong>Title:</strong> ${requirement.title}</p>
        <p><strong>Description:</strong> ${requirement.description}</p>
        <p><strong>Owner:</strong> ${requirement.owner.fullName}</p>

        <form action="deleteRequirement" method="post">
            <input type="hidden" name="reqId" value="${requirement.reqId}" />
            <button type="submit" class="button button-danger">Yes, Delete</button>
            <button type="button" class="button button-secondary" onclick="window.location.href='listRequirement'">Cancel</button>
        </form>
    </div>
</body>
</html>
