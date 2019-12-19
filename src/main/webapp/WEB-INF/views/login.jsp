<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AP Transport Center | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<c:url value='/assets/bootstrap/css/bootstrap.min.css' />">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<c:url value='/assets/fontawesome/css/all.css' />">  
  <!-- Theme style -->
  <link rel="stylesheet" href="<c:url value='/assets/dist/css/AdminLTE.min.css' />">
  <!-- iCheck -->
  <link rel="stylesheet" href="<c:url value='/assets/plugins/iCheck/square/blue.css' />">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="#"><b>AP</b>  Transport Center</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>
    <c:if test = "${not empty loginFail}">
    <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <i class="icon fa fa-ban"></i> Invalid Username or Password.!
               
    </div>
    </c:if>
	<form method="POST" action="<c:url value='/login' />" data-toggle="validator" role="form">   
      <div class="form-group has-feedback">
        <input type="text" name="username" id="username" class="form-control" placeholder="Username" data-minlength="8" data-error="Minimum of 8 characters" required="required"/>
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password"  name="password" id="password" class="form-control" placeholder="Enter Password" data-minlength="8" data-error="Minimum of 8 characters" required="required"/>
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox" name="rememberMy" value="1"  /> Remember Me
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
        </div>
        <!-- /.col -->
      </div>
		<!-- This method will : after click link provide delete attribute href.
      <div class="row">
	        <div class="col-xs-4">
	          <a class="clicklink" onclick="window.open('http://example.com/no','_blank')">Example</a>
	          <br>
	          <a id="clicklink3" onclick="window.open('http://google.com')">Example4444</a>
	          <br>
	          <a class="clicklink2" href="http://google.com" target="_blank">Example2</a>
	        </div>
      </div>
 		-->
    </form>
    
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.0 -->
<script src="<c:url value='/assets/plugins/jQuery/jQuery-2.2.0.min.js' />"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<c:url value='/assets/bootstrap/js/bootstrap.min.js' />"></script>
<!-- Validate -->
<script src="<c:url value='/assets/plugins/validate/validator.js' />"></script>
<!-- iCheck -->
<script src="<c:url value='/assets/plugins/iCheck/icheck.min.js' />"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
 
	/*
	* This method will : after click link provide delete attribute href.
	$(".clicklink").on("click", function(){ 
		$(this).attr("onClick", false); 
	});
  
	$(".clicklink2").on("click", function(){ 
		var strLocation = $(this).attr("href");
		if(strLocation != null){
			window.open(strLocation);
		}
		$(this).removeAttr( "href" ); 
		$(this).removeAttr( "target" ); 
	});
	*/
	
</script>
</body>
</html>
