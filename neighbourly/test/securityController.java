import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.InputStream;


@MultipartConfig
@WebServlet(urlPatterns = {"/securityController"})
public class securityController extends HttpServlet {

    // Helper method to load the Oracle driver and get a database connection
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver"); // Load the Oracle JDBC driver
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "neighborly", "system");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accessType = request.getParameter("accessType");

        if ("addReport".equalsIgnoreCase(accessType)) {
            handleAddReport(request, response);
        } else if ("deleteReport".equalsIgnoreCase(accessType)) {
            handleDeleteReport(request, response);
        } else if ("addVisitor".equalsIgnoreCase(accessType)) {
            handleAddVisitor(request, response); 
        } else if ("editVisitor".equalsIgnoreCase(accessType)) {
            handleEditVisitor(request, response); 
        } else if ("deleteVisitor".equalsIgnoreCase(accessType)) {
            handleDeleteVisitor(request, response);
        } else {
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
    
    //Add Rounding Report Form
    private void handleAddReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String dateReport = request.getParameter("dateReport");
        String location = request.getParameter("location");
        String remarks = request.getParameter("remarks");
        String useridStr = request.getParameter("userid");

        if (dateReport == null || dateReport.trim().isEmpty() ||
            location == null || location.trim().isEmpty() ||
            remarks == null || remarks.trim().isEmpty()) {
            request.setAttribute("message", "Please insert all values");
            request.setAttribute("errorType", "addReport");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int userid = Integer.parseInt(useridStr);
            Part filePart = request.getPart("attachment");
            if (filePart == null) {
                request.setAttribute("message", "No file uploaded");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            String fileName = filePart.getSubmittedFileName();
            InputStream fileContent = filePart.getInputStream();

            // Use the helper method to get a connection
            try (Connection conn = getConnection()) {
                String query = "INSERT INTO Report (USERID, DATEOFVISIT, LOCATION, REMARKS, ATTACHMENT) VALUES (?, TO_DATE(?, YYYY-MM-DD), ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userid);
                stmt.setString(2, dateReport);
                stmt.setString(3, location);
                stmt.setString(4, remarks);
                stmt.setBlob(5, fileContent);
                stmt.executeUpdate();
            }

            request.setAttribute("message", "Data successfully submitted");
            request.setAttribute("type", "addReport");
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "Invalid user ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) { // Catch any Exception that may occur
            if (e instanceof ClassNotFoundException) {
                // Handle ClassNotFoundException specifically
                System.out.println("Oracle JDBC Driver not found.");
            } else if (e instanceof SQLException) {
                // Handle SQLException specifically
                System.out.println("A database error occurred: " + e.getMessage());
            } else {
                // Handle other exceptions
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

            e.printStackTrace(); // Print the stack trace for debugging
            request.setAttribute("message", "An error occurred while processing your request");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }
    
    //Add Visitor Form
    private void handleAddVisitor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String visitorName = request.getParameter("visitorname");
        String icPassport = request.getParameter("icpassport");
        String plateNo = request.getParameter("plateno");
        String entryTime = request.getParameter("entrytime");
        String dateVisit = request.getParameter("datevisit");
        String purposeVisit = request.getParameter("purposevisit");
        String phoneNo = request.getParameter("phoneno");
        String useridStr = request.getParameter("userid");

        if (visitorName == null || visitorName.trim().isEmpty() ||
            icPassport == null || icPassport.trim().isEmpty() ||
            plateNo == null || plateNo.trim().isEmpty() ||
            entryTime == null || entryTime.trim().isEmpty() ||
            dateVisit == null || dateVisit.trim().isEmpty() ||
            purposeVisit == null || purposeVisit.trim().isEmpty() ||
            phoneNo == null || phoneNo.trim().isEmpty()) {
            request.setAttribute("message", "Please insert all values");
            request.setAttribute("errorType", "addVisitor");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int userid = Integer.parseInt(useridStr);

            try (Connection conn = getConnection()) {
                String query = "INSERT INTO VISITOR (USERID, VISITOR_NAME, VISITOR_IC, NO_PLATE, ENTRYTIME, EXITTIME, DATEOFVISIT, PURPOSEOFVISIT, VISITOR_PHONENUM) VALUES (?, ?, ?, ?, TO_TIMESTAMP(?, 'HH24:MI'), ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userid);
                stmt.setString(2, visitorName);
                stmt.setString(3, icPassport);
                stmt.setString(4, plateNo);
                stmt.setString(5, entryTime);
                stmt.setString(6, null);
                stmt.setString(7, dateVisit);
                stmt.setString(8, purposeVisit);
                stmt.setString(9, phoneNo);
                stmt.executeUpdate();
            }

            request.setAttribute("message", "Data successfully submitted");
            request.setAttribute("type", "addVisitor");
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "Invalid user ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (Exception e) {
                if (e instanceof ClassNotFoundException) {
                    System.out.println("Oracle JDBC Driver not found.");
                } else if (e instanceof SQLException) {
                    System.out.println("A database error occurred: " + e.getMessage());
                } else {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }

                e.printStackTrace();
                request.setAttribute("message", "An error occurred while processing your request");
                request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    //Exit Visitor
    private void handleEditVisitor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String visitorIdStr = request.getParameter("id"); // Assuming visitor ID is passed as a parameter

        if (visitorIdStr == null || visitorIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Visitor ID is required to update exit time");
            request.setAttribute("errorType", "editVisitor");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int visitorId = Integer.parseInt(visitorIdStr);

            // Get the current time
            java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());

            // Use the helper method to get a connection
            try (Connection conn = getConnection()) {
                String query = "UPDATE VISITOR SET EXITTIME = ? WHERE REGISTERID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setTimestamp(1, currentTime); // Set the current time
                stmt.setInt(2, visitorId); // Set the visitor ID

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    request.setAttribute("message", "Exit time successfully updated for Visitor ID: " + visitorId);
                    request.getRequestDispatcher("VisitorTable.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "No visitor found with the specified ID");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "Invalid Visitor ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            if (e instanceof SQLException) {
                System.out.println("A database error occurred: " + e.getMessage());
            } else {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

            e.printStackTrace();
            request.setAttribute("message", "An error occurred while updating the visitor");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    //Delete Report
    private void handleDeleteReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String reportIdStr = request.getParameter("id");

        if (reportIdStr == null || reportIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Invalid report ID");
            request.setAttribute("errorType", "deleteReport");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int reportId = Integer.parseInt(reportIdStr);

            // Use the helper method to get a connection
            try (Connection conn = getConnection()) {
                String query = "DELETE FROM Report WHERE REPORTID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, reportId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    request.setAttribute("message", "Report successfully deleted");
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "Report not found");
                    request.setAttribute("errorType", "deleteReport");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "Invalid report ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while deleting the report");
            request.setAttribute("errorType", "deleteReports");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    //Delete Visitor
    private void handleDeleteVisitor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String registerIdStr = request.getParameter("id");

        if (registerIdStr == null || registerIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Invalid Visitor ID");
            request.setAttribute("errorType", "deleteVisitor");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int reportId = Integer.parseInt(registerIdStr);

            // Use the helper method to get a connection
            try (Connection conn = getConnection()) {
                String query = "DELETE FROM Visitor WHERE REGISTERID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, reportId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    request.setAttribute("message", "Report successfully deleted");
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "Report not found");
                    request.setAttribute("errorType", "deleteVisitor");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "Invalid report ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while deleting the report");
            request.setAttribute("errorType", "deleteVisitor");
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
