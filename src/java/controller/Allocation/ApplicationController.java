package controller.Allocation;

import dal.ApplicationDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Allocation;

public class ApplicationController extends HttpServlet {

    /**
     * Handles the HTTP GET method. Retrieves allocations and forwards to the
     * JSP for display.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String message = request.getParameter("message");
            ApplicationDAO allocationDAO = new ApplicationDAO();
            List<Allocation> allocations = allocationDAO.getAllAllocations();
            request.setAttribute("allocations", allocations);
            request.setAttribute("message", message);
            request.getRequestDispatcher("/WEB-INF/View/Allocation/Allocation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Keep this for console output during testing
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading allocations: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // In case you need specific POST handling, implement here
        doGet(request, response); // Optional: Handle POST like GET for simplicity
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "ApplicationController servlet for managing allocation data";
    }
}
