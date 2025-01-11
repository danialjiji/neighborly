/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
/**
 *
 * @author soleha
 */
@MultipartConfig
@WebServlet(urlPatterns = {"/Resident/residentController"})
public class residentController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
           String accessType = request.getParameter("accessType");

        if ("add".equalsIgnoreCase(accessType)) {
           addComplaints (request, response);
        } /*else if ("edit".equalsIgnoreCase(accessType)) {
            //editEmployee(request, response);
        }*/ else {
            try (PrintWriter out = response.getWriter()) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Invalid Access Type</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Invalid access type: " + accessType + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
    
    private void addComplaints (HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
         // Database connection details
        String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
        String dbUser = "neighbourlynew";
        String dbPassword = "system";
        
         String complaintType = request.getParameter("complaintType");
        String description = request.getParameter("description");
        String date = request.getParameter("date");
        String location = request.getParameter("location");

        // Handle file upload (optional)
        Part filePart = request.getPart("attachment");
        String fileName = filePart.getSubmittedFileName();

        
        //Convert date to java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);

        // Database insertion logic
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "neighbourlynew", "system");
            String query = "INSERT INTO Complaint (complaint_type_name, complaint_type_desc, complaint_date, complaint_location, complaint_attachment) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, complaintType);
            stmt.setString(2, description);
            stmt.setDate(3, sqlDate);
            stmt.setString(4, location);
            stmt.setString(5, fileName);
            conn.close();
            
            request.setAttribute("successMessage", "Complaint added successfully!");
            request.getRequestDispatcher("complaints.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error adding complaint: " + e.getMessage());
            request.getRequestDispatcher("complaints.jsp").forward(request, response);
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
