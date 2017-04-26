<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Неплатени глоби</title>
     <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <link href="<c:url value="/resources/data_tables/datatables.min.css" />" rel="stylesheet" type="text/css" />
	<script src="<c:url value="/resources/data_tables/datatables.min.js" />"></script>
	<script src="<c:url value="/resources/js/load-data-table-appearance.js" />"></script> 
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
		<c:choose>
		    <c:when test="${unpaidFines[0] != null}">
		      	<table id="dataTable" style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
					<col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">				
			        <caption>
				    	Неплатени глоби
			        </caption>
			        <thead>
			             <tr>
				             <th>ID</th>
				             <th>Дата</th>
				             <th>Рег. ном.</th>
				             <th>Нарушение</th>
				             <th>Паркинг №</th>
				             <th>Описание</th>
				             <th>Престой (часове)</th>
				             <th>Такса (за час)</th>
				             <th>Сума (лв)</th>
				             <th>Плати</th>
			             </tr>
			        </thead>
			        <tbody>
				    	<c:forEach var="unpaidFine" items="${unpaidFines}">
							<tr>
								<td><c:out value="${unpaidFine.id}"/></td>
								<td><fmt:formatDate pattern="dd.MM.yyyy HH:mm ч." value="${unpaidFine.date}" /></td>
								<td><c:out value="${unpaidFine.vehicleRegNum}"/></td>
								<td><c:out value="${unpaidFine.kindOfViolation}"/></td>
								<td><c:out value="${unpaidFine.parkingNumber}"/></td>
								<td><c:out value="${unpaidFine.description}"/></td>
								<td><c:out value="${unpaidFine.unpaidFinesStatus.timeOfStay}"/></td>
								<td><c:out value="${unpaidFine.unpaidFinesStatus.hourlyTax}"/></td>
								<td><c:out value="${unpaidFine.unpaidFinesStatus.amount}"/></td>
								<td>
									<form action="/app/employee/payFine" method="get">
										<button type="submit" class="btn btn-primary">
											<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
										</button>
										<input type="hidden" name="id" value="${unpaidFine.id}">
									</form>
								</td>
							</tr>
						</c:forEach>
			        </tbody>
				</table>     
		    </c:when>
		    <c:otherwise>
		    	<div class="alert alert-warning" role="alert">
		       		Няма намерени резултати.
		       	</div>
		    </c:otherwise>
		</c:choose>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>