<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Нов паркинг</title>
     <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h1>Паркинги и гаражи</h1>
    </div>
    <div id="menu">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<c:url value="/admin" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="<c:url value="/admin/showEmployees" />">Служители</a></li>
						<li><a href="<c:url value="/admin/showAdmins" />">Администратори</a></li>
						<li class="active"><a href="<c:url value="/admin/showParkings" />">Паркинги</a></li>
						<li><a href="<c:url value="/admin/createPost" />">Съобщение</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Настройки<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/changeEmail" />">Смяна на e-mail</a></li>
								<li><a href="<c:url value="/admin/changePassword" />">Смяна на парола</a></li>
							</ul>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<form class="navbar-form" action="/app/logout" method="post">
							<button class="btn btn-default navbar-right" type="submit">Изход</button>
						</form>
					</ul>
				</div>
			</div>
		</nav>
    </div>
    <div id="textbox">
		<form action="/app/admin/createParking" method="post" class="form-horizontal">	
			<div class="form-group">
				<label for="number" class="col-sm-4 control-label">Номер на паркинг:</label>
				<div class="col-sm-4">
					<input type="number" class="form-control" name="number" id="number" min="0" max="1000" required>
				</div>
			</div>
			<div class="form-group">
				<label for="address" class="col-sm-4 control-label">Адрес:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="address" id="address" maxlength="70" required>
				</div>
			</div>
			<div class="form-group">
				<label for="hourlyTax" class="col-sm-4 control-label">Такса за час престой:</label>
				<div class="col-sm-4">
					<input type="number" class="form-control" name="hourlyTax" id="hourlyTax" min="0" max="1000" step="0.01" required>
				</div>
			</div>
			<div class="form-group">
				<label for="hourlyTax" class="col-sm-4 control-label">Географска ширина:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="latitude" id="latitude" required>
				</div>
			</div>
			<div class="form-group">
				<label for="hourlyTax" class="col-sm-4 control-label">Географска дължина:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="longitude" id="longitude" required>
				</div>
			</div>
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
		      		<button type="submit" class="btn btn-success btn-block">Добави</button>
		    	</div>
		  	</div>
		</form>
		<c:if test="${message == 'InvalidDataEntered'}">
			<div class="alert alert-warning" role="alert">
				Бяха въведени невалидни данни.
			</div>
		</c:if>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>