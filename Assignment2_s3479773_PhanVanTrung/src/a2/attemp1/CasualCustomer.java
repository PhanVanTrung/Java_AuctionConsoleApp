/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2.attemp1;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author DELL_PC
 */
public class CasualCustomer extends Customer{
    Scanner sc = new Scanner(System.in);
    private int PIN, creditCard;
    public CasualCustomer(String cId, String cName, int cC, int Pin){
        super(cId,cName);
        PIN=Pin;
        creditCard=cC;
    }
    public int getPIN(){
        return PIN;
    }
    public int getCreditCard(){
        return creditCard;
    }
    public void completeSale(double price){
        System.out.print("Please enter your PIN: ");
        int pin=sc.nextInt();
        boolean flag=true;
        if (pin==PIN){
                flag=false;
                System.out.println("The sale price has been charged to your credit card!");
            }
        if(flag=false) {
            System.out.println("The item has been withheld pending confirmation of the customer's identity!");
        }
    }
    public void print(){
        super.print();
        System.out.print("\tCustomer credit card number: "+getCreditCard()+"\n");
    }
}
