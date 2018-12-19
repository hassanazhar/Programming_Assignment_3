/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.*;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {
    /**
     * Determines a solution to the optimal antenna range for the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the optimal solution
     */
    @Override
    public TownPlan OptimalRange(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Float> house_Positions = town.getHousePosition();
        /*
        Build a matrix NxK to hold the ranges which are also an arraylist of floats
        Ensure that the house index in matrix corresponds to housePositions array
         */
        ArrayList<ArrayList<ArrayList<Float>>> range_Matrix = new ArrayList<>();
        range_Matrix= ConstructMatrix(n,k,house_Positions);
        town.setRange(Collections.max(range_Matrix.get(k-1).get(n-1)));

        /*
        to print out stuff in above range_Matrix use below code
         */

        /*for(ArrayList obj: range_Matrix){
            ArrayList<ArrayList<Float>> temp = obj;
            for(ArrayList job : temp){
                System.out.print(job.toString()+" ");
            }
            System.out.println();
        }*/

        return town;
    }

    /*
    Constructs Matrix with Optimal Ranges.
     */
    public ArrayList ConstructMatrix(int n, int k, ArrayList<Float>house_Positions){
        ArrayList<ArrayList<ArrayList<Float>>> range_Matrix = new ArrayList<>();
        for(int i=0;i<k;i++){//rows BS
            range_Matrix.add(new ArrayList<ArrayList<Float>>()); //add 1 to k rows
            for(int j=0;j<n;j++){//columns = houses
                range_Matrix.get(i).add(new ArrayList<Float>());
                // Initialize lower triangle matrix w 0's.
                if (i>=j){
                    range_Matrix.get(i).get(j).add(0.0f);
                    // lower triangle of matrix should be 0's
                    //for (int m=0;m<=i;m++) {
                    //    range_Matrix.get(i).get(j).add(0.0f);
                    //}
                }
                // Initialize first row (BS) values
                else if(i==0&& j>0){
                    //initialize first row with range values
                    Float h_Pos = house_Positions.get(j);
                    Float init_Pos = house_Positions.get(0);
                    Float range = (h_Pos-init_Pos)/2;
                    range_Matrix.get(i).get(j).add(range);

                }
                //Pass matrix to another function to run all other code.
                //Only do this when i<j
                else if(i<j){
                    Float ret =MinRangeFinder(range_Matrix,house_Positions,i,j);
                    range_Matrix.get(i).get(j).add(ret);
                    //ArrayList<Float> ret_List = MinRangeFinder(range_Matrix,house_Positions,i,j);
                    //for (Float x: ret_List){
                    //    range_Matrix.get(i).get(j).add(x);
                    //}
                }

            }

        }
        return range_Matrix;
    }

    /*
    Pass in range_Matrix, the current row and current column. Iterate for values curr_Row-1, 0 to curr_Col-1.
    Rem always has only 1 base station to calculate.
    Equation: take Min after doing ->: Do for each cell: take Max{Take max of populated top left cell, range of placing 1 BS between remaining}.

    @return: return list of range values to insert at that cell in matrix. CHECK IF DON"T NEED TO RETURN LIST AND CAN UPDATE WITHIN FUNCTION
     */
    public Float MinRangeFinder (ArrayList<ArrayList<ArrayList<Float>>> range_Matrix, ArrayList<Float>house_Positions, int curr_Row, int curr_Col){
        if (curr_Row <=0 || curr_Col<= curr_Row){
            return null;
        }
        int top_Row = curr_Row-1;
        int top_Left_Col = curr_Col-1;
        ArrayList<Float> all_Ranges = new ArrayList<>();
        for (int i=0;i<=top_Left_Col;i++){
            Float max_CurrCell_Range = Collections.max(range_Matrix.get(top_Row).get(i)); //gets max range val in currmatrix entry
            Float dist_Rem_Right_House = house_Positions.get(curr_Col);//Remember, curr-Col 0 indexed in calling function.
            Float dist_Rem_Left_House = house_Positions.get(i+1);
            Float rem_Range = (dist_Rem_Right_House-dist_Rem_Left_House)/2; // rem range for 1 BS
            Float max_Range = Float.max(max_CurrCell_Range, rem_Range);
            all_Ranges.add(max_Range);// the index corresponds to column.

            //ArrayList<Float> col_And_maxRange = new ArrayList<>();
            //col_And_maxRange.add((float)i); // col is firsr
            //col_And_maxRange.add(max_Range); //max_range is second
            //all_Ranges.add(col_And_maxRange);

        }
        Float min_Range = Collections.min(all_Ranges);
        return min_Range;
        /* I was trying to add all range values to the spot in arraylist didnt work. */
        //int index_Min_Range =all_Ranges.indexOf(min_Range);
        //ArrayList<Float> return_List = new ArrayList<>();
        //return_List= range_Matrix.get(top_Row).get(index_Min_Range);
        //return_List.add(min_Range);
        //return return_List;

    }

    /*
    Need to calculate Min range like above.
    Then for that min range, need to find where the right most BS location would be and add it to list of all other BS and return.
     */
    public ArrayList<Float> MinDistanceFinder(ArrayList<Float> [][] distanceTable, ArrayList<ArrayList<ArrayList<Float>>> range_Matrix,ArrayList<Float> house_Positions, int curr_Row, int curr_Col ){
        if (curr_Row <=0 || curr_Col<= curr_Row){
            return null;
        }
        int top_Row = curr_Row-1;
        int top_Left_Col = curr_Col -1;
        ArrayList<ArrayList<Float>> min_Distances = new ArrayList<>(); // return this and insert into matrix[row][col]

        for (int i=0;i<=top_Left_Col;i++){
            Float max_CurrCell_Range = Collections.max(range_Matrix.get(top_Row).get(i)); //gets max range val in currmatrix entry
            Float dist_Rem_Right_House = house_Positions.get(curr_Col);//Remember, curr-Col 0 indexed in calling function.
            Float dist_Rem_Left_House = house_Positions.get(i+1);
            Float rem_Range = (dist_Rem_Right_House-dist_Rem_Left_House)/2; // rem range for 1 BS
            Float max_Range = Float.max(max_CurrCell_Range, rem_Range);// compute based on this
            Float max_Dist = (dist_Rem_Right_House+dist_Rem_Left_House)/2; //pos to place new BS

            //need to keep track of col where I found
            //Float max_CurrCell_Distance = Collections.max(distanceTable[top_Row][i]); //gets max dist val in currmatrix entry
            //Float dist_Rem_Right_House = house_Positions.get(curr_Col);//Remember, curr-Col 0 indexed in calling function.
            //Float dist_Rem_Left_House = house_Positions.get(i+1);
            //Float rem_Distance = (dist_Rem_Right_House + dist_Rem_Left_House)/2;
            //Float max_Dist = Float.max(max_CurrCell_Distance, rem_Distance);
            ArrayList<Float> tmp_Node = new ArrayList<>(); // Node that store both max_Range + col + max_Dist num. Put these in min_Distances
            tmp_Node.add(max_Range);
            tmp_Node.add((float)i);
            tmp_Node.add(max_Dist);
            min_Distances.add(tmp_Node);
        }
        //Min_Distance in memory. Take the min of all max_Dist now
        //[tmp_Node] -> max_Dist, col num, minRange
        //[tmp_Node] -> max_Dist, col num
        ArrayList<Float> tmp_List = new ArrayList<>(); //contains all max_Dist
        for (ArrayList<Float> Node: min_Distances){
            tmp_List.add(Node.get(0));
        }
        Float min = Collections.min(tmp_List); //min Range. Add it to return array
        int index = tmp_List.indexOf(min);
        Float dist = min_Distances.get(index).get(2);
        Float col = min_Distances.get(index).get(1);
        int col2 = Math.round(col); // PUT BRAKEPOINT AND MAKE SURE col =  0.0,1.0,2.0,3.0 for colums! Crucial!!!

        ArrayList<Float> return_List = new ArrayList<>();
        return_List.add(dist);
        return_List.addAll(distanceTable[top_Row][col2]); //CHECK IF THIS WORKS

        return return_List;





    }

    /**
     * Determines a solution to the set of base station positions that optimise antenna range for the given input set. Study the
     * project description to understand the variables which } represent the input to your solution.
     *
     * For lower triangle, just input the value of the first location.
     *
     * @return Updated TownPlan town with the optimal solution
     */
    @Override
    public TownPlan OptimalPosBaseStations(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Float> house_Positions = town.getHousePosition();
        ArrayList<ArrayList<ArrayList<Float>>> rangeMatrix = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Float>>> distanceMatrix = new ArrayList<>();
        ArrayList<Float> [][] distanceTable = new ArrayList[k][n]; //try using this first

        /* Initialize rangeMatrix & distanceTable*/
        rangeMatrix = ConstructMatrix(n,k,house_Positions); // matrix with all ranges.
        System.out.println(house_Positions);
        for (int i=0;i<k;i++){
            for(int j=0;j<n;j++){
                //Debug why null pointer happens. basically lower triangle not being filled correctly.
                // fill lower triangle with initial distance.
                if(i>=j){
                    for (int m=0;m<=i;m++) {//num of locations matches num BS
                        if(distanceTable[i][j]==null) {
                            distanceTable[i][j] = new ArrayList<>();
                            distanceTable[i][j].add(house_Positions.get(0));//put the first house loc down
                        }
                        else{
                            distanceTable[i][j].add(house_Positions.get(j));// j corresponds to the house itself
                        }
                    }

                }
                //initialize first row with distance values
                else if (i==0&& j>0){
                    Float distance_L = house_Positions.get(0);
                    Float distance_R = house_Positions.get(j);
                    Float new_Dist = (distance_R+distance_L)/2;
                    if(distanceTable[i][j]==null) {
                        distanceTable[i][j]= new ArrayList<>();
                        distanceTable[i][j].add(new_Dist);
                    }
                }
                //compute the distancenow. Create function like minRangeFinder but for distance//
                //Pass in rangeMatrix too
                // equation: take Min of: Take max of: max distance in x1, housen+ (max (x2))/2
                else if(i<j){
                    ArrayList<Float> return_List = MinDistanceFinder(distanceTable,rangeMatrix,house_Positions,i,j);
                    distanceTable[i][j]= return_List; //add returnlist to the entry in table. Contains all distances of BS.

                }
            }
        }
        ArrayList<Float> sortedBS = distanceTable[k-1][n-1];
        Collections.sort(sortedBS);
        town.setPositionBaseStations(sortedBS);
        /*for(int i=0;i<k;i++){
            for(int j=0;j<n;j++) {
                System.out.print(distanceTable[i][j].toString() + " ");
            }

            System.out.println();
        }*/






        /* TODO implement this function */
        return town; /* TODO remove this line */
    }
}
