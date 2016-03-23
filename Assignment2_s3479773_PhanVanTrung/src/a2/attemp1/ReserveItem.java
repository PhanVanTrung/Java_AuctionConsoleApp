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
public class ReserveItem extends AuctionItem{
    private double rPrice;
    public ReserveItem(int itemId, String itemDesc, String sId, double startingPrice, double rp){
        super(itemId,itemDesc,sId,startingPrice);
        rPrice=rp;
    }
    public double getRPrice(){
        return rPrice;
    }
    public String getAucStt(){
        if (super.getAucStt().equals("Closed")&&super.getHBid()<getRPrice())
            return "Passed In";
        else
            return super.getAucStt();
    }
    public void setRPrice(double rp){
        rPrice=rp;
    }
    
    public double calcListFee(){
        if (getRPrice()<=1.00)
            return 0.00;
        else if (getRPrice()<=100.00)
            return 0.04*getRPrice();
        else if (getRPrice()<1000.00)
            return 0.02*(rPrice-100)+4;
        else
            return 0.03*(rPrice-1000)+22;
    }
    
    public double calcSaleFee(){
        if (getAucStt().equals("Passed In")){
            return 0.02*getRPrice();
        }
        else
            return super.calcSaleFee();
    }
    
    public boolean lowerReserve(double newReservePrice){
        if (getAucStt().equals("Open")&&newReservePrice<getRPrice()){
            setRPrice(newReservePrice);
            return true;
        }
        else
            return false;
    }
    
    public void print(){
        super.print();
        System.out.printf("Reserve Price: $%.2f\n\n",getRPrice());
    }
}
