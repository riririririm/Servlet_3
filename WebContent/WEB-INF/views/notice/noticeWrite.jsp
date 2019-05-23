<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../temp/bootstrap.jsp" />
</head>
<body>
	<%-- <jsp:include page="../temp/header.jsp"/> --%>
	<c:import url="../temp/header.jsp" />

	<div class="container">
		<h1>Write page</h1>
		<form action="./noticeWrite" method="POST" enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">title:</label> 
				<input type="text" class="form-control" id="title" name="title">
			</div>
			<div class="form-group">
				<label for="writer">writer:</label> 
				<input type="text" class="form-control" id="writer" name="writer">
			</div>
			<div class="form-group">
				<label for="contents">Contents:</label>
				<textarea class="form-control" rows="5" id="contents"></textarea>
			</div>
			<div class="form-group">
				<label for="file">file:</label> 
				<input type="file" class="form-control" id="file" name="f1">
			</div>
			<button class="btn btn-primary">Write</button>
		</form>
	</div>


</body>
</html>