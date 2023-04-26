import javax.management.StringValueExp;
import java.util.Comparator;

import static java.lang.System.out;
public class Bid {

    public SellersBids comparator;
    final public String name;
    private int bid;

    public int getBid(){
        return bid;
    }

    public String getName(){
        return name;
    }

    void setBid(int newBid){
        bid = newBid;
    }

    public Bid(String name, int bid) {
        this.name = name;
        this.bid = bid;
    }

    public int hashCode() {
        return 1 + 23*bid + 31*name.hashCode();
    }

    public boolean equals(Object obj){
        if (obj == null || !(obj instanceof Bid)) return false;
        try {
            Bid bid = (Bid) obj;

            if(bid.getName().equals(this.name)){
                return true;
            }
            else {
                return false;
            }

        }
        // hitta en mer exakt exception
        catch (Exception e) {
            throw new UnsupportedOperationException();
        }
    }

    public String toString(){

        String amount = String.valueOf(bid);

        return name + " has bid " + amount;

    }
}


