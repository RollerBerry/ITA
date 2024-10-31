/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller.User;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Predator
 */
public class editProfileServlet extends HttpServlet {

    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id")); // Lấy userId từ URL
        UserDAO dao = new UserDAO();
        User user = dao.getUserById(userId); // Vẫn cần lấy thông tin người dùng
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/View/User/EditProfile.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        int role = Integer.parseInt(request.getParameter("role"));
        int department = Integer.parseInt(request.getParameter("department"));
        boolean status = request.getParameter("status") != null;
        
        UserDAO dao = new UserDAO();

        boolean updated = dao.updateUser(userId, fullName, userName, email, role, department, status);
        if (updated == false) {
            response.sendRedirect("ListUser");
        } else {
            // Nếu cập nhật không thành công, giữ lại id và thông tin người dùng để hiển thị
            User user = dao.getUserById(userId); // Lấy lại thông tin người dùng
            request.setAttribute("user", user); // Đặt thông tin người dùng vào request
            request.setAttribute("errorMessage", "Error updating user profile");

            request.getRequestDispatcher("/WEB-INF/View/User/EditProfile.jsp").forward(request, response);
        }
        System.out.println(updated);
    }


}
