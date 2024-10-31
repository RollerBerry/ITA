package controller.Setting;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import dal.SettingDAO;
import model.Setting;

public class UpdateSettingServlet extends HttpServlet {
    private SettingDAO settingDAO = new SettingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID setting từ request
        int id = Integer.parseInt(request.getParameter("id"));

        // Lấy thông tin setting từ database
        Setting setting = settingDAO.getSettingById(id);

        // Đặt thuộc tính setting và chuyển tiếp tới trang update
        request.setAttribute("setting", setting);
        RequestDispatcher dispatcher = request.getRequestDispatcher("update_setting.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        int status = Integer.parseInt(request.getParameter("status"));

        // Cập nhật đối tượng Setting
        Setting setting = new Setting();
        setting.setSettingId(id);
        setting.setName(name);
        setting.setValue(value);
        setting.setStatus(status == 1 ? "Active" : "Inactive");

        // Cập nhật setting trong cơ sở dữ liệu
        settingDAO.updateSetting(setting);

        // Chuyển hướng về danh sách settings
        response.sendRedirect("SettingListServlet");
    }
}
