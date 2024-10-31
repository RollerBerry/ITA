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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Allocation;
import model.Project;
import model.Setting;
import model.User;

/**
 *
 * @author Admin
 */
public class UpdateAllocationController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    
    try {
        String allocationIdParam = request.getParameter("id");
        int allocationId = Integer.parseInt(allocationIdParam);
        ApplicationDAO applicationDAO = new ApplicationDAO();
        Allocation allocation = applicationDAO.getAllocationById(allocationId);

        if (allocation != null) {
            // Retrieve additional data needed for the JSP
            List<User> users = applicationDAO.getUserIdAndFullName();
            List<Project> projects = applicationDAO.getAllProjectIdsAndNames();
            List<Setting> settings = applicationDAO.getAllSettingIdsAndNames();

            // Set the data as request attributes
            request.setAttribute("users", users);
            request.setAttribute("projects", projects);
            request.setAttribute("settings", settings);
            request.setAttribute("allocation", allocation);

            // Forward the request to the UpdateAllocation.jsp
            request.getRequestDispatcher("/UpdateAllocation.jsp").forward(request, response);
        } else {
            // Handle the case where the allocation is not found
            request.setAttribute("errorMessage", "Allocation with ID " + allocationId + " not found.");
            request.getRequestDispatcher("/UpdateAllocation.jsp").forward(request, response);
        }
    } catch (NumberFormatException e) {
        // Handle invalid allocation ID format
        request.setAttribute("errorMessage", "Invalid allocation ID.");
        request.getRequestDispatcher("/UpdateAllocation.jsp").forward(request, response);
    } catch (Exception e) {
        // Handle other exceptions
        request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        request.getRequestDispatcher("/UpdateAllocation.jsp").forward(request, response);
    }
}


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    int allocationId = Integer.parseInt(request.getParameter("allocation_id"));
    String memberId = request.getParameter("member_id");
    String projectId = request.getParameter("project_id");
    String projectRoleId = request.getParameter("project_role_id");
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày
    Date fromDate = null;
    Date toDate = null;
    
    // Chuyển đổi chuỗi thành Date với xử lý lỗi
    try {
        fromDate = sdf.parse(request.getParameter("start_date"));
        toDate = sdf.parse(request.getParameter("end_date"));
    } catch (ParseException e) {
        e.printStackTrace();
        request.setAttribute("errorMessage", "Invalid date format. Please use yyyy-MM-dd.");
        request.getRequestDispatcher("updateAllocation.jsp").forward(request, response);
        return; // Kết thúc xử lý
    }
    
    double effortRate = Double.parseDouble(request.getParameter("effort_rate"));
    String description = request.getParameter("description");
    String createdById = request.getParameter("created_by_id"); // Thay đổi kiểu nếu cần

    // Sử dụng AllocationDAO để cập nhật
    ApplicationDAO applicationDAO = new ApplicationDAO();
    boolean isUpdated = applicationDAO.updateAllocation(allocationId, memberId, projectId, 
                                                        projectRoleId, fromDate, toDate, 
                                                        effortRate, description, 
                                                        createdById); 

    if (isUpdated) {
         request.getSession().setAttribute("message", "Allocation update successfully!");
        request.getSession().setAttribute("messageType", "success");
        response.sendRedirect("application");
        } else {
            request.setAttribute("message", "Failed to update allocation!");
            request.setAttribute("messageType", "error");
           
         request.getRequestDispatcher("UpdateAllocation.jsp").forward(request, response);
    }

    
   
}

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
