package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;

public class MemoEditAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		//1. 수정할 글 번호(idx) 받기
		String idxStr = req.getParameter("idx");		
		
		//2. 유효성 체크 (memoList.do로 redirect 이동)
		
		if(idxStr == null || idxStr.trim().isEmpty())
		{
			this.setViewPage("memoList.do");
			this.setRedirect(true);
			return;
		}
		
		//3. MemoDAO 객체 생성 (selectMemoByIdx)
		MemoDAO dao = new MemoDAO();
		MemoDTO memo = dao.selectMemoByIdx(Integer.parseInt(idxStr.trim()));
		
		//4. 결과 받아서 req에 저장.		
		
		// key 값 memo
		req.setAttribute("memo", memo);
		
		this.setViewPage("memo/memoEdit.jsp");
		this.setRedirect(false);
		
	}

}
