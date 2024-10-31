package controlller.Issue;

import dal.IssueDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Issue;
import model.Setting;
import model.Requirement;

public class ListIssue extends HttpServlet {
    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy tất cả các issues
            List<Issue> issues = issueDao.listAllIssues();
            request.setAttribute("issues", issues);

            // Lấy danh sách types, statuses và requirements từ IssueDao để sử dụng cho bộ lọc
            List<Setting> types = issueDao.getSettingsByType(6); // Giả định type 6 là cho các loại Issue
            List<Setting> statuses = issueDao.getTypeIssue(); // Giả định type 5 là cho status
            List<Requirement> requirements = issueDao.getAllRequirement();

            // Đặt vào request để hiển thị trên giao diện JSP
            request.setAttribute("types", types);
            request.setAttribute("statuses", statuses);
            request.setAttribute("requirements", requirements);

            // Thiết lập giá trị mặc định cho filter (khi không có lọc)
            request.setAttribute("selectedTypeId", -1); // -1 biểu thị "All"
            request.setAttribute("selectedStatusId", -1);
            request.setAttribute("selectedReqId", -1);

            // Chuyển tiếp đến trang listIssue.jsp
            request.getRequestDispatcher("/WEB-INF/View/Issue/listIssue.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InsertIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy các thông tin từ request để thực hiện bộ lọc
        try {
            Integer selectedStatusId = request.getParameter("status") == null ? -1 : Integer.parseInt(request.getParameter("status"));
            String selectedTypeName = request.getParameter("type");
            String selectedReqTitle = request.getParameter("requirement");

            // Thực hiện bộ lọc dựa vào các giá trị lọc
            List<Issue> issues = issueDao.filterIssues(
                    selectedStatusId == -1 ? null : selectedStatusId,
                    (selectedTypeName == null || selectedTypeName.isEmpty()) ? null : selectedTypeName,
                    (selectedReqTitle == null || selectedReqTitle.isEmpty()) ? null : selectedReqTitle
            );

            // Cập nhật các thuộc tính trên request
            request.setAttribute("issues", issues);

            // Để lại các lựa chọn đã chọn trong bộ lọc khi làm mới trang
            request.setAttribute("selectedStatusId", selectedStatusId);
            request.setAttribute("selectedTypeName", selectedTypeName);
            request.setAttribute("selectedReqTitle", selectedReqTitle);

            // Lấy lại danh sách types, statuses và requirements cho bộ lọc
            List<Setting> types = issueDao.getSettingsByType(6);
            List<Setting> statuses = issueDao.getTypeIssue();
            List<Requirement> requirements = issueDao.getAllRequirement();

            request.setAttribute("types", types);
            request.setAttribute("statuses", statuses);
            request.setAttribute("requirements", requirements);

            // Chuyển tiếp đến trang listIssue.jsp sau khi lọc
            request.getRequestDispatcher("/WEB-INF/View/Issue/listIssue.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(InsertIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
