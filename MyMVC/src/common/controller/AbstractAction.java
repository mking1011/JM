package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import user.member.model.MemberVO;

//�߻� Ŭ���� : command�������̽��� ��ӹ޾Ƽ� execute() �߻� �޼ҵ带 �ɹ��� ���´�.
public abstract class AbstractAction implements Command{
	
	private String viewPage; //�� ������ ���� ���� ����
	private boolean isRedirect; //������ �̵���İ� ���õ� ���� ���� ����
								//redirect������� �̵��� true, forward�̵��� false�� ���´�.
	
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	public MemberVO loginCheck(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		MemberVO user = (MemberVO)session.getAttribute("loginUser");
		return user;
	}
		
}//////
