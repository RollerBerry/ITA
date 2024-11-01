<%-- 
    Document   : insertRequirement
    Created on : 28 thg 10, 2024, 14:16:28
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Insert Requirement</title>
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
            input[type="text"], select, textarea {
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
            .button-secondary {
                background-color: #6c757d;
                text-align: center;
                display: block;
                width: 100px;
                margin: 20px auto;
                text-decoration: none;
            }
            .button-secondary:hover, .button-primary:hover {
                opacity: 0.9;
            }
        </style>
    </head>
    <body>
        <h2>Add New Requirement</h2>
        <c:if test="${not empty errors}">
            <div style="color: red; border: 1px solid red; padding: 10px; margin-bottom: 20px;">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="insertRequirement" method="post">
            <label for="title">Title:</label>
            <input type="text" name="title" id="title" required><br><br>

            <label for="ownerId">Owner:</label>
            <select name="ownerId" id="ownerId" required>
                <c:forEach var="user" items="${users}">
                    <option value="${user.userId}">${user.fullName}</option>
                </c:forEach>
            </select><br><br>

            <label for="complexityId">Complexity:</label>
            <select name="complexityId" id="complexityId" required>
                <c:forEach var="complexity" items="${complexities}">
                    <option value="${complexity.settingId}">${complexity.name}</option>
                </c:forEach>
            </select><br><br>

            <label for="statusId">Status:</label>
            <select name="statusId" id="statusId" required>
                <c:forEach var="status" items="${statuses}">
                    <option value="${status.settingId}">${status.name}</option>
                </c:forEach>
            </select><br><br>

            <label for="description">Description:</label>
            <textarea name="description" id="description"></textarea><br><br>

            <button type="submit" class="button button-primary">Add Requirement</button>
        </form>
        <br>
        <a href="listRequirement" class="button button-secondary">Back to List</a>
    </body>
</html>


