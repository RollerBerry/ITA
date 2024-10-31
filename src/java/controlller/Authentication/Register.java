/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller.Authentication;

import Service.EmailService;
import Service.Library;
import Service.Service;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.User;

/**
 *
 * @author Predator
 */
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/View/Authentication/Register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        Service service = new Service();
        List<String> validationErrors = service.validateInputObject(email, userName, fullname);

        if (!validationErrors.isEmpty()) {
            // Nếu có lỗi, gửi các lỗi đến trang đăng ký và dừng xử lý
            request.setAttribute("errorMessages", validationErrors);
            request.getRequestDispatcher("/WEB-INF/View/Authentication/Register.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        User user = new User(fullname, userName, email, password);
        boolean success = dao.register(user);

        if (success) {

            String code = Library.generateSixDigitCode();

            String subject = "Mã xác nhận đăng ký tài khoản";
            String body = "Mã xác nhận của bạn là: " + code;
            EmailService.sendEmail(email, subject, body);

            // Lưu mã xác nhận vào session hoặc cơ sở dữ liệu để xác thực sau này
            request.getSession().setAttribute("verificationCode", code);

            response.sendRedirect("verify");
        } else {
            request.setAttribute("error", "Đăng ký không thành công. Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/View/Authentication/Register.jsp").forward(request, response);
        }
    }

}
