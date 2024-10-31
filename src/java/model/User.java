/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.security.Timestamp;

/**
 *
 * @author Predator
 */
public class User {

    private int userId;
    private String fullName;
    private String userName;
    private String email;
    private String password;
    private int roleId;
    private int dep_id;
    private boolean status;
    private String note;
    private int createdById;
    private int updatedById;
    private String departmentName;
    private String roleName; 
    private int projectCount;

    public User() {
        
    }
    
        public User(String fullName, String userName, String email, String password ) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    } 
    
    public User(int userId, String fullName, String userName, String email, String password, int roleId, int dep_id, boolean status, String note, int createdById, int updatedById) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.dep_id = dep_id;
        this.status = status;
        this.note = note;
        this.createdById = createdById;
        this.updatedById = updatedById;
    }

    public User(String fullName, String userName, String email, String password, int roleId, int dep_id, boolean status) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.dep_id = dep_id;
        this.status = status;
    }
    public User(int userId, String fullName, String userName, String email, String password, int roleId, int dep_id, boolean status, String note, int createdById, int updatedById, String departmentName, String roleName, int projectCount) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.dep_id = dep_id;
        this.status = status;
        this.note = note;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.departmentName = departmentName;
        this.roleName = roleName;
        this.projectCount = projectCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public int getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(int updatedById) {
        this.updatedById = updatedById;
    }

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(int projectCount) {
        this.projectCount = projectCount;
    }
    
    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", fullName=" + fullName + ", userName=" + userName + ", email=" + email + " password=" + password + ", roleId=" + roleId + ", status=" + status + ", note=" + note + ", createdAt=" + ", createdById=" + createdById + ", updatedAt=" + ", updatedById=" + updatedById + '}';
    }

}
