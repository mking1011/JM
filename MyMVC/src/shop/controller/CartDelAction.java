package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import shop.model.CartBean;
import user.member.model.MemberVO;

public class CartDelAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 0. �α��� ���� üũ
		HttpSession session = req.getSession();
		MemberVO user = (MemberVO)session.getAttribute("loginUser");
		if(user == null) return;
		
		// 1. ������ ��ǰ��ȣ �ޱ�
		String pnum = req.getParameter("pnum");			
		
		// 2. ��ȿ�� üũ
		if(pnum == null || pnum.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
				
		// 3. ��ٱ��� ���� ���ǿ��� ������
		CartBean cart = (CartBean)session.getAttribute("cartBean"+user.getUserid());
		
		if(cart != null)
		{
			//��ٱ��Ͽ� �ش� ��ǰ ���� ó���ϴ� �޼ҵ� ȣ��
			cart.removeProduct(new Integer(pnum.trim()));
		}
		
		// 4. ������ �̵� ó��
		this.setRedirect(true);
		this.setViewPage("cartList.do");
	}

}
