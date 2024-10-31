<%-- 
    Document   : verify.jsp
    Created on : Oct 27, 2024, 2:40:03 PM
    Author     : Predator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xác nhận tài khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-5">
            <h2 class="text-center">Nhập mã xác nhận</h2>
            <form action="verify" method="POST">
                <div class="mb-3">
                    <label for="verificationCode" class="form-label">Mã xác nhận</label>
                    <input type="text" class="form-control" id="verificationCode" name="verificationCode" required>
                </div>
                <button type="submit" class="btn btn-primary">Xác nhận</button>
                <div class="mt-3 text-center">
                    <p><a href="Register">Quay lại trang đăng kí</a></p>
                </div>
            </form>
            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>
        </div>
    </body>
</html>