<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AP Transport Center | Calendar</title>
  <%@ include file="/WEB-INF/include/cssInclude.jsp" %>
  <style>
  .fc-title{
	  position: absolute;
	  padding-top:10px;
	  left: 40%;
	  font-size:1.85em;
  }
  </style>
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
       Calendar
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fas fa-tachometer-alt"></i> &nbsp;&nbsp;Home</a></li>
        <li><a href="#">Calendar</a></li>        
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
      <!-- /.col -->
        <div class="col-md-12">
          <div class="box box-primary">
            <div class="box-body no-padding">
              <!-- THE CALENDAR -->
              <div id="calendar"></div>
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
 var _y=${_year};
 var _m=${_month};
 var _d=${_date};
 var dayclick=false;
 var options={
	      header: {
	        left: 'prev,next',
	        center: 'title',
	        right: ''
	      },	     
	      timeFormat: ' ',
		  dayClick: function(date, jsEvent, view) {
			  if(!dayclick){
			    window.location.href = "<c:url value='/load-list/"+date.format()+"' />";
			    dayclick=true;
			  }
				//alert('Clicked on: ' + date.format());
		   },
		   events: [
			   <c:if test = "${not empty carriers}">
			  	<c:forEach items="${carriers}" var="carriers">
			  	<fmt:parseDate value="${carriers.loadDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="loadDate" type="both" />
			  	{
			  		title: '${carriers.numOfLoad}',
		           start: new Date(<fmt:formatDate pattern="yyyy" value="${ loadDate }" />, <fmt:formatDate pattern="MM" value="${ loadDate }" />-1,<fmt:formatDate pattern="dd" value="${ loadDate }" />)
			  	},
			  	</c:forEach>
			  </c:if> 
			   ],
		  eventBackgroundColor :"#ffffff",
		  eventBorderColor :"#ffffff",
		  eventTextColor :"#f56954",
	      editable: false,
	      droppable: false,
		  defaultDate: _y+'-'+_m+'-'+(_d+14)
	    };

  $(function () {
	  
	  /* initialize the calendar
	     -----------------------------------------------------------------*/
	    //Date for the calendar events (dummy data)
	    
	    $('#calendar').fullCalendar(options);
	   
	    $('.fc-prev-button').click(function(){			  
	    	var cdate1 = new Date(_y,_m-1,_d);
			   cdate1.setDate(0);			   			  
			   var endDate=getDateString(cdate1);			   
			   cdate1.setDate(1);
			   var startDate=getDateString(cdate1);			   	
			   window.location.href = "<c:url value='/calendar/"+startDate+"/"+endDate+"' />";
		});

		$('.fc-next-button').click(function(){
			 var cdate2 = new Date(_y,_m-1,_d);			   
			   cdate2.setMonth(cdate2.getMonth()+2);
			   cdate2.setDate(0);
			   var endDate=getDateString(cdate2);
			   cdate2.setDate(1);
			   var startDate=getDateString(cdate2);
			   window.location.href = "<c:url value='/calendar/"+startDate+"/"+endDate+"' />";
		});
  });
  
  function getDateString(date){
	  return  date.getFullYear()+"-"+((date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1))+"-"+(date.getDate()<10?"0"+date.getDate():date.getDate());
  }
</script>
</body>
</html>
