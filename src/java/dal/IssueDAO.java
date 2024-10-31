package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Issue;
import model.Requirement;
import model.Setting;
import model.User;

public class IssueDAO extends DBContext {

    /**
     * Liệt kê tất cả các issue với thông tin ngắn gọn.
     *
     * @return Danh sách các issue.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    public List<Issue> listAllIssues() throws SQLException {
        List<Issue> issues = new ArrayList<>();
        String sql = """
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title,
                       issue.deadline, issue.status_id
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Issue issue = new Issue();
                issue.setIssueId(rs.getInt("issue_id"));
                issue.setTitle(rs.getString("title"));

                Setting type = new Setting();
                type.setName(rs.getString("type_name"));
                issue.setTypeId(type);

                Requirement req = new Requirement();
                req.setTitle(rs.getString("req_title"));
                issue.setReqId(req);

                issue.setDeadline(rs.getDate("deadline"));
                issue.setStatusId(rs.getInt("status_id"));

                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    /**
     * Lấy chi tiết của một issue dựa trên ID.
     *
     * @param issueId ID của issue.
     * @return Đối tượng Issue với thông tin chi tiết hoặc null nếu không tìm
     * thấy.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    public Issue getIssueDetail(int issueId) throws SQLException {
        String sql = """
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title, issue.deadline,
                       issue.status_id, assigner.full_name AS assigner_name, assignee.full_name AS assignee_name,
                       issue.status_date, issue.description, issue.created_at, created_by.full_name AS created_by_name,
                       issue.updated_at, updated_by.full_name AS updated_by_name
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id
                LEFT JOIN user AS assigner ON issue.assigner_id = assigner.user_id
                LEFT JOIN user AS assignee ON issue.assignee_id = assignee.user_id
                LEFT JOIN user AS created_by ON issue.created_by_id = created_by.user_id
                LEFT JOIN user AS updated_by ON issue.updated_by_id = updated_by.user_id
                WHERE issue.issue_id = ?;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Issue issue = new Issue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setTitle(rs.getString("title"));

                    Setting type = new Setting();
                    type.setName(rs.getString("type_name"));
                    issue.setTypeId(type);

                    Requirement req = new Requirement();
                    req.setTitle(rs.getString("req_title"));
                    issue.setReqId(req);

                    issue.setDeadline(rs.getDate("deadline"));
                    issue.setStatusId(rs.getInt("status_id"));

                    User assigner = new User();
                    assigner.setFullName(rs.getString("assigner_name"));
                    issue.setAssignerId(assigner);

                    User assignee = new User();
                    assignee.setFullName(rs.getString("assignee_name"));
                    issue.setAssigneeId(assignee);

                    issue.setStatusDate(rs.getTimestamp("status_date"));
                    issue.setDescription(rs.getString("description"));
                    issue.setCreatedAt(rs.getTimestamp("created_at"));

                    User createdBy = new User();
                    createdBy.setFullName(rs.getString("created_by_name"));
                    issue.setCreatedById(createdBy);

                    issue.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User updatedBy = new User();
                    updatedBy.setFullName(rs.getString("updated_by_name"));
                    issue.setUpdatedById(updatedBy);

                    return issue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tìm kiếm các issue theo tiêu đề (không phân biệt chữ hoa chữ thường).
     *
     * @param title Tiêu đề của issue cần tìm kiếm.
     * @return Danh sách các issue phù hợp.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    public List<Issue> searchIssuesByTitle(String title) throws SQLException {
        List<Issue> issues = new ArrayList<>();
        String sql = """
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title, issue.deadline,
                       issue.status_id
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id
                WHERE LOWER(issue.title) LIKE LOWER(?);
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Issue issue = new Issue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setTitle(rs.getString("title"));

                    Setting type = new Setting();
                    type.setName(rs.getString("type_name"));
                    issue.setTypeId(type);

                    Requirement req = new Requirement();
                    req.setTitle(rs.getString("req_title"));
                    issue.setReqId(req);

                    issue.setDeadline(rs.getDate("deadline"));
                    issue.setStatusId(rs.getInt("status_id"));

                    issues.add(issue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    /**
     * Lọc các issue theo trạng thái, loại hoặc yêu cầu.
     *
     * @param status Trạng thái của issue.
     * @param typeName Loại của issue.
     * @param reqTitle Tiêu đề của yêu cầu liên quan.
     * @return Danh sách các issue phù hợp.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    public List<Issue> filterIssues(Integer status, String typeName, String reqTitle) throws SQLException {
        List<Issue> issues = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
                SELECT issue.issue_id, issue.title, setting.name AS type_name, requirement.title AS req_title,
                       issue.deadline, issue.status_id
                FROM issue
                LEFT JOIN setting ON issue.type_id = setting.setting_id
                LEFT JOIN requirement ON issue.req_id = requirement.req_id
                WHERE 1=1
                """);

        if (status != null) {
            sql.append("AND issue.status_id = ? ");
        }
        if (typeName != null && !typeName.isEmpty()) {
            sql.append("AND setting.name = ? ");
        }
        if (reqTitle != null && !reqTitle.isEmpty()) {
            sql.append("AND requirement.title = ? ");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (status != null) {
                ps.setInt(paramIndex++, status);
            }
            if (typeName != null && !typeName.isEmpty()) {
                ps.setString(paramIndex++, typeName);
            }
            if (reqTitle != null && !reqTitle.isEmpty()) {
                ps.setString(paramIndex, reqTitle);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Issue issue = new Issue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setTitle(rs.getString("title"));

                    Setting type = new Setting();
                    type.setName(rs.getString("type_name"));
                    issue.setTypeId(type);

                    Requirement req = new Requirement();
                    req.setTitle(rs.getString("req_title"));
                    issue.setReqId(req);

                    issue.setDeadline(rs.getDate("deadline"));
                    issue.setStatusId(rs.getInt("status_id"));

                    issues.add(issue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    /**
     * Thêm một issue mới vào cơ sở dữ liệu.
     *
     * @param title Tiêu đề của issue.
     * @param typeId ID của loại issue.
     * @param reqId ID của yêu cầu liên quan.
     * @param assignerId ID của người giao.
     * @param assigneeId ID của người nhận.
     * @param deadline Hạn chót của issue.
     * @param statusId Trạng thái của issue.
     * @param description Mô tả của issue.
     * @return true nếu thêm thành công, false nếu không.
     * @throws SQLException nếu có lỗi khi thêm vào cơ sở dữ liệu.
     */
    public boolean insertIssue(String title, int typeId, int reqId, int assignerId, int assigneeId, Date deadline,
            int statusId, String description) throws SQLException {
        String sql = """
                INSERT INTO issue (title, type_id, req_id, assigner_id, assignee_id, deadline, status_id, description, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, typeId);
            ps.setInt(3, reqId);
            ps.setInt(4, assignerId);
            ps.setInt(5, assigneeId);
            ps.setDate(6, deadline);
            ps.setInt(7, statusId);
            ps.setString(8, description);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật một issue hiện có.
     *
     * @param title Tiêu đề của issue.
     * @param typeId ID của loại issue.
     * @param reqId ID của yêu cầu liên quan.
     * @param assignerId ID của người giao.
     * @param assigneeId ID của người nhận.
     * @param deadline Hạn chót của issue.
     * @param statusId Trạng thái của issue.
     * @param description Mô tả của issue.
     * @param issueId ID của issue.
     * @return true nếu cập nhật thành công, false nếu không.
     * @throws SQLException nếu có lỗi khi cập nhật cơ sở dữ liệu.
     */
    public boolean updateIssue(String title,
            int typeId,
            int reqId,
            int assignerId,
            int assigneeId,
            Date deadline,
            int statusId,
            String description,
            int issueId) throws SQLException {
        String sql = """
                UPDATE issue
                SET title = ?, type_id = ?, req_id = ?, assigner_id = ?, assignee_id = ?, 
                    deadline = ?, status_id = ?, description = ?
                WHERE issue_id = ?;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, typeId);
            ps.setInt(3, reqId);
            ps.setInt(4, assignerId);
            ps.setInt(5, assigneeId);
            ps.setDate(6, deadline);
            ps.setInt(7, statusId);
            ps.setString(8, description);
            ps.setInt(9, issueId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa một issue dựa trên ID.
     *
     * @param issueId ID của issue cần xóa.
     * @return true nếu xóa thành công, false nếu không.
     * @throws SQLException nếu có lỗi khi xóa khỏi cơ sở dữ liệu.
     */
    public boolean deleteIssue(int issueId) throws SQLException {
        if (getIssueDetail(issueId) == null) {
            System.out.println("Issue with ID " + issueId + " does not exist.");
            return false;
        }
        String sql = "DELETE FROM issue WHERE issue_id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting issue with ID " + issueId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy danh sách tất cả người dùng.
     *
     * @return Danh sách người dùng.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
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
     * Lấy tất cả các yêu cầu.
     *
     * @return Danh sách các yêu cầu.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    public List<Requirement> getAllRequirement() throws SQLException {
        List<Requirement> requirements = new ArrayList<>();
        String sql = "SELECT req_id, title FROM requirement";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Requirement requirement = new Requirement();
                requirement.setReqId(rs.getInt("req_id"));
                requirement.setTitle(rs.getString("title"));
                requirements.add(requirement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requirements;
    }

    /**
     * Lấy danh sách các thiết lập loại issue.
     *
     * @return Danh sách Setting cho loại issue.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    public List<Setting> getTypeIssue() throws SQLException {
        return getSettingsByType(6); // Giả định rằng type 5 là cho status
    }

    /**
     * Lấy các thiết lập dựa trên type.
     *
     * @param type Loại của setting.
     * @return Danh sách các Setting.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    public List<Setting> getSettingsByType(int type) throws SQLException {
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
        IssueDAO dao = new IssueDAO();

        try {
            // Kiểm tra phương thức listAllIssues
            System.out.println("Testing listAllIssues:");
            List<Issue> issues = dao.listAllIssues();
            issues.forEach(issue -> System.out.println("Issue ID: " + issue.getIssueId() + ", Title: " + issue.getTitle()));
            System.out.println();

            // Kiểm tra phương thức getIssueDetail
            System.out.println("Testing getIssueDetail:");
            Issue issueDetail = dao.getIssueDetail(1); // Giả sử ID 1 tồn tại
            if (issueDetail != null) {
                System.out.println("Issue Detail for ID 1: " + issueDetail.getTitle() + " - " + issueDetail.getDescription());
            } else {
                System.out.println("Issue with ID 1 not found.");
            }
            System.out.println();

            // Kiểm tra phương thức searchIssuesByTitle
            System.out.println("Testing searchIssuesByTitle:");
            List<Issue> searchResults = dao.searchIssuesByTitle("example"); // Giả sử có tiêu đề chứa "example"
            searchResults.forEach(issue -> System.out.println("Found Issue ID: " + issue.getIssueId() + ", Title: " + issue.getTitle()));
            System.out.println();

            // Kiểm tra phương thức filterIssues
            System.out.println("Testing filterIssues:");
            List<Issue> filteredIssues = dao.filterIssues(1, "Bug", "Sample Requirement"); // Giả sử tồn tại các giá trị này
            filteredIssues.forEach(issue -> System.out.println("Filtered Issue ID: " + issue.getIssueId() + ", Title: " + issue.getTitle()));
            System.out.println();

            // Kiểm tra phương thức insertIssue
            System.out.println("Testing insertIssue:");
            boolean insertResult = dao.insertIssue("New Issue", 1, 1, 1, 2, new Date(System.currentTimeMillis()), 1, "Test description");
            System.out.println("Insert Issue Result: " + insertResult);
            System.out.println();

            // Kiểm tra phương thức updateIssue
            System.out.println("Testing updateIssue:");
            boolean updateResult = dao.updateIssue("Updated Issue Title", 1, 1, 1, 2, new Date(System.currentTimeMillis()), 2, "Updated description", 1);
            System.out.println("Update Issue Result: " + updateResult);
            System.out.println();

            // Kiểm tra phương thức deleteIssue
            System.out.println("Testing deleteIssue:");
            boolean deleteResult = dao.deleteIssue(1); // Giả sử ID 1 tồn tại
            System.out.println("Delete Issue Result: " + deleteResult);
            System.out.println();

            // Kiểm tra phương thức getAllUsers
            System.out.println("Testing getAllUsers:");
            List<User> users = dao.getAllUsers();
            users.forEach(user -> System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getFullName()));
            System.out.println();

            // Kiểm tra phương thức getAllRequirement
            System.out.println("Testing getAllRequirement:");
            List<Requirement> requirements = dao.getAllRequirement();
            requirements.forEach(req -> System.out.println("Requirement ID: " + req.getReqId() + ", Title: " + req.getTitle()));
            System.out.println();

            // Kiểm tra phương thức getTypeIssue
            System.out.println("Testing getTypeIssue:");
            List<Setting> typeIssues = dao.getTypeIssue();
            typeIssues.forEach(type -> System.out.println("Type ID: " + type.getSettingId() + ", Name: " + type.getName()));
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
