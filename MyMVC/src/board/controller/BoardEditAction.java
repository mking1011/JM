package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
import common.util.MyUtil;

public class BoardEditAction extends AbstractAction {

   @Override
   public void execute(HttpServletRequest req,
         HttpServletResponse res) throws Exception {
      //1. �۹�ȣ,��� �ޱ�
      String idxStr=req.getParameter("idx");
      String pwd=req.getParameter("pwd");
      //2. ��ȿ�� üũ
      if(idxStr==null || pwd==null || pwd.trim().isEmpty()){
         this.setRedirect(true);
         this.setViewPage("board-list.do#bbs");
         return;
      }//if
      
      //3. db��� ������ ���ϱ�
      BoardDAOMyBatis dao=new BoardDAOMyBatis();
      String dbPwd=dao.selectPwd(new Integer(idxStr.trim()));
      if(!pwd.equals(dbPwd)){
         String loc="javascript:history.back()";
         MyUtil.addMsgLoc(req, "��й�ȣ�� Ʋ����", loc);
         this.setRedirect(false);
         this.setViewPage("memo/message.jsp");  
         return;
      }//if
      
      //4. ����� ��ġ�ϸ� �ش� �� ���� ��������
      BoardVO board=dao.viewBoard(new Integer(idxStr.trim()));
      
      req.setAttribute("board", board);
      
      this.setRedirect(false);
      this.setViewPage("/board/boardEdit.jsp");

   }///

}/////cs


