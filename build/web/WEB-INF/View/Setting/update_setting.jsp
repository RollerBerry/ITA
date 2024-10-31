<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Setting</title>
    <style>
        /* General styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9fc;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* Container styling */
        .container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: left;
        }

        h1 {
            color: #4CAF50;
            text-align: center;
        }

        /* Form and input styling */
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="text"], input[type="number"], select, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
        }

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px;
            margin-top: 15px;
            border-radius: 4px;
            width: 100%;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }

        /* Link styling */
        a {
            display: block;
            margin-top: 15px;
            text-align: center;
            color: #4CAF50;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Update Setting</h1>

        <!-- Form for updating the setting -->
        <form action="UpdateSettingServlet" method="post">
            <input type="hidden" name="id" value="${setting.settingId}">

            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${setting.name}" required>

            <label for="value">Value:</label>
            <input type="text" id="value" name="value" value="${setting.value}" required>

            <label for="type">Type ID:</label>
            <input type="number" id="type" name="type" value="${setting.typeId}" required>

            <label for="priority">Priority:</label>
            <input type="number" id="priority" name="priority" value="${setting.priority}" required>

            <label for="status">Status:</label>
            <select id="status" name="status" required>
                <option value="1" ${setting.status == 'Active' ? 'selected' : ''}>Active</option>
                <option value="0" ${setting.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
            </select>

            <label for="description">Description:</label>
            <textarea id="description" name="description">${setting.description}</textarea>

            <button type="submit">Update Setting</button>
        </form>

        <a href="SettingListServlet">Back to Setting List</a>
    </div>
</body>
</html>
