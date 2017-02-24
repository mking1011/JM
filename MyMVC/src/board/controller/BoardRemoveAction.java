package board.controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import common.controller.AbstractAction;

public class BoardRemoveAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. ������ �� ��ȣ �ޱ�.
		String idxStr = req.getParameter("idx");
		
		// 2. ��й�ȣ �ޱ�.
		String pwd = req.getParameter("pwd");
		
		// 3. ��ȿ�� üũ
		if(idxStr == null || pwd == null || pwd.trim().isEmpty())
		{
			this.setRedirect(true);
			this.setViewPage("board-list.do");
			return;
		}
		
		// 4. DAO�� �޼ҵ� ȣ��
		// DB board���̺��� pwd�� ����ڰ� �Է��� pwd�� ��ġ�ϴ��� ���θ� üũ
		BoardDAOMyBatis dao = new BoardDAOMyBatis();
		String dbPwd = dao.selectPwd(new Integer(idxStr.trim()));
		
		if(!pwd.equals(dbPwd))
		{
			req.setAttribute("message", "��й�ȣ�� ��ġ���� �ʾƿ�");
			req.setAttribute("loc", "javascript:history.back()");;
			this.setRedirect(false);
			this.setViewPage("memo/message.jsp");
			return;
		}
		
		// 5. ����� ��ġ�Ѵٸ�
		// 	(1) ÷�� ������ ���� ��� ���� ÷�������� �����Ѵ�.
		String filename = dao.selectFile(new Integer(idxStr.trim()));
		
		if(filename != null)
		{
			//���ε� dir ������ ���ϱ�
			ServletContext app = req.getServletContext();
			String upDir = app.getRealPath("/Upload");
			String path = upDir + "/" + filename;
			File delFile = new File(path);
			boolean b = delFile.delete();
			System.out.println("÷������ ���� ���� : " + b);
		}
		
		// 	(2)	DB���� ���ڵ� ����
		int n = dao.deleteBoard(new Integer(idxStr.trim()));
		
		String msg = (n > 0)? "�� ���� ����" : "�� ���� ����";
		String loc = "board-list.do";
		
		req.setAttribute("message", msg);
		req.setAttribute("loc", loc);
		
		
		// 6. �� ��� �޽��� ó��
		
		this.setRedirect(false);
		this.setViewPage("memo/message.jsp");
		
		// 7. �������� ���� �� �̵���� ����

	}

}
