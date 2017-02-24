package memo.model;
/*  DAO : Data Acess Object
: 데이터베이스에 접근하여 biz로직 (insert, delete, update, select 등)을 수행하는 클래스

CRUD 작업 수행
C: create  ->insert문
R: read   ->select문
U: update -> update문
D delete  ->delete문
*/

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MemoDAO {
   
    private String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private String user = "scott", pwd = "tiger";
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rst;
  
    
    public MemoDAO()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url,user,pwd);
            System.out.println("DB connected....");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }///생성자------------------------
    
    
    public boolean insertMemo2(MemoDTO memo)
    {
    	try {
			String sql = "{call MEMO_ADD(?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, memo.getName());
    		cs.setString(2, memo.getMsg());
    		cs.execute();
    		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    /*메모 내용을 등록하는 메소드 => INSERT문을 수행*/
    public boolean insertMemo(MemoDTO memo) 
    {
        int cnt = 0;
        PreparedStatement ps;
        try {
         
          String sql = "INSERT INTO MEMO(IDX,NAME,MSG,WDATE) VALUES(MEMO_IDX_SEQ.NEXTVAL,?,?,SYSDATE)";
          ps = con.prepareStatement(sql);
          
          ps.setString(1, memo.getName());
          ps.setString(2, memo.getMsg());          
         cnt = ps.executeUpdate();
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(cnt>0)
        {     
            return true;
        } 
        else
            return false;
        
        
     }
    
    public MemoDTO selectMemoByIdx(int idx)
    {
        
        MemoDTO md = new MemoDTO();
        try {
            //select문 where 절
            // ? 셋팅
            //실행 => rs
            //반복문 돌면서 db 컬럼 데이터 꺼내기
            //MemoDTO에 담아서 반환
            
            String sql ="SELECT IDX,NAME,MSG,WDATE FROM MEMO WHERE IDX = ?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setInt(1, idx);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int num = rs.getInt(1);
                String name = rs.getString(2);
                String msg = rs.getString(3);
                java.sql.Date wdate = rs.getDate(4);
               
                md = new MemoDTO(num,name,msg,wdate);               
                                
            }
            
    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return md;
    }
            
          
    
    public Vector<MemoDTO> selectAllMemo()   //모든 메모 목록을 가져오는 메소드
    {
        
  
        Vector<MemoDTO> v = new Vector<MemoDTO>();
        
            try {
            //1. select 문 작성 order by idx desc
            //2 ps 얻어오기
            //3 executeexxx() 메소드로 rs받기
            //4 반복문 돌면서 컬럼데이터 꺼내기
            //5 MemoDTO를 생성해서 데이터를 setting
            
            String sql = "SELECT IDX,NAME,RPAD(MSG,50,' '),WDATE FROM MEMO ORDER BY IDX DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int idx = rs.getInt(1);
                String name = rs.getString(2);
                String msg = rs.getString(3);
                java.sql.Date date = rs.getDate(4);
                
                MemoDTO md = new MemoDTO(idx,name,msg,date);
              
                
                v.add(md);
            }
          
          
        } catch (Exception e) {
        }
            return v;
    }
    
    public boolean deleteMemo(int idx)   //글 번호(pk)로 특정 글을 삭제하는 메소드 -delete문 수행
    {      
        int cnt=0;
            
        try {
            //sql문 작성 -> PreparedStatement 얻기 -> ? 값 세팅 -> 실행 -> 그 결과 반환
            String sql = "DELETE FROM MEMO WHERE IDX=?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1,idx);
            cnt = ps.executeUpdate();        
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
       if(cnt>0)
           return true;
       else
           return false;
    }////delete()----------
    
    
   public boolean updateMemo(MemoDTO memo)
    {
        int cnt=0;
        try {
            //update문 작성 where절
            //ps 얻어오기
            //? 값 세팅
            //실행
            //그 결과 반환값 처리
            
            String sql = "UPDATE MEMO SET NAME = ?, MSG = ?, WDATE = ? WHERE IDX = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, memo.getName());
            ps.setString(2,memo.getMsg());
            ps.setDate(3,memo.getWdate());
            ps.setInt(4, memo.getIdx());
           
            cnt =ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        if(cnt > 0) return true;
        else return false; 
        
    }  //updateMemo()----------
   
   public void WriterSearch(String name)
   {
        try {
            String sql = "SELECT IDX,NAME,MSG,WDATE FROM MEMO WHERE  NAME = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int idx = rs.getInt(1);
                String name1 = rs.getString(2);
                String msg = rs.getString(3);
                java.sql.Date date = rs.getDate(4);
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      
       
   }

    public Vector<MemoDTO> findMemo(int index, String keyword) {// 검색어로 메모 내용을 검색하는 메소드
        try {
            //0 -> 작성자(name), 1 -> 메모내용(msg)  2-> 글번호(idx)
            //SELECT문 작성 WHERE절
            //ps 얻어오기
            //?값 셋팅
            //실행 RS받기.
            //반복문 돌면서 데이터 꺼내와 MEMODTO에 담기
            //vector에 저장 후 반환
            
            String colName="";
            
                      
           
            switch(index)
            {
                case 0:  colName = "name"; break;
                case 1:  colName = "msg"; break;
                case 2:  colName = "idx"; break;
                    
            }///////////Switch
            
           // String sql = "SELECT IDX, NAME, MSG,WDATE FROM MEMO WHERE "+colName+"=?";
          //  System.out.println("sql :" + sql);
          //SELECT문 WHERE절에 LIKE 연산자를 사용해보세요
          String sql = "SELECT IDX, NAME, MSG,WDATE FROM MEMO WHERE "+ colName +" LIKE ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"%"+keyword+"%");
            rst = pstmt.executeQuery();
            Vector<MemoDTO> v = makeVector(rst);
            if(rst !=null) rst.close();
            if(pstmt != null) pstmt.close();
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//////////
    
    private Vector<MemoDTO> makeVector(ResultSet rs) throws SQLException
    {
        
         Vector<MemoDTO> v = new Vector<MemoDTO>();
          while(rs.next())
            {
                int idx = rs.getInt(1);
                String name = rs.getString(2);
                String msg = rs.getString(3);
                java.sql.Date date = rs.getDate(4);
                
                MemoDTO md = new MemoDTO(idx,name,msg,date);
              
                
                v.add(md);
            }
          
          return v;
    }

    public void dbclose() {
        try{
            System.out.println("DB disconnect....");
            if(pstmt != null) pstmt.close();
            if(con != null) con.close();
        }catch(SQLException e)
        {
            
        }
    }
    
}//////////////////////////////////////////////
        
            
          

