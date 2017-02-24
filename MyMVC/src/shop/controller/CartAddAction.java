package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import shop.model.CartBean;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;
import user.member.model.MemberVO;

public class CartAddAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		//1. ��ǰ ��ȣ, ���� �޾ƿ���
		String pnumStr = req.getParameter("pnum");
		String oqtyStr = req.getParameter("oqty");
		
		//2. ��ȿ�� üũ
		if(pnumStr == null || oqtyStr == null || pnumStr.trim().isEmpty() || oqtyStr.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		//3. ȸ���� �̿��� �� �����Ƿ� �α��� ���θ� üũ.
		
		HttpSession session = req.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null)
		{
			MyUtil.addMsgLoc(req, "�α����ؾ� �̿��� �� �־��", "index.do");
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
			return;
		}
		
		
		
		//4. ��ǰ��ȣ��  ��ٱ��Ͽ� �߰��� ��ǰ������ DB���� ��������.
		ProductDAOMyBatis pdao = new ProductDAOMyBatis();
		ProductVO item = pdao.selectByPnum(new Integer(pnumStr.trim()));
		
		//5 ���ǿ� ����� CartBean�� �ִ��� ��������.
		CartBean cart = null;		
		
		cart = (CartBean)session.getAttribute("cartBean" + loginUser.getUserid());
		
		
		//6. �ش� ��ǰ�� CartBean�� addProduct()�� ��ٱ��Ͽ� �߰�.
		
		if(cart == null)
		{	
			cart = new CartBean();
		}
		
		Integer pnum = new Integer(pnumStr.trim());
		int oqty = Integer.parseInt(oqtyStr.trim());
		cart.addProduct(pnum, oqty, item);
		
		//7. ���ǿ� CartBean�� �����Ѵ�.
		session.setAttribute("cartBean" + loginUser.getUserid(), cart);
		
		//8. ��ٱ��� ��� ��������
		List<ProductVO> cartList = cart.getCartList();
		req.setAttribute("cartList", cartList);
		
		//9. �������� ���� �� �̵���� ����
		
		/*
		 * forward�� �̵��� ������ �߻��Ѵ�.
			this.setRedirect(false);
			this.setViewPage("/shop/cartList.jsp");
			-���� url�� ����ϹǷ� ���ΰ�ħ �� ������ ��� �����ϴ� ������ �߻��ȴ�.
		*/
		
		//redirect�� �����ϸ� req.setAttribute("cartList", cartList)�� ������Եȴ�
		this.setRedirect(true);
		this.setViewPage("cartList.do");
		
	}

}
