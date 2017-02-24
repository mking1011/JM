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
		//1. 사용자가 입력한 userid,pwd 받기
		
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");
		
		//2. 유효성 체크 = > index.do로 redirect로 이동시키기
		
		if(userid == null || userid.trim().isEmpty() || pwd == null || pwd.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		//3. MemberDAO 객체 생성 => loginCheck(userid,pwd) 메소드 호출
		
		MemberDAO dao = new MemberDAO();
		
		try{
			
			MemberVO user = dao.loginCheck(userid, pwd);
			
			//4. 반환 받은 MemberVO를 세션에 저장하자
			
			HttpSession session =  req.getSession();
			
			if(user != null){
				session.setAttribute("loginUser", user);
			}		
			
			//5 index.do 페이지로 redirect 이동
			
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
