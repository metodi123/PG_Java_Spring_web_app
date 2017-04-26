<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Статистика</title>
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/select-values.js" />"></script>
    <link href="<c:url value="/resources/data_tables/datatables.min.css" />" rel="stylesheet" type="text/css" />
	<script src="<c:url value="/resources/data_tables/datatables.min.js" />"></script>
	<script src="<c:url value="/resources/js/load-data-table-appearance.js" />"></script> 
</head>
<body onload="selectValues(<c:out value="${year}"/>, '<c:out value="${month.toLowerCase()}"/>')">
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
						<li class="active"><a href="<c:url value="/employee/parkingStatistics" />">Статистика</a></li>
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
    	<div class="row" id="selector-text">
    		<form action="/app/employee/parkingStatistics">
				<div class="col-sm-offset-5 col-sm-1">
					<label for="year" class="control-label">Година:</label>
				</div>
				<div class="col-sm-2">
					<select name="year" id="year" class="form-control">
						<c:forEach var="statisticsYear" items="${statisticsYears.descendingSet()}">
							<option value="${statisticsYear}" id="${statisticsYear}">${statisticsYear}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select name="month" id="month" class="form-control">
						<option value="january" id="january">Януари</option>
						<option value="february" id="february">Февруари</option>
						<option value="march" id="march">Март</option>
						<option value="april" id="april">Април</option>
						<option value="may" id="may">Май</option>
						<option value="june" id="june">Юни</option>
						<option value="july" id="july">Юли</option>
						<option value="august" id="august">Август</option>
						<option value="september" id="september">Септември</option>
						<option value="october" id="october">Октомври</option>
						<option value="november" id="november">Ноември</option>
						<option value="december" id="december">Декември</option>
					</select>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary btn-block">Избери</button>
				</div>
			</form>
		</div>
		<table id="dataTable" style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
			<col width="auto">
			<col width="auto">
			<col width="auto">
			<col width="auto">		
			<caption>
				Статистика
			</caption>
			<thead>
				<tr>
					<th>Паркинг №</th>
					<th>Брой автомобили</th>
					<th>Постъпления (лв.)</th>
					<th>Брой платени глоби</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="parking" items="${parkings}">
					<c:forEach var="parkingStatistics" items="${parking.parkingStatistics}">
						<c:if test="${parkingStatistics.year == year && parkingStatistics.month.equalsIgnoreCase(month)}">
							<tr>
								<td><c:out value="${parkingStatistics.parkingNumber}"/></td>
								<td><c:out value="${parkingStatistics.currentVehicleCount}"/></td>
								<td><c:out value="${parkingStatistics.gainings}"/></td>
								<td><c:out value="${parkingStatistics.paidFinesCount}"/></td>
							</tr>
						</c:if>
					</c:forEach>	
			    </c:forEach>
			</tbody>
		</table>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>