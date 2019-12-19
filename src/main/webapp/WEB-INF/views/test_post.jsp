<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>AP Transport Center | Search by Load ID (Load Retrieve)</title>
	<%@ include file="/WEB-INF/include/cssInclude.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	
	  <%@ include file="/WEB-INF/include/header.jsp" %>
	  <%@ include file="/WEB-INF/include/rightMenu.jsp" %>
	
	  <!-- Content Wrapper. Contains page content -->
	  <div class="content-wrapper">
	    <!-- Content Header (Page header) -->
	    <section class="content-header">
	      <h1>
	       Search by Load ID
	        <small></small>
	      </h1>
	      <ol class="breadcrumb">
	        <li><a href="#"><i class="fas fa-tachometer-alt"></i> &nbsp;&nbsp;Home</a></li>
	        <li><a href="<c:url value='/searchby-loadid' />">Search by Load ID</a></li>                 
	      </ol>
	    </section>
	
	<!-- Main content -->
	<section class="content"> 
		<form method="POST"  data-toggle="validator" role="form" > 
			<!-- .row --> 
			<div class="row" style="margin-bottom:20px">       
				<div class="col-md-3">
					<div class="form-group has-feedback">
						<input type="text" name="loadID" id="loadID" class="form-control" placeholder="LoadID" data-minlength="1" data-error="Minimum of 1 characters" required="required"/>  
					</div>
			    </div>       
			</div>
			<!-- /.row -->
			
			<!-- .row -->
			<div class="row" style="margin-bottom:20px">       
				<div class="col-md-3">
					<div class="form-group  input-group date">
						<div class="input-group-addon">
						<i class="fa fa-calendar"></i> Date : 
						</div>
						<input type="text" class="form-control pull-right datepicker" name="selectDate" id="selectDate" data-error="Please Select End Date" required="required"/>
					</div>
				</div> 
	        </div>

			<!-- /.row -->
			
			<!-- .row -->
			<div class="row" style="margin-bottom:20px">       
				<div class="col-md-3">
					<div class="col-md-1" style="padding-top:3px">
						<input type="submit" value="search"/>
					</div>
				</div> 
			</div>
			<!-- /.row -->
		</form>
		
		 <!-- =========================================================== -->
     	<div class="row">
			<!-- /.col -->
	        <div class="col-md-3">
	           <label  class="col-sm-5 control-label">LoadID. : </label>
	            <div class="col-sm-7" style="padding-top:8px" >
                    ${loadID}
                </div>
	          <!-- /. box -->
	        </div>        
	        <!-- /.col -->
	        <!-- /.col -->
	        <div class="col-md-3">
	           <label  class="col-sm-5 control-label">DateTime. : </label>
	            <div class="col-sm-7" style="padding-top:8px" >
                    ${selectDate}
                </div>
	          <!-- /. box -->
	        </div>        
	        <!-- /.col -->
		</div>      
		<!-- /.row -->
      
	</section>
	<!-- /.content -->
	    
	  </div>
	  <!-- /.content-wrapper -->  
	  
	  <%@ include file="/WEB-INF/include/footer.jsp" %>
	  
	</div>
	<!-- ./wrapper -->

	<%@ include file="/WEB-INF/include/jsInclude.jsp" %>
	<!-- page script -->
	<script>
		 //Date picker
	    $('.datepicker').datepicker({
	      autoclose: true,
	      format: 'yyyy-mm-dd'
	    });
	</script>
</body>
</html>