<%@page import="org.w3c.dom.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AP Transport Center | Manual Add Load</title>
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
       Manual Add Load
        <small></small>
      </h1>
       <ol class="breadcrumb">
        <li><a href="<c:url value='/calendar'/>"><i class="fas fa-tachometer-alt"></i> &nbsp;&nbsp;Home</a></li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
     <div class="row">
      	<div class="col-md-12">
      		<c:if test="${Warning!=null || Success!=null }">
              <div class='alert ${Warning!=null?"alert-warning":"alert-success"}  alert-dismissible'>
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4><i class="icon fa  ${Warning!=null?'fa-ban':'fa-check'}"></i>${Warning!=null?'Warning!':'Success'} </h4>
                <c:out value="${Warning!=null?Warning:Success} "></c:out>
              </div>
            </c:if>           
      	</div>
      </div>
      
     <form method="POST" id="manualAddLoadForm" action="<c:url value='/manual-add-load' />"  onsubmit="return myFunction()" data-toggle="validator" role="form" >     
      <div class="row" style="margin-bottom:20px"> 
      	<div class="col-sm-2"> 
		</div>
      	<div class="col-sm-3"> 
      		<div class="form-group input-group">
      		<div class="input-group-addon">
   				<label  class="control-label">LoadID :</label>
   			</div>
   			<input type="text" name="systemLoadID" id="systemLoadID" class="form-control" placeholder="LoadID" data-minlength="6" data-error="Minimum of 6 characters" required="required"/>
      		</div>
      	</div>
      	<div class="col-sm-3"> 
      		<div class="form-group  input-group date">
      			 <div class="input-group-addon">
      			 <label  class="control-label">Date :</label>
      			 </div>
      			 <input type="text" class="form-control pull-right datepicker" name="loadDate" id="loadDate" value="${loadDate}" data-error="Please Select Date" required="required"/> 
      		</div>		 
		</div>
		<div class="col-sm-4"> 
			<button type="submit" class="btn btn-primary">Submit</button>	
      		<a href="<c:url value='/calendar' />"><button type="button" class="btn btn-default">Cancel</button></a>
            	 
		</div>
      </div>      
      <!-- /.row -->
      </form> 
      
      
      
      	 
	<c:if test = "${not empty loadStops}">  
	<div class="row"> 
	
		<div class="box-body ">
              <table id="loadStopTable" class="table table-bordered table-striped" style="width : 100% ">
                <thead>
                <tr>
                  	<th >Stop Sequence</th>
					<th >Pickup GSDB</th>
					<th >Pickup Supplier Name</th>
					<th >Arrive Time</th>
					<th >Departure Time</th>
					<th >Status</th>                  
                </tr>
                </thead>
                <tbody>
	                <c:forEach items="${loadStops}" var="loadStop">
						<tr >
						<td>${loadStop.stopSequence}</td>
						<td>${loadStop.stopShippingLocation}</td>
						<td>${loadStop.stopShippingLocationName}</td>
						<td>
						<fmt:parseDate value="${loadStop.arriveTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedArriveTime" type="both" />
						<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss" value="${ parsedArriveTime }" />
						</td>
						<td>
						<fmt:parseDate value="${loadStop.departureTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDepartureTime" type="both" />
						<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss" value="${ parsedDepartureTime }" />
						</td>
						<td>${loadStop.status}</td>
								
						</tr>
					</c:forEach>
                              
                </tbody>                
              </table>
              
		</div>
        <!-- /.box-body -->
	
	</div>
	</c:if>
	
      
      	<!-- Modal -->
		<div class="modal fade" id="myModalStopUser" role="dialog">
   		<div class="modal-dialog">
			 <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title">Stop User</h4>
		        </div>
		        <div class="modal-body">
		          <p>Error Message : <span  id="showErrorText" class="badge bg-red" style="font-size:16px;font-weight: 600;">-</span>.</p>
		        </div>
		        <div class="modal-footer">
		          <%-- Bunyong 2019-08-23
		          <a id="processStopUser" >
		          	<button type="button" class="btn btn-default">OK</button>
		          </a>
		          --%>
		          <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
		        </div>
		      </div>
	      </div>
	      </div>
		      
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

    function myFunction() 
    {

        if (document.getElementById("loadid").value == '') 
        {
        	document.getElementById("showErrorText").innerHTML="LoadID is null !";
        	$("#errorMsgBox").modal();	
            return false;
        }
        else if(document.getElementById("loadDate").value == '')
        {
        	document.getElementById("showErrorText").innerHTML="Please choose date !";
        	$("#errorMsgBox").modal();	
            return false;
        }
        else
        {            
        	//document.getElementById("password").value = document.getElementById("newpassword").value;
            //document.getElementById("regForm").submit();
            return true;
        }
    }
    
    
    $(document).ready(function(){
    	$("#loadStopTable").DataTable({scrollX:true}); 
    	
    	$('.datepicker').datepicker({
  	      autoclose: true,
  	      format: 'yyyy-mm-dd'
  	    });	
    });
    
</script>
</body>
</html>
