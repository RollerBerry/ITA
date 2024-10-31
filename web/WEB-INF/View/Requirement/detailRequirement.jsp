<%-- 
    Document   : detailRequirement
    Created on : 28 thg 10, 2024, 14:17:04
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Requirement Details</title>
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
        }
        .button-secondary {
            background-color: #6c757d;
            text-align: center;
            display: block;
            width: 100px;
            margin: 20px auto;
        }
        .button-secondary:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <h2>Requirement Details</h2>
    <div class="detail-container">
    <p><strong>Title:</strong> ${requirement.title}</p>
    <p><strong>Owner:</strong> ${requirement.owner.fullName}</p>
    <p><strong>Complexity:</strong> ${requirement.complexity.name}</p>
    <p><strong>Status:</strong> ${requirement.status.name}</p>
    <p><strong>Description:</strong> ${requirement.description}</p>
    <p><strong>Created At:</strong> ${requirement.createdAt}</p>
    <p><strong>Created By:</strong> ${requirement.createdById.fullName}</p>
    <p><strong>Updated At:</strong> ${requirement.updatedAt}</p>
    <p><strong>Updated By:</strong> ${requirement.updatedById.fullName}</p>
    </div>>
    <a href="listRequirement" class="button button-secondary">Back to List</a>
</body>
</html>


