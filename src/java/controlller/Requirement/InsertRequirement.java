package controlller.Requirement;

import dal.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author mituz
 */
public class InsertRequirement extends HttpServlet {

    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Đặt các danh sách cần thiết cho dropdown (users, complexities, statuses)
            request.setAttribute("users", requirementDAO.getAllUsers());
            request.setAttribute("complexities", requirementDAO.getComplexitySettings());
            request.setAttribute("statuses", requirementDAO.getStatusSettings());

            request.getRequestDispatcher("/WEB-INF/View/Requirement/insertRequirement.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InsertRequirement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        int ownerId = Integer.parseInt(request.getParameter("ownerId"));
        int complexityId = Integer.parseInt(request.getParameter("complexityId"));
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        String description = request.getParameter("description");

        List<String> errors = validateTitle(title);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/View/Requirement/insertRequirement.jsp").forward(request, response);
        } else {
            try {
                boolean inserted = requirementDAO.insertRequirement(title, ownerId, complexityId, statusId, description);
                if (inserted) {
                    response.sendRedirect("listRequirement");
                } else {
                    request.getRequestDispatcher("/WEB-INF/View/Requirement/error.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsertRequirement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<String> validateTitle(String title) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.trim().isEmpty()) {
            errors.add("Title is required.");
        } else if (Pattern.compile("[0-9]").matcher(title).find()) {
            errors.add("Title should not contain numbers.");
        } else if (Pattern.compile("[^a-zA-Z ]").matcher(title).find()) {
            errors.add("Title should not contain special characters.");
        }
        return errors;
    }
}
