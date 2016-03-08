/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Mu'ti C Putro
 */
public class Node {
    private int id;
    private double x;
    private double y;
    private double demand;

    public Node() {}

    public Node(int id, double x, double y, double demand) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.demand = demand;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDemand() {
        return demand;
    }
    
    public void display(){
        System.out.println(id+" x:"+x+" - y:"+y+" = "+demand);
    }
    
}
