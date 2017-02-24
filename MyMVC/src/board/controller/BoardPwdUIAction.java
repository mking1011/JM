package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;

public class BoardPwdUIAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String mode = req.getParameter("mode");
		String idxStr = req.getParameter("idx");
		
		if(idxStr == null || idxStr.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("board-list.do");
			return;
		}
		
		String title = "MVC �Խ���";
		
		if(mode.equals("del"))
		{
			title += "�ۻ���";
		}
		else if(mode.equals("edit"))
		{
			title += "�� ����";
		}
		
		req.setAttribute("title", title);
		req.setAttribute("mode", mode);
		req.setAttribute("idx", idxStr);
		
		this.setRedirect(false);
		this.setViewPage("/board/boardPwd.jsp");
	}

}
