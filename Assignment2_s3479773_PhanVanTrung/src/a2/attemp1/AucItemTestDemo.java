/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2.attemp1;
import java.util.Scanner;
/**
 *
 * @author DELL_PC
 */
public class AucItemTestDemo {
    public static void main(String[] args){
        AuctionItem[] items = new AuctionItem[4];
        items[0] = new AuctionItem(1,"Tonka Truck","M001",1.00);
        items[1] = new AuctionItem(2,"Xbox ","M002",20.00);
        items[2] = new AuctionItem(3,"Teddy Bear","M003",5.00);
        items[3] = new AuctionItem(4,"Antique Doll","M004",100.00);
        System.out.print("Available Item List:\n");        
        
        for (int i=0;i<items.length;i++){
            System.out.printf("Item ID: "+items[i].getItemId()+" Desc: "+items[i].getItemDesc()+" Start Price: $%.2f\n",items[i].getStartingPrice());
        }
        
        System.out.println();   
        for (int i=0;i<items.length;i++){
            try{
                System.out.printf("Auction %s"+" has started - listing fee: $%.2f\n",items[i].getItemId(),items[i].open());
            }
            catch(Exception e1){
                System.out.println(e1.getMessage());
            }
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the id of the auction to bid on: ");
        int item=sc.nextInt();
        boolean flag = true;
        items[1].setAucStt("Closed");
        for (int i=0;i<items.length;i++){
            if (items[i].getItemId()==item){
                flag=false;
                System.out.print("You are bidding in the auction for "+items[i].getItemDesc());
                System.out.print("\nEnter bid amount: ");
                double amount = sc.nextDouble();
                System.out.print("Enter bidder id: ");
                String bidder = sc.next();
                try{
                    items[i].placeBid(amount,bidder);
                    System.out.print("Your bid was successful !\n");
                    items[i].print();
                }
                catch(Exception e2){
                    System.out.print(e2.getMessage());
                }

                for (int j=0;j<items.length;j++){
                    try{
                        System.out.printf("\nAuction "+items[j].getItemId()+" has ended - sale fee: $%.2f",items[j].close());
                    }    
                    catch(Exception e3){
                        System.out.print(e3.getMessage());
                    }
                }
            }
        }
        if (flag){
            System.out.print("No auction item with id "+item+" found!\n");
        }
    }
}
