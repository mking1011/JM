package memo.controller;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.MemoDAO;
import memo.model.MemoDTO;

public class MemoListAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		MemoDAO dao = new MemoDAO();
		Vector<MemoDTO> v = dao.selectAllMemo();
		req.setAttribute("memoV", v);
		
		this.setViewPage("memo/memoList.jsp");
		this.setRedirect(false);		
	}
	
}
