package controlller.Issue;

import dal.IssueDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Issue;

/**
 * Servlet for displaying issue details
 */
public class DetailIssue extends HttpServlet {
    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy issueId từ tham số yêu cầu
            int issueId = Integer.parseInt(request.getParameter("issueId"));
            
            // Gọi phương thức lấy thông tin chi tiết của issue
            Issue issue = issueDao.getIssueDetail(issueId);
            
            if (issue != null) {
                // Đặt thuộc tính "issue" để sử dụng trong JSP
                request.setAttribute("issue", issue);
                // Chuyển tiếp đến trang chi tiết Issue
                request.getRequestDispatcher("/WEB-INF/View/Issue/detailIssue.jsp").forward(request, response);
            } else {
                // Chuyển tiếp đến trang lỗi nếu issue không tồn tại
                request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển tiếp POST request đến doGet để xử lý chung
        doGet(request, response);
    }
}
