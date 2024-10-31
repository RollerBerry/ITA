<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm Allocation</title>
    <style>
        /* Your existing CSS styles... */
        body {
            font-family: Times New Roman, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            width: 1000px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        label {
            font-weight: bold;
            color: #555;
        }
        select, input[type="date"], input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        textarea {
            resize: vertical;
        }
        /* Styles for message */
        .message {
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 5px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Add New Allocation</h2>
        
        <c:if test="${not empty sessionScope.message}">
    <div class="message ${sessionScope.messageType}">
        ${sessionScope.message}
    </div>
    <!-- Remove the message from the session after displaying -->
    <c:set var="dummy" value="${sessionScope.message}"/>
    <c:remove var="message" />
    <c:remove var="messageType" />
</c:if>


        <form action="addAllocation" method="post">

            <label for="member_id">Member:</label>
            <select name="member_id" id="member_id" required>
                <option value="">-- Choose Member --</option>
                <c:forEach var="member" items="${users}">
                    <option value="${member.userId}">${member.fullName}</option>
                </c:forEach>
            </select>

            <label for="project_id">Project:</label>
            <select name="project_id" id="project_id" required>
                <option value="">-- Choose Project --</option>
                <c:forEach var="project" items="${projects}">
                    <option value="${project.projectId}">${project.name}</option>
                </c:forEach>
            </select>

            <label for="project_role_id">Role:</label>
            <select name="project_role_id" id="project_role_id" required>
                <option value="">-- Choose Role --</option>
                <c:forEach var="role" items="${settings}">
                    <option value="${role.settingId}">${role.name}</option>
                </c:forEach>
            </select>

            <label for="created_by_id">Creater:</label>
            <select name="created_by_id" id="created_by_id" required>
                <option value="">-- Choose Creater --</option>
                <c:forEach var="creator" items="${users}">
                    <option value="${creator.userId}">${creator.fullName}</option>
                </c:forEach>
            </select>

            <label for="start_date">StartDate:</label>
            <input type="date" id="start_date" name="start_date" required>

            <label for="end_date">EndDate:</label>
            <input type="date" id="end_date" name="end_date">

            <label for="effort_rate">Effort Rate (%):</label>
            <input type="text" id="effort_rate" name="effort_rate" min="0" max="100" required>

            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4" placeholder="Enter Description ..."></textarea>

            <input type="submit" value="Add New Allocation">
        </form>
    </div>
</body>
</html>
