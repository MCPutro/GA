/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;

/**
 *
 * @author Mu'ti C Putro
 */
public class Kromosom {
    private ArrayList<Node> nodes;
    private double fitness;
    private double cost;
    public static int maksDemand;
    
    
    public Kromosom() {}
    
    public Kromosom(ArrayList<Node> nodes) {
        this.nodes = nodes;
        this.setFitness();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    
    
    public String toStrings(){
        String n = "";
        for(Node nd : this.nodes){
            n += nd.getId()+" ";
        }
        return n;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness() {
        double cost = 0;
        double demandLimit = 0;
        double a = 0.001;
        for (int j = 0; j < nodes.size() - 1; j++) {
            if (j == 0 && nodes.get(j).getId() != 0) {
                cost += jarakNode(nodes.get(0), nodes.get(j));
            } 
            else 
            if (demandLimit <= this.maksDemand) {
                cost += jarakNode(nodes.get(j), nodes.get(j + 1));
            } 
            else{
                cost += jarakNode(nodes.get(0), nodes.get(j - 1)) + jarakNode(nodes.get(0), nodes.get(j));
            }
        }
        double fitnessValue = 1 / cost + a;
        this.cost = cost;
        this.fitness = fitnessValue;

    }

    public double jarakNode(Node a, Node b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
    
    
    public String display(){
        String s = "";
        for (int i = 0; i < nodes.size(); i++) {
            //System.out.print(nodes.get(i).getId()+" ");
            s += nodes.get(i).getId()+" ";
        }
        //System.out.println("fitnes : "+this.getFitness());
        s += "  fitnes : "+this.getFitness()+"  cost : "+this.cost+"\n";
        return s;
    }
    
    
}
