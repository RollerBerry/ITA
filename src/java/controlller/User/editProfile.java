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
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author Predator
 */
public class editProfile extends HttpServlet {



    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/View/User/edit.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        System.out.println(user);
        UserDAO dao = new UserDAO();
        boolean isUpdated = dao.updateUserProfile(userId, fullName, userName, email);

        if (isUpdated) {
            user.setFullName(fullName);
            user.setUserName(userName);
            user.setEmail(email);
            request.getSession().setAttribute("user", user);
            response.sendRedirect("home");
        } else {
            request.setAttribute("error", "Cập nhật thông tin không thành công.");
            request.getRequestDispatcher("/WEB-NF/View/User/edit.jsp").forward(request, response);
        }
    }


}
