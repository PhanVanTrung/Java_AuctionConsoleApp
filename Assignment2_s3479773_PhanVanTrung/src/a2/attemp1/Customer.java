/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2.attemp1;

import java.io.*;

/**
 *
 * @author DELL_PC
 */
public abstract class Customer {
    private String custId, custName;
    public Customer(String cId, String cName){
        custId=cId;
        custName=cName;
    }
    public String getCustId(){
        return custId;
    }
    public String getCustName(){
        return custName;
    }
    public void setCustId(String id){
        custId=id;
    }
    public void setCustName(String name){
        custName=name;
    }
    public abstract void completeSale (double price) throws IOException;
    public  void  print(){
        System.out.print("Customer ID: "+custId+"\tCustomer Name: "+custName);
    }
}
