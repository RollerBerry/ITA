package dal;

import model.Setting;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettingDAO {

    // Lấy tất cả các setting từ cơ sở dữ liệu
    public List<Setting> getAllSettings() {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT * FROM setting";
        
        try {
            // Tạo kết nối từ DBContext
            DBContext dbContext = new DBContext();
            Connection connection = dbContext.connection;

            // Chuẩn bị và thực thi truy vấn
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Lặp qua các kết quả và thêm vào danh sách settings
            while (rs.next()) {
                Setting setting = extractSettingFromResultSet(rs);
                settings.add(setting);
            }

            // Đóng kết nối
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return settings;
    }

    // Thêm setting mới vào cơ sở dữ liệu
    public void addSetting(Setting setting) {
        String sql = "INSERT INTO setting (name, value, type, priority, status, description) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            // Tạo kết nối từ DBContext
            DBContext dbContext = new DBContext();
            Connection connection = dbContext.connection;

            // Chuẩn bị truy vấn
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, setting.getName());
            ps.setString(2, setting.getValue());
            ps.setInt(3, setting.getTypeId());
            ps.setInt(4, setting.getPriority());
            ps.setInt(5, setting.getStatus().equalsIgnoreCase("Active") ? 1 : 0);
            ps.setString(6, setting.getDescription());

            // Thực thi truy vấn
            ps.executeUpdate();

            // Đóng kết nối
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin setting trong cơ sở dữ liệu
    public void updateSetting(Setting setting) {
        String sql = "UPDATE setting SET name = ?, value = ?, type = ?, priority = ?, status = ?, description = ? WHERE setting_id = ?";
        
        try {
            // Tạo kết nối từ DBContext
            DBContext dbContext = new DBContext();
            Connection connection = dbContext.connection;

            // Chuẩn bị truy vấn
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, setting.getName());
            ps.setString(2, setting.getValue());
            ps.setInt(3, setting.getTypeId());
            ps.setInt(4, setting.getPriority());
            ps.setInt(5, setting.getStatus().equalsIgnoreCase("Active") ? 1 : 0);
            ps.setString(6, setting.getDescription());
            ps.setInt(7, setting.getSettingId());

            // Thực thi truy vấn
            ps.executeUpdate();

            // Đóng kết nối
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy settings theo điều kiện tìm kiếm và lọc
    public List<Setting> getFilteredSettings(String keyword, String status) {
        List<Setting> settings = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM setting WHERE 1=1");
        
        // Thêm điều kiện tìm kiếm và lọc vào truy vấn
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
        }
        
        try {
            // Tạo kết nối từ DBContext
            DBContext dbContext = new DBContext();
            Connection connection = dbContext.connection;

            // Chuẩn bị truy vấn
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            int index = 1;

            // Gán giá trị cho các tham số
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setInt(index++, status.equalsIgnoreCase("Active") ? 1 : 0);
            }

            // Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            // Lặp qua các kết quả và thêm vào danh sách settings
            while (rs.next()) {
                Setting setting = extractSettingFromResultSet(rs);
                settings.add(setting);
            }

            // Đóng kết nối
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return settings;
    }

    // Lấy setting theo ID
    public Setting getSettingById(int id) {
        Setting setting = null;
        String sql = "SELECT * FROM setting WHERE setting_id = ?";
        
        try {
            // Tạo kết nối từ DBContext
            DBContext dbContext = new DBContext();
            Connection connection = dbContext.connection;

            // Chuẩn bị truy vấn
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Lấy chi tiết setting
            if (rs.next()) {
                setting = extractSettingFromResultSet(rs);
            }

            // Đóng kết nối
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setting;
    }

    // Hàm để lấy dữ liệu từ ResultSet và tạo đối tượng Setting
    private Setting extractSettingFromResultSet(ResultSet rs) throws SQLException {
        Setting setting = new Setting();
        setting.setSettingId(rs.getInt("setting_id"));
        setting.setName(rs.getString("name"));
        setting.setValue(rs.getString("value"));
        setting.setTypeId(rs.getInt("type"));
        setting.setPriority(rs.getInt("priority"));
        setting.setStatus(rs.getInt("status") == 1 ? "Active" : "Inactive");
        setting.setDescription(rs.getString("description"));
        return setting;
    }

    // Hàm main để kiểm tra các phương thức
    public static void main(String[] args) {
        SettingDAO settingDAO = new SettingDAO();

        // Thêm setting mới
        Setting newSetting = new Setting();
        newSetting.setName("New Setting");
        newSetting.setValue("New Value");
        newSetting.setTypeId(1);
        newSetting.setPriority(5);
        newSetting.setStatus("Active");
        newSetting.setDescription("Description of new setting");
        settingDAO.addSetting(newSetting);

        // Cập nhật setting
        Setting updateSetting = settingDAO.getSettingById(1); // Giả sử ID là 1
        if (updateSetting != null) {
            updateSetting.setName("Updated Setting");
            updateSetting.setValue("Updated Value");
            updateSetting.setStatus("Inactive");
            settingDAO.updateSetting(updateSetting);
        }
    }
}
