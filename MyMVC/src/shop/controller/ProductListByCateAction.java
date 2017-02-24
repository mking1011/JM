package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class ProductListByCateAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. code, cname 파라미터 값 받아오기
		
		String code = req.getParameter("code");
		String cname = req.getParameter("cname");
		
		// 2. 유효성 체크 (null 이면 index.do로 redirect 이동시킨다)
		
		if(code.trim().isEmpty() || cname.trim().isEmpty() || code == null || cname == null)
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		// 3. ProductDAOMyBatis객체 생성 후 카테고리 코드로 상품 목록 가져오는 메소드 호출
		// List<ProductVO> selectByCategory(code)
		
		ProductDAOMyBatis pdao = new ProductDAOMyBatis();
		List<ProductVO> plist = pdao.selectByCategory(code.trim());
	
		
		// 4. req에 상품목록 저장
		
		req.setAttribute("pList", plist);
		req.setAttribute("code", code);
		req.setAttribute("cname", cname);
		
		// 5. 뷰페이지 지정 및 이동 방식 지정
		// shop/mallByCategory.jsp
		
		this.setRedirect(false);
		this.setViewPage("shop/mallByCategory.jsp");

	}

}
