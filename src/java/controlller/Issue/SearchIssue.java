/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlller.Issue;

import dal.IssueDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Issue;

/**
 *
 * @author 
 */
public class SearchIssue extends HttpServlet {
    private final IssueDAO issueDao = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        if (title != null && !title.isEmpty()) {
            try {
                List<Issue> issues = issueDao.searchIssuesByTitle(title);
                request.setAttribute("issues", issues);
            } catch (SQLException ex) {
                Logger.getLogger(SearchIssue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getRequestDispatcher("/WEB-INF/View/Issue/listIssue.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Gọi lại doGet để hiển thị kết quả tìm kiếm
    }
}
