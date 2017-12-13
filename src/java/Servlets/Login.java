/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controllers.UseraccountJpaController;
import Entities.Useraccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MrHacker
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    PrintWriter outw;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UseraccountJpaController userController = new UseraccountJpaController();

        List<Useraccount> userList = userController.findUseraccountEntities();
        try (PrintWriter out = response.getWriter()) {

            outw = out;
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StoreTheSession</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StoreTheSession at " + request.getContextPath() + "</h1>");
            out.println("<h>" + email + "</h><br>");
            out.println("<h>" + password + "</h><br>");

            Integer userId = 0;
            boolean userExist = false;
            Useraccount user = null;
            for (Useraccount userItem : userList) {
                if (userItem.getEmail().equals(email) && userItem.getUserPassword().equals(password)) {
                    userId = userItem.getId();
                    user = userItem;
                    userExist = true;
                    break;
                }
            }
            
            HttpSession newSession = null;
            if (userExist) {
                //Create session
                newSession = request.getSession(true);
                newSession.setAttribute("userId", userId);
                newSession.setAttribute("userInfo", user);
                newSession.setMaxInactiveInterval(12 * 60 * 60);
            } else {
                response.sendRedirect("index.jsp");
            }
            
            response.sendRedirect("Home.jsp");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception ex) {
            outw.println(ex.getMessage());
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
