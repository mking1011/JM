package shop.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ProductDAOMyBatis {
	
	private final String NS = "shop.model.BoardMapper";
	private static SqlSessionFactory factory;
	private SqlSession ses;
	
	//public ProductDAOMyBatis(){}
	
	static     //���κ��� ���� ����
	{
		String resource = "config/mybatis-config.xml";
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		factory = new SqlSessionFactoryBuilder().build(is);
	}

	
	//ī�װ� ��� ��������
	public List<CategoryVO> getCategory()
	{
		try{
			
			ses = factory.openSession(true);
			List<CategoryVO> arr = ses.selectList(NS + ".categoryList");
			return arr;
			
		} finally{
			close();
		}
	}
	

	
	
	//Pspec ���� (HIT,NEW,BEST..) ��ǰ ������ �������� �޼ҵ�
	public List<ProductVO> selectByPspec(String pspec)
	{
		try {
			
			ses = factory.openSession(true);
			List<ProductVO> arr = ses.selectList(NS + ".selectByPspec", pspec.toUpperCase());
			return arr;
			
		} finally {
			close();
		}
	}

	
	public List<ProductVO> selectByCategory(String code) {
		try {
			ses = factory.openSession(true);
			List<ProductVO> arr = ses.selectList(NS+".selectByCategory",code);
			return arr;
		} finally {
			close();
		}
	}

	
	//��ǰ��ȣ(pk)�� ��ǰ���� ��������
	public ProductVO selectByPnum(Integer pnum) {
		try {
			
			ses = factory.openSession(true);  //����Ŀ��
			ProductVO prod = ses.selectOne(NS+".selectByPnum",pnum);
			return prod;
		} finally {
			close();
		}
	}
	
	
	private void close() {		
		if(ses != null) ses.close();		
	}
	
}//////////////////////////////
