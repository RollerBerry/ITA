<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách người dùng</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="../../../header.jsp" %>
        <div class="container mt-5">
            <h2 class="mb-4">Danh sách người dùng</h2>

            <form action="ListUser" method="POST" class="mb-4">
                <div class="row">
                    <div class="col-md-3">
                        <label for="department" class="form-label">Chọn phòng ban</label>
                        <select class="form-select" id="department" name="department">
                            <option value="">Tất cả</option>
                            <option value="HR">HR</option>
                            <option value="Development">Development</option>
                            <option value="Marketing">Marketing</option>
                            <option value="Finance">Finance</option>
                            <!-- Thêm các phòng ban khác nếu cần -->
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label for="userName" class="form-label">Tìm theo tên đăng nhập</label>
                        <input type="text" class="form-control" id="userName" name="userName" placeholder="Nhập tên đăng nhập">
                    </div>
                    <div class="col-md-3">
                        <label for="status" class="form-label">Trạng thái</label>
                        <select class="form-select" id="status" name="status">
                            <option value="">Tất cả</option>
                            <option value="1">Active</option>
                            <option value="0">Inactive</option>
                        </select>
                    </div>
                    <div class="col-md-3 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary me-2">Tìm kiếm</button>
                        <a href="adduser" class="btn btn-success">Thêm người dùng mới</a>
                    </div>
                </div>
            </form>


            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>User Name</th>
                        <th>Department</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Action</th>
                        <th>Edit Profile</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty userList}">
                            <c:forEach var="u" items="${userList}">
                                <tr id="user-${u.userId}">
                                    <td>${u.userId}</td>
                                    <td>${u.fullName}</td>
                                    <td>${u.userName}</td>
                                    <td>${u.departmentName}</td>
                                    <td>${u.roleName}</td>
                                    <td id="status-${u.userId}">${u.status ? 'Active' : 'Inactive'}</td>
                                    <td>
                                        <button class="btn ${u.status ? 'btn-danger' : 'btn-success'} toggle-status" 
                                                data-user-id="${u.userId}" 
                                                data-current-status="${u.status ? 1 : 0}">
                                            ${u.status ? 'Inactive' : 'Active'}
                                        </button>
                                    </td>
                                    <td>
                                        <a href="editProfileServlet?id=${u.userId}" class="btn btn-warning">Edit</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8" class="text-center">No user.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
            <a href="home" class="btn btn-secondary mt-3">Back to Home</a>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script>
            $(document).ready(function () {
                // Khi người dùng nhấn nút toggle trạng thái
                $('.toggle-status').on('click', function () {
                    var button = $(this);  // Nút được nhấn
                    var userId = button.data('user-id');  // Lấy userId từ thuộc tính data
                    var currentStatus = button.data('current-status');  // Lấy trạng thái hiện tại

                    // Xác định trạng thái mới
                    var newStatus = currentStatus == 1 ? 0 : 1;

                    // Gửi yêu cầu AJAX để cập nhật trạng thái
                    $.ajax({
                        url: 'ListUser', // URL tới servlet xử lý
                        type: 'POST',
                        data: {
                            userId: userId,
                            status: newStatus
                        },
                        success: function (response) {
                            // Cập nhật giao diện khi thành công
                            var newStatusText = newStatus == 1 ? 'Active' : 'Inactive';
                            var newButtonClass = newStatus == 1 ? 'btn-danger' : 'btn-success';
                            var newButtonText = newStatus == 1 ? 'Inactive' : 'Active';

                            // Cập nhật cột trạng thái
                            $('#status-' + userId).text(newStatusText);

                            // Cập nhật nút toggle
                            button.removeClass('btn-success btn-danger').addClass(newButtonClass);
                            button.text(newButtonText);
                            button.data('current-status', newStatus);
                        },
                        error: function () {
                            alert('Error updating status!');
                        }
                    });
                });
            });
        </script>



    </body>
</html>
