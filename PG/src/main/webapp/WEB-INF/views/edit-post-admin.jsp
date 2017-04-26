<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Редактиране на съобщение</title>
    <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/ckeditor/ckeditor/ckeditor.js" />"></script>
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
					<a class="navbar-brand" href="<c:url value="/admin" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="<c:url value="/admin/showEmployees" />">Служители</a></li>
						<li><a href="<c:url value="/admin/showAdmins" />">Администратори</a></li>
						<li><a href="<c:url value="/admin/showParkings" />">Паркинги</a></li>
						<li class="active"><a href="<c:url value="/admin/createPost" />">Съобщение</a></li>
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
		<form action="/app/admin/editPost" method="post">
			<div class="row">
				<label for="title" class="col-sm-offset-1 col-sm-1 control-label">Заглавие:</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="title" id="title" value="<c:out value="${post.title}"/>" maxlength="100" required />
				</div>
			</div>
			<br>
			<div class="row">
			    <textarea name="postText" id="postText"  maxlength="10000" required><c:out value="${post.text}"/></textarea>
	            <script>
	            	CKEDITOR.replace('postText');
	            </script>
			</div>
			<div class="row">
				<div class="col-sm-offset-5 col-sm-2">
		            <br>
		            <input type="hidden" name="id" value="<c:out value="${post.id}"/>">
		            <button type="submit" class="btn btn-success btn-block">Редактирай</button>
	            </div>
	        </div>
		</form>
	    <div class="row">
			<div class="col-sm-offset-5 col-sm-2">
				<form action="/app/admin/deletePost" method="post" class="form-horizontal" onsubmit="return confirm('Наистина ли искате да изтриете този запис от системата?');">	
					<br>
					<input type="hidden" name="id" value="${post.id}">
					<button type="submit" class="btn btn-danger btn-block">Премахни</button>
					<br>
				</form>
			</div>
	    </div>
		<br>
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