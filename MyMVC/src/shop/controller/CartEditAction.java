package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import shop.model.CartBean;
import user.member.model.MemberVO;

public class CartEditAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		// 1. ������ ��ǰ��ȣ, ���� �ޱ�
		String pnum = req.getParameter("pnum");
		String oqty = req.getParameter("oqty");
		
		// 2. ��ȿ�� üũ
		if(pnum == null || oqty == null || pnum.trim().isEmpty() || oqty.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		// 3. �α��� ���� üũ
		MemberVO loginUser = this.loginCheck(req);
		if(loginUser == null)
		{
			MyUtil.addMsgLoc(req, "�α����ϼ���", "index.do");
			return;
		}
		
		// 4. ��ٱ��� �� ���ǿ��� ��������
		HttpSession ses = req.getSession();
		CartBean cart = (CartBean)ses.getAttribute("cartBean"+loginUser.getUserid());
		
		try{
			if(cart != null)
			{
				//��ٱ��� Ư�� ��ǰ�� ������ �����ϴ� �޼ҵ�
				cart.setEdit(new Integer(pnum.trim()),Integer.parseInt(oqty));
				this.setRedirect(true);
				this.setViewPage("cartList.do#shop");
			}
			else
			{
				MyUtil.addMsgLoc(req, "��ٱ��ϰ� ������", "index.do");
				this.setRedirect(false);
				this.setViewPage("/memo/message.jsp");
			}
		}catch(NumberFormatException e){
			String msg = e.getMessage();
			String loc = "javascript:history.back()";
			MyUtil.addMsgLoc(req, msg, loc);
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
			
		}

	}

}
