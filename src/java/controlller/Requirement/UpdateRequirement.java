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
import model.Requirement;
import model.Setting;
import model.User;

public class UpdateRequirement extends HttpServlet {

    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int reqId = Integer.parseInt(request.getParameter("reqId"));
            Requirement requirement = requirementDAO.getRequirementDetails(reqId);

            if (requirement != null) {
                List<Setting> complexities = requirementDAO.getComplexitySettings();
                List<Setting> statuses = requirementDAO.getStatusSettings();
                List<User> users = requirementDAO.getAllUsers();

                request.setAttribute("requirement", requirement);
                request.setAttribute("complexities", complexities);
                request.setAttribute("statuses", statuses);
                request.setAttribute("users", users);

                request.getRequestDispatcher("/WEB-INF/View/Requirement/updateRequirement.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Requirement/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateRequirement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reqId = Integer.parseInt(request.getParameter("reqId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int ownerId = Integer.parseInt(request.getParameter("ownerId"));
        int complexityId = Integer.parseInt(request.getParameter("complexityId"));
        int statusId = Integer.parseInt(request.getParameter("statusId"));

        List<String> errors = validateTitle(title);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/View/Requirement/updateRequirement.jsp").forward(request, response);
        } else {
            try {
                boolean updated = requirementDAO.updateRequirement(reqId, title, ownerId, description, complexityId, statusId);
                if (updated) {
                    response.sendRedirect("listRequirement");
                } else {
                    request.getRequestDispatcher("/WEB-INF/View/Requirement/error.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UpdateRequirement.class.getName()).log(Level.SEVERE, null, ex);
                request.getRequestDispatcher("/WEB-INF/View/Requirement/error.jsp").forward(request, response);
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
