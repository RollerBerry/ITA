package controlller.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import Service.Library;
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
public class AddNewUser extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/View/User/addNewUser.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");

        // Không cần lấy password từ request nữa
        String password = Library.generateSixDigitCode(); // Tạo mật khẩu ngẫu nhiên
        int role = Integer.parseInt(request.getParameter("role"));
        int dep_id = Integer.parseInt(request.getParameter("department"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        User newUser = new User(fullName, userName, email, password, role, dep_id, status);

        UserDAO userDAO = new UserDAO();
        boolean result = userDAO.addUser(newUser);

        if (result) {
            // Thông báo mật khẩu đã tạo cho Admin (có thể ghi vào log hoặc gửi qua email)
            System.out.println("Generated Password for user " + userName + ": " + password);
            response.sendRedirect("ListUser");
        } else {
            request.setAttribute("errorMessage", "Error adding user");
            request.getRequestDispatcher("/WEB-INF/View/User/addNewUser.jsp").forward(request, response);
        }
    }


}
