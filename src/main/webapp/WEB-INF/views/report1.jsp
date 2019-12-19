<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AP Transport Center | Operation Report </title>
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
       Operation Report
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fas fa-tachometer-alt"></i> &nbsp;&nbsp;Home</a></li>
        <li><a href="#' />">Operation Report</a></li>                 
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">  
     <div class="row" style="margin-bottom:20px"> 
     	<form method="POST" action="<c:url value='/report' />" data-toggle="validator" role="form" >        
        <div class="col-md-2" ></div>
        <div class="col-md-3">
        				<div class="form-group  input-group date">
		                  <div class="input-group-addon">
		                    <i class="fa fa-calendar"></i> Start Date : 
		                  </div>
		                  <input type="text" class="form-control pull-right datepicker" name="startDate" id="startDate" value="${startDate}" data-error="Please Select Start Date" required="required"/>
						  
						</div>
        </div>        
        <div class="col-md-1" ></div>
        <div class="col-md-3">
        				<div class="form-group  input-group date">
		                  <div class="input-group-addon">
		                   <i class="fa fa-calendar"></i> End Date : 
		                  </div>
		                  <input type="text" class="form-control pull-right datepicker" name="endDate" id="endDate" value="${endDate}" data-error="Please Select End Date" required="required"/>
						  
						</div>
        </div>
        <div class="col-md-1" style="padding-top:3px"><input type="submit" value="search"/></div>
       <div class="col-md-2"></div>
       </form>
     </div>
      <div class="row">
      <!-- /.col -->
        <div class="col-md-12">
          <div class="box box-primary">            	      	
            <div class="box-body ">
              <table id="reportTable" class="table table-bordered table-striped">
                <thead>
                <tr>
                  	<th >Load ID</th>
					<th >Alert Type Code</th>
					<th >Route No.</th>
					<th >Pickup GSDB</th>
					<th >Pickup Supplier Name</th>
					<th >Truck Number</th>
					<th >Arrive Time</th>
					<th >Departure Time</th>
					<th >Load Start Date Time</th>
					<th >Load End Date Time</th>
					<th >Status</th>
					<th >Movement Date Time</th>
					<th >Estimated Date Time At Stop</th>
					<th >Latitude</th>
					<th >Longitude</th>                  
                </tr>
                </thead>
                <tbody>
	               <c:if test = "${not empty report}">
						<c:forEach items="${report}" var="report">
							<tr >
							<td>${report.systemLoadID}</td>
							<td>${report.alertTypeCode}</td>
							<td>${report.loadDescription}</td>
							<td>${report.stopShippingLocation}</td>
							<td>${report.stopShippingLocationName}</td>
							<td>${report.truckNumber}</td>
							<td>${report.arriveTime}</td>				
							<td>${report.departureTime}</td>
							<td>${report.loadStartDateTime}</td>				
							<td>${report.loadEndDateTime}</td>
							<td>${report.completedFlag}</td>
							<td>${report.movementDateTime}</td>
							<td>${report.estimatedDateTime}</td>				
							<td>${report.latitude}</td>
							<td>${report.longitude}</td>
							</tr>
						</c:forEach>
					</c:if>    
                </tbody>                
              </table>
              
            </div>
            <!-- /.box-body -->
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
var strStartDate='${startDate}';
var strEndDate='${endDate}';
var sd = strStartDate.split("-");
var ed=strEndDate.split("-");
var d = sd[2]+sd[1]+sd[0]+' - '+ed[2]+ed[1]+ed[0];

  $(function () {	  	 
	  
	  
	  	$("#reportTable").DataTable({
	        dom: "<'row'<'col-sm-7'l><'col-sm-2'B><'col-sm-3'f>>" +
	        "<'row'<'col-sm-12'tr>>" +
	        "<'row'<'col-sm-5'i><'col-sm-7'p>>",
	        buttons: [{extend: 'excelHtml5',text: 'Export To Excel',filename: 'Report_'+d}],
	        scrollX: true
	            });
	  	
	  	 //Date picker
	    $('.datepicker').datepicker({
	      autoclose: true,
	      format: 'yyyy-mm-dd'
	    });
  });
</script>
</body>
</html>
