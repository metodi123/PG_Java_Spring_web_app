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
			    <div id="map"></div>
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
				<button class="btn btn-primary text-right" onclick="history.back();" type="button">Обратно</button>
			</div>
		</div>
	</div>
	<div id="footer">
        <hr>
        Copyright © 2017 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
<script>
	function initMap() {
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 15,
			center: {lat: <c:out value="${parking.location.latitude}"/>, lng: <c:out value="${parking.location.longitude}"/> }
		});
		
		<c:forEach var="parkingCurrent" items="${parkings}">
			var message = '<p><strong>Паркинг №<c:out value="${parkingCurrent.number}"/></strong></p><p><c:out value="${parkingCurrent.address}"/></p>';
		
			var marker = new google.maps.Marker({
				position: {
					lat: <c:out value="${parkingCurrent.location.latitude}"/>,
					lng: <c:out value="${parkingCurrent.location.longitude}"/>
				},
				map: map
			});
			attachMessage(marker, message);
		
			<c:if test="${parkingCurrent.number == parking.number}">
				var infowindow = new google.maps.InfoWindow({
					content: message
				});
				infowindow.open(marker.get('map'), marker);
			</c:if>
		</c:forEach>
	}

	function attachMessage(marker, message) {
		var infowindow = new google.maps.InfoWindow({
			content: message
		});

		marker.addListener('click', function() {
			infowindow.open(marker.get('map'), marker);
		});
	}
</script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=<c:out value="${mapsApiKey}"/>&callback=initMap">
</script>
</body>
</html>