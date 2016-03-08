/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mu'ti C Putro
 */
public class Driver {
    
    public static void bataTXT(String file){
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            GA.nodes = new ArrayList<>();
            while ((line = br.readLine()) != null) {                
                if(line.contains("maksPopulasi")){
                    String[] s = line.split(":");
                    int populasiMaks = Integer.parseInt(s[1].trim());
                    GA.maksPopulasi = populasiMaks;
                }   
                else
                if(line.contains("maksGenerasi")){
                    String[] s = line.split(":");
                    int maksGenerasi = Integer.parseInt(s[1].trim());
                    GA.maksGenerasi = maksGenerasi;
                }
                else
                if(line.contains("probCrossover")){
                    String[] s = line.split(":");
                    double probCrossover = Double.parseDouble(s[1].trim());
                    GA.probCrossover = probCrossover;
                }
                else
                if(line.contains("probMutasi")){
                    String[] s = line.split(":");
                    double probMutasi = Double.parseDouble(s[1].trim());
                    GA.probMutasi = probMutasi;
                }
                else
                if(line.contains("maksDemand")){
                    String[] s = line.split(":");
                    int kapasitasMax = Integer.parseInt(s[1].trim());
                    GA.maksDemand = kapasitasMax;
                    Kromosom.maksDemand = kapasitasMax;
                }
//                else
//                if(line.contains("iterasi")){
//                    String[] s = line.split(":");
//                    int iterasi = Integer.parseInt(s[1].trim());
//                    GA.iterasi = iterasi;
//                }
                else
                if(!line.contains("node :")){
                    String[] koordinat = line.split(" ");
                    int x = Integer.parseInt(koordinat[0].trim());
                    int y = Integer.parseInt(koordinat[1].trim());
                    int demand = Integer.parseInt(koordinat[2].trim());
                    GA.nodes.add(new Node(i, x, y, demand));
                    i++;
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"error : "+e.getMessage()+" <<");
            System.exit(0);
        }
    }
    
    
    public static void main(String[] args) {
        try {
            JFileChooser cariFile = new JFileChooser();
            cariFile.showOpenDialog(null);
            File data = cariFile.getSelectedFile();
            String path = data.getAbsolutePath();
            bataTXT(path);
            //bataTXT("C:\\Users\\Mu'ti C Putro\\Documents\\percobaan.txt");
            GA baru = new GA();
            baru.display();
            System.out.println("hahah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error");
        }
        
    }
}
