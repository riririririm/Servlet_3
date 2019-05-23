<%@page import="com.rim.notice.NoticeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../temp/bootstrap.jsp"/>
</head>
<body>
	<jsp:include page="../temp/header.jsp"/>
	<div class="container">
		<h1>Select Page</h1>
		<h3>Title : ${dto.title} </h3>
		<h3>Contents : ${dto.contents} </h3>
		<h3>Writer : ${dto.writer}</h3>
		<h3>Param    : ${param.num ge dto.num}</h3>
		<h3>Writer : ${dto.writer ne 'test'}</h3>
		<h3>Upload : <a href="../upload/${dto.uploadDTO.fname}">${dto.uploadDTO.oname}</a></h3>
	</div>
	
	<a href="./noticeUpdate?num=${dto.num}">Update</a>
	<a href="./noticeDelete?num=${dto.num}">Delete</a>
</body>
</html>