/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlller.Requirement;

import dal.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Requirement;

/**
 *
 * @author mituz
 */
public class DetailRequirement extends HttpServlet {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int reqId = Integer.parseInt(request.getParameter("reqId"));
            Requirement requirement = requirementDAO.getRequirementDetails(reqId);
            if (requirement != null) {
                request.setAttribute("requirement", requirement);
                request.getRequestDispatcher("/WEB-INF/View/Requirement/detailRequirement.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Requirement/error.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailRequirement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
