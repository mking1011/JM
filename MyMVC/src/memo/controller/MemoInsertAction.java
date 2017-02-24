package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;

public class MemoInsertAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		req.setCharacterEncoding("UTF-8");
		//1. 사용자가 입력한 값 받기.
		String name = req.getParameter("name");
		String msg = req.getParameter("msg");
		//2. 유효성
		if(name == null || msg == null|| name.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("memoWrite.do");
			return;
		}
		//3 DAO 생성
		MemoDAO mDao = new MemoDAO();
		MemoDTO memo = new MemoDTO(0,name,msg,null);
		
		//실행결과 메시지 등을 req에 저장
		boolean n = mDao.insertMemo(memo);
		String str = (n)? "글쓰기 성공" : "글쓰기 실패";
		String loc = (n)? "memoList.do" : "javascript:history.back()";
		
		req.setAttribute("message", str);
		req.setAttribute("loc", loc);
		
		//5.뷰 페이지 지정
		this.setViewPage("memo/message.jsp");
		
		//6. 이동방식 결정
		this.setRedirect(false);
	}

}
