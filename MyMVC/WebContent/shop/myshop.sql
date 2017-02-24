--ī�װ� ���̺�

--drop table category;

create table category(
	cnum number(8) constraint category_cnum_pk primary key,
	code varchar2(10) constraint catefory_code_uk unique,
	cname varchar2(50) not null	
);

--drop sequence category_cnum_seq;

create sequence category_cnum_seq
start with 1
increment by 1
nocache;

insert into category values(category_cnum_seq.nextval,'10000000','������ǰ');
insert into category values(category_cnum_seq.nextval,'20000000','��Ȱ��ǰ');
insert into category values(category_cnum_seq.nextval,'30000000','������');



-- ��ǰ ���̺�
--drop table product;

create table product(
    --��ǰ��ȣ(pk)
    pnum number(8) constraint product_pnum_pk primary key,
    pname varchar2(100) not null, --��ǰ��
    pcategory_fk varchar2(10)
       constraint product_pcategory_fk
       references category (code), --ī�װ� �ڵ�(�θ����̺��� code�÷� ����)
    pcompany varchar2(50), --������
    pimage1 varchar2(100) default 'noimage.png', --��ǰ�̹���1
    pimage2 varchar2(100) default 'noimage.png', --��ǰ�̹���2
    pimage3 varchar2(100) default 'noimage.png', --��ǰ�̹���3
    pqty number(8) default 0, --��ǰ ����
    price number(8) default 0, --��ǰ ����
    saleprice number(8) default 0, --��ǰ �ǸŰ�
    pspec varchar2(20), --��ǰ ���� (HIT,BEST,NEW)
    pcontents varchar2(1000), --��ǰ ����
    point number(8) default 0, --����Ʈ
    pindate date default sysdate --�԰���
 );


--drop sequence product_pnum_seq;
create sequence product_pnum_seq nocache;

  insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'NoteBook',10000000,
 'SAMSUNG','note1.png','note2.png','note3.png',100,1200000,
 1000000,'HIT','�����ؿ�',50);
 
   insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'NoteBook',10000000,
 'APPLE','snote1.png','snote2.png','snote3.png',43,890000,
 800000,'HIT','�����ؿ�',50);
 
  insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'TV',10000000,
 'LG','tv1.png','tv2.png','tv3.png',97,2340000,
 300000,'HIT','ȭ�Ұ� ���ƿ�',54);
 
  insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'����',20000000,
 '���̼�','t1.png','t2.png','t3.png',17,42100,
 39000,'NEW','�Ǽ��ؿ�',5);
 
   insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'������s7',10000000,
 'SAMSUNG','p1.png','p2.png','p3.png',17,891000,
 800000,'NEW','���̽��� ���� �����',5);
 
 insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'�ظ����� ���ֹ��� ����',30000000,
 '���м�ø','book�ظ�����.PNG','book�ظ�����.PNG','book�ظ�����.PNG',17,17000,
 15000,'NEW','���ο� �ظ����� �ø���!',2);
 
 insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'���μ��� ���������Ƿ�',30000000,
 '�����ø','book���������Ƿ�.png','book���������Ƿ�.png','book���������Ƿ�.png',17,23000,
 21000,'NEW','���������Ƿ��� �ѱ����� ������!',1);

 


commit;
