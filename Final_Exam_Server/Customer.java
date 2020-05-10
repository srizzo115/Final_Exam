import java.util.ArrayList;

public class Customer {
	public String name;
	public ArrayList<Bid> bidHistory ;
	
	public Customer(String n) {
		name = n;
		bidHistory = new ArrayList<Bid>();
	}
	
	public boolean addBid(Bid b) {
		return bidHistory.add(b);
	}
}
