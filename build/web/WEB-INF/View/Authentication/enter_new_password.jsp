<%-- 
    Document   : enter_new_password
    Created on : Oct 28, 2024, 4:47:52 PM
    Author     : Predator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Enter New Password</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">Enter New Password</h2>
            <form action="ResetPassword" method="POST">
                <div class="mb-3">
                    <label for="resetCode" class="form-label">Reset Code</label>
                    <input type="text" class="form-control" id="resetCode" name="resetCode" required>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label">New Password</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                </div>
                <button type="submit" class="btn btn-primary">Reset Password</button>
            </form>
        </div>
    </body>
</html>