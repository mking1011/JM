package board.controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.*;
import common.controller.AbstractAction;

public class BoardWriteEndAction extends AbstractAction {

	
	private String upDir;
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// 게시판 글쓰기 처리 => 필요한 컴포넌트 cos.jar (업로드 컴포넌트)
		// 업로드 디렉토리의 절대경로
		// C:\MyJava\Workspace2\MyMVC\WebContent\Upload
		ServletContext application = req.getServletContext();
		upDir = application.getRealPath("/Upload");
		System.out.println("upDir");
		
		//1. 첨부파일 업로드 처리
		MultipartRequest mr 
			= new MultipartRequest(req,upDir,10*1024*1024,"UTF-8",new DefaultFileRenamePolicy());
		//System.out.println("파일 업로드 성공");
		
		//2. 사용자가 입력한 값 받기.
		String name = mr.getParameter("name");
		String email = mr.getParameter("email");
		String homepage = mr.getParameter("homepage");
		String pwd = mr.getParameter("pwd");
		String subject = mr.getParameter("subject");
		String content = mr.getParameter("content");
		
		int readnum = 0; //조회수
		String filename = mr.getFilesystemName("filename"); //!!!주의
		long filesize = 0; 
		
		//첨부파일이 있는 경우
		
		if(filename != null)
		{
			File f = mr.getFile("filename");
			filesize = f.length();
		}
		
		int refer = 0, lev = 0, sunbun = 0;
		
		//3. VO에 담아주기
		BoardVO board = new BoardVO(null,name,email,homepage,pwd,subject,content,
				null,readnum,filename,filesize,refer,lev,sunbun);
		
		
		//4. DAO생성 -biz-method 호출
		//BoardDAO dao = new BoardDAO(); jdbc
		//int n = dao.insertBoard(board);
		
		BoardDAOMyBatis z = new BoardDAOMyBatis();
		int n = z.insertBoard(board);
		
		
		//5. 결과 메시지 처리 및 이동경로 지정
		String msg = (n>0)? "글쓰기 성공" : "글쓰기 실패";
		String loc = (n>0)? "board-list.do" : "javascript:history.back()";
		
		//6.req에 저장
		req.setAttribute("message", msg);
		req.setAttribute("loc", loc);
		
		//7.뷰 페이지 지정 및 이동방식 지정
		this.setViewPage("/memo/message.jsp");
		this.setRedirect(false);
	}

}
