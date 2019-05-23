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
		<h1>Update page</h1>
		<form action="#" method="POST">
			<div class="form-group">
				<label for="title">title:</label> 
				<input type="text" class="form-control" id="title" name="title" value="${dto.title }">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" id="writer" name="writer" value="${dto.writer }" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="contents">Contents:</label>
				<textarea class="form-control" rows="5" id="contents">${dto.contents }</textarea>
			</div>
			<div class="form-group">
				<label for="file">file:</label> 
				<input type="file" class="form-control" id="file" name="f1">
			</div>
			<button class="btn btn-primary">Update</button>
		</form>
	</div>
</body>
</html>