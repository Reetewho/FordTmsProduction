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
		<form method="POST" action="<c:url value='/searchby-loadid-processloadRetrieve' />" data-toggle="validator" role="form" > 
			<!-- .row --> 
			<div class="row" style="margin-bottom:20px">       
				<div class="col-md-3">
					<div class="form-group has-feedback">
						<input type="text" name="loadID" id="loadID" value="${loadID}" class="form-control" placeholder="LoadID" data-minlength="1" data-error="Minimum of 1 characters" required="required"/>  
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
						<input type="text" class="form-control pull-right datepicker" name="selectDate" id="selectDate" value="${selectDate}" data-error="Please Select End Date" required="required"/>
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
	        <div class="col-md-12">
	          <div class="box box-primary">
	          	<div class="box-header">
		            <c:if test="${Error!=null} }">
		              <div class="alert alert-danger alert-dismissible">
		                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
		                <h4><i class="icon fa fa-ban"></i> Error!</h4>
		                <c:out value="${Error} }"></c:out>
		              </div>
		            </c:if>
	            </div>
	            <!-- /.box-header -->
	            <div class="box-body ">
	              <table id="loadStopTable" class="table table-bordered table-striped">
	                <thead>
	                <tr>
	                  	<th >Stop Sequence</th>
						<th >Route No.</th>
						<th >Pickup GSDB</th>
						<th >Pickup Supplier Name</th>
						<th >Truck Number</th>
						<th >Arrive Time</th>
						<th >Departure Time</th>
						<th >Status</th>
						<th></th>
						<th></th>                  
	                </tr>
	                </thead>
	                <tbody>
		                <c:forEach items="${loadStops}" var="loadStop">
							<tr >
							<td>${loadStop.stopSequence}</td>
							<td>${load.loadDescription}</td>
							<td>${loadStop.stopShippingLocation}</td>
							<td>${loadStop.stopShippingLocationName}</td>
							<td>${loadStop.truckNumber}</td>
							<td>
							<fmt:parseDate value="${loadStop.arriveTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedArriveTime" type="both" />
							<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss" value="${ parsedArriveTime }" />
							</td>
							<td>
							<fmt:parseDate value="${loadStop.departureTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDepartureTime" type="both" />
							<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss" value="${ parsedDepartureTime }" />
							</td>
							<td>${loadStop.status}</td>
							<td><a href="<c:url value='/loadStatusUpdate/${loadDate}/${load.systemLoadID}-${loadStop.stopShippingLocation}-${loadStop.id}' />">Status Update</a></td>
							<!-- td><a href="<c:url value='/setStopETA/${load.carrier.carrierCode}-${load.systemLoadID}-${loadStop.stopShippingLocation}-${loadStop.id}' />">Set Stop ETA</a></td-->
							 
							<c:choose>
								<c:when test="${loadStop.status=='update'}">
									<td></td>
								</c:when>
								<c:otherwise>
									<td><a href="<c:url value='/setStopETA/${loadDate}/${load.systemLoadID}-${loadStop.stopShippingLocation}-${loadStop.id}' />">Set Stop ETA</a></td>
								</c:otherwise>
							</c:choose>	
									
							</tr>
						</c:forEach>
	                              
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
		 //Date picker
	    $('.datepicker').datepicker({
	      autoclose: true,
	      format: 'yyyy-mm-dd'
	    });
	</script>
</body>
</html>