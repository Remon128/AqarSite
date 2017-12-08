/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Useraccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

        ArrayList<Useraccount> userList = new ArrayList<Useraccount>();

        try (PrintWriter out = response.getWriter()) {

            outw = out;

            out.println("<h>" + email + "</h><br>");

            Map<String, HttpSession> sessionsManager = (HashMap<String, HttpSession>) this
                    .getServletContext()
                    .getAttribute("sessionsManager");

            if (sessionsManager == null) {//sessionsManager not exist
                out.print("sessionManger == null");
                request.getServletContext().removeAttribute("MyCurrentSession");
                response.sendRedirect("intro.jsp");
            }

            //Create session
            HttpSession newSession = request.getSession(true);
            //newSession.setAttribute("username", username);
            newSession.setMaxInactiveInterval(3 * 60);

            //Create user object
            //Useraccount user = new Useraccount(newSession.getId(),
              //      username,
                //    email,
                  //  phone);

            //Create cookie that contains sessionId
            Cookie myCurrentSession = new Cookie("MyCurrentSession", newSession.getId());
            myCurrentSession.setMaxAge(3 * 60);
            myCurrentSession.setPath("/");

            //put session(sessId -> username) into session manager
            sessionsManager.put(newSession.getId(), newSession);
            request.getServletContext().setAttribute("sessionsManager", sessionsManager);

            //insert into db
           // Database.insertIntoDB(out, user);
            response.addCookie(myCurrentSession);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StoreTheSession</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StoreTheSession at " + request.getContextPath() + "</h1>");

            if (sessionsManager != null) {
                out.println("<h>" + "Sessiosn = " + sessionsManager.size() + "</h><br>");

                for (String c : sessionsManager.keySet()) {
                    out.println("<h>" + c + "</h><br>");
                }
            }

            response.sendRedirect("intro.jsp");

            String form = "<form  action=\"Logout\" method=\"post\">"
                    + "<input type=\"submit\" value=\"Logout\"/>"
                    + "</form>";

            out.print(form);

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
