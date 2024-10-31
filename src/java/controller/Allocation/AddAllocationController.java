/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Allocation;

import dal.ApplicationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Project;
import model.Setting;
import model.User;

/**
 *
 * @author Admin
 */
public class AddAllocationController extends HttpServlet {

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
        ApplicationDAO applicationDAO = new ApplicationDAO();
        List<User> users = applicationDAO.getUserIdAndFullName();
        List<Project> projects = applicationDAO.getAllProjectIdsAndNames();
        List<Setting> settings = applicationDAO.getAllSettingIdsAndNames();

        request.setAttribute("users", users);
        request.setAttribute("projects", projects);
        request.setAttribute("settings", settings);

        request.getRequestDispatcher("addAllocation.jsp").forward(request, response);
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
        processRequest(request, response);
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
        int memberId = Integer.parseInt(request.getParameter("member_id"));
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        int projectRoleId = Integer.parseInt(request.getParameter("project_role_id"));
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        double effortRate = Double.parseDouble(request.getParameter("effort_rate"));
        String description = request.getParameter("description");
        int createdById = Integer.parseInt(request.getParameter("created_by_id"));

        java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date sqlEndDate = (endDate != null && !endDate.isEmpty()) ? java.sql.Date.valueOf(endDate) : null;

        ApplicationDAO applicationDAO = new ApplicationDAO();
        boolean isInserted = applicationDAO.insertAllocation(memberId, projectId, projectRoleId,
                sqlStartDate, sqlEndDate,
                effortRate, description, createdById);

        if (isInserted) {
        request.getSession().setAttribute("message", "Allocation added successfully!");
        request.getSession().setAttribute("messageType", "success");
        response.sendRedirect("application");
        } else {
            request.setAttribute("message", "Failed to add allocation!");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("addAllocation.jsp").forward(request, response);

        }


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
