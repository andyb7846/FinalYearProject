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
          <h1 class="h3 mb-2 text-gray-800">Properties</h1>

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
                            <h4 class="modal-title">Add Property</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                          </div>

                          <!-- Modal body -->
                          <div class="modal-body">
                            <form class="user" id="myForm" action="${pageContext.request.contextPath}/web/property/add" method="post">
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="company_id" name="company_id" placeholder="Enter Company id...">
                                        </div>
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="street_name" name="street_name" placeholder="Enter Street Name...">
                                        </div>
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="house_number" name="house_number" placeholder="Enter House Number...">
                                        </div>
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="post_code" name="post_code" placeholder="Enter Post Code...">
                                        </div>
                                        <div class="form-group">
                                          <input type="text" class="form-control form-control-user" id="yearly_cost" name="yearly_cost" placeholder="Enter Yearly Cost...">
                                        </div>

                                      </form>
                          </div>

                          <!-- Modal footer -->
                          <div class="modal-footer">
                            <button id="modal_add" type="button" class="btn btn-danger">Add</button>
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
                      <th>Property id</th>
                      <th>Company id</th>
                      <th>Street Name</th>
                      <th>House Number</th>
                      <th>Post Code</th>
                      <th>Yearly Cost</th>
                      <th>Create time</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>Property id</th>
                      <th>Company id</th>
                      <th>Street Name</th>
                      <th>House Number</th>
                      <th>Post Code</th>
                      <th>Yearly Cost</th>
                      <th>Create time</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <c:forEach items="${properties}" var="property" varStatus="tagStatus">
                    <tr>
                      <td>${property.property_id}</td>
                      <td>${property.company.company_id}</td>
                      <td>${property.street_name}</td>
                      <td>${property.house_number}</td>
                      <td>${property.post_code}</td>
                      <td>${property.yearly_cost}</td>
                      <td>${property.create_time}</td>
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
  var property_id = "";
  var company_id = "";
  var street_name = "";
  var house_number = "";
  var post_code = "";
  var yearly_cost = "";


	  $(document).ready(function() {
	  var table = $('#dataTable').DataTable();


		$('#dataTable tbody').on( 'click', 'tr', function () {
			if ( $(this).hasClass('selected') ) {
				$(this).removeClass('selected');
				property_id = "";
                company_id = "";
                street_name = "";
                house_number = "";
                post_code = "";
                yearly_cost = "";

			}
			else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				property_id = $(this).find("td:nth-child(1)").text();
				company_id = $(this).find("td:nth-child(2)").text();
				street_name = $(this).find("td:nth-child(3)").text();
				house_number = $(this).find("td:nth-child(4)").text();
				post_code = $(this).find("td:nth-child(5)").text();
				yearly_cost = $(this).find("td:nth-child(6)").text();
			}
		} );

		$('#delete').click(function(){
			window.location.replace("https://127.0.0.1:8443/SpringMVCHibernate/web/property/delete?property_id=" + property_id);
			console.log(property_id)
		});

		$('#update').click(function(){
            is_add = 0;
            $('#modal_title').text('Update Property');
		    $("#modal_add").prop('value', 'Update');
            $('#company_id').val(company_id);
            $('#street_name').val(street_name);
            $('#house_number').val(house_number);
            $('#post_code').val(post_code);
            $('#yearly_cost').val(yearly_cost);
        });

        $('#add').click(function(){
           is_add = 1;
           $('#modal_title').text('Add Property');
		   $("#modal_add").prop('value', 'Add');
            $('#company_id').val("");
            $('#street_name').val("");
            $('#house_number').val("");
            $('#post_code').val("");
            $('#yearly_cost').val("");
        });

        $('#myModal').on('show.bs.modal', function (e) {
           if(property_id == "" && is_add == 0){
               return e.preventDefault();
           }
        });
        
        $('#modal_add').click(function(){
            if(is_add == 1){
                $('#property_id').remove();
                $('#myForm').attr('action', "property/add").submit();
                $('#myForm').submit();
            }
            else{
                var a = '<input id="property_id" type="hidden" name="property_id" value="' + property_id + '" />';
                $('#myForm').append(a);
                $('#myForm').attr('action', "property/update").submit();
            }
        });
    });
  </script>
  
</body>

</html>
