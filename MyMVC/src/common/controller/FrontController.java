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
		 * 서블릿 실행시 최초 요청이 있을 때 딱한번만 호출되는 메소드
		 * 여기서 init-param에 기술되어 있는 config에 해당하는 value 값 (command.properties의 절대경로)를 얻어오자
		 * 어노테이션에 명시되어 있음
		*/
		String value = sc.getInitParameter("config");
		System.out.println("init parameter value 값 : " + value);
		Properties props = new Properties();
		
		
		try {			
			props.load(new FileInputStream(value));
			//System.out.println(props.getProperty("/index.do"));
			//Command.properties 파일에 매핑된 정보를 properties 자료구조에 옮긴다.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Properties객체에 저장된 key 값들만 추출하자.
		Enumeration<Object> en = props.keys();
		while(en.hasMoreElements())
		{
			String key = en.nextElement().toString();
			String className = props.getProperty(key); //value 값 얻기
			System.out.println(key + "/" +className);
			
			if(className != null)
			{
				className = className.trim(); //앞뒤 공백 제거 
			}
			
			try {
				Class cls = Class.forName(className);
				Object cmdInstance = cls.newInstance();
				//해당 클래스를 객체화시켜 메모리에 올려준다
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
			
			System.out.println("FronController실행됨...");
			//1. 클라이언트의 요청 URI를 분석해보자.
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
				System.out.println("action이 null");
				return;
			}
		
			
			
			try{
			//******
				action.execute(req, res);
				String viewPage = action.getViewPage(); //이동할 뷰 페이지 얻기.
				System.out.println("viewPage : " + viewPage);
				
				//이동 방식에 따라서 뷰 페이지 이동
				if(action.isRedirect())
				{
					//redirect 방식으로 이동
					res.sendRedirect(viewPage);
				}
				else
				{
					//forward방식으로 이동
					RequestDispatcher disp = req.getRequestDispatcher(viewPage);
					disp.forward(req, res);
				}
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}

}
