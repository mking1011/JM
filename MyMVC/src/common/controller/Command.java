package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//�������̽�  : �߻� �޼ҵ� + public static final ����� �ɹ��� ���´�.
public interface Command {

	void execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
	//�������̽��� �߻� �޼ҵ忡�� �ڵ������� public abstract �����ڰ� �ٴ´�.	
}
