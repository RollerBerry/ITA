package controller.Allocation;

import dal.ApplicationDAO;
import model.Allocation;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ViewDetailApplicationController", urlPatterns = {"/viewdetails"})
public class ViewDetailApplicationController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ViewDetailApplicationController.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String allocationIdParam = request.getParameter("id");
    if (allocationIdParam == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing allocation ID.");
        return;
    }
    
    int allocationId;
    try {
        allocationId = Integer.parseInt(allocationIdParam);
    } catch (NumberFormatException e) {
        LOGGER.log(Level.SEVERE, "Invalid allocation ID format: " + allocationIdParam, e);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid allocation ID format.");
        return;
    }

    ApplicationDAO applicationDAO = new ApplicationDAO();
    try {
        Allocation allocation = applicationDAO.getAllocationById(allocationId);
        if (allocation != null) {
            request.setAttribute("allocation", allocation);
            request.getRequestDispatcher("/ViewDetailsApplication.jsp").forward(request, response);
        } else {
            // Set an attribute to indicate "not found" and forward to an error page
            request.setAttribute("errorMessage", "Allocation with ID " + allocationId + " not found.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error retrieving allocation details for ID: " + allocationId, e);
        throw new ServletException("Error retrieving allocation details", e);
    }   catch (Exception ex) {
            Logger.getLogger(ViewDetailApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
}


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
