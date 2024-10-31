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
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            text-align: center;
        }
        .error-container {
            padding: 20px;
            border: 1px solid #f5c6cb;
            border-radius: 8px;
            background-color: #f8d7da;
            color: #721c24;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }
        h2 {
            color: #d9534f;
        }
        .error-message {
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
            margin: 5px;
        }
        .button-primary {
            background-color: #4a90e2;
        }
        .button-secondary {
            background-color: #6c757d;
        }
        .button-primary:hover, .button-secondary:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h2>Error</h2>
        <p class="error-message">An unexpected error has occurred. Please try again later.</p>
        <br>
        <a href="javascript:history.back()" class="button button-secondary">Go Back</a>
        <a href="listIssue" class="button button-primary">Go to Requirements List</a>
    </div>
</body>
</html>
