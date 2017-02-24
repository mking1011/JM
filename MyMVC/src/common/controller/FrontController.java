package common.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import memo.controller.MemoDeleteAction;
import memo.controller.MemoEditAction;
import memo.controller.MemoEditEndAction;
import memo.controller.MemoInsertAction;
import memo.controller.MemoListAction;
import memo.controller.MemoWriteAction;

@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "config", 
				value = "C:/MyJava/Workspace2/MyMVC/WebContent/WEB-INF/Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> cmdMap = new HashMap<String,Object>();
	
	@Override
	public void init(ServletConfig sc) throws ServletException
	{
		/*
		 * ���� ����� ���� ��û�� ���� �� ���ѹ��� ȣ��Ǵ� �޼ҵ�
		 * ���⼭ init-param�� ����Ǿ� �ִ� config�� �ش��ϴ� value �� (command.properties�� ������)�� ������
		 * ������̼ǿ� ��õǾ� ����
		*/
		String value = sc.getInitParameter("config");
		System.out.println("init parameter value �� : " + value);
		Properties props = new Properties();
		
		
		try {			
			props.load(new FileInputStream(value));
			//System.out.println(props.getProperty("/index.do"));
			//Command.properties ���Ͽ� ���ε� ������ properties �ڷᱸ���� �ű��.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Properties��ü�� ����� key ���鸸 ��������.
		Enumeration<Object> en = props.keys();
		while(en.hasMoreElements())
		{
			String key = en.nextElement().toString();
			String className = props.getProperty(key); //value �� ���
			System.out.println(key + "/" +className);
			
			if(className != null)
			{
				className = className.trim(); //�յ� ���� ���� 
			}
			
			try {
				Class cls = Class.forName(className);
				Object cmdInstance = cls.newInstance();
				//�ش� Ŭ������ ��üȭ���� �޸𸮿� �÷��ش�
				cmdMap.put(key, cmdInstance);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestProcess(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestProcess(request,response);
	}
		
	private void requestProcess(HttpServletRequest req, HttpServletResponse res) {
			
			System.out.println("FronController�����...");
			//1. Ŭ���̾�Ʈ�� ��û URI�� �м��غ���.
			String uri = req.getRequestURI();
			System.out.println("URI : " + uri);
			
			String myctx = req.getContextPath();
			System.out.println("myctx : " + myctx);
			int len = myctx.length();
			System.out.println("len : " + len);
			String cmd = uri.substring(len);
			System.out.println("cmd : " + cmd);
			
			AbstractAction action = null;
			
			///////////////
			
			Object instance = cmdMap.get(cmd);
			action = (AbstractAction)instance;
			
			///////////////
			
			
			if(action == null)
			{
				System.out.println("action�� null");
				return;
			}
		
			
			
			try{
			//******
				action.execute(req, res);
				String viewPage = action.getViewPage(); //�̵��� �� ������ ���.
				System.out.println("viewPage : " + viewPage);
				
				//�̵� ��Ŀ� ���� �� ������ �̵�
				if(action.isRedirect())
				{
					//redirect ������� �̵�
					res.sendRedirect(viewPage);
				}
				else
				{
					//forward������� �̵�
					RequestDispatcher disp = req.getRequestDispatcher(viewPage);
					disp.forward(req, res);
				}
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}

}
