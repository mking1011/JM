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
	
	static     //메인보다 먼저 실행
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

	
	//카테고리 목록 가져오기
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
	

	
	
	//Pspec 별로 (HIT,NEW,BEST..) 상품 정보를 가져오는 메소드
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

	
	//상품번호(pk)로 상품정보 가져오기
	public ProductVO selectByPnum(Integer pnum) {
		try {
			
			ses = factory.openSession(true);  //오토커밋
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
