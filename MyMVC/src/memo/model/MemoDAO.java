package memo.model;
/*  DAO : Data Acess Object
: �����ͺ��̽��� �����Ͽ� biz���� (insert, delete, update, select ��)�� �����ϴ� Ŭ����

CRUD �۾� ����
C: create  ->insert��
R: read   ->select��
U: update -> update��
D delete  ->delete��
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
        
    }///������------------------------
    
    
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
    
    /*�޸� ������ ����ϴ� �޼ҵ� => INSERT���� ����*/
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
            //select�� where ��
            // ? ����
            //���� => rs
            //�ݺ��� ���鼭 db �÷� ������ ������
            //MemoDTO�� ��Ƽ� ��ȯ
            
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
            
          
    
    public Vector<MemoDTO> selectAllMemo()   //��� �޸� ����� �������� �޼ҵ�
    {
        
  
        Vector<MemoDTO> v = new Vector<MemoDTO>();
        
            try {
            //1. select �� �ۼ� order by idx desc
            //2 ps ������
            //3 executeexxx() �޼ҵ�� rs�ޱ�
            //4 �ݺ��� ���鼭 �÷������� ������
            //5 MemoDTO�� �����ؼ� �����͸� setting
            
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
    
    public boolean deleteMemo(int idx)   //�� ��ȣ(pk)�� Ư�� ���� �����ϴ� �޼ҵ� -delete�� ����
    {      
        int cnt=0;
            
        try {
            //sql�� �ۼ� -> PreparedStatement ��� -> ? �� ���� -> ���� -> �� ��� ��ȯ
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
            //update�� �ۼ� where��
            //ps ������
            //? �� ����
            //����
            //�� ��� ��ȯ�� ó��
            
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

    public Vector<MemoDTO> findMemo(int index, String keyword) {// �˻���� �޸� ������ �˻��ϴ� �޼ҵ�
        try {
            //0 -> �ۼ���(name), 1 -> �޸𳻿�(msg)  2-> �۹�ȣ(idx)
            //SELECT�� �ۼ� WHERE��
            //ps ������
            //?�� ����
            //���� RS�ޱ�.
            //�ݺ��� ���鼭 ������ ������ MEMODTO�� ���
            //vector�� ���� �� ��ȯ
            
            String colName="";
            
                      
           
            switch(index)
            {
                case 0:  colName = "name"; break;
                case 1:  colName = "msg"; break;
                case 2:  colName = "idx"; break;
                    
            }///////////Switch
            
           // String sql = "SELECT IDX, NAME, MSG,WDATE FROM MEMO WHERE "+colName+"=?";
          //  System.out.println("sql :" + sql);
          //SELECT�� WHERE���� LIKE �����ڸ� ����غ�����
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
        
            
          

