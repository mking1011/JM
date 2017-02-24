package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;

public class MemoDeleteAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String idx = req.getParameter("idx");
		MemoDAO dao = new MemoDAO();
		
		boolean b = dao.deleteMemo(Integer.parseInt(idx));
		String msg = (b)? "삭제 성공!!!!" : "삭제 실패!!!!";
		String loc = (b)? "memoList.do" : "javascript:history.back()";
		
		req.setAttribute("message", msg);
		req.setAttribute("loc", loc);
		
		this.setViewPage("/memo/message.jsp");
		this.setRedirect(false);			
	}
	
}
