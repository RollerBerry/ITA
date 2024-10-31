<%-- 
    Document   : error.jsp
    Created on : 28 thg 10, 2024, 13:28:59
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        /* Custom CSS cho màn hình lỗi */
        .error-container {
            text-align: center;
            margin-top: 100px;
            color: #dc3545;
        }
        .error-message {
            font-size: 18px;
            font-weight: bold;
            color: #dc3545;
        }
        .button {
            margin: 10px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h2>Error</h2>
        <p class="error-message">An unexpected error has occurred. Please try again later.</p>
        <br>
        <a href="javascript:history.back()" class="button button-secondary">Go Back</a>
        <a href="listRequirement" class="button button-primary">Go to Requirements List</a>
    </div>
</body>
</html>

