<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mini Project Management System</title>
        <script src="https://kit.fontawesome.com/1d2bfd0d3f.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap">
        <link href="css/header.css" rel="stylesheet" type="text/css"/>
        <style>
            body {
                font-family: 'Roboto', sans-serif;
            }
            .header {
                background-color: #343a40;
                color: white;
            }
            .header h4 {
                margin: 0;
            }
            .dropdown-menu {
                background-color: #495057;
            }
            .dropdown-item {
                color: white;
            }
            .dropdown-item:hover {
                background-color: #6c757d;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid sticky-top py-2 px-4 header">
            <h4><a class="link-underline-dark" href="home" style="color: white;">Mini Project Management System</a></h4>

            <div class="dropdown d-inline position-absolute top-50 end-0 translate-middle-y">
                <a class="link-underline-dark" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false" style="color: white;">
                    <i class="fa-regular fa-circle-user fa-xl"></i> ${sessionScope.user.fullName}
                </a>

                <ul class="dropdown-menu">
                    <c:if test="${sessionScope.user != null}">
                        <c:choose>
                            <c:when test="${sessionScope.user.roleId == 5}">
                                <li><a class="dropdown-item" href="editProfile">Profile</a></li>
                                <li><a class="dropdown-item" href="ListUser">Manage User</a></li>
                                <li><a class="dropdown-item" href="listRequirement">Requirement List</a></li>
                                <li><a class="dropdown-item" href="listIssue">Issue List</a></li>
                                <li><a class="dropdown-item" href="application">Allocation</a></li>
                                <li><a class="dropdown-item" href="SettingDetailServlet">View-Detail</a></li>
                                <li><a class="dropdown-item" href="Logout">Logout</a></li>
                                
                                </c:when>

                            <c:when test="${sessionScope.user.roleId == 6}">
                                <li><a class="dropdown-item" href="editProfile">Profile</a></li>
                                <li><a class="dropdown-item" href="listIssue">Issue List</a></li>
                                <li><a class="dropdown-item" href="Logout">Logout</a></li>
                                </c:when>

                            <c:when test="${sessionScope.user.roleId == 7}">
                                <li><a class="dropdown-item" href="editProfile">Profile</a></li>
                                <li><a class="dropdown-item" href="Logout">Logout</a></li>
                                </c:when>
                                <c:when test="${sessionScope.user.roleId == 8}">
                                <li><a class="dropdown-item" href="editProfile">Profile</a></li>
                                <li><a class="dropdown-item" href="listRequirement">Requirement List</a></li>
                                <li><a class="dropdown-item" href="Logout">Logout</a></li>
                                </c:when>
                                <c:when test="${sessionScope.user.roleId == 9}">
                                <li><a class="dropdown-item" href="editProfile">Profile</a></li>
                                <li><a class="dropdown-item" href="application">Allocation</a></li>
                                <li><a class="dropdown-item" href="Logout">Logout</a></li>
                                </c:when>
                            </c:choose>
                        </c:if>
                </ul>
            </div>
        </div>
    </body>
</html>