<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<jsp:include page="/top.jsp"/>

<script type="text/javascript" src="./js/jquery.magnifier.js"></script>
<div align="center" id="shop">
<h1>상품 정보</h1>
<div class="section">
	
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<table class="table table-responsive">
					<tr>
						<td align="center">
						<%--<a href="#shop" onclick="openWin('${item.pimage1}')"><img src="images/${item.pimage1}"
						class="img img-responsive"></a> --%>
						<a><img src= 'images/${item.pimage1}' class="magnify img img-responsive"  data-magnifyby='2'></a>
						
						</td>
						<td>
						<h4><span class="label label-danger">
						${item.pspec}</span></h4>
						상품번호 : <b>${item.pnum}</b> <br>
						상품이름 : <b>${item.pname}</b> <br>
						정    가 : <del><b><fmt:formatNumber value="${item.price}" pattern="###,###"/>	</b></del> 원<br>
						판 매 가 : <span style="color:red"><b><fmt:formatNumber value="${item.saleprice }" pattern="###,###"/>						</b></span>원<br>
						할 인 율 : <span style="color:blue"><b>${item.percent}%</b></span><br>
						POINT    : <span class="label label-success">${item.point} Point</span>
					<p></p>
					
					<!--form 시작---------------  -->
					
					<form name="frm" id="frm" method="POST">
					
							<!-- 상품 번호를 hidden으로 넘긴다 -->
						<input type ='hidden' name = 'pnum' value = '${item.pnum}'>
						<label for="oqty">상품 갯수</label>
						<input type="number" name="oqty" id="oqty"min="1" max="50" value="1">
											
					</form>
					
					<!-- form 끝----------------- -->
					
							<button class="btn btn-primary" onclick = 'goCart()'>장바구니</button>
							<button class="btn btn-warning" onclick = "goOrder()">주문하기</button>
							<button class="btn btn-success" onclick = 'goWish()'>위시리스트</button>
						</td>
					</tr>
					<tr>
						<td align="center">
							<img src="images/${item.pimage2}" class="img img-responsive">
						</td>
						<td align="center">
							<img src="images/${item.pimage3}" class="img img-responsive">						
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>상품설명</p>
							<pre>${item.pcontents}</pre>						
						</td>
					</tr>
				</table>
			</div>
		</div>
</div>
</div>
<script type="text/javascript">

	var goCart = function()
	{
		frm.action = "cartAdd.do"
		//frm.method = "POST";
		frm.submit();
	}
	
	var goOrder = function()
	{
		frm.action = 'order.do';
		frm.submit();
	}
	
	var goWish = function()
	{
		frm.action = "wish.do";
		frm.submit();
	}



	//팝업창으로 상세 이미지를 보여주는 함수
	var openWin=function(img){
		//alert(img);
		var url = "images/"+img;
		
		var imgObj = new Image();
		imgObj.src = url;
		var w = imgObj.width + 80;
		var h = imgObj.height + 80;
		
		window.open(url,"imgView","width = " + w + ", height = "+ h +", left = 100, top = 100")
		
		//팝업창의 폭 = (원래 이미지 폭 + 80px)
		//팝업창의 높이 = (원래 이미지 높이 + 80px)		
	}
</script>

<jsp:include page="/foot.jsp"/>








