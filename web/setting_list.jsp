<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Setting List</title>
    <style>
        table { 
            border-collapse: collapse; 
            width: 100%; 
            margin-top: 20px;
        }
        th, td { 
            border: 1px solid black; 
            padding: 8px; 
            text-align: center; 
        }
        .search-container, .filter-container, .add-container {
            margin: 10px 0;
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .search-container input[type="text"], .add-container input[type="text"] {
            padding: 5px;
            width: 200px;
        }
        .search-container button, .filter-container button, .add-container button {
            padding: 5px 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>Setting List</h1>

    <!-- Nút thêm mới setting -->
    <div class="add-container">
        <form action="AddSettingServlet" method="post">
            <input type="text" name="name" placeholder="Enter new setting name" required />
            <input type="text" name="value" placeholder="Enter value" required />
            <select name="status" required>
                <option value="1">Active</option>
                <option value="0">Inactive</option>
            </select>
            <button type="submit">Add Setting</button>
        </form>
    </div>

    <!-- Search Form -->
    <form action="SettingListServlet" method="GET" class="search-container">
        <input type="text" name="search" value="${param.search}" placeholder="Search by name...">
        <button type="submit">Search</button>
    </form>
    
    <!-- Filter Form -->
    <form action="SettingListServlet" method="GET" class="filter-container">
        <select name="status">
            <option value="">Select Status</option>
            <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
            <option value="Inactive" ${param.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
        </select>
        <button type="submit">Filter</button>
    </form>

    <c:if test="${not empty settings}">
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Value</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="setting" items="${settings}">
                <tr>
                    <td>${setting.settingId}</td>
                    <td>${setting.name}</td>
                    <td>${setting.value}</td>
                    <td>${setting.status}</td>
                    <td>
                        <a href="SettingDetailServlet?id=${setting.settingId}">View</a> | 
                        <a href="UpdateSettingServlet?id=${setting.settingId}">Update</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty settings}">
        <p>No settings found!</p>
    </c:if>
</body>
</html>
