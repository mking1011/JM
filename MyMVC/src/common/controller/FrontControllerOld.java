package common.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableServer.RequestProcessingPolicy;

import memo.controller.*;

@WebServlet("*.do2")
public class FrontControllerOld extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestProcess(request,response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		requestProcess(req,res);
	}

	private void requestProcess(HttpServletRequest req, HttpServletResponse res) {
		
		System.out.println("FronControllerOld실행됨...");
		//1. 클라이언트의 요청 URI를 분석해보자.
		String uri = req.getRequestURI();
		System.out.println("URI : " + uri);
		
		String myctx = req.getContextPath();
		System.out.println("myctx : " + myctx);
		int len = myctx.length();
		System.out.println("len : " + len);
		String cmd = uri.substring(len+1);
		System.out.println("cmd : " + cmd);
		
		AbstractAction action = null;
		if(cmd.equals("index.do"))
		{
			action = new IndexAction();
		}
		else if(cmd.equals("memoWrite.do"))
		{
			action = new MemoWriteAction();
		}
		else if(cmd.equals("memoInsert.do"))
		{
			action = new MemoInsertAction();
		}
		else if(cmd.equals("memoList.do"))
		{
			action = new MemoListAction();
		}
		else if(cmd.equals("memoDel.do"))
		{
			action = new MemoDeleteAction();
		}
		else if(cmd.equals("memoEdit.do"))
		{
			action = new MemoEditAction();
		}else if(cmd.equals("memoEditEnd.do"))
		{
			action = new MemoEditEndAction();
		}
		
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
