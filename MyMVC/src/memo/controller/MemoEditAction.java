package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;

public class MemoEditAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		//1. ������ �� ��ȣ(idx) �ޱ�
		String idxStr = req.getParameter("idx");		
		
		//2. ��ȿ�� üũ (memoList.do�� redirect �̵�)
		
		if(idxStr == null || idxStr.trim().isEmpty())
		{
			this.setViewPage("memoList.do");
			this.setRedirect(true);
			return;
		}
		
		//3. MemoDAO ��ü ���� (selectMemoByIdx)
		MemoDAO dao = new MemoDAO();
		MemoDTO memo = dao.selectMemoByIdx(Integer.parseInt(idxStr.trim()));
		
		//4. ��� �޾Ƽ� req�� ����.		
		
		// key �� memo
		req.setAttribute("memo", memo);
		
		this.setViewPage("memo/memoEdit.jsp");
		this.setRedirect(false);
		
	}

}
