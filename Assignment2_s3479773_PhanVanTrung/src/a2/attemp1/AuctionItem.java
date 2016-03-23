/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2.attemp1;

/**
 *
 * @author DELL_PC
 */
public class AuctionItem {
    private int itemId;
    private String itemDesc, sellerId, aucStt, hBidder;
    private double startingPrice, hBid;
    
    public AuctionItem(int iId, String iDesc, String sId, double sP){
        itemId=iId;
        itemDesc = iDesc;
        sellerId = sId;
        startingPrice=sP;
        aucStt = "Pending";
        hBid=0;
    }
    
    public int getItemId(){
        return itemId;
    }
    
    public String getItemDesc(){
        return itemDesc;
    }
    
    public String getSellerId(){
        return sellerId;
    }
    
    public String getAucStt(){
        return aucStt;
    }
    
    public double getStartingPrice(){
        return startingPrice;
    }
    
    public double getHBid(){
        return hBid;
    }
    
    public String getHBidder(){
        return hBidder;
    }
    
    public void setAucStt(String stt){
        aucStt=stt;
    }
    
    public void setHBid(double amount){
        hBid=amount;
    }
    
    public void setHBidder(String bidder){
        hBidder=bidder;
    }
    
    public boolean hasBids(){
        if (getHBid()>=getStartingPrice())
            return true;
        else
            return false;
    }
    
    public double calcListFee(){
        if (getStartingPrice()<=5.00)
            return 0.20;
        else if (getStartingPrice()<=20.00)
            return 0.50;
        else if (getStartingPrice()<100.00)
            return 1.00;
        else if (getStartingPrice()<=250.00)
            return 2.50;
        else
            return 5.00;
    }
    
    public double open() throws Exception{
        if (getAucStt().equals("Pending")){
            setAucStt("Open");
            return calcListFee();
        }
        else
            throw new Exception("\nThis item cannot be opened because it is not pending\n");
    }
    
    public double calcSaleFee(){
        if (getHBid()==0 || getHBid()<=1.00)
            return 0.00;
        else if (getHBid()<=100.00)
            return 0.05*getHBid();
        else if (getHBid()<1000.00)
            return 5.00 + 0.03*(getHBid()-100);
        else
            return 32.00 + 0.01*(getHBid()-1000);
    }
    
    public double close() throws Exception{
        if (getAucStt().equals("Open")){
            setAucStt("Closed");
            return calcSaleFee();
        }
        else
            throw new Exception("\nThis item with id "+getItemId()+" because it is not currently Open\n");
    }
    
    public void placeBid(double bidAmount, String bidder) throws Exception{
        if (getAucStt().equals("Open")&&bidAmount>=getStartingPrice()&&bidAmount>getHBid()){
                setHBid(bidAmount);
                setHBidder(bidder);
            }
        else if(!getAucStt().equals("Open"))
            throw new Exception("\nThe auction item with id "+getItemId()+" is not currently Open!\n");
        else
            throw new Exception("\nInvalid bid for auction id: "+getItemId()+"\n");
    }
    
    public void print(){
        System.out.printf("\nItem ID: %s\n"+"Description: %s\n"+"Seller ID: %s\n"
                +"Starting Price: $%.2f\n"+"Auction Status: %s\n"+"Highest Bid: $%.2f\n"
                +"Highest Bidder: %s\n"
                ,getItemId(),getItemDesc(),getSellerId(),getStartingPrice(),getAucStt(),
                getHBid(), getHBidder());
    }
    /**
     * @param args the command line arguments
     */
}
