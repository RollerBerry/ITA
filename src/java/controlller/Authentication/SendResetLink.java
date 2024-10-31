/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller.Authentication;

import Service.EmailService;
import Service.Library;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Predator
 */
public class SendResetLink extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendResetLink</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendResetLink at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/View/Authentication/reset_password.jsp").forward(request, response);
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
        String email = request.getParameter("email");

        // Kiểm tra email có tồn tại trong cơ sở dữ liệu không
        UserDAO dao = new UserDAO();
        if (!dao.emailExists(email)) {
            request.setAttribute("error", "Email không tồn tại.");
            request.getRequestDispatcher("/View/Authentication/reset_password.jsp").forward(request, response);
            return;
        }

        // Tạo mã xác nhận
        String resetCode = Library.generateSixDigitCode();

        // Gửi email chứa mã xác nhận
        String subject = "Reset Password";
        String body = "Mã xác nhận của bạn là: " + resetCode;
        EmailService.sendEmail(email, subject, body);

        // Lưu mã xác nhận vào session hoặc cơ sở dữ liệu để xác thực sau này
        request.getSession().setAttribute("resetCode", resetCode);
        request.getSession().setAttribute("email", email);

        // Chuyển hướng đến trang nhập mật khẩu mới
        response.sendRedirect("ResetPassword");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
