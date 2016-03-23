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
public class RegisteredCustomer extends Customer{
    private double bal;
    public RegisteredCustomer(String cId, String cName, double bB){
        super(cId,cName);
        if (bB<=0)
            bal=0;
        else
            bal=bB;
    }
    public double getBal(){
        return bal;
    }
    public void addFunds(double amount){
        bal+=amount;
    }
    public void deductFees(double fees){
        bal-=fees;
    }
    public void completeSale(double price){
        bal-=price;
    }
    public void print(){
        super.print();
        System.out.printf("\tAccount Bank Ballance: $%.2f\n",getBal());
    }
    
}
