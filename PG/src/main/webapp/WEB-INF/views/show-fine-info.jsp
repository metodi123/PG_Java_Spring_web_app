<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Глоба</title>
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>
<div id="wrapper">
	<div id="header">
        <h1>Паркинги и гаражи</h1>
    </div>
    <div id="textbox">
	    <div id="fine-info">
	   		<div class="row">
		    	<label class="control-label col-sm-6 text-right">Регистрационен номер:</label>
		    	<div class="col-sm-6">
		    		<p class="control-static"><c:out value="${fine.vehicleRegNum}"/></p>
				</div>
				<label class="control-label col-sm-6 text-right">Нарушение:</label>
		    	<div class="col-sm-6">
		    		<p class="control-static"><c:out value="${fine.kindOfViolation}"/></p>
				</div>
				<label class="control-label col-sm-6 text-right">Дата:</label>
		    	<div class="col-sm-6">
		    		<p class="control-static"><fmt:formatDate pattern="dd.MM.yyyy HH:mm ч." value="${fine.date}" /></p>
				</div>
				<label class="control-label col-sm-6 text-right">Паркинг №</label>
		    	<div class="col-sm-6">
		    		<p class="control-static"><c:out value="${parking.number}"/></p>
				</div>
				<label class="control-label col-sm-6 text-right">Паркинг адрес:</label>
		    	<div class="col-sm-6">
		    		<p class="control-static"><c:out value="${parking.address}"/></p>
				</div>
		    </div>
		    
			<div class="row">
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<c:if test="${message == 'InvalidData'}">
		    			<div class="alert alert-warning" role="alert">
		   					Въведени са невалидни данни.
		   				</div>
					</c:if>
				</div>
			</div>
			<div class="col-sm-offset-5 col-sm-7">
				<button class="btn btn-info text-right" onclick="history.back();" type="button">Обратно</button>
			</div>
		</form>
	</div>
	<div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>