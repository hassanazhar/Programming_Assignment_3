import java.util.ArrayList;

/**
 * A Solution to the base station problem that is stored in the TownPlan class
 */
public class TownPlan {
    /**
     * Number of houses.
     */
    private Integer n;

    /**
     * Number of base station antennas.
     */
    private Integer k;

    /**
     * A list containing positions of houses in ascending order
     */
    private ArrayList<Float> position_houses;

    /**
     * Information of the position of the base stations to be placed that needs to be updated
     */
    private ArrayList<Float> position_base_stations;

    /**
     * Optimum antenna range that needs to be found
     */ 
    private Float range;

    public TownPlan(
            Integer n,
            Integer k,
            ArrayList<Float> position_houses) {
        this.n = n;
        this.k = k;
        this.position_houses = position_houses;
        this.position_base_stations = null;
        this.range = null;
    }

    public TownPlan(
            Integer n,
            Integer k,
            ArrayList<Float> position_houses,
	    ArrayList<Float> position_base_stations,
            Float range) {
        this.n = n;
        this.k = k;
        this.position_houses = position_houses;
        this.position_base_stations =position_base_stations;
        this.range = range;
    }

    /**
     * Constructs a dynamic programming based solution to the problem, given the problem as TownPlan. Take a
     * TownPlan which represents the problem data with no solution, and position_base_stations and range which
     * solves the problem given in data.
     *
     * @param data              	The given problem to solve.
     * @param position_base_stations	The solution to the problem to find base station antenna positions
     * @param range			The solution to the problem to find the optimum cluster range
     */
    public TownPlan(TownPlan data, ArrayList<Float> position_base_stations, Float range) {
        this(
                data.n,
                data.k,
                data.position_houses,
                position_base_stations,
                range);
    }

    /**
     * Creates a TownPlan from data which includes an empty solution.
     *
     * @param data The TownPlan containing the problem to solve.
     */
    public TownPlan(TownPlan data) {
        this(
                data.n,
                data.k,
                data.position_houses,
                new ArrayList<Float>(0),
		new Float(10000.00));
    }

    public void setPositionBaseStations(ArrayList<Float> position_base_stations) {
        this.position_base_stations = position_base_stations;
    }

    public void setRange(Float range){
	this.range = range;
    }

    public Integer getHouseCount() {
        return n;
    }

    public Integer getStationCount() {
        return k;
    }

    public ArrayList<Float> getHousePosition() {
        return position_houses;
    }

    public ArrayList<Float> getStationPosition() {
        return position_base_stations;
    }

    public Float getRange() {
        return range;
    }

    public String getInputSizeString() {
        return String.format("n = %d k = %d\n", n, k);
    }

    public String getRangeString(){
	if (range == null) {
            return "";
        }

        return String.format("Optimum Antenna Range =  %f\n", range);
    }
    public String getSolutionString() {
        if (position_base_stations == null) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < position_base_stations.size(); i++) {
            String str = String.format("Antenna %d Position %f", i, position_base_stations.get(i));
            s.append(str);
            if (i != position_base_stations.size() - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    public String toString() {
        return getInputSizeString() + getRangeString() + getSolutionString();
    }
}
