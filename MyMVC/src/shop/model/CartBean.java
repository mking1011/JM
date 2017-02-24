package shop.model;
import java.util.*;
import java.io.*;

/*
 * ��ٱ���(ArrayList)�� ��ǰ�� �߰�, ����, �����ϴ� ������ ���� ���� ����
 */


public class CartBean implements Serializable{
	
	private List<ProductVO> cartList = new ArrayList<ProductVO>();

	public List<ProductVO> getCartList() {
		return cartList;
	}
	
	/**��ٱ��Ͽ� ��ǰ�� �߰��ϴ� �޼ҵ�*/
	public void addProduct(Integer pnum, int oqty, ProductVO item)
	{
		//1. ���� �߰��Ϸ��� ��ǰ�� �̹� ��ٱ���(cartList)�� �ִ��� ���θ� üũ�Ѵ�. (�ִٸ� ������ ����)
		
		if(item == null) return;
		
		for(ProductVO cartP : cartList)
		{
			if(cartP.getPnum().equals(pnum))
			{
				//��ٱ��� ��ǰ�� ������ ������ ���� �߰��ϴ� ������ �����Ѵ�.
				int qty = cartP.getPqty() + oqty;
				cartP.setPqty(qty);
				return;
			}
		}
		
		//2. �׷��� �ʰ� ���� �߰��Ϸ��� ��ǰ�̶�� cartList�� ����.
		
		item.setPqty(oqty);  //��������
		cartList.add(item);  //��ٱ��Ͽ� ����
	}
	
	/**��ٱ��Ͽ� ��� ��ǰ�� �� �ݾװ� �� ����Ʈ�� ���� HashMap�� �����Ͽ� ��ȯ�ϴ� �޼ҵ�*/
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
	
	/**��ٱ��Ͽ� Ư�� ��ǰ �����ϱ�*/
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
	
	/**��ٱ��� Ư�� ��ǰ�� ������ �����ϴ� �޼ҵ�*/
	public void setEdit(Integer pnum, int oqty) {
		
		for(ProductVO cartPd : cartList)
		{
			if(cartPd.getPnum().equals(pnum))
			{
				if(oqty == 0)
				{
					//����ó��
					cartList.remove(cartPd);
				}
				else if(oqty > 0)
				{
					//��������
					cartPd.setPqty(oqty);
				}
				else
				{
					//���� : ���� �߻�
					throw new NumberFormatException("������ ������� �ؿ�");
				}
				
				break;
			}
		}
	}
	
	/**��ٱ��� ����*/
	public void clearAll()
	{
		cartList.clear();
	}



	
}///////////////
