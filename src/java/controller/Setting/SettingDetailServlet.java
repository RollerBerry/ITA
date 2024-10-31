package controller.Setting;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Setting;
import dal.SettingDAO;

public class SettingDetailServlet extends HttpServlet {
    private SettingDAO settingDAO = new SettingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy ID từ tham số URL
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int settingId = Integer.parseInt(idParam);

                // Gọi phương thức getSettingById từ SettingDAO
                Setting setting = settingDAO.getSettingById(settingId);

                if (setting != null) {
                    // Đặt đối tượng setting vào request attribute
                    request.setAttribute("setting", setting);
                } else {
                    // Nếu không tìm thấy setting
                    request.setAttribute("error", "Không tìm thấy setting với ID: " + settingId);
                }
            } else {
                // Trường hợp không có ID hợp lệ
                request.setAttribute("error", "");
            }
        } catch (NumberFormatException e) {
            // Xử lý ngoại lệ khi ID không phải là số
            request.setAttribute("error", "");
            e.printStackTrace();
        }

        // Chuyển tiếp tới trang setting_detail.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("setting_detail.jsp");
        dispatcher.forward(request, response);
    }
}
