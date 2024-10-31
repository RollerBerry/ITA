package controller.Setting;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import dal.SettingDAO;
import model.Setting;

public class AddSettingServlet extends HttpServlet {
    private SettingDAO settingDAO = new SettingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        int status = Integer.parseInt(request.getParameter("status"));

        // Tạo đối tượng Setting mới
        Setting setting = new Setting();
        setting.setName(name);
        setting.setValue(value);
        setting.setStatus(status == 1 ? "Active" : "Inactive");

        // Thêm setting vào cơ sở dữ liệu
        settingDAO.addSetting(setting);

        // Chuyển hướng về danh sách settings
        response.sendRedirect("SettingListServlet");
    }
}
