<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Setting</title>
</head>
<body>
    <h1>Add New Setting</h1>

    <form action="AddSettingServlet" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="value">Value:</label>
        <input type="text" id="value" name="value" required><br><br>

        <label for="type">Type ID:</label>
        <input type="number" id="type" name="type" required><br><br>

        <label for="priority">Priority:</label>
        <input type="number" id="priority" name="priority" required><br><br>

        <label for="status">Status:</label>
        <select id="status" name="status" required>
            <option value="1">Active</option>
            <option value="0">Inactive</option>
        </select><br><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description"></textarea><br><br>

        <button type="submit">Add Setting</button>
    </form>

    <a href="SettingListServlet">Back to Setting List</a>
</body>
</html>
