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

        if ("add".equalsIgnoreCase(accessType)) {
            handleAddReport(request, response);
        } else if ("delete".equalsIgnoreCase(accessType)) {
            handleDeleteReport(request, response);
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

    private void handleAddReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String dateReport = request.getParameter("dateReport");
        String location = request.getParameter("location");
        String remarks = request.getParameter("remarks");
        String useridStr = request.getParameter("userid");

        if (dateReport == null || dateReport.trim().isEmpty() ||
            location == null || location.trim().isEmpty() ||
            remarks == null || remarks.trim().isEmpty()) {
            request.setAttribute("message", "Please insert all values");
            request.setAttribute("errorType", "add");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int userid = Integer.parseInt(useridStr);
            java.sql.Date sqlDate = java.sql.Date.valueOf(dateReport);
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
                String query = "INSERT INTO Report (USERID, DATEOFVISIT, LOCATION, REMARKS, ATTACHMENT) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userid);
                stmt.setDate(2, sqlDate);
                stmt.setString(3, location);
                stmt.setString(4, remarks);
                stmt.setBlob(5, fileContent);
                stmt.executeUpdate();
            }

            request.setAttribute("message", "Data successfully submitted");
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

    private void handleDeleteReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String reportIdStr = request.getParameter("id");

        if (reportIdStr == null || reportIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Invalid report ID");
            request.setAttribute("errorType", "delete");
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
                    request.setAttribute("errorType", "delete");
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
            request.setAttribute("errorType", "delete");
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
