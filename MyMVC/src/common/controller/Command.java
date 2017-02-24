package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//인터페이스  : 추상 메소드 + public static final 상수만 맴버로 갖는다.
public interface Command {

	void execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
	//인터페이스의 추상 메소드에는 자동적으로 public abstract 지정자가 붙는다.	
}
