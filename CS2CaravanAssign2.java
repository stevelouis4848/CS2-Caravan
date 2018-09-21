/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs2caravanassign2;

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
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
    public static int BUDGET;
    public static int COST_OF_WAGONS;
    public static int TOTAL_WEIGHT;
  
        
   private static class Road{
       
        private int city1;
        private  int city2;
        private int cost;
        private  int weightLimit;
       
       
       private Road(int a,int b,int c,int d){
           
            city1 = a;
            city2 = b;
            cost = c;
            weightLimit = d;
       }
       
        public int getCost(){
           
           return cost;
        }      
        public int getWeigth(){
           
           return weightLimit;
        }
        public int getcity1(){

          return city1;
        }
        public int getCity2(){
           
           return city2;
       }
    }
      
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int MAX_NUM_OF_WAGONS = 9;
        numOfCities = input.nextInt();
        numOfRoads = input.nextInt();
        
       PriorityQueue<Road> queueOfRoads = new PriorityQueue<Road>(numOfCities,new Comparator<Road>() {
        public int compare(Road road1, Road road2) {
            if (road1.getCost() < road2.getCost()) return -1;
            if (road1.getCost() > road2.getCost()) return 1;
            return 0;
        }
       });
       
       for(int i = 0; i < numOfRoads; i++){
        
            Road newRoad = new Road(input.nextInt(),input.nextInt(),input.nextInt(),input.nextInt());
            
            queueOfRoads.add(newRoad);
        }
      
       BUDGET = input.nextInt();
       COST_OF_WAGONS = input.nextInt();
       TOTAL_WEIGHT = input.nextInt();
             
       ArrayList<Integer> solutionList = new ArrayList<>();
       
       ArrayList<Road> completeList = new ArrayList<>();
       
       for(int i = 0; i < MAX_NUM_OF_WAGONS; i++){
           
           int roadWeightLimit = TOTAL_WEIGHT / i;
           
         if(roadBuilder(i, roadWeightLimit, queueOfRoads, completeList) == true){
             
                solutionList.add(i);
                printSolution(solutionList);
                
             
         }
       }
        
    }
    
    public static boolean roadBuilder(int numOfWagons, int roadWeigthLimit, PriorityQueue<Road> queueOfRoads, ArrayList<Road> completeList ){
        
        
        for(int i = 0; i < numOfRoads; i++){
            
            Road toCompare = queueOfRoads.remove();
            
            if(!ifUnionValid(toCompare)){

                return false; 
            }
            
            completeList.add(toCompare);
            
            if(!ifNetworkValid(numOfWagons, completeList)){
                
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean ifUnionValid(Road roadToCheck){
        
        
        
        return true;
    }
    
     public static boolean ifNetworkValid(int numOfWagons, ArrayList<Road> completeList){
        
       
        
        
                
        return true;
    }
     
    public static boolean checkCost(int numberOfWagons, ArrayList<Road> completeList){
        
       int networkCost;
       
       for(int i = 0; i < completeList.size();i++ ){
          
            networkCost =+ completeList.get(i).getCost();            
       }
       
       networkCost =+ numberOfWagons * COST_OF_WAGONS;
       
       
       if(networkCost > BUDGET){
           
           return false;
       }
        
        return true;
    }
    
    public static boolean checkNumberOfNodes(ArrayList completerList){
        
       
        
        return true;
    }
    
    
    public int addToCompletedList(){
        
        return 1;
    }
    
    public boolean checkWeight(){
        
        return true;
    }
    
    
    
    public static void printSolution( ArrayList<Integer> solutionList){
        
        
    }
}

