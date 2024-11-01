package controller.Setting;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/Setting/update_setting.jsp");
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

        // Gọi hàm validate
        List<String> errors = validateInputObject(setting);

        if (!errors.isEmpty()) {
            // Nếu có lỗi, chuyển tiếp tới trang update và hiển thị lỗi
            request.setAttribute("errors", errors);
            request.setAttribute("setting", setting);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/Setting/update_setting.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Cập nhật setting trong cơ sở dữ liệu nếu không có lỗi
        settingDAO.updateSetting(setting);

        // Chuyển hướng về danh sách settings
        response.sendRedirect("SettingListServlet");
    }

    // Hàm validate dữ liệu đầu vào
    private List<String> validateInputObject(Setting setting) {
        List<String> errors = new ArrayList<>();

        if (setting.getSettingId() < 0) {
            errors.add("ID không thể là số âm");
        }
        if (setting.getName() == null || setting.getName().trim().isEmpty()) {
            errors.add("Tên không được để trống");
        }
        if (setting.getValue() == null || setting.getValue().trim().isEmpty()) {
            errors.add("Giá trị không được để trống");
        }
        if (!"Active".equals(setting.getStatus()) && !"Inactive".equals(setting.getStatus())) {
            errors.add("Trạng thái không hợp lệ");
        }

        return errors;
    }
}
