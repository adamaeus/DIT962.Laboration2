
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
        // TODO: declaration of two priority queues

//		List<CompareBids> comparators = new ArrayList<>();
//		PriorityQueue sellersQueue = new PriorityQueue((comparators);
//		Comparator<Bid> comparators = new ArrayList<>();
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
                Bid newBuyBid = new Bid(name, newBuyPrice);
                buyersQueue.add(newBuyBid);
                // Bengt NK 70 72
            } else if (action.equals("NS")) {
                int newSellPrice = Integer.parseInt(parts[3]);
                Bid newSellBid = new Bid(name, newSellPrice);
                sellersQueue.add(newSellBid);

            } else {
                throw new RuntimeException(
                        "line " + line_no + ": invalid action");
            }



            /**
             * Update, added first logic for the "transaction",
             * if lowest seller price <= highest buyer price, remove both.
             * Lade också till en printer:
             * " Buyer buys a share from Seller for x"
             */
            if (sellersQueue.size() == 0 || buyersQueue.size() == 0) continue;

            String buyer = buyersQueue.minimum().getName();
            String seller = sellersQueue.minimum().getName();
            // Om det första värdet i sellersQueue är mindre eller lika med det första värdet i buyersQueue
            // ta bort dessa två värden

            if (sellersQueue.minimum().getBid() <= buyersQueue.minimum().getBid()) {
                System.out.println(buyer + " buys a share from " + seller + " for " + buyersQueue.minimum().getBid());
                sellersQueue.deleteMinimum();
                buyersQueue.deleteMinimum();
                System.out.println("TestInsideIfStatement");

            }
            /**
             * Lade till en break för while loopen. Om ovanstående if sats INTE kommer kunna genomföras,
             * kan vi dra slutsatsen att det inte finns någon annan buyer i heapen som har råd med det minsta sell price
             */

            System.out.println("TestOutsideIfStatement");

            // TODO:
            // compare the bids of highest priority from each of
            // each priority queues.
            // if the lowest seller price is lower than or equal to
            // the highest buyer price, then remove one bid from
            // each priority queue and add a description of the
            // transaction to the output.
        }
        /**
         * Går igenom båda listorna och printar ut enligt:
         *
         * 		Order book:
         * 		Sellers: Cecilia 70, Bengt 71, David 73, Erika 77
         * 		Buyers: Ada 69, Fredrik 68, Gustaf 68
         *
         *
         */

        sb.append("Order book:\n");

        sb.append("Sellers: ");
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
        System.out.println(sb);


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


