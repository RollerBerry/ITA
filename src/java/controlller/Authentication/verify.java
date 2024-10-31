/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller.Authentication;

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
public class verify extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/View/Authentication/verify.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String inputCode = request.getParameter("verificationCode");
        String generatedCode = (String) request.getSession().getAttribute("verificationCode");

        if (inputCode != null && inputCode.equals(generatedCode)) {
            response.sendRedirect("home"); 
        } else {
            request.setAttribute("error", "Mã xác nhận không chính xác. Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/View/Authentication/verify.jsp").forward(request, response);
        }
    }


}
