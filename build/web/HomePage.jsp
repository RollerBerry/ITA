<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mini Project Management System</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            .notlogin {
                min-height: 100vh;
                background-image: url("https://www.baypmtech.com/wp-content/uploads/2014/01/847x480-14-IBM-023_illustration_analytics_3.png");
            }
            .overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
            }
            .sec {
                min-height: 100vh;
            }
            .sidebar {
                height: 100vh; /* Chiều cao của sidebar */
                position: fixed; /* Giữ sidebar cố định */
                top: 0; /* Đặt ở phía trên cùng */
                left: 0; /* Đặt ở phía bên trái */
                width: 250px; /* Chiều rộng của sidebar */
                background-color: #343a40; /* Màu nền của sidebar */
                padding: 20px; /* Padding bên trong sidebar */
                color: white; /* Màu chữ trong sidebar */
                display: none; /* Bắt đầu ẩn sidebar */
            }
            .sidebar a {
                color: white; /* Màu chữ các liên kết */
                text-decoration: none; /* Bỏ gạch chân */
            }
            .sidebar a:hover {
                text-decoration: underline; /* Hiệu ứng khi hover */
            }
            .content {
                margin-left: 270px; /* Đẩy nội dung sang phải để không bị che bởi sidebar */
                padding: 20px; /* Padding cho nội dung */
            }
            .toggle-button {
                margin: 10px; /* Khoảng cách cho nút toggle */
            }
            .chart-container {
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .chart-box {
                width: 45%; /* Đặt chiều rộng cho mỗi biểu đồ để vừa trên một hàng */
                padding: 20px;
            }
        </style>
        <script>
            function toggleSidebar() {
            const sidebar = document.getElementById("sidebar");
            if (sidebar.style.display === "none" || sidebar.style.display === "") {
            sidebar.style.display = "block"; // Hiện sidebar
            } else {
            sidebar.style.display = "none"; // Ẩn sidebar
            }
            }
        </script>
    </head>
    <body>
        <section class="sec">
            <c:if test="${sessionScope.user == null}">
                <div class="container-fluid notlogin">
                    <div class="overlay d-flex justify-content-center align-items-center" style="flex-direction: column;">
                        <div>
                            <p style="color: white; font-size: 50px">LOGIN TO USE</p><br>
                        </div>
                        <div>
                            <a type="button" class="btn btn-success" href="login">Login</a>    
                            <a type="button" class="btn btn-warning" href="Register">Register</a>    
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <%@include file="header.jsp" %>
                <c:choose>
                    <c:when test="${sessionScope.user.roleId == 5}">

                        <div class="chart-container row">
                            <div class="chart-box col">
                                <h3 class="text-center">Số lượng dự án theo trạng thái</h3>
                                <canvas id="projectStatusChart"></canvas>
                            </div>
                            <div class="chart-box col">
                                <h3 class="text-center">Số lượng vấn đề theo trạng thái</h3>
                                <canvas id="issueStatusChart"></canvas>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 6}">
                        <div class="content">
                            <h2>Welcome Staff</h2>
                            <p>As a Staff, you can view and manage issues.</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 7}">
                        <div class="content">
                            <h2>Welcome Setting Admin</h2>
                            <p>As a Setting Admin, you can manage settings.</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 8}">
                        <div class="content">
                            <h2>Welcome Project Leader</h2>
                            <p>As a Project Leader, you can view requirements.</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.roleId == 9}">
                        <div class="content">
                            <h2>Welcome Department Leader</h2>
                            <p>As a Department Leader, you can view allocations.</p>
                        </div>
                    </c:when>
                </c:choose>
            </c:if>
        </section>
        <%@include file="footer.html" %>


        <script>
            const projectStatusData = {
            labels: [
            <c:forEach var="entry" items="${projectStatusData}" varStatus="status">
            "${entry.key}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ],
                    datasets: [{
                    label: 'Số lượng dự án',
                            data: [
            <c:forEach var="entry" items="${projectStatusData}" varStatus="status">
                ${entry.value}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                            ],
                            backgroundColor: 'rgba(54, 162, 235, 0.6)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                    }]
            };
            const issueStatusData = {
            labels: [
            <c:forEach var="entry" items="${issueStatusData}" varStatus="status">
            "${entry.key}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ],
                    datasets: [{
                    label: 'Số lượng vấn đề',
                            data: [
            <c:forEach var="entry" items="${issueStatusData}" varStatus="status">
                ${entry.value}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                            ],
                            backgroundColor: 'rgba(75, 192, 192, 0.6)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                    }]
            };
            // Render project status chart
            new Chart(document.getElementById('projectStatusChart').getContext('2d'), {
            type: 'bar',
                    data: projectStatusData,
                    options: {
                    responsive: true,
                            scales: {
                            y: { beginAtZero: true, title: { display: true, text: 'Số lượng dự án' } }
                            }
                    }
            });
            // Render issue status chart
            new Chart(document.getElementById('issueStatusChart').getContext('2d'), {
            type: 'bar',
                    data: issueStatusData,
                    options: {
                    responsive: true,
                            scales: {
                            y: { beginAtZero: true, title: { display: true, text: 'Số lượng vấn đề' } }
                            }
                    }
            });
        </script>
    </body>
</html>