package board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
import common.util.MyUtil;

public class BoardEditEndAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. ���� ���ε� ó��
		ServletContext app = req.getServletContext();
		String upDir = app.getRealPath("/Upload");
		System.out.println("upDir");
		
		
		
		try{
		DefaultFileRenamePolicy df = new DefaultFileRenamePolicy();
		
		MultipartRequest mr = new MultipartRequest(req,upDir,10*1024*1024,"UTF-8",df);
		
		// 2.÷�������� �ִٸ� ������ ���ε� �ߴ� ������ �������� ����ó��
		BoardDAOMyBatis dao = new BoardDAOMyBatis();
		String idxStr = mr.getParameter("idx");
		Integer idx = new Integer(idxStr.trim());
		String filename = mr.getFilesystemName("filename");
		long filesize = 0;
		
		if(filename!=null)
		{
			String oldFile = dao.selectFile(idx);
			String path = upDir + "/" + oldFile;
			File delFile = new File(path);
			boolean b = delFile.delete();
			
			//���� ���ε��� ������ ũ�� �˾Ƴ���
			File newFile = mr.getFile("filename");
			filesize = newFile.length();
			
		}
		
		// 3. ����ڰ� �Է��� �� �ޱ�
		
		String name = mr.getParameter("name");
		String email = mr.getParameter("email");
		String homepage = mr.getParameter("homepage");
		String pwd = mr.getParameter("pwd");
		String subject = mr.getParameter("subject");
		String content = mr.getParameter("content");
		
		int readnum = 0, refer = 0, lev = 0, sunbun = 0;
		
		BoardVO board 
		= new BoardVO(idx,name,email,homepage,pwd,subject,content,
				null,readnum,filename,filesize,refer,lev,sunbun);
		
		//4. ����ó��(DAO�޼ҵ� ȣ��)
		int n = dao.updateBoard(board);
		
		//5. �� ��� �޽��� �� �̵���� ó��
		String msg = (n>0)? "��������" : "��������";
		String loc = (n>0)? "board-list.do#bbs" : "javascript:history.back()";
		MyUtil.addMsgLoc(req, msg, loc);
		
		//6. ������������, �̵���� ����
		this.setRedirect(false);
		this.setViewPage("/memo/message.jsp");
		
		}catch(IOException e){
			String msg = "���� : �߸��� ��� �̰ų�\\n ÷������ �뷮�� 10M�� �ʰ��߾��";
			String loc = "javascript:history.back()";
			MyUtil.addMsgLoc(req, msg, loc);
			
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
		}
		
		
	}

}
