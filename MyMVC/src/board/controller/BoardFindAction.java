package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardFindAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		//1. ���� ������ ������ �Ķ���Ͱ��� �޴´�
		String cpStr = req.getParameter("cpage");
		
		if(cpStr == null)
		{
			cpStr = "1";  //����Ʈ �������� 1�������� ����
		}
		
		int cpage = Integer.parseInt(cpStr.trim());
		
		//������ ������ (�� ������ �� ������ ��� ����) �ޱ�
		HttpSession session = req.getSession();
		
		String psStr = req.getParameter("pageSize");
		
		if(psStr == null)
		{
			//�Ķ���ͷ� �Ѿ���� �ʾҴٸ� ���ǿ� ����� pageSize�� ������
			psStr = (String)session.getAttribute("pageSize");
			if(psStr == null)
			{
				psStr = "5";
			}
		}
		
		//���ǿ� pageSize ����
		session.setAttribute("pageSize", psStr);
		
		
		//�˻� ����, �˻��� �ޱ�
		String findTypeStr = req.getParameter("findType");
		String findString = req.getParameter("findString");
		
		if(findTypeStr == null)
		{
			findTypeStr = (String)session.getAttribute("findTypeStr");
			if(findTypeStr == null)
			{
				findTypeStr = "1";
			}
		}
		
		if(findString == null)
		{
			findString = (String)session.getAttribute("findString");
			if(findString == null)
			{
				findString = "";
			}
		}
		//�˻� ������ �˻�� ���ǿ� ��������.
		//������ �̵��� �˻� ����, �˻�� �����ϱ� ����
		session.setAttribute("findTypeStr", findTypeStr);
		session.setAttribute("findString", findString);
		
		String findType = "";
		
		switch(findTypeStr)
		{
			case "1" : findType = "subject"; break;
			case "2" : findType = "name"; break;
			case "3" : findType = "content"; break;
		}
		
		
		
		
		//BoardDAO dao = new BoardDAO();
		BoardDAOMyBatis dao = new BoardDAOMyBatis();
		
		//�˻��� ���� �� �Խù� �� ��������
		int totalCount = dao.getFindTotalCount(findType,findString);
		
		//�� �������� ������ ��ϰ���
		int pageSize = Integer.parseInt(psStr.trim());
		int pageCount = 1;
		
	//////////////////////////////////////////////////////////////
		/*	if(totalCount%pageSize == 0)
			{
				pageCount = totalCount/pageSize;
			}
			else
			{
				pageCount = totalCount/pageSize + 1;
			}*/
	//////////////////////////////////////////////////////////////
	
		pageCount = (totalCount-1)/pageSize+1;
		
		if(cpage <= 0) //������
		{
			cpage = 1; //1�������� �����ش�
		}
		if(cpage>pageCount)
		{
			cpage = pageCount; //������ �������� �����ش�
		}
		
		int end = cpage *pageSize;
		int start = end - (pageSize - 1);
		
		/*	select * from board order by idx desc;

			select rownum rn, board.* from board order by idx desc;
			
			
			select * from(
			select rownum rn,a.*from
			(select * from board order by idx desc)a
			)
			where rn between 6 and 10;
			
			----------------------------------------------------  
			  cpage pageSize  start end
			  1     5         1     5
			  2     5         6     10
			  3     5         11    15
			  4     5         16    20
			-----------------------------------------------------
		 */
		
		//�� ��� ��������
		List<BoardVO> arr = dao.findListBoard(start,end,findType,findString);
		
		req.setAttribute("boardList", arr);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("cpage", cpage);
		req.setAttribute("pageSize", pageSize);
		req.setAttribute("findTypeStr", findTypeStr);
		req.setAttribute("findString", findString);
		
		this.setViewPage("/board/boardFind.jsp");
		this.setRedirect(false);
	}

}
