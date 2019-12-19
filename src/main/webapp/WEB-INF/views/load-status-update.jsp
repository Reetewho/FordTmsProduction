<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AP Transport Center | Load Status Update</title>
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
       Load Status Update
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fas fa-tachometer-alt"></i> &nbsp;&nbsp;Home</a></li>
        <li><a href="<c:url value='/calendar/${loadDate}' />">Calendar</a></li>
        <li><a href="<c:url value='/load-list/${loadDate}'/>">${loadDate}</a></li>
        <li><a href="<c:url value='/loadStop-list/${loadDate}/${load.systemLoadID}-${load.loadID}'/>">${load.systemLoadID}</a></li> 
        <li><a href="#">Load Status Update</a></li>         
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
      <div class="row">
      	<div class="col-md-12">
      		<c:if test="${Error!=null || Success!=null }">
              <div class='alert ${Error!=null?"alert-danger":"alert-success"}  alert-dismissible'>
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4><i class="icon fa  ${Error!=null?'fa-ban':'fa-check'}"></i>${Error!=null?'Error!':'Success'} </h4>
                <c:out value="${Error!=null?Error:Success} "></c:out>
              </div>
            </c:if>           
      	</div>
      </div>
      
      <div class="row">
      <!-- /.col -->
      
       <div class="col-md-5">
       	<div class="box box-warning">
            <div class="box-header with-border">
              <h3 class="box-title">Load Info</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->            
              <div class="box-body form-horizontal" style="min-height: 330px;">
                <div class="form-group">
                  <label for="" class="col-sm-5 control-label">Load ID : </label>
                  <div class="col-sm-7" style="padding-top:8px" >
                    ${load.systemLoadID}
                  </div>
                </div>
                <div class="form-group">
                  <label for="" class="col-sm-5 control-label">Alert Type Code : </label>
                  <div class="col-sm-7" style="padding-top:8px" >
                    ${load.alertTypeCode}
                  </div>
                </div>
                <div class="form-group">
                  <label  class="col-sm-5 control-label">Route No. : </label>
                  <div class="col-sm-7" style="padding-top:8px" >
                    ${load.loadDescription}
                  </div>
                </div>
                <div class="form-group">
                  <label  class="col-sm-5 control-label">Load Start Date Time : </label>
                  <div class="col-sm-7" style="padding-top:8px" >
                    ${load.loadStartDateTime}
                  </div>
                </div>
                <div class="form-group"  >
                  <label  class="col-sm-5 control-label">Load End Date Time : </label>
                  <div class="col-sm-7" style="padding-top:8px">
                    ${load.loadEndDateTime}
                  </div>
                </div>
              </div>
              <!-- /.box-body -->  
          </div>
       </div>
        <div class="col-md-7">
         <form:form method="POST" modelAttribute="loadStop" data-toggle="validator" role="form">    
          <div class="box box-primary">
          	 <div class="box-header with-border">
              <h3 class="box-title">Load Stop Update</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body form-horizontal">                    
             	<form:input type="hidden" path="id" id="id"/>
				<form:input type="hidden" path="loadID" id="loadID"/>
				<form:input type="hidden" path="stopShippingLocation" id="stopShippingLocation"/>
				<form:input type="hidden" path="status" id="status"/>      
		        <div class="form-group">
                  <label for="inputEmail3" class="col-sm-4 control-label">Shipping Location :</label>
                  <div class="col-sm-8" style="padding-top:8px">
                    ${loadStop.stopShippingLocation}
                  </div>
                </div>
                 <div class="form-group">
                  <label  class="col-sm-4 control-label">Shipping Location Name : </label>
                  <div class="col-sm-8" style="padding-top:8px">
                    ${loadStop.stopShippingLocationName}
                  </div>
                </div>
                <div class="form-group">
                  <label  class="col-sm-4 control-label">Truck Number :</label>
                  <div class="col-sm-8" >
                    <form:input path="truckNumber" id="truckNumber" class="form-control" required="required"/>                  
                  </div>
                </div>
                <div class="form-group">
                  <label  class="col-sm-4 control-label">Arrival Date Time :</label>
                  <div class="col-sm-8">
                   <form:input path="arriveTime" id="arriveTime" class="form-control" required="required" />                    
                  </div>
                </div>
                <div class="form-group">
                  <label  class="col-sm-4 control-label">Departure Date Time :</label>
                  <div class="col-sm-8">
                    <form:input path="departureTime" id="departureTime" class="form-control"  required="required" />                   
                  </div>
                </div>		
                
               <%--  <div class="form-group">
                  <label  class="col-sm-4 control-label">SO :</label>
                  <div class="col-sm-8">
                    <form:input path="ShipingOrder" id="ShipingOrder" class="form-control"  required="required" />                   
                  </div>
                </div>	
                
                <div class="form-group">
                  <label  class="col-sm-4 control-label">WY :</label>
                  <div class="col-sm-8">
                    <form:input path="WaybillNumber" id="WaybillNumber" class="form-control"  required="required" />                   
                  </div>
                </div>	 --%>
                          
            </div>
            <!-- /.box-body -->
            <div class="box-footer">
                <a href="<c:url value='/loadStop-list/${loadDate}/${load.systemLoadID}-${load.loadID}' />"><button type="button" class="btn btn-default">Cancel</button></a>
                <button type="submit" class="btn btn-primary pull-right">Submit</button>
              </div>
              <!-- /.box-footer -->
          </div>
          <!-- /. box -->
           </form:form> 
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
  $(function () {
	  	
  });
</script>
</body>
</html>
