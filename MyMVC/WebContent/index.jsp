<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = 'c' uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page = "/top.jsp"/>

	<div align = "center">
    
		<h1>MY MVC PAGE</h1>
		
		<c:import url = "/mall.do"/>
	</div>

<jsp:include page = "/foot.jsp"/>