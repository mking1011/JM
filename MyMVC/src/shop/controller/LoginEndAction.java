package shop.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import user.member.model.MemberDAO;
import user.member.model.MemberVO;
import user.member.model.NotMemberException;

public class LoginEndAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1. ����ڰ� �Է��� userid,pwd �ޱ�
		
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");
		
		//2. ��ȿ�� üũ = > index.do�� redirect�� �̵���Ű��
		
		if(userid == null || userid.trim().isEmpty() || pwd == null || pwd.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		//3. MemberDAO ��ü ���� => loginCheck(userid,pwd) �޼ҵ� ȣ��
		
		MemberDAO dao = new MemberDAO();
		
		try{
			
			MemberVO user = dao.loginCheck(userid, pwd);
			
			//4. ��ȯ ���� MemberVO�� ���ǿ� ��������
			
			HttpSession session =  req.getSession();
			
			if(user != null){
				session.setAttribute("loginUser", user);
			}		
			
			//5 index.do �������� redirect �̵�
			
			this.setRedirect(true);
			this.setViewPage("index.do");
			
		} catch(NotMemberException e){
			
			String msg = e.getMessage();
			String loc = "index.do";
			MyUtil.addMsgLoc(req, msg, loc);
			
			this.setRedirect(false);
			this.setViewPage("memo/message.jsp");
		}
	}

}
