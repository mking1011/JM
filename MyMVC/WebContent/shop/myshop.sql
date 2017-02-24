--카테고리 테이블

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

insert into category values(category_cnum_seq.nextval,'10000000','전자제품');
insert into category values(category_cnum_seq.nextval,'20000000','생활용품');
insert into category values(category_cnum_seq.nextval,'30000000','도서류');



-- 상품 테이블
--drop table product;

create table product(
    --상품번호(pk)
    pnum number(8) constraint product_pnum_pk primary key,
    pname varchar2(100) not null, --상품명
    pcategory_fk varchar2(10)
       constraint product_pcategory_fk
       references category (code), --카테고리 코드(부모테이블의 code컬럼 참조)
    pcompany varchar2(50), --제조사
    pimage1 varchar2(100) default 'noimage.png', --상품이미지1
    pimage2 varchar2(100) default 'noimage.png', --상품이미지2
    pimage3 varchar2(100) default 'noimage.png', --상품이미지3
    pqty number(8) default 0, --상품 수량
    price number(8) default 0, --상품 정가
    saleprice number(8) default 0, --상품 판매가
    pspec varchar2(20), --상품 스펙 (HIT,BEST,NEW)
    pcontents varchar2(1000), --상품 설명
    point number(8) default 0, --포인트
    pindate date default sysdate --입고일
 );


--drop sequence product_pnum_seq;
create sequence product_pnum_seq nocache;

  insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'NoteBook',10000000,
 'SAMSUNG','note1.png','note2.png','note3.png',100,1200000,
 1000000,'HIT','선명해요',50);
 
   insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'NoteBook',10000000,
 'APPLE','snote1.png','snote2.png','snote3.png',43,890000,
 800000,'HIT','슬림해요',50);
 
  insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'TV',10000000,
 'LG','tv1.png','tv2.png','tv3.png',97,2340000,
 300000,'HIT','화소가 좋아요',54);
 
  insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'휴지',20000000,
 '다이소','t1.png','t2.png','t3.png',17,42100,
 39000,'NEW','뽀송해요',5);
 
   insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'갤럭시s7',10000000,
 'SAMSUNG','p1.png','p2.png','p3.png',17,891000,
 800000,'NEW','케이스도 같이 드려요',5);
 
 insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'해리포터 저주받은 아이',30000000,
 '문학수첩','book해리포터.PNG','book해리포터.PNG','book해리포터.PNG',17,17000,
 15000,'NEW','새로운 해리포터 시리즈!',2);
 
 insert into product(pnum,pname,pcategory_fk,
 pcompany,pimage1,pimage2,pimage3,pqty,price,
 saleprice,pspec,pcontents,point)
 values(product_pnum_seq.nextval,'설민석의 조선왕조실록',30000000,
 '역사수첩','book조선왕조실록.png','book조선왕조실록.png','book조선왕조실록.png',17,23000,
 21000,'NEW','조선왕족실록을 한권으로 끝낸다!',1);

 


commit;
