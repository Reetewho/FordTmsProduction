<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
         <img src="<c:url value='/assets/dist/svg/004-chick.svg' />" class="img-circle" alt="User Image" style="background-color:white">
        </div>
        <div class="pull-left info">
          <p>${S_FordUser.name}</p>
          <a href="#"><i class="fas fa-circle text-success"></i> Online</a>
        </div>
      </div>
      
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
         <li>
          <a href="<c:url value='/calendar' />">
            <i class="fas fa-calendar-alt"></i> &nbsp;&nbsp;<span>Calendar</span>            
          </a>
        </li>
        <!-- 
        <li>
          <a href="<c:url value='/searchby-loadid-processloadRetrieve' />">
            <i class="fas fa-file-alt"></i> &nbsp;&nbsp;<span>Search by Load ID (LoadRetrieve)</span>            
          </a>
        </li>
        -->
        <li>
          <a href="<c:url value='/searchby-loadid' />">
            <i class="fas fa-search"></i> &nbsp;&nbsp;<span>Search by Load ID</span>            
          </a>
        </li>
        <li>
          <a href="<c:url value='/report' />">
            <i class="fas fa-file-alt"></i> &nbsp;&nbsp;<span>Operation Report</span>            
          </a>
        </li>
        <li>
          <a href="<c:url value='/paymentreport' />">
            <i class="fas fa-receipt"></i> &nbsp;&nbsp;<span>Summary Report</span>            
          </a>
        </li>
		<li>
          <a href="<c:url value='/manual-add-load' />">
            <i class="fas fa-plus-square"></i> &nbsp;<span>Manual Add Load</span>            
          </a>
        </li>
        <!-- 
         <li>
          <a href="<c:url value='/showtest' />">
            <i class="fas fa-file-alt"></i> &nbsp;&nbsp;<span>Test</span>            
          </a>
        </li>
        -->
         <!-- li>
          <a href="<c:url value='/userList' />">
            <i class="fa fa-user"></i> &nbsp;&nbsp;<span>User</span>            
          </a>
        </li-->        
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>