/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2.attemp1;
import java.util.*;
import java.io.*;
/**
 *
 * @author DELL_PC
 */
public class DrivenMenu {
    static Scanner sc = new Scanner(System.in);
    static AuctionItem[] items = new AuctionItem[10];
    static Customer[] custs = new Customer[50];

    public static void menu(){
        System.out.print("****** iBuy Auction Recording System ******\n"+
                "A - Add Auction\n"+
                "B - Display Auction Details \n" +
                "C - Open Auction \n" +
                "D - Place Bid \n" +
                "E - Close Auction \n" +
                "F – Add Customer\n" +
                "G – Display Customer Details\n" +
                "H – Add Funds to Account\n" +
                "X - Exit the Program \n\n" +
                "Enter you selection:");
    }
    // check for the item id to see if it is duplicate
    public static boolean dupCheck(int id){
        int flag=0;
        for (int i=0;i<items.length;i++){
            if(items[i]!=null && items[i].getItemId()==id){
                flag++;
            }
        }
        return flag==0;
    }
    
    // check if the customer id exists
    public static boolean custCheck(String id){
        int flag=0;
        for (int i=0;i<custs.length;i++){
            if(custs[i]!=null && custs[i].getCustId().equals(id)){
                flag++;
            }
        }
        return flag != 0;
    }
    // check if the customer is registered
    public static int isRegistered(String id){
        int flag=0;
        for (int i=0;i<custs.length;i++){
            if(custs[i]!=null && custs[i].getCustId().equals(id)){
                if (custs[i] instanceof RegisteredCustomer){
                    RegisteredCustomer rc =  (RegisteredCustomer) custs[i];
                    if (rc.getBal()>=0)
                        flag=1;
                    else
                        flag=2;
                }
            }
        }
        return flag;
    }
    // add auction feature    
    public static void addAuc(){
        System.out.print("\nAdding a new auction item...\nEnter item ID:");
        int iId=sc.nextInt();
        if(dupCheck(iId)){              // Check duplicate item ID
            System.out.print("Enter item description:");
            sc.nextLine();
            String desc = sc.nextLine();
            System.out.print("Enter seller ID:");
            String sId=sc.next();
            if(custCheck(sId)){         // Validate seller ID
                if (isRegistered(sId)==1){      // Check if customer is Registered Customer, and balance >=0
                    System.out.print("Enter starting price:");
                    double sPrice=sc.nextDouble();
                    System.out.print("Create a new AuctionItem or a ReserveItem? (enter A or R): ");
                    String type=sc.next();
                    if (type.equals("A")){
                        for (int i=0;i<items.length;i++){
                            if(items[i]==null){
                                items[i]=new AuctionItem(iId, desc, sId, sPrice);
                                System.out.print("Auction item “"+desc+"” successfully added to system!\n\n");
                                break;                    }
                        }
                    }
                    else if (type.equals("R")){
                        System.out.print("Enter reserve price: ");
                        double rPrice = sc.nextDouble();
                        for (int i=0;i<items.length;i++){
                            if(items[i]==null){
                            items[i]=new ReserveItem(iId, desc, sId, sPrice, rPrice);
                            System.out.print("Auction item “"+desc+"” successfully added to system!\n\n");
                            break;
                            }
                        }
                    }
                    else    // if input is neither A nor R
                        System.out.print("Error - Invalid auction type!\n\n");
                }
                
                else if(isRegistered(sId)==2)       // Bank balance is negative
                    System.out.print("Error - Your bank balance is negative!\n\n");
                
                else            // Seller is not a Registered Customer
                    System.out.print("Error - Sorry, you must register first!\n\n ");
            }
            else            // Seller ID is not valid
                System.out.print("Error - seller id not in database!\n\n");
        }
        else                // Duplicate entered Item ID
            System.out.println("Error - duplicate item ID entered!");
    }
    // Display auction details feature
    public static void aucDetails(){
        for (int i=0;i<items.length;i++){
            if (items[i] != null)
                items[i].print();
        }
    }
    // open auction feature   
    public static void openAuc() throws Exception{
        System.out.print("Enter the id of the auction to open: ");
        int id=sc.nextInt();
        int flag=0;
        for (int i=0;i<items.length;i++){
            if(items[i]!=null && items[i].getItemId()==id){ // check for the id if it is existed
                try{
                    System.out.printf("Auction "+id+" has started - listing fee: $%.2f\n\n",items[i].open());
                    for (int j=0;j<custs.length;j++){
                        if(custs[j]!=null && custs[j].getCustId().equals(items[i].getSellerId())){ 
                            // deduct fee from the registered customer bank balance
                            if (custs[j] instanceof RegisteredCustomer){
                                RegisteredCustomer rc =  (RegisteredCustomer) custs[j];
                                rc.deductFees(items[i].calcListFee());
                            }
                        }
                    }    
                }
                catch(Exception e1){
                    System.out.println(e1.getMessage());
                }
                flag=1;
            }
        }
        if (flag==0)    // item id entered not exists
            System.out.print("The auction item with id "+id+" is not found! \n\n");
    }
    // place a bid feature
    public static void placeABid() throws Exception{
        System.out.print("Enter the id of the auction to bid on: ");
        int id=sc.nextInt();
        int flag=0;
        for (int i=0;i<items.length;i++){   // seach through items array to find the matching item
            if(items[i]!=null && items[i].getItemId()==id){
                flag=1;
                System.out.print("You are bidding in the auction for item \""+items[i].getItemDesc()+"\".\n"
                        + "Enter bid amount: ");
                double amt=sc.nextDouble();
                System.out.print("Enter bidder id: ");
                String bidder=sc.next();
                int temp=0;
                for (int j=0;j<custs.length;j++){   // seach through custs array to find the matching customer
                    if(custs[j]!=null && custs[j].getCustId().equals(bidder)){
                        try{
                            temp=1;
                            items[i].placeBid(amt,bidder);
                            System.out.println("Your bid was successful!\n");
                            items[i].print();
                        }
                        catch(Exception e2){
                            System.out.println(e2.getMessage());
                        }
                    }
                }
                if (temp==0)    // no matching bidder found
                    System.out.print("Error - Invalid bidder id!\n");
            }
        }
        if (flag==0)    // no matching item id found
            System.out.print("The auction item with id "+id+" is not found! \n\n");
    }
    // close auction feature
    public static void closeAuc() throws Exception{
        System.out.print("Enter the id of the auction to close: ");
        int id=sc.nextInt();
        int flag=0;
        for (int i=0;i<items.length;i++){   // seach through items array to find matching item id
            if(items[i]!=null && items[i].getItemId()==id){
                if (items[i] instanceof ReserveItem){   // if the item is a reserve item
                    ReserveItem item= (ReserveItem) items[i];
                    if (item.getHBid()<item.getRPrice()){   // attemp to lower reserve price if the item is Passed in
                        System.out.print("Do you wish to lower the reserve price? (enter Y or N) ");
                        String opt = sc.next();
                        if (opt.equals("Y")){
                            System.out.print("Enter new reserve price: ");
                            double newRP=sc.nextDouble();
                            if(item.lowerReserve(newRP)){
                                System.out.print("Reserve price has been lowered successfully!\n\n");
                            }
                            else{
                                System.out.print("Error! Either the item is not currently open OR the new reserve price"
                                        + " is invalid\n\n");
                            }
                        }
                        else{   // close auction for the reserve item
                            try{
                                System.out.printf("Auction "+id+" has ended - sale fee: $%.2f\n\n",items[i].close());
                                for (int j=0;j<custs.length;j++){
                                    if(custs[j]!=null && custs[j].getCustId().equals(items[i].getSellerId())){
                                        if (custs[j] instanceof RegisteredCustomer){
                                            RegisteredCustomer rc =  (RegisteredCustomer) custs[j];
                                            rc.deductFees(items[i].calcSaleFee());
                                        }
                                    }
                                }    
                            }
                            catch(Exception e3){
                                System.out.println(e3.getMessage());
                            }
                        }
                        flag=1;
                    }
                }
                else{   // close normal auction item
                    try{
                        System.out.printf("Auction "+id+" has ended - sale fee: $%.2f\n\n",items[i].close());
                        for (int j=0;j<custs.length;j++){
                            if(custs[j]!=null && custs[j].getCustId().equals(items[i].getSellerId())){
                                if (custs[j] instanceof RegisteredCustomer){
                                    RegisteredCustomer rc =  (RegisteredCustomer) custs[j];
                                    rc.deductFees(items[i].calcSaleFee());
                                }
                            }
                        }    
                    }
                    catch(Exception e3){
                        System.out.println(e3.getMessage());
                    }
                }
                flag=1;
            }
        }
        if (flag==0)    // the item id enter not exists
            System.out.print("The auction item with id "+id+" is not found! \n\n");
    }
    // add customers feature
    public static void addCust(){
        System.out.print("Enter Customer ID: ");
        String id=sc.next();
        int flag=0;
        for (int i=0;i<custs.length;i++){   // check if the entered id has being used
            if(custs[i]!=null && custs[i].getCustId().equals(id)){
                System.out.print("The ID has already being used\n\n");
                flag=1;
            }
        }
        if (flag==0){   // if valid entered id
            System.out.print("Enter Customer Name: ");
            sc.nextLine();
            String name=sc.nextLine();
            System.out.print("Please select type of customer: Casual(C) OR Registered(R): ");
            String type = sc.next();
            if(type.equals("C")){   // create Casual customer accout
                System.out.print("Please a credit card number: ");
                int creditNum=sc.nextInt();
                System.out.print("Please enter PIN: ");
                int pin=sc.nextInt();
                for (int i=0;i<custs.length;i++){
                    if(custs[i]==null){
                        custs[i]=new CasualCustomer(id, name, creditNum, pin);
                        System.out.print("Customer “"+name+"” is successfully added to system!\n\n");
                        break;
                    }
                }                
            }
            if(type.equals("R")){   // create Registered customer accout
                System.out.print("Please a starting account balance: ");
                double bal=sc.nextDouble();
                for (int i=0;i<custs.length;i++){
                    if(custs[i]==null){
                        custs[i]=new RegisteredCustomer(id, name, bal);
                        System.out.print("Customer “"+name+"” is successfully added to system!\n\n");
                        break;
                    }
                }
            }
            else    // neither input is C nor R
                System.out.print("Error - Invalid option.");
        }
    }
    // display customers feature
    public static void displayCusts(){
        for (int i=0;i<custs.length;i++){
            if (custs[i] != null){
                custs[i].print();
            }
        }
    }
    // add funds to an account feature
    public static void addFunds(){
        System.out.print("Please enter customer ID to add funds: ");
        String id = sc.next();
        int flag = 0;
        for (int j=0;j<custs.length;j++){   // for valid customer id
            if(custs[j]!=null && custs[j].getCustId().equals(id)){
                if (custs[j] instanceof RegisteredCustomer){
                    RegisteredCustomer rc =  (RegisteredCustomer) custs[j];
                    System.out.print("Enter amount to add to the account balance: ");
                    double amount = sc.nextDouble();
                    rc.addFunds(amount);    // succeed to add funds to the account
                    flag=1;
                }
            }
        }
        if (flag==0)    // if entered id is invalid
            System.out.print("Error - Customer Id entered is invalid!");
    }
    // Write all the data to files for future use
    public static void writeFiles() throws IOException{
        PrintWriter cc = new PrintWriter(new File("CasualCustomersFile.txt"));  // creaate Casual Customers file
        PrintWriter rc = new PrintWriter(new File("RegisteredCustomersFile.txt"));  // creaate Registered Customers file
        for (int i=0;i<custs.length;i++){   // pass customers details to files
            if (custs[i]!=null && custs[i] instanceof CasualCustomer){
                CasualCustomer cust=(CasualCustomer) custs[i];
                cc.println(cust.getCustId()+"\t"+cust.getCustName()+"\t"+cust.getCreditCard()+
                        "\t"+cust.getPIN());
            }
            else if (custs[i]!=null && custs[i] instanceof RegisteredCustomer){ // pass customers details to files
                RegisteredCustomer cust = (RegisteredCustomer) custs[i];
                rc.println(cust.getCustId()+"\t"+cust.getCustName()+"\t"+cust.getBal());
            }
        }
        cc.close();
        rc.close();
        
        PrintWriter normItem = new PrintWriter(new File("NormalItemsFile.txt"));  // creaate normal items file
        PrintWriter rsvItem = new PrintWriter(new File("ReserveItemsFile.txt"));  // creaate reserve items file
        for (int i=0;i<items.length;i++){
            if (items[i]!=null){
                if (items[i] instanceof ReserveItem){// pass items details to files
                    ReserveItem item=(ReserveItem) items[i];
                    rsvItem.println(item.getItemId()+"\t"+item.getItemDesc()+"\t"+item.getSellerId()+"\t"
                            +item.getStartingPrice()+"\t"+item.getAucStt()+"\t"+item.getHBidder()
                            +"\t"+item.getHBid()+"\t"+item.getRPrice());
                }
                else{// pass items details to files
                    normItem.println(items[i].getItemId()+"\t"+items[i].getItemDesc()+"\t"+items[i].getSellerId()
                    +"\t"+items[i].getStartingPrice()+"\t"+items[i].getAucStt()+"\t"+items[i].getHBidder()
                            +"\t"+items[i].getHBid());
                }
            }
        }
        normItem.close();
        rsvItem.close();
    }
    // Reading data from files to create item objects and customer objects
    public static void readFiles() throws Exception{
        String line, sid, name, desc, hBidder, aucStt;
        int creditNum, pin, iid;
        double bal, sPrice, rPrice, hBid;
        // Reading data from files using scanners as inputs
        Scanner cc = new Scanner(new File("CasualCustomersFile.txt"));
        Scanner rc = new Scanner(new File("RegisteredCustomersFile.txt"));
        Scanner normItem = new Scanner(new File("NormalItemsFile.txt"));
        Scanner rsvItem = new Scanner(new File("ReserveItemsFile.txt"));

        int i=0;
        int j=0;
        while(cc.hasNext()){    // read and create new casual customer instances
            line=cc.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,"\t");
            sid = inReader.nextToken();
            name = inReader.nextToken();
            creditNum = Integer.parseInt(inReader.nextToken());
            pin = Integer.parseInt(inReader.nextToken());
            custs[i] = new CasualCustomer(sid, name, creditNum, pin);
            i++;
        }
        
        while(rc.hasNext()){    // read and create new registered customer instances
            line=rc.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,"\t");
            sid = inReader.nextToken();
            name = inReader.nextToken();
            bal = Double.parseDouble(inReader.nextToken());
            custs[i] = new RegisteredCustomer(sid, name, bal);
            i++;
        }
        
        while(normItem.hasNext()){    // read and create new normal items instances
            line=normItem.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,"\t");
            iid = Integer.parseInt(inReader.nextToken());
            desc = inReader.nextToken();
            sid = inReader.nextToken();
            sPrice = Double.parseDouble(inReader.nextToken());
            aucStt=inReader.nextToken();
            hBidder=inReader.nextToken();
            hBid=Double.parseDouble(inReader.nextToken());
            items[i]=new AuctionItem(iid, desc, sid, sPrice);
            j++;
        }
        
        while(rsvItem.hasNext()){    // read and create new reserve items instances
            line=rsvItem.nextLine();
            StringTokenizer inReader = new StringTokenizer(line,"\t");
            iid = Integer.parseInt(inReader.nextToken());
            desc = inReader.nextToken();
            sid = inReader.nextToken();
            sPrice = Double.parseDouble(inReader.nextToken());
            aucStt=inReader.nextToken();
            hBidder=inReader.nextToken();
            hBid=Double.parseDouble(inReader.nextToken());
            rPrice = Double.parseDouble(inReader.nextToken());
            items[i]=new ReserveItem(iid, desc, sid, sPrice, rPrice);
            j++;
        }
        
        cc.close();
        rc.close();
        normItem.close();
        rsvItem.close();
    }
    public static void main(String[] args) throws Exception {
        readFiles();          // read data from files and create objects instances when program first running      
        while(true){
            menu();
            String opt=sc.next();

            switch(opt){
                case "A": addAuc();
                    break;
                case "B": aucDetails();
                    break;
                case "C": openAuc();
                    break;
                case "D": placeABid();
                    break;
                case "E": closeAuc();
                    break;
                case "F": addCust();
                    break;
                case "G": displayCusts();
                    break;
                case "H": addFunds();
                    break;
                case "X": writeFiles(); // write all customers and items details to files
                    System.out.println("Thank you. Good bye.");
                    System.exit(0);
                default: System.out.println("Invalid option. Please choose again");
                    break;
            }
        }
    }
}
