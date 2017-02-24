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
		
		// �Խ��� �۾��� ó�� => �ʿ��� ������Ʈ cos.jar (���ε� ������Ʈ)
		// ���ε� ���丮�� ������
		// C:\MyJava\Workspace2\MyMVC\WebContent\Upload
		ServletContext application = req.getServletContext();
		upDir = application.getRealPath("/Upload");
		System.out.println("upDir");
		
		//1. ÷������ ���ε� ó��
		MultipartRequest mr 
			= new MultipartRequest(req,upDir,10*1024*1024,"UTF-8",new DefaultFileRenamePolicy());
		//System.out.println("���� ���ε� ����");
		
		//2. ����ڰ� �Է��� �� �ޱ�.
		String name = mr.getParameter("name");
		String email = mr.getParameter("email");
		String homepage = mr.getParameter("homepage");
		String pwd = mr.getParameter("pwd");
		String subject = mr.getParameter("subject");
		String content = mr.getParameter("content");
		
		int readnum = 0; //��ȸ��
		String filename = mr.getFilesystemName("filename"); //!!!����
		long filesize = 0; 
		
		//÷�������� �ִ� ���
		
		if(filename != null)
		{
			File f = mr.getFile("filename");
			filesize = f.length();
		}
		
		int refer = 0, lev = 0, sunbun = 0;
		
		//3. VO�� ����ֱ�
		BoardVO board = new BoardVO(null,name,email,homepage,pwd,subject,content,
				null,readnum,filename,filesize,refer,lev,sunbun);
		
		
		//4. DAO���� -biz-method ȣ��
		//BoardDAO dao = new BoardDAO(); jdbc
		//int n = dao.insertBoard(board);
		
		BoardDAOMyBatis z = new BoardDAOMyBatis();
		int n = z.insertBoard(board);
		
		
		//5. ��� �޽��� ó�� �� �̵���� ����
		String msg = (n>0)? "�۾��� ����" : "�۾��� ����";
		String loc = (n>0)? "board-list.do" : "javascript:history.back()";
		
		//6.req�� ����
		req.setAttribute("message", msg);
		req.setAttribute("loc", loc);
		
		//7.�� ������ ���� �� �̵���� ����
		this.setViewPage("/memo/message.jsp");
		this.setRedirect(false);
	}

}
