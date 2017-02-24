package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.*;
import common.controller.AbstractAction;

public class BoardViewAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// [1] �۹�ȣ �޾ƿ���
		
		String idxStr = req.getParameter("idx");
		
		// [2] ��ȿ�� üũ
		
		if(idxStr == null || idxStr.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("board-list.do");
			return;
		}
		
		// [3] DAO ����, �ش� �� �������� �޼ҵ� ȣ��
		
		//BoardDAO dao = new BoardDAO();
		BoardDAOMyBatis dao = new BoardDAOMyBatis();
		
		//��ȸ�� ����.
		boolean b = dao.updateReadnum(new Integer(idxStr.trim()));
		
		BoardVO board = dao.viewBoard(new Integer(idxStr.trim()));
		
		// [4] �� ��� req�� ����
		req.setAttribute("board", board);
		
		// [5] �� ������ ���� �� �̵���� ����
		this.setRedirect(false);
		this.setViewPage("/board/boardView.jsp");
	}

}
