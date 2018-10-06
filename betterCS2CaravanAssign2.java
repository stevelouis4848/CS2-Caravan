/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs2caravanassign2;

import java.util.Scanner;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
/**
 *  Steve Louis 
 *  CS2 Travis Meade
 *  Assignment 2 Caravan
 * 
 */
public class CS2CaravanAssign2 {
    
    public static final int MAX_NUM_OF_WAGONS = 10;
    public static int numOfCities;
    public static int numOfRoads;
    public static int BUDGET;
    public static int COST_OF_WAGONS;
    public static double TOTAL_WEIGHT;
    public static double currWeightLimit;
    public static double currNumOfWagons;
    
    private static class Road implements Comparable{
       
        private final int city1;
        private final  int city2;
        private final int cost;
        private final  int weightLimit;
       
        //Constructor for the road objects takes in two cities,cost,and weightlimit.
        private Road(int a,int b,int c,int d){
           
            city1 = a;
            city2 = b;
            cost = c;
            weightLimit = d;
       }
       
        public int compareTo(Road compareRoad) {
            int compareCost=((Road)compareRoad).getCost();
            /* For Ascending order*/
            return cost-compareCost;
        }
       
        public int getCost(){
           
           return cost;
        }      
        public int getWeigth(){
           
           return weightLimit;
        }
        public int getCity1(){

          return city1;
        }
        public int getCity2(){
           
           return city2;
       }
        
        // Compare Road Objects by cost and puts the in ascending order of cost
        public static Comparator<Road> roadCostComparator = new Comparator<Road>() {

	public int compare(Road r1, Road r2) {
	   int cost1 = r1.getCost();
	   int cost2 = r2.getCost();

	   //ascending order
	   return cost1 - cost2;
        }};       

        @Override
        public int compareTo(Object t) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
      
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        numOfCities = input.nextInt();
        numOfRoads = input.nextInt();   
             
        ArrayList<Road> arrayListOfRoads = new ArrayList<>();      
        ArrayList<Integer>outputList = new ArrayList<>();
        int[] parentArray = new int[numOfCities + 1];
        int[] rankArray = new int[numOfCities + 1];
       
        

        //Reads the information for the roads and stores the roads in an arrayList
        for(int i = 0; i < numOfRoads; i++){
        
            arrayListOfRoads.add(new Road(input.nextInt(),input.nextInt(),input.nextInt(),input.nextInt()));
        }
       
        // Retrieves and sets the value of the constants.
        BUDGET = input.nextInt();
        COST_OF_WAGONS = input.nextInt();
        TOTAL_WEIGHT = input.nextInt();
        
        // Sorts the arrayList of roads from least to greatest cost
        Collections.sort(arrayListOfRoads, Road.roadCostComparator);

        // For each possible # of wagons there's an atttempt to union the roads 
        for(int i = 1; i <= MAX_NUM_OF_WAGONS; i++){
                             
            currNumOfWagons = i;
            
            /*  The Most amount of weight that a  wagon will need carry and a road
                in the network must to be able to support.*/
            currWeightLimit = TOTAL_WEIGHT / currNumOfWagons;
            
            // The parent and rank arrays are reset for the current attempt.
            for(int j = 0; j <= numOfCities; j++){
                parentArray[j] = j;
                rankArray[j] = 0;
            }
        
            /*  Once a network is succesully build, the number of wagons
                involved is added to the output list to be printed later. */
            if(networkBuilder(arrayListOfRoads, outputList, parentArray, rankArray)){    
                outputList.add(i);
            }
        }   

        printSolution(outputList);       
    }
    
    public static boolean networkBuilder(ArrayList<Road> arrayListOfRoads, ArrayList<Integer> outputList,  int[] parentArray,
        int[] rankArray){
        
        // The contents of the last network building attempt is cleared.
        ArrayList<Road> currNetworkList = new ArrayList<>();
        double networkCost = 0;
        Road roadToCompare;
        
       
        // The cost of each roads in the network are sumed up.
       
        // The cost of the wagons is added to the network cost
        networkCost = currNumOfWagons * COST_OF_WAGONS;
       
        /* Each road is considered for the network in order of the lowest cost
            roads to the highest cost roads. */

        for(int i = 0; (i < numOfRoads) && (currNetworkList.size() < numOfCities -1 ); i++){
            
            // We retrieve the road being considered for the network.
            roadToCompare = arrayListOfRoads.get(i);
            
            /* If the road can be unioned into the network then it is
                added to the network and the parrent array is updated */
            if(ifUnionValid(roadToCompare, parentArray)){

                currNetworkList.add(roadToCompare);
                networkCost = networkCost + roadToCompare.getCost();
               
                if(rankArray[roadToCompare.getCity1()] < rankArray[roadToCompare.getCity2()]){
                    
                    parentArray[roadToCompare.getCity1()] = find(roadToCompare.getCity2(), parentArray);
                }
                else if(rankArray[roadToCompare.getCity1()] > rankArray[roadToCompare.getCity2()]){
                    
                    parentArray[roadToCompare.getCity2()] = find(roadToCompare.getCity1(), parentArray);
                }
                else{
                    
                    parentArray[roadToCompare.getCity1()] = find(roadToCompare.getCity2(), parentArray);
                    rankArray[roadToCompare.getCity2()]++;
                }
                
                if(networkCost > BUDGET){
                    return false;
                }
            }        
        }
        
        if(currNetworkList.size() < numOfCities - 1 ){
            return false;
        }
       
        return true;
    }
    
    public static boolean ifUnionValid( Road roadToCompare, int[] parentArray){
        
        // Checks if the road being considered will create a cycle.
        if(find(roadToCompare.getCity1(), parentArray) == find(roadToCompare.getCity2(), parentArray)){           
            return false;
        }
        // Checks if the road being will not be able to suppirt the wagons.
        
        return roadToCompare.getWeigth() >= currWeightLimit;
    }
    
    /*  Returns the root node in the network containing the node passed in,while
        implementing path compression. */
    public static int find(int city, int[] parentArray){
     
        if(parentArray[city] == city){
            return city;
        }
        
        /*  Implementation of path compression to quicken runtime,recursively 
            sets the parents of the nodes in a network to the root node and
            returns the root node */
        return parentArray[city] = find(parentArray[city], parentArray);         
    }
    
    // Prints the size of the outputList followed by its contents.
    public static void printSolution( ArrayList<Integer> outputList){
        
        int outputSize = outputList.size();
        
        //  Prints the size of the outputList.
        System.out.printf("%d\n", outputSize);
        
        //  Prints the contents of the outputList.
        for(int i = 0; i < outputSize; i++ ){
            
            System.out.printf("%d ", outputList.get(i));
        } 
        System.out.printf("\n");
    }
}
