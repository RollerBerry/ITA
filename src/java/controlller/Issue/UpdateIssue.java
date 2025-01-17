package controlller.Issue;

import dal.IssueDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import model.Issue;
import model.Requirement;
import model.Setting;
import model.User;

public class UpdateIssue extends HttpServlet {

    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int issueId = Integer.parseInt(request.getParameter("issueId"));
            Issue issue = issueDao.getIssueDetail(issueId);

            if (issue != null) {
                List<Setting> types = issueDao.getSettingsByType(6); // Giả sử type 6 là cho loại của issue
                List<User> users = issueDao.getAllUsers();
                List<Requirement> requirements = issueDao.getAllRequirement();

                request.setAttribute("issue", issue);
                request.setAttribute("types", types);
                request.setAttribute("users", users);
                request.setAttribute("requirements", requirements);

                request.getRequestDispatcher("/WEB-INF/View/Issue/updateIssue.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int issueId = Integer.parseInt(request.getParameter("issueId"));
        String title = request.getParameter("title");
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        int reqId = Integer.parseInt(request.getParameter("reqId"));
        int assignerId = Integer.parseInt(request.getParameter("assignerId"));
        int assigneeId = Integer.parseInt(request.getParameter("assigneeId"));
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        String description = request.getParameter("description");
        Date deadline = Date.valueOf(request.getParameter("deadline"));

        List<String> errors = new ArrayList<>();
        errors.addAll(validateTitle(title));
        errors.addAll(validateDeadline(deadline, new Date(System.currentTimeMillis())));

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/View/Issue/updateIssue.jsp").forward(request, response);
        } else {
            try {
                boolean updated = issueDao.updateIssue(title, typeId, reqId, assignerId, assigneeId, deadline, statusId, description, issueId);
                if (updated) {
                    response.sendRedirect("listIssue");
                } else {
                    request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UpdateIssue.class.getName()).log(Level.SEVERE, null, ex);
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

    private List<String> validateDeadline(Date deadline, Date createdDate) {
        List<String> errors = new ArrayList<>();
        if (deadline != null && deadline.before(createdDate)) {
            errors.add("Deadline cannot be before the creation date.");
        }
        return errors;
    }
}
