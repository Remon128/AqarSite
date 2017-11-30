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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lenovo
 */
@WebServlet(name = "DataBaseTester", urlPatterns = {"/DataBaseTester"})
public class DataBaseTester extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    EntityManagerFactory emf;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if(emf == null)
            emf = Persistence.createEntityManagerFactory("AqarSitePU");
        UseraccountJpaController accountCont = new UseraccountJpaController(emf);
        AdvertisementJpaController adCont = new AdvertisementJpaController(emf);
        Useraccount account = new Useraccount(2);
        account.setUsername("haitham");
        account.setEmail("haitham@gmail.com");
        account.setUserpassword("private");
        try {
            accountCont.create(account);
        } catch (Exception ex) {
            Logger.getLogger(DataBaseTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Useraccount> useraccounts = accountCont.findUseraccountEntities();
        List<Advertisement> ads = adCont.findAdvertisementEntities();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DataBaseTester</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>users are" + useraccounts+ "</h1>");
            out.println("<h1>ads are" + ads+ "</h1>");
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
