<%-- 
    Document   : Register
    Created on : Oct 22, 2024, 7:42:34 AM
    Author     : Predator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .card {
                border-radius: 1rem;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .form-label {
                font-weight: bold;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card p-4">
                        <h2 class="text-center mb-4">Đăng ký</h2>

                        <% if (request.getAttribute("errorMessages") != null) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= request.getAttribute("errorMessages") %>
                        </div>
                        <% } %>

                        <form action="Register" method="POST" onsubmit="return validateForm()">
                            <div class="mb-3">
                                <label for="fullname" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="fullname" name="fullname" required>
                            </div>
                            <div class="mb-3">
                                <label for="userName" class="form-label">Tên đăng nhập</label>
                                <input type="text" class="form-control" id="userName" name="userName" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Mật khẩu</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>

                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Nhập lại mật khẩu</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            </div>

                            <div id="passwordError" class="text-danger mb-3"></div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-block">Đăng ký</button>
                            </div>
                        </form>

                        <div class="mt-3 text-center">
                            <p>Đã có tài khoản? <a href="login">Đăng nhập</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

        <script>
                            function validateForm() {
                                var userName = document.getElementById("userName").value;
                                var password = document.getElementById("password").value;
                                var confirmPassword = document.getElementById("confirmPassword").value;
                                var errorDiv = document.getElementById("passwordError");

                                if (userName.trim() === "" || password.trim() === "") {
                                    errorDiv.innerHTML = "Tên đăng nhập và mật khẩu không được để trống.";
                                    clearForm();
                                    return false;
                                }

                                if (userName.includes(" ") || password.includes(" ")) {
                                    errorDiv.innerHTML = "Tên đăng nhập và mật khẩu không được chứa dấu cách.";
                                    clearForm();
                                    return false;
                                }

                                if (password !== confirmPassword) {
                                    errorDiv.innerHTML = "Mật khẩu và xác nhận mật khẩu không khớp.";
                                    clearForm();
                                    return false;
                                }

                                errorDiv.innerHTML = ""; // Xóa thông báo lỗi nếu mọi thứ hợp lệ
                                return true;
                            }

                            function clearForm() {
                                document.getElementById("fullname").value = "";
                                document.getElementById("userName").value = "";
                                document.getElementById("email").value = "";
                                document.getElementById("password").value = "";
                                document.getElementById("confirmPassword").value = "";
                            }
        </script>
    </body>
</html>