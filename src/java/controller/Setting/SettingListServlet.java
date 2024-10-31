package controller.Setting;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Setting;
import dal.SettingDAO;  

public class SettingListServlet extends HttpServlet {
    private SettingDAO settingDAO = new SettingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm và lọc từ request
        String searchKeyword = request.getParameter("search");
        String filterStatus = request.getParameter("status");

        // Khai báo danh sách settings
        List<Setting> settings;

        // Kiểm tra điều kiện tìm kiếm và lọc
        if ((searchKeyword != null && !searchKeyword.isEmpty()) || 
            (filterStatus != null && !filterStatus.isEmpty())) {
            // Lấy danh sách settings từ DAO với điều kiện tìm kiếm và lọc
            settings = settingDAO.getFilteredSettings(searchKeyword, filterStatus);
        } else {
            // Lấy tất cả settings nếu không có điều kiện tìm kiếm và lọc
            settings = settingDAO.getAllSettings();
        }

        // Đặt thuộc tính cho request để truyền dữ liệu sang JSP
        request.setAttribute("settings", settings);
        request.setAttribute("searchKeyword", searchKeyword);
        request.setAttribute("filterStatus", filterStatus);

        // Chuyển tiếp tới trang setting_list.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("setting_list.jsp");
        dispatcher.forward(request, response);
    }
}
