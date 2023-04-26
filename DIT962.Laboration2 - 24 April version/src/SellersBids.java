import java.util.Comparator;

public class SellersBids implements Comparator<Bid> {

    @Override
    public int compare(Bid o1, Bid o2) {
        return Integer.compare(o1.getBid(),o2.getBid());
    }
}
