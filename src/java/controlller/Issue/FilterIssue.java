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

/**
 *
 * @author
 */
public class FilterIssue extends HttpServlet {
    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        // Lấy giá trị statusId, typeName và reqTitle từ request
        String statusParam = request.getParameter("statusId");  // Đổi từ "status" thành "statusId" để khớp với JSP
        String typeName = request.getParameter("typeName");
        String reqTitle = request.getParameter("reqTitle");

        // Nếu giá trị status không tồn tại hoặc rỗng, đặt thành null
        Integer status = (statusParam == null || statusParam.isEmpty()) ? null : Integer.parseInt(statusParam);

        // Gọi phương thức filterIssues với các tham số lọc
        List<Issue> issues = issueDao.filterIssues(status, typeName, reqTitle);

        // Lấy danh sách types và requirements cho dropdown bộ lọc
        List<Setting> types = issueDao.getSettingsByType(6); // Giả sử type 6 là type của issue
        List<Requirement> requirements = issueDao.getAllRequirement();
        List<Setting> statuses = issueDao.getTypeIssue(); // Lấy danh sách trạng thái từ database hoặc danh sách tĩnh

        // Đặt các thuộc tính cho danh sách và giá trị đã chọn để dùng trong JSP
        request.setAttribute("issues", issues);
        request.setAttribute("types", types);
        request.setAttribute("requirements", requirements);
        request.setAttribute("statuses", statuses);
        request.setAttribute("selectedStatus", status);
        request.setAttribute("selectedTypeName", typeName);
        request.setAttribute("selectedReqTitle", reqTitle);

        // Chuyển tiếp đến trang listIssue.jsp
        request.getRequestDispatcher("/WEB-INF/View/Issue/listIssue.jsp").forward(request, response);

    } catch (SQLException ex) {
            Logger.getLogger(InsertIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Xử lý POST bằng doGet
    }
}
