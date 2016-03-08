/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.*;


/**
 *
 * @author Mu'ti C Putro
 */
public class GA {
    
    //
    tampilan dataData = new tampilan();
    
    private ArrayList<Kromosom> orangTua;
    private ArrayList<Kromosom> anak;
    private ArrayList<Kromosom> elitisme;
    private ArrayList<Kromosom> surviv;  
    //private static Random rand = new Random();
    public static ArrayList<Node> nodes ;
    public static int maksPopulasi;
    public static int maksGenerasi;
    public static double probCrossover;
    public static double probMutasi;
    public static double maksDemand;
    public static int iterasi;
    private ArrayList<ArrayList<Integer>> bestRute;

    public GA() {
        this.orangTua = new ArrayList<>();
        this.anak = new ArrayList<>();
        this.elitisme = new ArrayList<>();
        this.surviv = new ArrayList<>();
        set();
    }

    void set(){
        dataData.setTable(nodes);
        dataData.setInfo("Maksimal Generasi : "+maksGenerasi);
        dataData.setInfo("Maksimal Populasi : "+maksPopulasi);
        dataData.setInfo("Probabilitas Crossover : "+probCrossover);
        dataData.setInfo("Probabilitas Mutasi : "+probMutasi);
        dataData.setInfo("Maksimal Demand : "+maksDemand);
    }
    
    public ArrayList<ArrayList<Integer>> getBestRute() {
        this.bestRute = generateRandomRute();
        return bestRute;
    }
    
    public double getCostBestRute(){
        double tmp = 0;
        for (int i = 0; i < bestRute.size(); i++) {
            for (int j = 0; j < bestRute.get(i).size()-1; j++) {
//                tmp += Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
                int index1 = bestRute.get(i).get(j);
                int index2 = bestRute.get(i).get(j+1);
                tmp += Math.sqrt(Math.pow((GA.nodes.get(index1).getX() - GA.nodes.get(index2).getX()) , 2) + Math.pow((GA.nodes.get(index1).getY()- GA.nodes.get(index2).getY()) , 2));
        //        System.out.print(bestRute.get(i).get(j)+" ");
            }
        }
        //System.out.println("\n"+tmp+" <<<<<<<<<<<<<< cost ");
        return tmp;
    }
    
    private ArrayList<ArrayList<Integer>> generateRandomRute(){
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
        
        ArrayList<Integer> tempNodes = new ArrayList<>();
        for (int i = 1; i < GA.nodes.size(); i++) {
            tempNodes.add(i);
        }
//        System.out.println(nodes);
        ArrayList<Integer> rute = new ArrayList<>();
        rute.add(0);
        Random rand = new Random();
        while(tempNodes.size() > 0 && ruteIsValid(rute)){
        //for (int i = 0; i < 3; i++) {
            int getIndexRandom = rand.nextInt(tempNodes.size());
            Integer i = tempNodes.get(getIndexRandom);
            rute.add(i);
//            dataData.tampildata(rute+" - "+getDemandRute(rute)+"\n");
            if(ruteIsValid(rute)){
                tempNodes.remove(getIndexRandom);
            }
            else
            {
                rute.remove(rute.size()-1);
                rute.add(0);
                temp.add(rute);
                //rute.clear();
                rute = new ArrayList<>();
                rute.add(new Integer(0));
            }
        }
        
        rute.add(new Integer(0));
        temp.add(rute);
        //System.out.println(temp);
        return temp;
    }
    
    private double getDemandRute(ArrayList<Integer> list){
        double totalDemand = 0;
        for(int i : list){
            totalDemand += GA.nodes.get(i).getDemand();
        }
        return totalDemand;
    }
    
    private boolean ruteIsValid(ArrayList<Integer> list){
        return getDemandRute(list) <= GA.maksDemand;
    }
    
    public void inisialisasi() {
        for (int i = 0; i < GA.maksPopulasi; i++) {
            int[] ints = new Random().ints(0, nodes.size()).distinct().limit(nodes.size()).toArray();
            ArrayList<Node> Gen = new ArrayList<>();
            for (int Int : ints) {
                if (Int != 0) 
                    Gen.add(nodes.get(Int));
            }
            orangTua.add(new Kromosom(Gen));
        }
        orangTua.stream().forEach((p) -> {
            dataData.tampildata( p.display());
        });
    }
    
    public void crossOver() {
        int comLeng = orangTua.size() % 2 == 0 ? orangTua.size() / 2 : (orangTua.size() + 1) / 2;
        double r = randomInRang(0, GA.probCrossover);

        for (int i = 1; i <= comLeng; i++) {
            Kromosom bapak = pilihOrtu();
            Kromosom ibu = pilihOrtu();
            System.out.println("------------------");
            while (bapak == ibu) {
                ibu = pilihOrtu();
            }

            Kromosom anak1 = new Kromosom();
            Kromosom anak2 = new Kromosom();

            if (r <= GA.probCrossover) {
                anak1.setNodes(cycleCrossOver(bapak, ibu));
                anak1 = mutasi(anak1);
                anak1.setFitness();
                anak.add(anak1);

                anak2.setNodes(cycleCrossOver(ibu, bapak));
                anak2 = mutasi(anak2);
                anak2.setFitness();
                anak.add(anak2);
            }
        }
        anak.stream().forEach((k) -> {
            dataData.tampildata(k.display());
        });

    }
    
    public void elit(){
//        this.elitisme = new ArrayList<>(this.orangTua);
        this.elitisme.addAll(this.orangTua);
        this.elitisme.addAll(this.anak);
        System.out.println(elitisme.size()+" <<<<<<<<<");
        elitisme.stream().forEach((k) -> {
            dataData.tampildata(k.display());
        });
    }
   
    public void survive() {
        ArrayList<Kromosom> tempChild = new ArrayList<>();
        tempChild = anak;
        surviv = orangTua;
        for (int i = 0; i < surviv.size(); i++) {
            for (int j = 0; j < tempChild.size(); j++) {
                if (surviv.get(i).getFitness() < tempChild.get(j).getFitness()) {
                    surviv.set(i, tempChild.get(j));
                    tempChild.remove(j);
                }
            }
        }
        orangTua = new ArrayList<>();
        orangTua.addAll(surviv);
        surviv.stream().forEach((k) -> {
            dataData.tampildata(k.display());
        });
        
        
        double min = surviv.get(0).getFitness()+1;
        double sum = 0;
        int index = -1;
        for (int i = 0; i < surviv.size(); i++) {
            if(min > surviv.get(i).getFitness()){
                min = surviv.get(i).getFitness();
                index = i;
            }
            sum += surviv.get(i).getFitness();
        }
        dataData.tampildata("\nrata-rata fitnes : "+sum/surviv.size());
        dataData.tampildata("\nrute : "+surviv.get(index).toStrings());
        dataData.tampildata("\nmin : "+min);
        
        
    }
    
    public Kromosom pilihOrtu(){//metode roulette wheel
        double S = sumFitnes(orangTua);
        double r = randomInRang(0, S);
        //System.out.println(r+ " <<<<<<<<<<<<< r");
        double s = 0;
        int t = 0;
        while(s < r){
            s += orangTua.get(t).getFitness();
            //System.out.println(s+" <<<<<<<<<<< s");
            t++;
        }
        //System.out.println(s+" <<<<<<<<<<< s last");
        return orangTua.get(t-1);
        
        
//        double pSumFitness = 0;
//        for (Kromosom k : orangTua) {
//            pSumFitness += k.getFitness();
//            if (pSumFitness >= r) {
//                return k;
//            }
//        }
//        return null;
    }
    
    public Kromosom mutasi(Kromosom anak) {
        Node temp = new Node();
        double randNumb = randomInRang(0, probMutasi);
        if (randNumb <= probMutasi) {
            double index1 = randomInRang(0, nodes.size()-1);
            double index2 = randomInRang(0, nodes.size()-1);
            while (index1 == index2) {
                index2 = randomInRang(0, nodes.size()-1);
            }
            temp = anak.getNodes().get((int) index1);
            anak.getNodes().set((int) index1, anak.getNodes().get((int) index2));
            anak.getNodes().set((int) index2, temp);
            return anak;
        } 
        else 
            return anak;
        
    }
    
    public ArrayList<Node> cycleCrossOver(Kromosom k1, Kromosom k2) {
        ArrayList<Node> tempAnak = new ArrayList<>(k2.getNodes());

        int i = 0;
        while (k1.getNodes().get(0) != k2.getNodes().get(i)) {
            tempAnak.set(i, k1.getNodes().get(i));
            i = getIndexNode(k1, k2.getNodes().get(i));
        }
        tempAnak.set(i, k1.getNodes().get(i));

        return tempAnak;
    }
    
    int getIndexNode(Kromosom k, Node n){
        for (int i = 0; i < k.getNodes().size(); i++) {
            if(n == k.getNodes().get(i)){
                return i;
            }
        }
        return -1;
    }
    
    public double sumFitnes(ArrayList<Kromosom> ortu){
        double jumlah = 0;
        for(Kromosom k : ortu){
            jumlah += k.getFitness();
        }
        return jumlah;
    }
    
    public static double randomInRang(double min, double max) {
        Random r = new Random();
        return r.doubles(min,max).findFirst().getAsDouble();
    }
    
    /*
    public void viewPopulasi(ArrayList<Kromosom> populasi) {
        for (int j = 0; j < populasi.size(); j++) {
            for (int k = 0; k < populasi.get(j).getNodes().size(); k++) {
                System.out.print(populasi.get(j).getNodes().get(k).getId() + " ");
            }
            System.out.print("Fitness : " + populasi.get(j).getFitness());
            System.out.println("");
        }
    }
    */
    
    public void display(){
//        System.out.println("maksGenerasi : "+GA.maksGenerasi);
//        System.out.println("maksPopulasi : "+GA.maksPopulasi);
//        System.out.println("probCrossover : "+GA.probCrossover);
//        System.out.println("probMutasi : "+GA.probMutasi);
//        System.out.println("berat maks : "+GA.maksDemand);
//        for(Node n : nodes){
//            n.display();
//        }
        for (int i = 0; i < GA.maksGenerasi; i++) {
            dataData.tampildata("==============================================================================================================================\n");
            dataData.tampildata("Generasi ke-"+(i+1)+"\n");
            dataData.tampildata("==============================================================================================================================\n");
            anak.clear();
            elitisme.clear();
            surviv.clear();
            if( i == 0){
                dataData.tampildata("inisial\n");
                inisialisasi();
//                System.out.println("cross over");
//                crossOver();
//                System.out.println("-------------------");
            }
            else
            {
                dataData.tampildata("inisial\n");
                orangTua.stream().forEach((k) -> {
                    dataData.tampildata(k.display());
                });
            }
            dataData.tampildata("\nCross Over\n");
            crossOver();
            dataData.tampildata("\nElitisme\n");
            elit();
            dataData.tampildata("\nSurvive\n");
            survive();
            dataData.tampildata("\n\n");
            
        }
        dataData.tampildata("==============================================================================================================================\n");
        System.out.println("\n\n");
        dataData.tampildata("Rute yang ditawarkan : \n"+getBestRute());
        dataData.tampildata("\ncost : "+getCostBestRute());
        dataData.setVisible(true);
    }
    
    

}
