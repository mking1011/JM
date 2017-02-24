package board.model;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.*;
import java.util.*;
import java.io.*;



public class BoardDAOMyBatis {
	
	//어떤 mapper를 사용할지 정함(네임스페이스 지정 필수);
	private final String NS = "board.model.BoardMapper";
	private SqlSession ses;
	
	//세션 펙토리를 얻는 메소드 구성
	private SqlSessionFactory getSessionFactory()
	{
		String resource = "config/mybatis-config.xml";
		InputStream is = null;
		try {
			
			is = Resources.getResourceAsStream(resource);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSessionFactory fac = new SqlSessionFactoryBuilder().build(is);
		return fac;
	}
	
	//[게시판 목록 관련] 총 게시글 수 가져오기
	public int getTotalCount()
	{
		ses = this.getSessionFactory().openSession();
		int count = ses.selectOne(NS + ".totalCount");
		
		close();
		return count;
	}

	public int insertBoard(BoardVO board) {
		ses = this.getSessionFactory().openSession();
		try{
			int n =ses.insert(NS + ".insertBoard", board);
			if(n>0)
			{
				ses.commit();
			}else
			{
				ses.rollback();
			}
			
			return n;
		}finally{
			close();
		}
	}

	public List<BoardVO> listBoard(int start, int end) {
		try {
			
			ses = this.getSessionFactory().openSession();
						
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("start", start);
			map.put("end", end);
			List<BoardVO> arr = ses.selectList(NS+".listBoardPaging",map);
			return arr;
			
		} finally {
			close();
		}
	}
	
	//게시글 내용보기 관련 - 조회수 증가
	public boolean updateReadnum(Integer idx) {
		try{
			ses = this.getSessionFactory().openSession();
			int n = ses.update(NS + ".updateReadnum", idx);
			if(n > 0)
			{
				ses.commit();
				return true;
			}
			else
			{
				ses.rollback();
				return false;
			}
		}finally{
			close();
		}
	}
	
	//게시글 내용 보기 관련
	public BoardVO viewBoard(Integer idx) {
		try{
			ses = this.getSessionFactory().openSession();
			BoardVO vo = ses.selectOne(NS + ".selectBoard",idx);
			
			return vo;
		}finally{
			close();
		}
	}
	
	
	public String selectPwd(Integer idx) {
		
		try {
			
			ses = this.getSessionFactory().openSession();
			String dbPwd = ses.selectOne(NS + ".selectPwd",idx);
			return dbPwd;
			
		} finally {
			close();
		}
	}
	
	//글 번호로 첨부파일 가져오기
	public String selectFile(Integer idx) {
		try {
			
			ses = getSessionFactory().openSession();
			String filename = ses.selectOne(NS + ".selectFile", idx);
			return filename;
			
		} finally {
			close();
		}
	}
	
	//글 번호로 글 삭제 처리
	public int deleteBoard(Integer idx) {
		try {
			
			ses = this.getSessionFactory().openSession(true); //자동 commit을 하고 싶으면 true값을 준다.
			int n = ses.delete(NS + ".deleteBoard", idx);
			
			return n;
			
		} finally {
			close();
		}
	}
	
	
	//글번호로 글 수정 처리
	public int updateBoard(BoardVO board) {
		
		try{
			ses = this.getSessionFactory().openSession(true);			
			int n = ses.update(NS + ".updateBoard", board);			
			return n;			
		} finally{
			close();
		}
	}
	
	//검색한 글의 게시글 수 가져오기
	public int getFindTotalCount(String findType, String findString) {
		
		System.out.println(findType + "//" + findString);
		
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("findType", findType);
			map.put("findString", findString);
			ses = this.getSessionFactory().openSession(true);
			int count = ses.selectOne(NS + ".findTotalCount", map);
			
			return count;
			
		} finally{
			
		}
	}
	
	//검색한 게시물 가져오기
	public List<BoardVO> findListBoard(int start, int end, String findType, String findString) {
		try {
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("findType", findType);
			map.put("findString", findString);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			ses = getSessionFactory().openSession(true);
			
			List<BoardVO> arr = ses.selectList(NS + ".findListBoard", map);
			return arr;
			
		} finally {
			close();
		}
	}
	
	private void close() {
		if(ses != null) ses.close();
	}







}
