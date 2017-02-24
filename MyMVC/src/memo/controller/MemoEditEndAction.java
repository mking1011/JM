package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;

public class MemoEditEndAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		req.setCharacterEncoding("UTF-8");
	
		
		String idx = req.getParameter("idx");
		
		MemoDAO dao = new MemoDAO();
		MemoDTO memo = dao.selectMemoByIdx(Integer.parseInt(idx));
		
		memo.setMsg(req.getParameter("msg"));
		memo.setName(req.getParameter("name"));
		
		boolean b = dao.updateMemo(memo);
		
		String str = (b)? "수정성공" : "수정실패";
		String loc = "javascript:self.close()";
		
		req.setAttribute("message", str);
		req.setAttribute("loc", loc);
		req.setAttribute("mode", "pop");
		
		this.setViewPage("memo/message.jsp");
		this.setRedirect(false);
		
	}
	
}
