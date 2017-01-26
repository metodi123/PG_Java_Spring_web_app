<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Информация</title>
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body onload="onPageLoad()">
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
						<li class="dropdown">
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
    	<div id="user-info">
    		<div class="col-sm-12 text-center greeting-message">
    			<h3>Добре дошли!</h3>
    		</div>
	    	<label class="control-label col-sm-3 text-right">Потребителско име:</label>
	    	<div class="col-sm-9">
	    		<p class="control-static"><c:out value="${sessionScope.currentUser.username}"/></p>
			</div>
			<label class="control-label col-sm-3 text-right">Име:</label>
	    	<div class="col-sm-9">
	    		<p class="control-static"><c:out value="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.lastName}"/></p>
			</div>
			<label class="control-label col-sm-3 text-right">e-mail:</label>
	    	<div class="col-sm-9">
	    		<p class="control-static"><c:out value="${sessionScope.currentUser.email}"/></p>
			</div>
			<label class="control-label col-sm-3 text-right">Паркинг №</label>
	    	<div class="col-sm-9">
	    		<p class="control-static"><c:out value="${sessionScope.currentUser.parkingNumber}"/></p>
			</div>
	    </div>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>