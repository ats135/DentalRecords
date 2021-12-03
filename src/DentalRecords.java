import java.util.Scanner;
//=============================================================================
public class DentalRecords {
    private static final Scanner keyboard = new Scanner(System.in);
    private static final int MAX_TEETH = 10;
    private static final int MAX_TEETH_LOC = 2;
    //-----------------------------------------------------------------------------

    public static void main(String[] args) {

        int totalFamilyNum;
        String[] nameRecord;
        char[][][] teethInfo;
        int[][] numTeeth;
        char menuChoice;


        System.out.println("Welcome to the Floridian Tooth Records\n" +
                "--------------------------------------");
        // Enter num of people in fam

        totalFamilyNum = getFamilyNum();
        numTeeth = new int[2][totalFamilyNum];
        teethInfo = new char[totalFamilyNum][MAX_TEETH_LOC][MAX_TEETH];

        // Enter name, upper 10 teeth, and lower teeth

        nameRecord = new String[totalFamilyNum];
        getFamData(totalFamilyNum, nameRecord, teethInfo, numTeeth);

        // Print Main menu

        System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
        menuChoice = keyboard.next().charAt(0);

        // Check if menu choice is valid

        while (menuChoice != 'X' && menuChoice != 'x') {
            switch (menuChoice) {
                case 'P':
                case 'p':
                    printFullRecords(totalFamilyNum, nameRecord, teethInfo, numTeeth);
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                    break;
                case 'E':
                case 'e':
                    extractTooth(totalFamilyNum, nameRecord, teethInfo, numTeeth);
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                    break;
                case 'R':
                case 'r':
                    calcRoot(totalFamilyNum, teethInfo, numTeeth);
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                    break;
                default:
                    System.out.print("Invalid menu option, try again              : ");
            }
            menuChoice = keyboard.next().charAt(0);
        }

        System.out.print("Exiting the Floridian Tooth Records :-)");
    }

    //-----------------------------------------------------------------------------

    public static int getFamilyNum() {
        int totalFamNum;

        // Prompt user for number of family members

        System.out.print("Please enter number of people in the family : ");
        totalFamNum = keyboard.nextInt();

        while (totalFamNum < 0 || totalFamNum > 5) {
            System.out.print("Invalid number of people, try again         : ");
            totalFamNum = keyboard.nextInt();
        }
        return (totalFamNum);

    }

    //-----------------------------------------------------------------------------
    public static void getFamData(int totalFamilyNum, String[] famNames, char[][][] teethInformation, int[][] numTeeth) {
        int showNum = 0;
        String uppers;
        String lowers;

        // Prompt for name, record in string array

        for (int famIndex = 0; famIndex < totalFamilyNum; famIndex++) {
            showNum = famIndex + 1;
            System.out.print("Please enter the name for family member " + showNum + "   : ");
            famNames[famIndex] = keyboard.next();

            // Get uppers for specific family member
            uppers = getUppersLowers(famIndex, famNames[famIndex], "uppers");
            // Store get uppers for each member
            numTeeth[0][famIndex] = uppers.length();
            for (int i = 0; i < uppers.length(); i++) {
                teethInformation[famIndex][0][i] = uppers.charAt(i);
            }

            // Get lowers

            lowers = getUppersLowers(famIndex, famNames[famIndex], "lowers");

            //Store get lowers for each member

            numTeeth[1][famIndex] = lowers.length();
            for (int i = 0; i < lowers.length(); i++) {
                teethInformation[famIndex][1][i] = lowers.charAt(i);
            }

        }
    }

    //-----------------------------------------------------------------------------

    public static String getUppersLowers(int famIndex, String famName, String teethLoc) {
        String teethUorL;
        int teethLength = 0;
        boolean validity = true;

        System.out.printf("Please enter the %s for %-10s      : ", teethLoc,famName);
        teethUorL = keyboard.next();

        // Check for all characters must be BCM (lowercase and upper case)

        validity = checkTeethValidity(teethUorL);
        teethLength = teethUorL.length();

        // Check for length <= 10

        while (!validity || teethLength > 10) {
            if (!validity) {
                System.out.print("Invalid teeth types, try again              : ");
            } else {
                System.out.print("Too many teeth, try again                   : ");
            }
            teethUorL = keyboard.next();
            teethLength = teethUorL.length();
            validity = checkTeethValidity(teethUorL);
        }
        teethUorL = teethUorL.toUpperCase();
        return teethUorL;
    }
    //-----------------------------------------------------------------------------
    public static boolean checkTeethValidity(String teeth) {
        boolean teethValidity = false;

        // Make sure teeth are either C, B or M upper or lowercase

        for (int index = 0; index < teeth.length(); index++) {
            switch (teeth.charAt(index)) {
                case 'C':
                case 'c':
                case 'B':
                case 'b':
                case 'M':
                case 'm':
                    teethValidity = true;
                    break;
                default:
                    //anything else is not valid
                    teethValidity = false;

            }
        }
        return teethValidity;
    }
    //-----------------------------------------------------------------------------

    public static void printFullRecords(int totalFamilyNum, String[] famNames, char[][][] teethInformation, int[][] numTeeth) {

        // Print names in string array

        for (int famIndex = 0; famIndex < totalFamilyNum; famIndex++) {
            System.out.println(famNames[famIndex]);
            System.out.print("  Uppers:  ");
            for (int toothIndex = 0; toothIndex < numTeeth[0][famIndex]; toothIndex++) {
                System.out.printf("%3d:%S", toothIndex + 1, teethInformation[famIndex][0][toothIndex]);
            }
            System.out.println();
            System.out.print("  Lowers:  ");
            for (int toothIndex = 0; toothIndex < numTeeth[1][famIndex]; toothIndex++) {
                System.out.printf("%3d:%S", toothIndex + 1, teethInformation[famIndex][1][toothIndex]);
            }
            System.out.println();

        }
    }

    //-----------------------------------------------------------------------------

    public static void extractTooth(int totalFamilyNum, String [] famNames, char[][][] teethInformation, int [][] numTeeth){

        String famMember;
        boolean found = false;
        boolean correctUL = false;
        char toothLayer;
        int saveMemberID = 0;
        int toothNum;
        int toothRow = 0;

        // Get family member name

        System.out.print("Which family member                         : ");
        famMember = keyboard.next();

        // Check if family member is valid

        do{
            for (int famIndex = 0; famIndex <totalFamilyNum; famIndex++){
                if ( famMember.equalsIgnoreCase(famNames[famIndex])) {
                    found = true;
                    saveMemberID = famIndex;
                }
            }

            if (!found){
                System.out.print("Invalid family member, try again            : ");
                famMember = keyboard.next();
            }

        }while (!found);

         // Get tooth layer
        System.out.print("Which tooth layer (U)pper or (L)ower        : ");
        toothLayer = keyboard.next().charAt(0);

        // Get tooth layer whether upper or lower
        do {
            switch (toothLayer) {
                case 'U':
                case 'u':
                    correctUL = true;
                    toothRow = 0;
                    break;
                case 'L':
                case 'l':
                    correctUL = true;
                    toothRow = 1;
                    break;
                default:
                    System.out.print("Invalid layer, try again                    : ");
                    toothLayer = keyboard.next().charAt(0);
                }
            }while(!correctUL);

        // Get tooth number
        System.out.print("Which tooth number                          : ");
        toothNum = keyboard.nextInt();

        // Check if missing

        // Replace with M if valid

        while (toothNum > numTeeth[toothRow][saveMemberID] || toothNum <= 0 ||
                teethInformation [saveMemberID][toothRow][toothNum-1]== 'M') {
           if (toothNum > numTeeth[toothRow][saveMemberID] || toothNum <= 0) {
               System.out.print("Invalid tooth number, try again             : ");
           }else if (teethInformation[saveMemberID][toothRow][toothNum-1] == 'M'){
               System.out.print("Missing tooth, try again                    : ");
           }
            toothNum = keyboard.nextInt();
        }
        teethInformation[saveMemberID][toothRow][toothNum-1] = 'M';
    }

    //-----------------------------------------------------------------------------
    public static void calcRoot(int totalFamilyNum, char[][][] teethInformation, int [][] numTeeth) {

        double a;
        double b;
        double c;
        double root1, root2;
        double discriminant;
        double sumC = 0.0;
        double sumB = 0.0;
        double sumM = 0.0;

        // For loop to add up all C, B and M

        for (int famIndex = 0; famIndex < totalFamilyNum; famIndex++) {
            for (int rowIndex =0; rowIndex < MAX_TEETH_LOC;rowIndex ++){
                for (int toothIndex = 0; toothIndex < numTeeth[rowIndex][famIndex]; toothIndex++) {
                switch (teethInformation[famIndex][rowIndex][toothIndex]) {
                    case 'C':
                        sumC += 1;
                        break;
                    case 'B':
                        sumB += 1;
                        break;
                    case 'M':
                        sumM += 1;
                        break;
                    default:
                        System.out.print("Error in reading teeth information                    :");
                    }
                }
            }
        }
        // Bx2+Cx-M
        a = sumB;
        b = sumC;
        c = -1*sumM;

        // Calculate discriminant to find real root canals

        discriminant = b * b - 4.0 * a * c;
        if (discriminant == 0.0) {
            root1 = -b / (2.0 * a);
            System.out.printf("One root canal at %6.2f\n", root1);
            System.out.println();
        }else if (discriminant> 0.0) {
            root1 = (-b + Math.pow(discriminant, 0.5)) / (2.0 * a);
            root2 = (-b - Math.pow(discriminant, 0.5)) / (2.0 * a);
            System.out.printf("One root canal at %6.2f\nAnother root canal at %6.2f", root1, root2);
            System.out.println();
        }else {
            System.out.println("No real roots");
        }
    }
}






