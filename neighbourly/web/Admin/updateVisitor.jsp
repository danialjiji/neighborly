<%-- 
    Document   : Visitor
    Created on : Dec 23, 2024, 9:23:23 AM
    Author     : Dean Ardley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="java.sql.*"%>

<%
    String updateRegisterID = request.getParameter("registerID");
    
    int updateUserID = 0;  
    String updateVisitorName = "";
    String updateVisitorIC = "";
    String updatePlateNumber = "";
    String updateEntryTime = "";
    String updateExitTime = "";
    String updateVisitDate = "";
    String updatePurposeOfVisit = "";
    String updatePhoneNumber = "";
    
    if(updateRegisterID != null && !updateRegisterID.trim().isEmpty()){
        try{
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/neighborly", "app", "app");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM visitor WHERE registerID = ?");
            stmt.setInt(1, Integer.parseInt(updateRegisterID));
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                updateUserID = rs.getInt("userID");
                updateVisitorName = rs.getString("visitor_name");
                updateVisitorIC = rs.getString("visitor_ic");
                updatePlateNumber = rs.getString("no_plate");
                updateEntryTime = rs.getString("entryTime");
                updateExitTime = rs.getString("exitTime");
                updateVisitDate = rs.getString("dateOfVisit");
                updatePurposeOfVisit = rs.getString("purposeOfVisit");
                updatePhoneNumber = rs.getString("visitor_phonenum");
                
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
%>

<%
    if(request.getMethod().equals("post")){
        String registerID = request.getParameter("registerID");
        String userID = request.getParameter("userID");
        String visitorName = request.getParameter("visitorName");
        String plateNumber = request.getParameter("plateNumber");
        String entryTime = request.getParameter("entryTime");
        String exitTime = request.getParameter("exitTime");
        String visitDate = request.getParameter("visitDate");
        String purposeOfVisit = request.getParameter("purposeOfVisit");
        String phoneNumber = request.getParameter("phoneNumber");
        
        try{
            //Connection conn = DriverManager.getConnection();
            //PreparedStatement stmt = conn.prepareStatement();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html
  lang="en"
  class="light-style layout-navbar-fixed layout-menu-fixed layout-compact"
  dir="ltr"
  data-theme="theme-default"
  data-assets-path="../assets/"
  data-template="vertical-menu-template-starter">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

    <title>Visitor</title>

    <meta name="description" content="" />

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="../assets/img/favicon/favicon.ico" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css" integrity="sha512-5Hs3dF2AEPkpNAR7UiOHba+lRSJNeM2ECkwxUIxC1Q/FLycGTbNapWXB4tP889k5T5Ju8fs4b1P5z/iB4nMfSQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&ampdisplay=swap"
      rel="stylesheet" />

    <link rel="stylesheet" href="../assets/vendor/fonts/tabler-icons.css" />
    <!-- <link rel="stylesheet" href="../../assets/vendor/fonts/fontawesome.css" /> -->
    <!-- <link rel="stylesheet" href="../../assets/vendor/fonts/flag-icons.css" /> -->

    <!-- Core CSS -->
    <link rel="stylesheet" href="../assets/vendor/css/rtl/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="../assets/vendor/css/rtl/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="../assets/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="../assets/vendor/libs/node-waves/node-waves.css" />
    <link rel="stylesheet" href="../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="../assets/vendor/js/helpers.js"></script>
    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Template customizer: To hide customizer set displayCustomizer value false in config.js.  -->
    <script src="../assets/vendor/js/template-customizer.js"></script>
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../assets/js/config.js"></script>
  </head>

  <body>
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <!-- Menu -->

        <aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
          <div class="app-brand demo">
            <a href="index.html" class="app-brand-link">
              <span class="app-brand-logo demo">
                <svg width="32" height="22" viewBox="0 0 32 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M0.00172773 0V6.85398C0.00172773 6.85398 -0.133178 9.01207 1.98092 10.8388L13.6912 21.9964L19.7809 21.9181L18.8042 9.88248L16.4951 7.17289L9.23799 0H0.00172773Z"
                    fill="#7367F0" />
                  <path
                    opacity="0.06"
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M7.69824 16.4364L12.5199 3.23696L16.5541 7.25596L7.69824 16.4364Z"
                    fill="#161616" />
                  <path
                    opacity="0.06"
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M8.07751 15.9175L13.9419 4.63989L16.5849 7.28475L8.07751 15.9175Z"
                    fill="#161616" />
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M7.77295 16.3566L23.6563 0H32V6.88383C32 6.88383 31.8262 9.17836 30.6591 10.4057L19.7824 22H13.6938L7.77295 16.3566Z"
                    fill="#7367F0" />
                </svg>
              </span>
              <span class="app-brand-text demo menu-text fw-bold">Neighbourly</span>
            </a>

            <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto">
              <i class="ti menu-toggle-icon d-none d-xl-block ti-sm align-middle"></i>
              <i class="ti ti-x d-block d-xl-none ti-sm align-middle"></i>
            </a>
          </div>

          <div class="menu-inner-shadow"></div>

            <ul class="menu-inner py-1">
                <!-- Page -->
                <li class="menu-item">
                    <a href="Dashboard.jsp" class="menu-link">
                    <i class="menu-icon fa-solid fa-chart-line"></i>
                    <div data-i18n="Page 1">Dashboard</div>
                    </a>
                </li>

                <li class="menu-item active">
                    <a href="Visitor.jsp" class="menu-link">
                    <i class="menu-icon fa-solid fa-user"></i>
                    <div data-i18n="Page 1">Visitor</div>
                    </a>
                </li>

                <li class="menu-item">
                    <a href="Complaints.jsp" class="menu-link">
                    <i class="menu-icon fa-solid fa-pen-clip"></i>
                    <div data-i18n="Page 1">Complaints</div>
                    </a>
                </li>

                <li class="menu-item">
                    <a href="Report.jsp" class="menu-link">
                    <i class="menu-icon fa-solid fa-file-lines"></i>
                    <div data-i18n="Page 2">Report</div>
                    </a>
                </li>

                <li class="menu-item">
                    <a href="Fee.jsp" class="menu-link">
                    <i class="menu-icon fa-solid fa-dollar-sign"></i>
                    <div data-i18n="Page 1">Fee</div>
                    </a>
                </li>
            </ul>
        </aside>
        <!-- / Menu -->

        <!-- Layout container -->
        <div class="layout-page">
          <!-- Navbar -->

          <nav
            class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme"
            id="layout-navbar">
            <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
                <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                    <i class="ti ti-menu-2 ti-sm"></i>
                </a>
            </div>

            <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
              <div class="navbar-nav align-items-center">
                <div class="nav-item dropdown-style-switcher dropdown me-2 me-xl-0">
                  <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                    <i class="ti ti-md"></i>
                  </a>
                  <ul class="dropdown-menu dropdown-menu-start dropdown-styles">
                    <li>
                      <a class="dropdown-item" href="javascript:void(0);" data-theme="light">
                        <span class="align-middle"><i class="ti ti-sun me-2"></i>Light</span>
                      </a>
                    </li>
                    <li>
                      <a class="dropdown-item" href="javascript:void(0);" data-theme="dark">
                        <span class="align-middle"><i class="ti ti-moon me-2"></i>Dark</span>
                      </a>
                    </li>
                    <li>
                      <a class="dropdown-item" href="javascript:void(0);" data-theme="system">
                        <span class="align-middle"><i class="ti ti-device-desktop me-2"></i>System</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </div>

              <ul class="navbar-nav flex-row align-items-center ms-auto">
                <!-- User -->
                <li class="nav-item navbar-dropdown dropdown-user dropdown">
                  <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                    <div class="avatar avatar-online">
                      <img src="../assets/img/avatars/1.png" alt class="h-auto rounded-circle" />
                    </div>
                  </a>
                  <ul class="dropdown-menu dropdown-menu-end">
                    <li>
                      <a class="dropdown-item" href="#">
                        <div class="d-flex">
                          <div class="flex-shrink-0 me-3">
                            <div class="avatar avatar-online">
                              <img src="../assets/img/avatars/1.png" alt class="h-auto rounded-circle" />
                            </div>
                          </div>
                          <div class="flex-grow-1">
                            <span class="fw-medium d-block">John Doe</span>
                            <small class="text-muted">Admin</small>
                          </div>
                        </div>
                      </a>
                    </li>
                    <li>
                      <div class="dropdown-divider"></div>
                    </li>
                    <li>
                      <a class="dropdown-item" href="#">
                        <i class="ti ti-user-check me-2 ti-sm"></i>
                        <span class="align-middle">My Profile</span>
                      </a>
                    </li>
                    <li>
                      <a class="dropdown-item" href="#">
                        <i class="ti ti-settings me-2 ti-sm"></i>
                        <span class="align-middle">Settings</span>
                      </a>
                    </li>
                    <li>
                      <a class="dropdown-item" href="#">
                        <span class="d-flex align-items-center align-middle">
                          <i class="flex-shrink-0 ti ti-credit-card me-2 ti-sm"></i>
                          <span class="flex-grow-1 align-middle">Billing</span>
                          <span class="flex-shrink-0 badge badge-center rounded-pill bg-label-danger w-px-20 h-px-20"
                            >2</span
                          >
                        </span>
                      </a>
                    </li>
                    <li>
                      <div class="dropdown-divider"></div>
                    </li>
                    <li>
                      <a class="dropdown-item" href="#">
                        <i class="ti ti-logout me-2 ti-sm"></i>
                        <span class="align-middle">Log Out</span>
                      </a>
                    </li>
                  </ul>
                </li>
                <!--/ User -->
              </ul>
            </div>
          </nav>

          <!-- / Navbar -->

          <!-- Content wrapper -->
          <div class="content-wrapper">
            <!-- Content -->
  
            <div class="container-xxl flex-grow-1 container-p-y">
                <h4 class="py-3 mb-4"><span class="text-muted fw-light">Update Visitor</h4>
                
                <!-- Basic Layout -->
                <div class="row">
                    <div class="col-xl">
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5 class="mb-0">Update Visitor Information</h5>
                                <small class="text-muted float-end">Default label</small>
                            </div>
                            
                            <div class="card-body">
                                <form action="Visitor.jsp" method="post">

                                    <div class="mb-3">
                                        <label class="form-label" for="basic-default-fullname">Register ID</label>
                                        <input type="text" class="form-control" id="basic-default-fullname" name="registerID" placeholder="Register ID" value="<%= updateRegisterID %>" readonly>
                                    </div>

                                    <div class="mb-3">
                                        <label for="defaultSelect" class="form-label">User ID</label>
                                        <select name="userID" class="form-select">
                                            <sql:query var="result" dataSource="${myDatasource}">
                                                SELECT userID FROM admin
                                            </sql:query>
                                                
                                            <option value="<%= updateUserID %>" required><%= updateUserID %></option>
                                            <c:forEach var="row" items="${result.rowsByIndex}">
                                                <c:forEach var="column" items="${row}">
                                                    <option value="${column}"><c:out value="${column}"/></option>
                                                </c:forEach>
                                            </c:forEach>                                                                                                                                                                           
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Visitor Name:</label>
                                        <input type="text" class="form-control" id="basic-default-fullname" name="visitorName" placeholder="Enter visitor name" value="<%= updateVisitorName%>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Plate Number:</label>
                                        <input type="text" class="form-control" id="basic-default-fullname" name="plateNumber" placeholder="Enter plate number" value="<%= updatePlateNumber %>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Entry Time:</label>
                                        <input type="time" class="form-control" id="basic-default-fullname" name="entryTime" value="<%= updateEntryTime%>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Exit Time:</label>
                                        <input type="time" class="form-control" id="basic-default-fullname" name="exitTime" value="<%= updateExitTime %>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Date of Visit:</label>
                                        <input type="date" class="form-control" id="basic-default-fullname" name="visitDate" value="<%= updateVisitDate%>" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Purpose of Visit:</label>
                                        <textarea type="text" name="purposeOfVisit" id="basic-default-message"
                                        class="form-control"
                                        value="<%= updatePurposeOfVisit %>" required><%= updatePurposeOfVisit %></textarea>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Phone Number:</label>
                                        <input type="tel" class="form-control" id="basic-default-fullname" placeholder="Enter phone number" name="phoneNumber" value="<%= updatePhoneNumber %>" required>
                                    </div>

                                    <button type="submit" class="btn btn-primary">Update</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                
               
            
            
        <!-- / Content -->

            <!-- Footer -->
            <footer class="content-footer footer bg-footer-theme">
                <div class="container-xxl">
                    <div
                        class="footer-container d-flex align-items-center justify-content-between py-2 flex-md-row flex-column">
                  
                    </div>
                </div>
            </footer>
            <!-- / Footer -->

            <div class="content-backdrop fade"></div>
          </div>
          <!-- Content wrapper -->
        </div>
        <!-- / Layout page -->
      </div>

      <!-- Overlay -->
      <div class="layout-overlay layout-menu-toggle"></div>

      <!-- Drag Target Area To SlideIn Menu On Small Screens -->
      <div class="drag-target"></div>
    </div>
    <!-- / Layout wrapper -->

    <!-- Core JS -->
    <!-- build:js assets/vendor/js/core.js -->

    <script src="../assets/vendor/libs/jquery/jquery.js"></script>
    <script src="../assets/vendor/libs/popper/popper.js"></script>
    <script src="../assets/vendor/js/bootstrap.js"></script>
    <script src="../assets/vendor/libs/node-waves/node-waves.js"></script>
    <script src="../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>
    <script src="../assets/vendor/libs/hammer/hammer.js"></script>

    <script src="../assets/vendor/js/menu.js"></script>

    <!-- endbuild -->

    <!-- Vendors JS -->

    <!-- Main JS -->
    <script src="../assets/js/main.js"></script>

    <!-- Page JS -->
  
  </body>
</html>
