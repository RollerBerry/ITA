-- Create DATABASE and USE it
CREATE DATABASE IF NOT EXISTS mpms;
USE mpms;

-- Set up initial configurations
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Table structure for table `allocation`
DROP TABLE IF EXISTS `allocation`;
CREATE TABLE `allocation` (
  `allocation_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `project_id` int NOT NULL,
  `project_role_id` int NOT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `effort_rate` decimal(5,2) DEFAULT NULL,
  `description` text,
  `status` int DEFAULT 1,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`allocation_id`),
  CONSTRAINT `fk_allocation_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_allocation_member` FOREIGN KEY (`member_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_allocation_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `fk_allocation_project_role` FOREIGN KEY (`project_role_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_allocation_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `issue`
DROP TABLE IF EXISTS `issue`;
CREATE TABLE `issue` (
  `issue_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `type_id` int DEFAULT NULL,
  `req_id` int DEFAULT NULL,
  `assigner_id` int DEFAULT NULL,
  `assignee_id` int DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `status_id` int DEFAULT 1,
  `status_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`issue_id`),
  CONSTRAINT `fk_issue_assignee` FOREIGN KEY (`assignee_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_issue_assigner` FOREIGN KEY (`assigner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_issue_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_issue_req` FOREIGN KEY (req_id) REFERENCES requirement(req_id) ON DELETE CASCADE,
  CONSTRAINT `fk_issue_type` FOREIGN KEY (`type_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_issue_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `project`
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `dept_id` int DEFAULT NULL,
  `status` int DEFAULT 1,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `fk_project_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_project_department` FOREIGN KEY (`dept_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_project_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `requirement`
DROP TABLE IF EXISTS `requirement`;
CREATE TABLE `requirement` (
  `req_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `owner_id` int DEFAULT NULL,
  `complexity_id` int DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`req_id`),
  CONSTRAINT `fk_requirement_complexity` FOREIGN KEY (`complexity_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_requirement_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_requirement_owner` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_requirement_status` FOREIGN KEY (`status_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_requirement_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Table structure for table `setting`
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `setting_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` varchar(50) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `status` int  DEFAULT 1,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`setting_id`),
  CONSTRAINT `fk_setting_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_setting_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `user`
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role_id` int DEFAULT NULL,
  `dept_id` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` int  DEFAULT 1,
  `note` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_user_department` FOREIGN KEY (`dept_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_user_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_user_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insert initial data into `setting` table with categorized values in the `value` column

INSERT INTO `setting` (`name`, `value`, `type`, `priority`, `status`, `description`, `created_by_id`, `updated_by_id`)
VALUES
-- Project Roles
('Project Manager', 'Project Role', 1, 1, 1, 'Role in projects', 1, 1),
('Developer', 'Project Role', 1, 2, 1, 'Role in projects', 1, 1),
('Tester', 'Project Role', 1, 3, 1, 'Role in projects', 1, 1),
('Designer', 'Project Role', 1, 4, 1, 'Role in projects', 1, 1),

-- User Roles
('Admin', 'User Role', 2, 1, 1, 'User role', 1, 1),
('Staff', 'User Role', 2, 2, 1, 'User role', 1, 1),
('Team Leader', 'User Role', 2, 3, 1, 'User role', 1, 1),
('Project Leader', 'User Role', 2, 4, 1, 'User role', 1, 1),
('Department Leader', 'User Role', 2, 5, 1, 'User role', 1, 1),

-- Departments
('Development', 'Department', 3, 1, 1, 'Department', 1, 1),
('Marketing', 'Department', 3, 2, 1, 'Department', 1, 1),
('Finance', 'Department', 3, 3, 1, 'Department', 1, 1),
('HR', 'Department', 3, 4, 1, 'Department', 1, 1),

-- Complexity Levels
('High', 'Complexity Level', 4, 1, 1, 'Complexity level', 1, 1),
('Medium', 'Complexity Level', 4, 2, 1, 'Complexity level', 1, 1),
('Low', 'Complexity Level', 4, 3, 1, 'Complexity level', 1, 1),

-- Requirement Status
('Pending', 'Requirement Status', 5, 1, 1, 'Requirement status', 1, 1),
('In-progress', 'Requirement Status', 5, 2, 1, 'Requirement status', 1, 1),
('Closed', 'Requirement Status', 5, 3, 1, 'Requirement status', 1, 1),
('Cancelled', 'Requirement Status', 5, 4, 1, 'Requirement status', 1, 1),

-- Issue Types
('Bug', 'Issue Type', 6, 1, 1, 'Issue type in the project management system', 1, 1),
('Feature', 'Issue Type', 6, 2,1, 'Issue type in the project management system', 1, 1),
('Task', 'Issue Type', 6, 3, 1, 'Issue type in the project management system', 1, 1),
('Improvement', 'Issue Type', 6, 4, 1, 'Issue type in the project management system', 1, 1);

-- Insert dữ liệu thực tế cho bảng `user`
INSERT INTO `user` (`full_name`, `user_name`, `email`, `password`, `role_id`, `dept_id`, `start_date`, `status`, `note`, `created_by_id`, `updated_by_id`)
VALUES
('Alice Johnson', 'alice.j', 'alice.johnson@company.com', 'alice_pass', 5, 10, '2023-01-02', 1, 'Quản trị viên hệ thống', 1, 1),
('Bob Smith', 'bob.smith', 'bob.smith@company.com', 'bob_pass', 6, 11, '2023-01-15', 2, 'Trưởng nhóm phát triển', 1, 1),
('Charlie Brown', 'charlie.b', 'charlie.brown@company.com', 'charlie_pass', 7, 10, '2023-02-01', 2, 'Trưởng dự án phụ trách dự án Alpha', 1, 1),
('Dana White', 'dana.w', 'dana.white@company.com', 'dana_pass', 8, 12, '2023-03-12', 1, 'Nhân viên marketing phụ trách dự án Beta', 1, 1),
('Eve Adams', 'eve.adams', 'eve.adams@company.com', 'eve_pass', 9, 13, '2023-04-05', 2, 'Nhân viên tài chính', 1, 1);

-- Insert dữ liệu thực tế cho bảng `project`
INSERT INTO `project` (`name`, `code`, `start_date`, `end_date`, `dept_id`, `status`, `description`, `created_by_id`, `updated_by_id`)
VALUES
('Website Redesign', 'WD2023', '2023-01-10', '2023-06-30', 10, 4, 'Dự án thiết kế lại trang web công ty', 1, 1),
('Product Launch Campaign', 'PLC2023', '2023-03-01', '2023-07-01', 11, 1, 'Chiến dịch ra mắt sản phẩm mới', 1, 1),
('Financial System Update', 'FSU2023', '2023-04-15', '2023-09-15', 12, 2, 'Cập nhật hệ thống tài chính nội bộ', 1, 1),
('Employee Training Program', 'ETP2023', '2023-02-20', '2023-08-20', 13, 3, 'Chương trình đào tạo cho nhân viên mới', 1, 1);

-- Insert dữ liệu thực tế cho bảng `requirement`
INSERT INTO `requirement` (`title`, `owner_id`, `complexity_id`, `status_id`, `description`, `created_by_id`, `updated_by_id`)
VALUES
('Responsive Layout', 2, 14, 18, 'Thiết kế layout đáp ứng cho trang web trên các thiết bị di động', 1, 1),
('SEO Optimization', 2, 15, 19, 'Tối ưu hóa SEO cho nội dung trang web', 1, 1),
('Data Migration', 3, 14, 19, 'Di chuyển dữ liệu từ hệ thống cũ sang hệ thống tài chính mới', 1, 1),
('Employee Onboarding Module', 4, 16, 20, 'Xây dựng module đào tạo nhân viên mới', 1, 1);

-- Insert dữ liệu thực tế cho bảng `allocation`
INSERT INTO `allocation` (`member_id`, `project_id`, `project_role_id`, `from_date`, `to_date`, `effort_rate`, `description`, `status`, `created_by_id`, `updated_by_id`)
VALUES
(2, 1, 1, '2023-01-10', '2023-06-30', 0.9, 'Quản lý dự án Website Redesign', 1, 1, 1),
(3, 3, 1, '2023-04-15', '2023-09-15', 0.8, 'Quản lý dự án Financial System Update',2, 1, 1),
(4, 2, 4, '2023-03-01', '2023-07-01', 0.6, 'Phụ trách marketing cho Product Launch Campaign', 1, 1, 1),
(5, 4, 2, '2023-02-20', '2023-08-20', 0.7, 'Tài chính cho chương trình đào tạo nhân viên', 2, 1, 1);

-- Insert dữ liệu thực tế cho bảng `issue`
INSERT INTO `issue` (`title`, `type_id`, `req_id`, `assigner_id`, `assignee_id`, `deadline`, `status_id`, `description`, `created_by_id`, `updated_by_id`)
VALUES
('Fix layout issue on mobile', 21, 1, 2, 3, '2023-05-15', 1, 'Lỗi layout không hiển thị đúng trên màn hình di động', 1, 1),
('Optimize images for SEO', 22, 2, 2, 3, '2023-06-10', 2, 'Tối ưu hóa hình ảnh để cải thiện SEO', 1, 1),
('Migrate financial records', 23, 3, 3, 4, '2023-08-01', 4, 'Di chuyển dữ liệu tài chính năm 2022 vào hệ thống mới', 1, 1),
('Update training content', 24, 4, 4, 5, '2023-07-15', 3, 'Cập nhật nội dung đào tạo mới cho nhân viên', 1, 1);
