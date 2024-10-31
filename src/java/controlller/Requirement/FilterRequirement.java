/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlller.Requirement;

import controlller.Issue.InsertIssue;
import dal.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Requirement;
import model.Setting;

/**
 *
 * @author mituz
 */
public class FilterRequirement extends HttpServlet {
    private final RequirementDAO requirementDAO = new RequirementDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Nhận giá trị complexityId và statusId từ request
            int complexityId = Integer.parseInt(request.getParameter("complexityId"));
            int statusId = Integer.parseInt(request.getParameter("statusId"));

            // Gọi phương thức filterRequirements với các tham số lọc
            List<Requirement> requirements = requirementDAO.filterRequirements(complexityId, statusId);

            // Lấy danh sách complexities và statuses cho dropdown bộ lọc
            List<Setting> complexities = requirementDAO.getComplexitySettings();
            List<Setting> statuses = requirementDAO.getStatusSettings();

            // Đặt thuộc tính cho các danh sách và giá trị đã chọn để dùng trong JSP
            request.setAttribute("requirements", requirements);
            request.setAttribute("complexities", complexities);
            request.setAttribute("statuses", statuses);
            request.setAttribute("selectedComplexityId", complexityId);
            request.setAttribute("selectedStatusId", statusId);

            // Chuyển tiếp đến trang listRequirement.jsp
            request.getRequestDispatcher("/WEB-INF/View/Requirement/listRequirement.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(InsertIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Xử lý POST bằng doGet
    }
}


