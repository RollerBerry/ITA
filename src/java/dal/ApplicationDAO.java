package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Allocation;
import model.Project;
import model.Setting;
import model.User;

public class ApplicationDAO extends DBContext {

    public List<Allocation> getAllAllocations() throws Exception {
        String query = """
                SELECT a.allocation_id, a.from_date, a.to_date, a.effort_rate, a.status, a.description,
                       u.full_name AS member_name,
                       p.name AS project_name,
                       s.name AS role_value
                FROM allocation a
                JOIN user u ON a.member_id = u.user_id
                JOIN project p ON a.project_id = p.project_id
                JOIN setting s ON a.project_role_id = s.setting_id;
                """;

        List<Allocation> allocations = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Allocation allocation = new Allocation();
                allocation.setID(rs.getInt("allocation_id"));
                allocation.setFromDate(rs.getDate("from_date"));
                allocation.setToDate(rs.getDate("to_date"));
                allocation.setEffort(rs.getDouble("effort_rate"));
                allocation.setStatus(rs.getString("status"));
                allocation.setDescription(rs.getString("description"));

                // Retrieve memberId, projectId, and roleId as integers
                allocation.setMemberId(rs.getString("member_name"));
                allocation.setProjectId(rs.getString("project_name"));
                allocation.setRoleId(rs.getString("role_value"));

                allocations.add(allocation);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching allocations: " + e.getMessage());
            throw e; // rethrow the exception to handle it at a higher level if necessary
        } finally {
            // Close ResultSet and PreparedStatement in the finally block
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                /* ignored */ }
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
                /* ignored */ }
        }

        return allocations;
    }

    public Allocation getAllocationById(int allocationId) throws Exception {
        String query = """
            SELECT a.allocation_id, a.from_date, a.to_date, a.effort_rate, a.status, a.description,
                   u.full_name AS member_name,
                   p.name AS project_name,
                   s.name AS role_value,
                   uc.full_name AS created_by_name
            FROM allocation a
            JOIN user u ON a.member_id = u.user_id
            JOIN project p ON a.project_id = p.project_id
            JOIN setting s ON a.project_role_id = s.setting_id
            JOIN user uc ON a.created_by_id = uc.user_id  -- Join to get creator's name
            WHERE a.allocation_id = ?;
            """;

        PreparedStatement ps = null;
        ResultSet rs = null;
        Allocation allocation = null;

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, allocationId);  // Set the allocationId parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                allocation = new Allocation();
                allocation.setID(rs.getInt("allocation_id"));
                allocation.setFromDate(rs.getDate("from_date"));
                allocation.setToDate(rs.getDate("to_date"));
                allocation.setEffort(rs.getDouble("effort_rate"));
                allocation.setStatus(rs.getString("status"));
                allocation.setDescription(rs.getString("description"));

                // Retrieve member name, project name, role value, and creator's name
                allocation.setMemberId(rs.getString("member_name"));
                allocation.setProjectId(rs.getString("project_name"));
                allocation.setRoleId(rs.getString("role_value"));
                allocation.setCreate_by(rs.getString("created_by_name")); // Set creator's name
            }
        } catch (SQLException e) {
            System.out.println("Error fetching allocation by ID: " + e.getMessage());
            throw e;
        } finally {
            // Close ResultSet and PreparedStatement in the finally block
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                /* ignored */ }
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
                /* ignored */ }
        }

        return allocation;
    }

    public List<User> getUserIdAndFullName() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT user_id, full_name FROM user";

    try (PreparedStatement stmt = connection.prepareStatement(sql); 
         ResultSet rs = stmt.executeQuery()) {

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


    public List<Project> getAllProjectIdsAndNames() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT project_id, name FROM project";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project();
                project.setProjectId(rs.getInt("project_id"));
                project.setName(rs.getString("name"));
                
                
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }
    
    
    public List<Setting> getAllSettingIdsAndNames() {
        List<Setting> settings = new ArrayList<>();
        String query = "SELECT setting_id, name FROM setting";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Setting setting = new Setting();
                setting.setSettingId(rs.getInt("setting_id"));
                setting.setName(rs.getString("name"));
                
                settings.add(setting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return settings;
    }
    
    public boolean insertAllocation(int memberId, int projectId, int projectRoleId, 
                                    java.sql.Date startDate, java.sql.Date endDate, 
                                    double effortRate, String description, int createdById) {
        String query = "INSERT INTO allocation (member_id, project_id, project_role_id, from_date, to_date, effort_rate, description, created_by_id, created_at, status) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 1)";


        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, memberId);
            stmt.setInt(2, projectId);
            stmt.setInt(3, projectRoleId);
            stmt.setDate(4, startDate);
            stmt.setDate(5, endDate);
            stmt.setDouble(6, effortRate);
            stmt.setString(7, description);
            stmt.setInt(8, createdById);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateAllocation(int id, String memberId, String projectId, String roleId, 
                                    Date fromDate, Date toDate, double effort, 
                                    String description, String createdBy) {
        String sql = "UPDATE allocation SET member_id = ?, project_id = ?, "
                   + "project_role_id = ?, from_date = ?, to_date = ?, "
                   + "effort_rate = ?, description = ?, status = 1, "
                   + "updated_at = NOW(), updated_by_id = ? WHERE allocation_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
             
            pstmt.setString(1, memberId);
            pstmt.setString(2, projectId);
            pstmt.setString(3, roleId);
            pstmt.setDate(4, new java.sql.Date(fromDate.getTime()));
            pstmt.setDate(5, new java.sql.Date(toDate.getTime()));
            pstmt.setDouble(6, effort);
            pstmt.setString(7, description);
            pstmt.setString(8, createdBy); // Assuming the creator is also the updater
            pstmt.setInt(9, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    
    public void deleteAllocationById(int allocationId) {
    String query = "DELETE FROM allocation WHERE allocation_id = ?;";
    PreparedStatement ps = null;

    try {
        ps = connection.prepareStatement(query);
        ps.setInt(1, allocationId); // Set the allocationId parameter
        ps.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error deleting allocation: " + e.getMessage());
    } 
}

    
    public static void main(String[] args) {
        ApplicationDAO dao = new ApplicationDAO();
        try {
            Allocation allocation = dao.getAllocationById(1); // Thay đổi ID nếu cần

            if (allocation != null) {
                System.out.println("Allocation ID: " + allocation.getID());
                System.out.println("Member ID: " + allocation.getMemberId());
                System.out.println("Project ID: " + allocation.getProjectId());
                System.out.println("Created By: " + allocation.getCreate_by());
            } else {
                System.out.println("No allocation found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
