package shop.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import shop.model.CartBean;
import shop.model.ProductVO;
import user.member.model.MemberVO;

public class CartListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 1. �α��� üũ ����
		HttpSession session = req.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null)
		{
			MyUtil.addMsgLoc(req, "�α����ؾ� �̿��� �� �־��", "index.do");
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
			return;
		}
		
		// 2. �α��� �ߴٸ� ���ǿ��� ��ٱ��� �� ��������
		String userid = loginUser.getUserid();
		
		CartBean cart = null;
		cart = (CartBean)session.getAttribute("cartBean" + userid);
		
		if(cart == null)
		{
			cart = new CartBean();
		}
		
		// 3. ��ٱ��� ��ǰ ��� ��������.
		List<ProductVO> cartList = cart.getCartList();
		
		
		// 4. ���ǿ� ����
		session.setAttribute("cartBean" + userid, cart);
		session.setAttribute("cartList", cartList);
		
		//5. ��ٱ��� �Ѿ� ��������
		Map<String,Integer> map = cart.getCartTotal();
		session.setAttribute("cartMap", map);
		
		//6. �� ������ ���� �� �̵���� ����
		this.setRedirect(false);
		this.setViewPage("/shop/cartList.jsp");
	}

}
