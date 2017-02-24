package user.member.model;


//로그인 및 회원인증 처리에 사용되는 사용자 정의 예외 클래스
public class NotMemberException extends Exception {
	
	public NotMemberException()
	{
		super("NotMemberException");
	}
	
	public NotMemberException(String msg)
	{
		super(msg);
	}
}////////////
