package user.member.model;


//�α��� �� ȸ������ ó���� ���Ǵ� ����� ���� ���� Ŭ����
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
