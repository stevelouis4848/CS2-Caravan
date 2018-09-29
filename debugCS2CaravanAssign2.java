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
    public static int[] parentArray;
    public static ArrayList<Road> arrayListOfRoads;
    public static ArrayList<Road> currNetworkList;
    public static ArrayList<Integer> outputList;
    public static Road roadToCompare;
        
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

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
         }};
        
            @Override
        public String toString() {
            return "city1: " + city1+ " city2: " + city2 + " cost: "+ cost;
        }

        @Override
        public int compareTo(Object t) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
      
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        numOfCities = input.nextInt();
        numOfRoads = input.nextInt();   
       
        arrayListOfRoads = new ArrayList<>();      
        outputList = new ArrayList<>();
        parentArray = new int[numOfCities + 1];
        currNetworkList = new ArrayList<>();
       
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
        
        System.out.println("Cost Sorting:\n");
        
        arrayListOfRoads.forEach((str) -> {
            System.out.println(str);
            
        }
        );
        
        System.out.println("\n\n");
       
        // For each possible # of wagons there's an atttempt to union the roads 
        for(int i = 1; i <= MAX_NUM_OF_WAGONS; i++){
                             
            currNumOfWagons = i;
            
            /*  The Most amount of weight that a  wagon will need carry and a road
                in the network must to be able to support.*/
            currWeightLimit = TOTAL_WEIGHT / currNumOfWagons;
        
            /*  Once a network is succesully build, the number of wagons
                involved is added to the output list to be printed later. */
            if(networkBuilder()){    
                 System.out.printf("RoadBuilder %d successful\n\n", i);
                outputList.add(i);
            }
            else{
                System.out.printf("RoadBuilder %d not successful\n\n", i);
            }
        }
        
        printSolution();       
    }
    
    public static boolean networkBuilder(){
        
        // The contents of the last network building attempt is cleared.
        currNetworkList.clear();
        
        // The parent array is reset for the current attempt.
        for(int i = 0; i <= numOfCities; i++){

            parentArray[i] = i;
        }
         
        /* Each road is considered for the network in order of the lowest cost
            roads to the highest cost roads. */
        for(int i = 0; i < numOfRoads; i++){
            
            // We retrieve the road being considered for the network.
            roadToCompare = arrayListOfRoads.get(i);
            
            /* If the road can be unioned into the network then it is
                added to the network and the parrent array is updated */
            if(ifUnionValid()){

                currNetworkList.add(roadToCompare);
                parentArray[roadToCompare.getCity1()] = find(roadToCompare.getCity2());
            }
        }
        
        currNetworkList.forEach((str) -> {
            System.out.println(str);
        });
        
        for(int i = 1; i <= numOfCities; i++){
            
            System.out.printf("parentArray[%d] = %d\n", i, parentArray[i]);
        }
         
        // Checks if the newly build network is a valid network
        if(!ifNetworkValid()){
                         
            return false;
        }
        
        return true;
    }
    
    public static boolean ifUnionValid(){
        
        // Checks if the road being considered will create a cycle.
        if(find(roadToCompare.getCity1()) == find(roadToCompare.getCity2())){
            
            System.out.printf("Parents are equal to each other city1:%d city2:%d\n",roadToCompare.getCity1(),roadToCompare.getCity2());
            return false;
        }
        
        // Checks if the road being will not be able to suppirt the wagons.
        if(roadToCompare.getWeigth() < currWeightLimit){
            
            System.out.printf("Road weigth limiit is too low currentWeightLimit:%f roadWeightLimit:%d \n", currWeightLimit, roadToCompare.getWeigth());
            return false;
        }
               
        return true;
    }
    
    /*  Returns the root node in the network containing the node passed in,while
        implementing path compression. */
    public static int find(int city){
     
        if(parentArray[city] == city){
            return city;
        }
        
        /*  Implementation of path compression to quicken runtime,recursively 
            sets the parents of the nodes in a network to the root node and
            returns the root node */
        return parentArray[city] = find(parentArray[city]);         
    }
    
     public static boolean ifNetworkValid(){
         
       double networkCost;
        // Checks if the network has (nodes - 1) number of edges. 
        if(currNetworkList.size() != numOfCities - 1){
            
            System.out.printf("Network failed notright amount roads\n");
            return false;
        }
       
        // Checks if the network cost is within the budget.
        if((networkCost = calculateCost()) > BUDGET){

            System.out.printf("Network failed cost over the budger budget:%d cost:%f \n", BUDGET, networkCost);
            return false;
        }
       
        System.out.printf("Network  cost successful inline of the budget budget:%d cost:%f \n", BUDGET, networkCost);
                       
        return true;
    }
     
    // Calculates and returns the total cost of roads in the network and buying the wagons.
    public static double calculateCost(){
        
       double networkCost = 0;
       
        // The cost of each roads in the network are sumed up.
        for(int i = 0; i < currNetworkList.size(); i++ ){

            networkCost = networkCost + currNetworkList.get(i).getCost();            
        }
       
        // The cost of the wagons is added to the network cost
        networkCost = networkCost + (currNumOfWagons * COST_OF_WAGONS);
       
        return networkCost;
    }
    
    // Prints the size of the outputList followed by its contents.
    public static void printSolution(){
        
        //  Prints the size of the outputList.
        System.out.printf("%d\n", outputList.size());
        
        //  Prints the contents of the outputList.
        for(int i = 0; i < outputList.size(); i++ ){
            
            System.out.printf("%d ", outputList.get(i));
        } 
        System.out.printf("\n");
    }
}
