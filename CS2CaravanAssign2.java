/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs2caravanassign2;

import java.util.Scanner;
import java.util.Arrays;
/**
 *  Steve Louis 
 *  CS2 Travis Meade
 * 
 *  
 * 
 */
public class CS2CaravanAssign2 {
    
    public static int numOfCities;
    public static int numOfRoads;
  
        
   private static class Road{
       
       int city1;
       int city2;
       int cost;
       int weightLimit;
       
       private Road(int a,int b,int c,int d){
           
            city1 = a;
            city2 = b;
            cost = c;
            weightLimit = d;
       }

    }
      
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        numOfCities = input.nextInt();
        numOfRoads = input.nextInt();
        Road[] roadList = new Road[numOfRoads];
        
       
        for(int i = 0; i < numOfRoads;i++){
            roadList[i].city1 = input.nextInt();
            roadList[i].city2 = input.nextInt();
            roadList[i].cost = input.nextInt();
            roadList[i].weightLimit = input.nextInt();
        }
        
    }  
    
    public static int maxWeight(){
        int maxWeight = 0;

     // TODO code application logic here
    return maxWeight;
    }
    
  
    
}

