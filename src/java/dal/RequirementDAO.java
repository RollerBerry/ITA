package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Requirement;
import model.Setting;
import model.User;

public class RequirementDAO extends DBContext {

    /**
     * Liệt kê tất cả các yêu cầu với thông tin chi tiết của chủ sở hữu, độ phức
     * tạp và trạng thái.
     *
     * @return Danh sách các yêu cầu.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public List<Requirement> listAllRequirements() throws SQLException {
        List<Requirement> requirements = new ArrayList<>();
        String sql = """
                SELECT requirement.req_id,requirement.title, user.full_name AS owner_name, 
                       complexity.name AS complexity_level, status.name AS requirement_status, 
                       requirement.description
                FROM requirement
                LEFT JOIN user ON user.user_id = requirement.owner_id
                LEFT JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id
                LEFT JOIN setting AS status ON status.setting_id = requirement.status_id;
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Requirement req = new Requirement();
                req.setReqId(rs.getInt("req_id"));
                req.setTitle(rs.getString("title"));
                req.setDescription(rs.getString("description"));

                User owner = new User();
                owner.setFullName(rs.getString("owner_name"));
                req.setOwner(owner);

                Setting complexity = new Setting();
                complexity.setName(rs.getString("complexity_level"));
                req.setComplexity(complexity);

                Setting status = new Setting();
                status.setName(rs.getString("requirement_status"));
                req.setStatus(status);

                requirements.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requirements;
    }

    /**
     * Lấy chi tiết của một yêu cầu cụ thể dựa trên ID.
     *
     * @param reqId ID của yêu cầu.
     * @return Đối tượng Requirement với đầy đủ thông tin, hoặc null nếu không
     * tìm thấy.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public Requirement getRequirementDetails(int reqId) throws SQLException {
        String sql = """
                SELECT requirement.title, requirement.description, requirement.created_at, requirement.req_id,
                       requirement.updated_at, owner.full_name AS owner_name, complexity.name AS complexity_level,
                       status.name AS requirement_status, created_by.full_name AS created_by_name,
                       updated_by.full_name AS updated_by_name
                FROM requirement
                JOIN user AS owner ON owner.user_id = requirement.owner_id
                JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id
                JOIN setting AS status ON status.setting_id = requirement.status_id
                LEFT JOIN user AS created_by ON created_by.user_id = requirement.created_by_id
                LEFT JOIN user AS updated_by ON updated_by.user_id = requirement.updated_by_id
                WHERE requirement.req_id = ?;
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reqId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Requirement req = new Requirement();
                    req.setReqId(rs.getInt("req_id"));
                    req.setTitle(rs.getString("title"));
                    req.setDescription(rs.getString("description"));
                    req.setCreatedAt(rs.getTimestamp("created_at"));
                    req.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User owner = new User();
                    owner.setFullName(rs.getString("owner_name"));
                    req.setOwner(owner);

                    Setting complexity = new Setting();
                    complexity.setName(rs.getString("complexity_level"));
                    req.setComplexity(complexity);

                    Setting status = new Setting();
                    status.setName(rs.getString("requirement_status"));
                    req.setStatus(status);

                    User createdBy = new User();
                    createdBy.setFullName(rs.getString("created_by_name"));
                    req.setCreatedById(createdBy);

                    User updatedBy = new User();
                    updatedBy.setFullName(rs.getString("updated_by_name"));
                    req.setUpdatedById(updatedBy);

                    return req;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cập nhật một yêu cầu với các thông tin mới.
     *
     * @param reqId ID của yêu cầu.
     * @param title Tiêu đề mới.
     * @param ownerId ID của chủ sở hữu mới.
     * @param description Mô tả mới.
     * @param complexityId ID của độ phức tạp mới.
     * @param statusId ID của trạng thái mới.
     * @return true nếu cập nhật thành công, false nếu không.
     * @throws SQLException nếu có lỗi khi cập nhật dữ liệu.
     */
    public boolean updateRequirement(int reqId, String title, int ownerId, String description, int complexityId, int statusId) throws SQLException {
        String sql = """
            UPDATE requirement
            SET title = ?, owner_id = ?, description = ?, complexity_id = ?, status_id = ?, updated_at = CURRENT_TIMESTAMP
            WHERE req_id = ?;
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setInt(2, ownerId);
            stmt.setString(3, description);
            stmt.setInt(4, complexityId);
            stmt.setInt(5, statusId);
            stmt.setInt(6, reqId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa một yêu cầu và các bản ghi liên quan trong bảng issue.
     *
     * @param reqId ID của yêu cầu cần xóa.
     * @return true nếu xóa thành công, false nếu không.
     * @throws SQLException nếu có lỗi khi xóa dữ liệu.
     */
    public boolean deleteRequirement(int reqId) throws SQLException {
        String deleteIssuesSql = "DELETE FROM issue WHERE req_id = ?;";
        String deleteRequirementSql = "DELETE FROM requirement WHERE req_id = ?;";
        try {
            // Xóa các bản ghi trong bảng issue trước
            connection.setAutoCommit(false);
            try (PreparedStatement issueStmt = connection.prepareStatement(deleteIssuesSql)) {
                issueStmt.setInt(1, reqId);
                issueStmt.executeUpdate();
            }

            // Xóa requirement
            try (PreparedStatement reqStmt = connection.prepareStatement(deleteRequirementSql)) {
                reqStmt.setInt(1, reqId);
                int rowsAffected = reqStmt.executeUpdate();
                connection.commit();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Kiểm tra xem một yêu cầu có thể bị xóa không (không có bản ghi nào trong
     * bảng issue).
     *
     * @param reqId ID của yêu cầu.
     * @return true nếu có thể xóa, false nếu không.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public boolean canDeleteRequirement(int reqId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM issue WHERE req_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reqId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // Trả về true nếu không có bản ghi nào trong bảng issue
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy danh sách tất cả người dùng.
     *
     * @return Danh sách các đối tượng User.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, full_name FROM user";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Thêm một yêu cầu mới vào cơ sở dữ liệu.
     *
     * @param title Tiêu đề của yêu cầu.
     * @param ownerId ID của chủ sở hữu.
     * @param complexityId ID của độ phức tạp.
     * @param statusId ID của trạng thái.
     * @param description Mô tả của yêu cầu.
     * @return true nếu thêm thành công, false nếu không.
     * @throws SQLException nếu có lỗi khi thêm dữ liệu.
     */
    public boolean insertRequirement(String title, int ownerId, int complexityId, int statusId, String description) throws SQLException {
        String sql = """
                INSERT INTO requirement (title, owner_id, complexity_id, status_id, created_at, description)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?);
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setInt(2, ownerId);
            stmt.setInt(3, complexityId);
            stmt.setInt(4, statusId);
            stmt.setString(5, description);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lọc yêu cầu theo độ phức tạp và trạng thái.
     *
     * @param complexityId ID của độ phức tạp.
     * @param statusId ID của trạng thái.
     * @return Danh sách các yêu cầu phù hợp.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public List<Requirement> filterRequirements(int complexityId, int statusId) throws SQLException {
        List<Requirement> requirements = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT requirement.req_id, requirement.title, user.full_name AS owner_name, "
                + "complexity.name AS complexity_level, status.name AS requirement_status, "
                + "requirement.description "
                + "FROM requirement "
                + "LEFT JOIN user ON user.user_id = requirement.owner_id "
                + "LEFT JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id "
                + "LEFT JOIN setting AS status ON status.setting_id = requirement.status_id "
                + "WHERE 1=1 ");

        // Thêm điều kiện lọc vào câu SQL
        if (complexityId != -1) {
            sql.append("AND requirement.complexity_id = ? ");
        }
        if (statusId != -1) {
            sql.append("AND requirement.status_id = ? ");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Thiết lập giá trị cho các tham số của câu truy vấn
            if (complexityId != -1) {
                stmt.setInt(paramIndex++, complexityId);
            }
            if (statusId != -1) {
                stmt.setInt(paramIndex, statusId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Requirement req = new Requirement();
                    req.setReqId(rs.getInt("req_id"));
                    req.setTitle(rs.getString("title"));
                    req.setDescription(rs.getString("description"));

                    User owner = new User();
                    owner.setFullName(rs.getString("owner_name"));
                    req.setOwner(owner);

                    Setting complexity = new Setting();
                    complexity.setName(rs.getString("complexity_level"));
                    req.setComplexity(complexity);

                    Setting status = new Setting();
                    status.setName(rs.getString("requirement_status"));
                    req.setStatus(status);

                    requirements.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requirements;
    }

    /**
     * Tìm kiếm yêu cầu theo tiêu đề.
     *
     * @param title Tiêu đề của yêu cầu cần tìm kiếm.
     * @return Danh sách các yêu cầu khớp với tiêu đề.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public List<Requirement> searchRequirementsByTitle(String title) throws SQLException {
        String sql = """
                SELECT requirement.req_id,requirement.title, user.full_name AS owner_name, complexity.name AS complexity_level,
                       status.name AS requirement_status, requirement.description
                FROM requirement
                LEFT JOIN user ON user.user_id = requirement.owner_id
                LEFT JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id
                LEFT JOIN setting AS status ON status.setting_id = requirement.status_id
                WHERE LOWER(requirement.title) LIKE LOWER(?);
                """;
        List<Requirement> requirements = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Requirement req = new Requirement();
                    req.setReqId(rs.getInt("req_id"));
                    req.setTitle(rs.getString("title"));
                    req.setDescription(rs.getString("description"));

                    User owner = new User();
                    owner.setFullName(rs.getString("owner_name"));
                    req.setOwner(owner);

                    Setting complexity = new Setting();
                    complexity.setName(rs.getString("complexity_level"));
                    req.setComplexity(complexity);

                    Setting status = new Setting();
                    status.setName(rs.getString("requirement_status"));
                    req.setStatus(status);

                    requirements.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requirements;
    }

    /**
     * Lấy danh sách Setting cho Complexity.
     *
     * @return Danh sách các đối tượng Setting cho độ phức tạp.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public List<Setting> getComplexitySettings() throws SQLException {
        return getSettingsByType(4); // Giả định rằng type 4 là cho complexity
    }

    /**
     * Lấy danh sách Setting cho Status.
     *
     * @return Danh sách các đối tượng Setting cho trạng thái.
     * @throws SQLException nếu có lỗi khi truy vấn dữ liệu.
     */
    public List<Setting> getStatusSettings() throws SQLException {
        return getSettingsByType(5); // Giả định rằng type 5 là cho status
    }

    /**
     * Lấy tên của Setting dựa trên type
     *
     * @param type
     * @return
     * @throws SQLException
     */
    private List<Setting> getSettingsByType(int type) throws SQLException {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT setting_id, name FROM setting WHERE type = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Setting setting = new Setting();
                    setting.setSettingId(rs.getInt("setting_id"));
                    setting.setName(rs.getString("name"));
                    settings.add(setting);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settings;
    }

    public static void main(String[] args) {
        RequirementDAO dao = new RequirementDAO();
        try {
            // Kiểm tra phương thức listAllRequirements
            System.out.println("Testing listAllRequirements:");
            List<Requirement> requirements = dao.listAllRequirements();
            requirements.forEach(req -> System.out.println("Requirement ID: " + req.getReqId() + ", Title: " + req.getTitle()));
            System.out.println();

            // Kiểm tra phương thức getRequirementDetails
            System.out.println("Testing getRequirementDetails for ID 1:");
            Requirement reqDetails = dao.getRequirementDetails(1);
            if (reqDetails != null) {
                System.out.println("Requirement Details: " + reqDetails.getTitle() + " - " + reqDetails.getDescription());
            } else {
                System.out.println("Requirement not found.");
            }
            System.out.println();

            // Kiểm tra phương thức updateRequirement
            System.out.println("Testing updateRequirement:");
            boolean updateResult = dao.updateRequirement(1, "Updated Title", 1, "Updated Description", 2, 3);
            System.out.println("Update Result: " + (updateResult ? "Success" : "Failed"));
            System.out.println();

            // Kiểm tra phương thức deleteRequirement
            System.out.println("Testing deleteRequirement (deleting ID 1):");
            boolean deleteResult = dao.deleteRequirement(1);
            System.out.println("Delete Result: " + (deleteResult ? "Success" : "Failed"));
            System.out.println();

            // Kiểm tra phương thức canDeleteRequirement
            System.out.println("Testing canDeleteRequirement for ID 2:");
            boolean canDelete = dao.canDeleteRequirement(2);
            System.out.println("Can Delete: " + (canDelete ? "Yes" : "No"));
            System.out.println();

            // Kiểm tra phương thức getAllUsers
            System.out.println("Testing getAllUsers:");
            List<User> users = dao.getAllUsers();
            users.forEach(user -> System.out.println("User ID: " + user.getUserId() + ", Full Name: " + user.getFullName()));
            System.out.println();

            // Kiểm tra phương thức insertRequirement
            System.out.println("Testing insertRequirement:");
            boolean insertResult = dao.insertRequirement("New Requirement", 1, 2, 3, "New Requirement Description");
            System.out.println("Insert Result: " + (insertResult ? "Success" : "Failed"));
            System.out.println();

            // Kiểm tra phương thức filterRequirements
            System.out.println("Testing filterRequirements with complexityId 2 and statusId 3:");
            List<Requirement> filteredRequirements = dao.filterRequirements(2, 3);
            filteredRequirements.forEach(req -> System.out.println("Filtered Requirement ID: " + req.getReqId() + ", Title: " + req.getTitle()));
            System.out.println();

            // Kiểm tra phương thức searchRequirementsByTitle
            System.out.println("Testing searchRequirementsByTitle with 'New':");
            List<Requirement> searchResults = dao.searchRequirementsByTitle("New");
            searchResults.forEach(req -> System.out.println("Search Result Requirement ID: " + req.getReqId() + ", Title: " + req.getTitle()));
            System.out.println();

            // Kiểm tra phương thức getComplexitySettings
            System.out.println("Testing getComplexitySettings:");
            List<Setting> complexitySettings = dao.getComplexitySettings();
            complexitySettings.forEach(setting -> System.out.println("Complexity Setting ID: " + setting.getSettingId() + ", Name: " + setting.getName()));
            System.out.println();

            // Kiểm tra phương thức getStatusSettings
            System.out.println("Testing getStatusSettings:");
            List<Setting> statusSettings = dao.getStatusSettings();
            statusSettings.forEach(setting -> System.out.println("Status Setting ID: " + setting.getSettingId() + ", Name: " + setting.getName()));
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
