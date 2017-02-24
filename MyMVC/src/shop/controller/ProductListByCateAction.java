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
		// 1. code, cname �Ķ���� �� �޾ƿ���
		
		String code = req.getParameter("code");
		String cname = req.getParameter("cname");
		
		// 2. ��ȿ�� üũ (null �̸� index.do�� redirect �̵���Ų��)
		
		if(code.trim().isEmpty() || cname.trim().isEmpty() || code == null || cname == null)
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		// 3. ProductDAOMyBatis��ü ���� �� ī�װ� �ڵ�� ��ǰ ��� �������� �޼ҵ� ȣ��
		// List<ProductVO> selectByCategory(code)
		
		ProductDAOMyBatis pdao = new ProductDAOMyBatis();
		List<ProductVO> plist = pdao.selectByCategory(code.trim());
	
		
		// 4. req�� ��ǰ��� ����
		
		req.setAttribute("pList", plist);
		req.setAttribute("code", code);
		req.setAttribute("cname", cname);
		
		// 5. �������� ���� �� �̵� ��� ����
		// shop/mallByCategory.jsp
		
		this.setRedirect(false);
		this.setViewPage("shop/mallByCategory.jsp");

	}

}
