<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = 'c' uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = 'fmt' uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="section">
      <div>
      
        <div class="row">
        	<div class = "col-md-12 col-xs-12 col-sm-12">
        		<h2 class = "text-danger">New상품</h1>
        	</div>
        </div>
        
        <div class = "row">
        <c:if test = "${newList eq null || empty newList }">
        	 <div class="col-md-3 col-xs-6 col-sm-6">
	            <img src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
	            class="img-responsive">
	            <h2>HIT 상품 준비중</h2>
	          </div>
        </c:if>
        
        <c:if test = "${newList ne null || not empty newList}">
        	<c:forEach var = "pd" items = "${newList}" varStatus="st">
        	
        	<!-- forEach에 begin값과 end값을 지정하면 원하는 갯수만큼만 보여줄수 있다 -->
        		<!-- 
        			varStatus에 지정된 변수로 반목문의 상태정보를 얻어올 수 있다.
        			st.index : 반복문의 인덱스 번호(0부터 시작)를 반환
        			st.count : 반복문의 횟수(1부터 시작)를 반환
        		 -->
		          <div class="col-md-3 col-xs-6 col-sm-6">
		            <a href = 'pdDetail.do?pnum=${pd.pnum}'><img src="images/${pd.pimage1}"
		            class="img-responsive"></a>
		            <h2>${pd.pname}</h2>
		            <p>
		            	<del>${pd.price}</del> 원 <br>
		            	<span class = "text text-danger"><b>상품판매가</b>${pd.saleprice}</span> 원 <br>
		            	<span class = "text text-primary"><b>${pd.percent}</b> %할인</span> <br>
		            	<span class = "label label-warning">${pd.point}POINT</span> <br>
		            </p>
		          </div>
		          
		          <c:if test = "${st.count mod 4 == 0}">
		          	</div> <!-- row(행)을 끝낸고 새로운 row를 시작 -->
		          	<div claoss = "row">
		          </c:if>
		          
	         </c:forEach>
          </c:if>
          
          </div>
      </div>
    </div>