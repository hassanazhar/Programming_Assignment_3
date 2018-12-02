import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static String filename;
    public static boolean testRange;
    public static boolean testPosBaseStation;

    public static void main(String[] args) throws Exception {
        parseArgs(args);

        TownPlan problem = parseTownPlanProblem(filename);
        testRun(problem);
    }

    private static void usage() {
        System.err.println("usage: java Driver [-r] [-p] <filename>");
        System.err.println("\t-r\tTest optimal antenna range");
        System.err.println("\t-p\tTest positions of base stations");
        System.exit(1);
    }

    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            usage();
        }

        filename = "";
        testRange = false;
        testPosBaseStation = false;
        boolean flagsPresent = false;

        for (String s : args) {
            if (s.equals("-r")) {
                flagsPresent = true;
                testRange = true;
            } else if (s.equals("-p")) {
                flagsPresent = true;
                testPosBaseStation = true;
            }else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testRange = true;
            testPosBaseStation = true;
        }
    }

    public static TownPlan parseTownPlanProblem(String inputFile) throws Exception {
        int n = 0;
        int k = 0;
        ArrayList<Float> position_houses;

        Scanner sc = new Scanner(new File(inputFile));
        String[] inputSizes = sc.nextLine().split(" ");

        n = Integer.parseInt(inputSizes[0]);
        k = Integer.parseInt(inputSizes[1]);
        position_houses = readPositionsList(sc, n);

        TownPlan problem = new TownPlan(n, k, position_houses);

        return problem;
    }

    private static ArrayList<Float> readPositionsList(Scanner sc, int n) {
        ArrayList<Float> position_houses = new ArrayList<Float>(0);

        String[] pos = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            position_houses.add(Float.parseFloat(pos[i]));
        }

        return position_houses;
    }

    public static void testRun(TownPlan problem) {
        Program3 program = new Program3();

        if (testRange) {
	    problem.setRange(null);
            problem.setPositionBaseStations(null);
            TownPlan RangeTownPlan = program.OptimalRange(problem);
            System.out.println(RangeTownPlan);
        }

        if (testPosBaseStation) {
	    problem.setRange(null);
	    problem.setPositionBaseStations(null);
            TownPlan PosBaseTownPlan = program.OptimalPosBaseStations(problem);
            System.out.println(PosBaseTownPlan);
        }
    }
}
