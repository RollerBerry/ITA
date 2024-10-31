<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Admin</title>
    <style>
        /* General body styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9fc;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
        }

        /* Header styling */
        h1 {
            color: #4CAF50;
            text-align: center;
            margin-top: 20px;
        }

        /* Table styling */
        table {
            border-collapse: collapse;
            width: 60%;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        th {
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            text-align: left;
        }
        td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        /* Error message styling */
        .error {
            color: red;
            text-align: center;
            font-weight: bold;
            margin-top: 20px;
        }

        /* Link styling */
        a {
            color: #4CAF50;
            text-decoration: none;
            font-weight: bold;
            margin-top: 20px;
            font-size: 16px;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>Welcome Admin</h1>

    <c:if test="${not empty setting}">
        <table>
            <tr>
                <th>ID</th>
                <td>${setting.settingId}</td>
            </tr>
            <tr>
                <th>Name</th>
                <td>${setting.name}</td>
            </tr>
            <tr>
                <th>Value</th>
                <td>${setting.value}</td>
            </tr>
            <tr>
                <th>Type</th>
                <td>${setting.typeId}</td>
            </tr>
            <tr>
                <th>Priority</th>
                <td>${setting.priority}</td>
            </tr>
            <tr>
                <th>Status</th>
                <td>${setting.status}</td>
            </tr>
            <tr>
                <th>Description</th>
                <td>${setting.description}</td>
            </tr>
        </table>
    </c:if>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <a href="SettingListServlet">Go to Setting List</a>
</body>
</html>
