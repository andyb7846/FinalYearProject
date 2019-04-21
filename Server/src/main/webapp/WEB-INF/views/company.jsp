<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin 2 - Tables</title>

  <!-- Custom fonts for this template -->
  <link href="${pageContext.request.contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/resources/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="${pageContext.request.contextPath}/resources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="user">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">SB Admin <sup>2</sup></div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Tables -->
      <li class="nav-item active">
        <a class="nav-link" href="user">
          <i class="fas fa-fw fa-table"></i>
          <span>Users</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Nav Item - Tables -->
      <li class="nav-item active">
        <a class="nav-link" href="company">
          <i class="fas fa-fw fa-table"></i>
          <span>Companies</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">


      <!-- Nav Item - Tables -->
      <li class="nav-item active">
        <a class="nav-link" href="employee">
          <i class="fas fa-fw fa-table"></i>
          <span>Employees</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">


      <!-- Nav Item - Tables -->
      <li class="nav-item active">
        <a class="nav-link" href="property">
          <i class="fas fa-fw fa-table"></i>
          <span>Properties</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">


      <!-- Nav Item - Tables -->
      <li class="nav-item active">
        <a class="nav-link" href="device">
          <i class="fas fa-fw fa-table"></i>
          <span>Devices</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">


      <!-- Nav Item - Tables -->
      <li class="nav-item active">
        <a class="nav-link" href="vehicle">
          <i class="fas fa-fw fa-table"></i>
          <span>Vehicles</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>


          <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">


                      <div class="topbar-divider d-none d-sm-block"></div>

                      <!-- Nav Item - User Information -->
                      <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                          <span class="mr-2 d-none d-lg-inline text-gray-600 small">${admin.name}</span>
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">

                          <a class="dropdown-item" href="logout">
                            <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                            Logout
                          </a>
                        </div>
                      </li>

                    </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Companies</h1>

          <button id='add' type="button" class="btn btn-primary btn-icon-split" data-toggle="modal" data-target="#myModal">
                      <span class="text">Add</span>
                    </button>


                    <button id='update' type="button" class="btn btn-primary btn-icon-split" data-toggle="modal" data-target="#myModal">
                      <span class="text">Update</span>
                    </button>

                    <a href="#" id="delete" class="btn btn-danger btn-icon-split">
                      <span class="text">Delete</span>
                    </a>

                    <!-- The Modal -->
                    <div class="modal" id="myModal">
                      <div class="modal-dialog">
                        <div class="modal-content">

                          <!-- Modal Header -->
                          <div class="modal-header">
                            <h4 class="modal-title">Add Company</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                          </div>

                          <!-- Modal body -->
                          <div class="modal-body">
                            <form class="user">
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="user_id" placeholder="Enter User id...">
                                        </div>
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="name" placeholder="Enter Name...">
                                        </div>
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="yearly_income" placeholder="Enter Yearly Income...">
                                        </div>

                                      </form>
                          </div>

                          <!-- Modal footer -->
                          <div class="modal-footer">
                            <button type="button" class="btn btn-danger">Add</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                          </div>

                        </div>
                      </div>
                    </div>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Company id</th>
                      <th>User id</th>
                      <th>Name</th>
                      <th>Yearly income</th>
                      <th>Create time</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>Company id</th>
                      <th>User id</th>
                      <th>Name</th>
                      <th>Yearly income</th>
                      <th>Create time</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <c:forEach items="${companies}" var="company" varStatus="tagStatus">
                    <tr>
                      <td>${company.company_id}</td>
                      <td>${company.user.user_id}</td>
                      <td>${company.name}</td>
                      <td>${company.yearly_income}</td>
                      <td>${company.create_time}</td>
                    </tr>
                    </c:forEach>

                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Your Website 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="${pageContext.request.contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="${pageContext.request.contextPath}/resources/js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="${pageContext.request.contextPath}/resources/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="${pageContext.request.contextPath}/resources/js/demo/datatables-demo.js"></script>

<script>
  var is_add = 0;
  var company_id = "";
  var user_id = "";
  var name = "";
  var yearly_income = "";

	  $(document).ready(function() {
	  var table = $('#dataTable').DataTable();


		$('#dataTable tbody').on( 'click', 'tr', function () {
			if ( $(this).hasClass('selected') ) {
				$(this).removeClass('selected');
				company_id = ""
                user_id = "";
                name = "";
                yearly_income = "";
			}
			else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				company_id = $(this).find("td:nth-child(1)").text();
				user_id = $(this).find("td:nth-child(2)").text();
				name = $(this).find("td:nth-child(3)").text();
				yearly_income = $(this).find("td:nth-child(4)").text();
			}
		} );

		$('#delete').click(function(){
			window.location.replace("https://127.0.0.1:8443/SpringMVCHibernate/web/company/delete?company_id=" + company_id);
			console.log(company_id)
		});

		$('#update').click(function(){
            is_add = 0;
            $('#user_id').val(user_id);
            $('#name').val(name);
            $('#yearly_income').val(yearly_income);
        });

        $('#add').click(function(){
           is_add = 1;
           $('#user_id').val("");
           $('#name').val("");
           $('#yearly_income').val("");
        });

        $('#myModal').on('show.bs.modal', function (e) {
           if(company_id == "" && is_add == 0){
               return e.preventDefault();
           }
        });
    });
  </script>
  
</body>

</html>
