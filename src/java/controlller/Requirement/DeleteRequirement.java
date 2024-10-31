package controlller.Requirement;

import dal.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Requirement;

/**
 *
 * @author mituz
 */
public class DeleteRequirement extends HttpServlet {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int reqId = Integer.parseInt(request.getParameter("reqId"));
            Requirement requirement = requirementDAO.getRequirementDetails(reqId);
            
            if (requirement != null) {
                request.setAttribute("requirement", requirement);
                request.getRequestDispatcher("/WEB-INF/View/Requirement/confirmDelete.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteRequirement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int reqId = Integer.parseInt(request.getParameter("reqId"));
            boolean deleted = requirementDAO.deleteRequirement(reqId);
            
            if (deleted) {
                response.sendRedirect("listRequirement");
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteRequirement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


