/*�ܼ��� �Խ���*/
--drop table board;
create table board(
   idx number(8) constraint board_idx_px primary key,
   name varchar2(30) not null,
   email varchar2(100),
   homepage varchar2(200),
   pwd varchar2(20) not null, -- �� ��й�ȣ(����,������ ���)
   subject varchar2(200),
   content varchar2(2000),
   wdate timestamp default systimestamp,
   readnum number(8), --��ȸ��
   filename varchar2(100), --÷�����ϸ�
   filesize number, --���� ũ��
   refer number, --�� �׷��ȣ
   lev number, --�㺱 ���� [�亯�� �Խ��ǿ��� ���]
   sunbun number --���� �� �׷� ���� ���� ����[�㺯�� �Խ��ǿ��� ���]
);
--idx ������ ����

drop sequence board_idx_seq;

create sequence board_idx_seq
start with 1
increment by 1
nocache;