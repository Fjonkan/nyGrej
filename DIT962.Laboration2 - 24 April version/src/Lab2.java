
//import java.io.*;
//import java.util.Comparator;
//import java.util.List;
//import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Lab2 {
    public static String pureMain(String[] commands) {

        PriorityQueue<Bid> sellersQueue = new PriorityQueue<Bid>(new SellersBids());
        PriorityQueue<Bid> buyersQueue = new PriorityQueue<Bid>(new BuyersBid());
        StringBuilder sb = new StringBuilder();

        for (int line_no = 0; line_no < commands.length; line_no++) {
            String line = commands[line_no];
            if (line.equals("")) continue;

            String[] parts = line.split("\\s+");
            if (parts.length != 3 && parts.length != 4)
                throw new RuntimeException("line " + line_no + ": " + parts.length + " words");
            String name = parts[0];
            if (name.charAt(0) == '\0')
                throw new RuntimeException("line " + line_no + ": invalid name");
            String action = parts[1];
            int price;
            try {
                price = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new RuntimeException(
                        "line " + line_no + ": invalid price");
            }


            if (action.equals("K")) {
                Bid buyersBid = new Bid(name, price);
                buyersQueue.add(buyersBid);
            } else if (action.equals("S")) {
                Bid sellersBid = new Bid(name, price);
                sellersQueue.add(sellersBid);
            } else if (action.equals("NK")) {
                int newBuyPrice = Integer.parseInt(parts[3]);
                int oldBuyPrice = Integer.parseInt(parts[2]);
                Bid newBuyBid = new Bid(name, newBuyPrice);
                Bid oldBuyBid = new Bid(name, oldBuyPrice);
                buyersQueue.updatePrice(newBuyBid, oldBuyBid);
                // Bengt NK 70 72
            } else if (action.equals("NS")) {
                int newSellPrice = Integer.parseInt(parts[3]);
                int oldSellPrice = Integer.parseInt(parts[2]);
                Bid newSellBid = new Bid(name, newSellPrice);
                Bid oldSellBid = new Bid(name, oldSellPrice);
                sellersQueue.updatePrice(newSellBid, oldSellBid);

            } else {
                throw new RuntimeException(
                        "line " + line_no + ": invalid action");
            }


            if (sellersQueue.size() == 0 || buyersQueue.size() == 0) continue;

            String buyer = buyersQueue.minimum().getName();
            String seller = sellersQueue.minimum().getName();


            if (sellersQueue.minimum().getBid() <= buyersQueue.minimum().getBid()) {
                System.out.println(buyer + " buys a share from " + seller + " for " + buyersQueue.minimum().getBid());
                sellersQueue.deleteMinimum();
                buyersQueue.deleteMinimum();

            }

        }


        sb.append("Order book:\n");

        sb.append("Sellers: \n");
        while (sellersQueue.size() != 0) {
            Bid bestSeller = sellersQueue.minimum();
            sb.append(bestSeller.getName()).append(" ").append(bestSeller.getBid()).append(",");
            sellersQueue.deleteMinimum();
        }

        sb.append("Buyers: ");
        while (buyersQueue.size() != 0) {
            Bid bestBuyer = buyersQueue.minimum();
            sb.append(bestBuyer.getName()).append(" ").append(bestBuyer.getBid()).append((","));
            buyersQueue.deleteMinimum();
        }


        return sb.toString();

    }


    public static void main(String[] args) throws IOException {
        final BufferedReader actions;
        if (args.length != 1) {
            actions = new BufferedReader(new InputStreamReader(System.in));
        } else {
            actions = new BufferedReader(new FileReader(args[0]));
        }

        List<String> lines = new LinkedList<String>();
        while (true) {
            String line = actions.readLine();
            if (line == null) break;
            lines.add(line);
        }
        actions.close();

        System.out.println(pureMain(lines.toArray(new String[lines.size()])));
    }
}


