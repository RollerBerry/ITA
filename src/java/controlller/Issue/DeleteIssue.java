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
 * Servlet xử lý xóa một Issue
 */
public class DeleteIssue extends HttpServlet {
    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy issueId từ tham số request
            int issueId = Integer.parseInt(request.getParameter("issueId"));
            
            // Lấy chi tiết Issue theo ID
            Issue issue = issueDao.getIssueDetail(issueId);
            
            // Kiểm tra nếu Issue tồn tại, hiển thị trang xác nhận xóa
            if (issue != null) {
                request.setAttribute("issue", issue);
                request.getRequestDispatcher("/WEB-INF/View/Issue/confirmDelete.jsp").forward(request, response);
            } else {
                // Nếu Issue không tồn tại, hiển thị trang lỗi
                request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy issueId từ tham số request
            int issueId = Integer.parseInt(request.getParameter("issueId"));
            
            // Thực hiện xóa Issue theo ID
            boolean deleted = issueDao.deleteIssue(issueId);
            
            // Kiểm tra kết quả xóa và điều hướng người dùng
            if (deleted) {
                // Xóa thành công, chuyển hướng đến danh sách Issue
                response.sendRedirect("listIssue");
            } else {
                // Xóa thất bại, hiển thị trang lỗi
                request.getRequestDispatcher("/WEB-INF/View/Issue/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
