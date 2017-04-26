<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Информация</title>
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/select-count-value.js" />"></script>
</head>
<body onload="selectCountValue(<c:out value="${count}"/>)">
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
						<li><a href="<c:url value="/admin/showParkings" />">Паркинги</a></li>
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
		</div>
		<div id="posts-header">
	    	<h2>Съобщения</h2>
	    </div>
	    <c:choose>
		    <c:when test="${posts[0] != null}">
			    <c:forEach var="post" items="${posts}">
					<div class="post-resource">
						<div class="row">
							<div class="col-sm-10">
								<h3><c:out value="${post.title}"/></h3>
							</div>
							<c:if test="${post.author.id == sessionScope.currentUser.id}">
								<div class="col-sm-2 edit-post-button">
									<form action="/app/admin/editPost" method="get">
										<button type="submit" class="btn btn-info">
											Редактирай <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
										</button>
										<input type="hidden" name="id" value="${post.id}">
									</form>
								</div>
							</c:if>
							<c:if test="${post.author.id != sessionScope.currentUser.id}">
								<div class="col-sm-2 edit-post-button">
									<form action="/app/admin/deletePost" method="post" class="form-horizontal" onsubmit="return confirm('Наистина ли искате да изтриете този запис от системата?');">
										<input type="hidden" name="id" value="${post.id}">
										<button type="submit" class="btn btn-danger btn-block">Премахни</button>
									</form>
								</div>
							</c:if>
						</div>
						<hr>
						<c:out value="${post.text}" escapeXml="false"/>
						<hr>
						<div class="post-metadata">
							Автор: <c:out value="${post.author.username}"/>
							<hr>
							Публикувано: <fmt:formatDate pattern="dd.MM.yyyy HH:mm ч." value="${post.datePublished}" />
							<c:if test="${not empty post.dateEdited}">
								&nbsp;&nbsp;&nbsp;
								Редактирано: <fmt:formatDate pattern="dd.MM.yyyy HH:mm ч." value="${post.dateEdited}" />
							</c:if>
						</div>
					</div>
				</c:forEach>
				<div class="row" id="selector-text">
		    		<form action="/app/admin">
						<div class="col-sm-offset-6 col-sm-2">
							<label for="count" class="control-label">Брой резултати:</label>
						</div>
						<div class="col-sm-2">
							<select name="count" id="count" class="form-control">
								<option value="5" id="5">5</option>
								<option value="10" id="10">10</option>
								<option value="20" id="20">20</option>
								<option value="50" id="50">50</option>
								<option value="100" id="100">100</option>
							</select>
						</div>
						<div class="col-sm-2">
							<button type="submit" class="btn btn-primary btn-block">Избери</button>
						</div>
					</form>
				</div>
			</c:when>
		    <c:otherwise>
		    	<div class="alert alert-warning" role="alert">
					В момента няма нови съобщения.
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