package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class ProductViewAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. ��ǰ ��ȣ �ޱ�(pnum})
		String pnumStr = req.getParameter("pnum");
		
		// 2. ��ȿ�� üũ

		
		if(pnumStr == null || pnumStr.isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
		}
		
		//3. ProductDAOMyBatis ���� �޼ҵ� ȣ��
		
		ProductDAOMyBatis pdao = new ProductDAOMyBatis();
		ProductVO prod = pdao.selectByPnum(new Integer(pnumStr.trim()));
		
		//4. �� ��� req�� ����
		req.setAttribute("item", prod);
		
		//5. �������� ���� �� �̵���� ����
		this.setRedirect(false);
		this.setViewPage("shop/proDetail.jsp");
	}

}
