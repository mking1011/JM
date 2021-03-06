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
		
		//1. 현재 보여줄 페이지 파라미터값을 받는다
		String cpStr = req.getParameter("cpage");
		
		if(cpStr == null)
		{
			cpStr = "1";  //디폴트 페이지를 1페이지로 지정
		}
		
		int cpage = Integer.parseInt(cpStr.trim());
		
		//페이즈 사이즈 (한 페이지 당 보여줄 목록 갯수) 받기
		HttpSession session = req.getSession();
		
		String psStr = req.getParameter("pageSize");
		
		if(psStr == null)
		{
			//파라미터로 넘어오지 않았다면 세션에 저장된 pageSize를 꺼낸다
			psStr = (String)session.getAttribute("pageSize");
			if(psStr == null)
			{
				psStr = "5";
			}
		}
		
		//세션에 pageSize 저장
		session.setAttribute("pageSize", psStr);
		
		
		//검색 유형, 검색어 받기
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
		//검색 유형과 검색어를 세션에 저장하자.
		//페이지 이동시 검색 유형, 검색어를 유지하기 위해
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
		
		//검색한 글의 총 게시물 수 가져오기
		int totalCount = dao.getFindTotalCount(findType,findString);
		
		//한 페이지당 보여줄 목록갯수
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
		
		if(cpage <= 0) //음수값
		{
			cpage = 1; //1페이지를 보여준다
		}
		if(cpage>pageCount)
		{
			cpage = pageCount; //마지막 페이지를 보여준다
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
		
		//글 목록 가져오기
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
