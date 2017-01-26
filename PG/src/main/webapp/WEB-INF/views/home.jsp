<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Търсене</title>
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>
<div id="wrapper">
	<div id="header">
        <h1>Паркинги и гаражи</h1>
    </div>
    <div id="loginbox">
		<form action="/app/showFineInfo" method="get">
			<div class="row">
				<div class="col-sm-9">
					<div class="row">
						<div class="col-sm-offset-6 col-sm-4">
							<label for="regNum">Регистрационен номер: </label><br>
							<input type="text" class="form-control" name="regNum" id="regNum" pattern="[A-Z0-9]{1,15}" title="Въведете до 15 символа с главни букви" required>
						</div>
					</div>
					<div class="row">
					<br>
						<div class="col-sm-offset-7 col-sm-2">
							<button type="submit" class="btn btn-primary btn-block">Търсене</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<c:if test="${param.message == 'InvalidData'}">
		    			<div class="alert alert-warning" role="alert">
		   					Няма открити резултати.
		   				</div>
					</c:if>
				</div>
			</div>
		</form>
	</div>
	<div id="footer">
		<a href="/app/employee">Вход за служител</a>
		<br>
		<a href="/app/admin">Вход за администратор</a>
		<br>
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>