package file.down;

import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileDown")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		download(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		download(request,response);
	}
	
	private void download(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		// 1. �ٿ�ε� �� ���ϸ� �޾ƿ���
		String filename = req.getParameter("filename");
		System.out.println("filename = " + filename );
		if(filename == null || filename.trim().isEmpty())
		{
			return;
		}
		
		//2. ���ε��� ���丮�� ������ ���.
		//JSP������ application.getRealPath("/Upload");
		ServletContext application = req.getServletContext();
		String upDir = application.getRealPath("/Upload");
		
		//3. �ٿ�ε� ���� ������ ������ �����.
		String path = upDir +"/" + filename;
		
		/*response ����Ʈ Ÿ�� ������ �ϸ� �������� �ش� ����Ʈ Ÿ���� �� �� �ִ� �����̸� �����ְ�,
		 * ���� �� �𸣴� ���� �����̸� �ٿ�ε� â�� �����Ų��.*/
		res.setContentType("application/octet-stream"); //res.setContentType("application/unknown"); // <- �ٿ�ε� ���̾�α׸� ���
		//�ѱ� ���ϸ��� ������ ���� ���� ����
		String filename_en = new String(filename.getBytes(),"ISO-8859-1");
		
		String attach = "attachment;filename=" +filename_en;
		res.setHeader("Content-Disposition", attach);
		
		//4. 3�� ��η� ��Ʈ�� �����ؼ� ������ �о���̰� Ŭ���̾�Ʈ ������ ��������.
		/*
		 * ������ �ҽ� : ����(path)
		 *  => FileInputStream ==> BufferedInputStream
		 * ������ ������ : Ŭ���̾�Ʈ (��������)
		 * 	=> ServletOutputStream => BufferedOutputStream
		 * */
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		BufferedOutputStream bos = new BufferedOutputStream(res.getOutputStream());
		
		byte[] data = new byte[4000];
		int n = 0;
		while((n = bis.read(data)) != -1)
		{
			bos.write(data,0,n);
			bos.flush();
		}
		if(bis != null) bis.close();
		if(bos != null) bos.close();
	}
	
}
