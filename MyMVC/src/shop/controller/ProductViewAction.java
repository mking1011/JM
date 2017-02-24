package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class ProductViewAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. 상품 번호 받기(pnum})
		String pnumStr = req.getParameter("pnum");
		
		// 2. 유효성 체크

		
		if(pnumStr == null || pnumStr.isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
		}
		
		//3. ProductDAOMyBatis 생성 메소드 호출
		
		ProductDAOMyBatis pdao = new ProductDAOMyBatis();
		ProductVO prod = pdao.selectByPnum(new Integer(pnumStr.trim()));
		
		//4. 그 결과 req에 저장
		req.setAttribute("item", prod);
		
		//5. 뷰페이지 지정 및 이동방식 지정
		this.setRedirect(false);
		this.setViewPage("shop/proDetail.jsp");
	}

}
