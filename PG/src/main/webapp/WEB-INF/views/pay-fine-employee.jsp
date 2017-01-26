<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Плащане на глоба</title>
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
		<form action="/app/employee/payFine" method="post" class="form-horizontal">
			<div class="form-group">
				<label for="vehicleRegNum" class="col-sm-4 control-label">Рег. номер:</label>
				<div class="col-sm-4">
					<p class="form-control-static">${fine.vehicleRegNum}</p>
				</div>
			</div>
			<div class="form-group">
				<label for="parkingNumber" class="col-sm-4 control-label">Паркинг №:</label>
				<div class="col-sm-4">
					<p class="form-control-static">${fine.parkingNumber}</p>
				</div>
			</div>
			<div class="form-group">
				<label for="kindOfViolation" class="col-sm-4 control-label">Вид наружение:</label>
				<div class="col-sm-4">
					<p class="form-control-static">${fine.kindOfViolation}</p>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-4 control-label">Описание:</label>
				<div class="col-sm-4">
					<p class="form-control-static">${fine.description}</p>
				</div>
			</div>
			<div class="form-group">
				<label for="timeOfStay" class="col-sm-4 control-label">Престой:</label>
				<div class="col-sm-4">
					<p class="form-control-static">${fine.unpaidFinesStatus.timeOfStay} часа</p>
				</div>
			</div>
			<div class="form-group">
				<label for="hourlyTax" class="col-sm-4 control-label">Такса:</label>
				<div class="col-sm-4">
					<p class="form-control-static">${fine.unpaidFinesStatus.hourlyTax} лв./час</p>
				</div>
			</div>
			<div class="form-group">
				<label for="paidBy" class="col-sm-4 control-label">Платено от:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="paidBy" id="paidBy" maxlength="45" required>
				</div>
			</div>
			<div class="form-group">
				<label for="amount" class="col-sm-4 control-label">Сума за плащане:</label>
				<div class="col-sm-4">
					<p class="form-control-static"><b>${fine.unpaidFinesStatus.amount} лв.</b></p>
				</div>
			</div>
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
		    		<input type="hidden" name="id" value="${fine.id}">
		    		<input type="hidden" name="amount" value="${fine.unpaidFinesStatus.amount}">
		      		<button type="submit" class="btn btn-success btn-block">Плати</button>
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
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>