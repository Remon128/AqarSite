/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controllers.AdvertisementJpaController;
import Controllers.UseraccountJpaController;
import Entities.Advertisement;
import Entities.Useraccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lenovo
 */
@WebServlet(name = "AddAdvertisement", urlPatterns = {"/AddAdvertisement"})
public class AddAdvertisement extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("Register.jsp");
        } else {
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("Register.jsp");
            } else {
                int ID = (Integer) session.getAttribute("userId");
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("AqarTestPU");
                AdvertisementJpaController adController = new AdvertisementJpaController(emf);
                UseraccountJpaController userController = new UseraccountJpaController(emf);
                Advertisement ad = new Advertisement();
                Useraccount user = userController.findUseraccount(ID);
                ad.setAccountID(user);
                ad.setTitle(request.getParameter("title"));
                ad.setAdType(Integer.parseInt(request.getParameter("adType")));
                ad.setDescription(request.getParameter("description"));
                ad.setSize(Integer.parseInt(request.getParameter("size")));
                ad.setFloor(Integer.parseInt(request.getParameter("floor")));
                ad.setPropStatus(request.getParameter("propStatus"));
                ad.setPropType(request.getParameter("propType"));
                try {
                    adController.create(ad);
                    response.sendRedirect("Advertisements.jsp");
                } catch (Exception ex) {
                    Logger.getLogger(AddAdvertisement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddAdvertisement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddAdvertisement at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
