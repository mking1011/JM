package shop.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.CategoryVO;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class DisplayAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ���θ��� ��ǰ�� Display�ϴ� ��Ʈ�ѷ�
		// HIT, NEW ��ǰ�� ������ req�� �����Ѵ�.
		
		ProductDAOMyBatis pdao = new ProductDAOMyBatis();
		
		//0 ī�װ� ��� ��������
		List<CategoryVO> cList = pdao.getCategory();
		
		//1 HIT��ǰ ��������
		List<ProductVO> hitList = pdao.selectByPspec("HIT");
		
		//2 NEW ��ǰ ��������
		List<ProductVO> newList = pdao.selectByPspec("NEW");
		
		
		
		//3 req�� ����
		req.setAttribute("hitList", hitList);
		req.setAttribute("newList", newList);
		
		//req.setAttribute("categoryList", cList);
		//ī�װ� ����� ��� ���������� ��µǾ�� �ϱ� ������ scope�� ����ū application�� ��������.
		ServletContext application = req.getServletContext();
		application.setAttribute("categoryList", cList);
		
		//4. �� ������ ���� �� �̵���� ����
		this.setViewPage("/shop/mall.jsp");
		this.setRedirect(false);
	}

}
