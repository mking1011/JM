package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;

public class LogoutAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//세션 변수 모두 무효화
		HttpSession ses = req.getSession();
		ses.invalidate();
		
		this.setRedirect(false);
		this.setViewPage("index.do");

	}

}
