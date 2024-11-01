package controlller.Issue;

import dal.IssueDAO;
import java.io.IOException;
import java.sql.Date;
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
 * Servlet for inserting a new issue.
 */
public class InsertIssue extends HttpServlet {

    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Đặt các danh sách cần thiết cho dropdown (users, requirements, types, statuses)
            request.setAttribute("users", issueDao.getAllUsers());
            request.setAttribute("requirements", issueDao.getAllRequirement());
            request.setAttribute("types", issueDao.getSettingsByType(6)); // Giả định rằng type 6 là dành cho loại Issue

            request.getRequestDispatcher("/WEB-INF/View/Issue/insertIssue.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InsertIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        int reqId = Integer.parseInt(request.getParameter("reqId"));
        int assignerId = Integer.parseInt(request.getParameter("assignerId"));
        int assigneeId = Integer.parseInt(request.getParameter("assigneeId"));
        Date deadline = Date.valueOf(request.getParameter("deadline"));
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        String description = request.getParameter("description");

        List<String> errors = new ArrayList<>();
        errors.addAll(validateTitle(title));
        errors.addAll(validateDeadline(deadline, new Date(System.currentTimeMillis()))); // Giả định ngày tạo là ngày hiện tại

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/View/Issue/insertIssue.jsp").forward(request, response);
        } else {
            try {
                boolean inserted = issueDao.insertIssue(title, typeId, reqId, assignerId, assigneeId, deadline, statusId, description);
                if (inserted) {
                    response.sendRedirect("listIssue");
                } else {
                    request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsertIssue.class.getName()).log(Level.SEVERE, null, ex);
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
