<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Нова глоба</title>
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
					<a class="navbar-brand" href="<c:url value="/employee" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="dropdown active">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Глоби<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/employee/showUnpaidFines" />">Неплатени глоби</a></li>
								<li><a href="<c:url value="/employee/showPaidFines" />">Платени глоби</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="<c:url value="/employee/newFine" />">Нова глоба</a></li>
							</ul>
						</li>
						<li><a href="<c:url value="/employee/showEmployees" />">Служители</a></li>
						<li><a href="<c:url value="/employee/parkingStatistics" />">Статистика</a></li>
						<li><a href="<c:url value="/employee/createPost" />">Съобщение</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Настройки<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/employee/changeEmail" />">Смяна на e-mail</a></li>
								<li><a href="<c:url value="/employee/changePassword" />">Смяна на парола</a></li>
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
		<form action="/app/employee/newFine" method="post" class="form-horizontal">		
			<div class="form-group">
				<label for="vehicleRegNum" class="col-sm-4 control-label">Рег. номер:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="vehicleRegNum" id="vehicleRegNum" pattern="[A-Z0-9]{1,15}" title="Въведете до 15 символа с главни букви" required>
				</div>
			</div>
			<div class="form-group">
				<label for="parkingNumber" class="col-sm-4 control-label">Паркинг №</label>
				<div class="col-sm-4">
					<input type="number" class="form-control" name="parkingNumber" id="parkingNumber" value="<c:out value="${sessionScope.currentUser.parkingNumber}"/>" min="0" max="1000" required>
				</div>
			</div>
			<div class="form-group">
				<label for="kindOfViolation" class="col-sm-4 control-label">Вид наружение:</label>
				<div class="col-sm-4">
					<textarea rows ="3" cols="1" class="form-control" name="kindOfViolation" id="kindOfViolation" maxlength="70" required></textarea>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-4 control-label">Описание:</label>
				<div class="col-sm-4">
					<textarea rows ="3" cols="1" class="form-control" name="description" id="description" maxlength="150" required></textarea>
				</div>
			</div>		
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
		      		<button type="submit" class="btn btn-success btn-block">Добави</button>
		    	</div>
		  	</div>
		</form>
		<c:if test="${message == 'InvalidDataEntered'}">
			<div class="alert alert-warning text-center" role="alert">
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