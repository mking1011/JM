package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("IndexAction�� execute() ȣ���...");
		
		req.setAttribute("mymsg", "��ſ� �Ϸ� �Ǽ���~");
		this.setViewPage("/index.jsp"); 		//�� ������ ����
		this.setRedirect(false); 				//�̵���� ���� (forward->false redirect->true)
	}
	
}
