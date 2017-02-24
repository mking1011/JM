package shop.model;
import java.util.*;
import java.io.*;

/*
 * 장바구니(ArrayList)에 상품을 추가, 삭제, 편집하는 로직을 갖는 서비스 빈즈
 */


public class CartBean implements Serializable{
	
	private List<ProductVO> cartList = new ArrayList<ProductVO>();

	public List<ProductVO> getCartList() {
		return cartList;
	}
	
	/**장바구니에 상품을 추가하는 메소드*/
	public void addProduct(Integer pnum, int oqty, ProductVO item)
	{
		//1. 새로 추가하려는 상품이 이미 장바구니(cartList)에 있는지 여부를 체크한다. (있다면 수량만 변경)
		
		if(item == null) return;
		
		for(ProductVO cartP : cartList)
		{
			if(cartP.getPnum().equals(pnum))
			{
				//장바구니 상품의 수량을 가져와 새로 추가하는 수량을 누적한다.
				int qty = cartP.getPqty() + oqty;
				cartP.setPqty(qty);
				return;
			}
		}
		
		//2. 그렇지 않고 새로 추가하려는 상품이라면 cartList에 저장.
		
		item.setPqty(oqty);  //수량설정
		cartList.add(item);  //장바구니에 저장
	}
	
	/**장바구니에 담긴 상품의 총 금액과 총 포인트를 구해 HashMap에 저장하여 반환하는 메소드*/
	public Map<String,Integer> getCartTotal()
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		int cartTotalPrice = 0, cartTotalPoint = 0;
		
		for(ProductVO pd :cartList)
		{
			cartTotalPrice += pd.getTotalPrice();
			cartTotalPoint += pd.getTotalPoint();
		}
		
		map.put("cartTotalPrice", cartTotalPrice);
		map.put("cartTotalPoint", cartTotalPoint);
		
		return map;
	}
	
	/**장바구니에 특정 상품 삭제하기*/
	public void removeProduct(Integer pnum) 
	{
		for(ProductVO cartPd : cartList)
		{
			if(cartPd.getPnum().equals(pnum))
			{
				cartList.remove(cartPd);
				break;
			}
		}
	}
	
	/**장바구니 특정 상품의 수량을 수정하는 메소드*/
	public void setEdit(Integer pnum, int oqty) {
		
		for(ProductVO cartPd : cartList)
		{
			if(cartPd.getPnum().equals(pnum))
			{
				if(oqty == 0)
				{
					//삭제처리
					cartList.remove(cartPd);
				}
				else if(oqty > 0)
				{
					//수량수정
					cartPd.setPqty(oqty);
				}
				else
				{
					//음수 : 예외 발생
					throw new NumberFormatException("수량은 양수여야 해요");
				}
				
				break;
			}
		}
	}
	
	/**장바구니 비우기*/
	public void clearAll()
	{
		cartList.clear();
	}



	
}///////////////
