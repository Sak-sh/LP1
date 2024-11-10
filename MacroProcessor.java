import java.util.*;

public class MacroProcessor {
    
    // Define constants
    static final int MAX_ENTRIES = 100;

    // MNT and MDT tables
    static String[][] MNT = new String[MAX_ENTRIES][2];
    static List<String> MDT = new ArrayList<>();
    static List<String> IC = new ArrayList<>();

    // MNT and MDT counters
    static int mntCount = 0;
    static int mdtCount = 0;

    // Pass I: Create MNT and MDT
    static void pass1(List<String> source) {
        boolean insideMacro = false;

        for (String line : source) {
            if (line.startsWith("MACRO")) {
                insideMacro = true;
                MNT[mntCount][0] = line.substring(6);  // Store macro name
                MNT[mntCount][1] = Integer.toString(mdtCount);  // Store MDT index
                mntCount++;
            } 
            else if (line.startsWith("ENDM") && insideMacro) {
                MDT.add(line);  // Store the ENDM line
                mdtCount++;
                insideMacro = false;
            } 
            else if (insideMacro) {
                MDT.add(line);  // Add macro definition to MDT
                mdtCount++;
            } 
            else {
                IC.add(line);  // Add line to Intermediate Code
            }
        }
    }

    // Pass II: Expand macros in Intermediate Code
    static void pass2() {
        System.out.println("\nExpanded Code (Pass-II):");

        for (String token : IC) {
            boolean isMacro = false;

            // Check if the token corresponds to a macro in MNT
            for (int i = 0; i < mntCount; i++) {
                if (MNT[i][0].equals(token)) {  // Found a macro
                    isMacro = true;
                    int mdtIndex = Integer.parseInt(MNT[i][1]);  // Get MDT index

                    // Print lines from MDT until ENDM is found
                    while (!MDT.get(mdtIndex).equals("ENDM")) {
                        System.out.println(MDT.get(mdtIndex));
                        mdtIndex++;
                    }
                    break;
                }
            }

            // If it's not a macro, print the line as it is
            if (!isMacro) {
                System.out.println(token);
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // Example source code
        List<String> sourceCode = Arrays.asList(
            "MACRO M1",
            "    MOVE A, B",
            "    ADD A, C",
            "ENDM",
            "MACRO M2",
            "    SUB D, E",
            "ENDM",
            "START",
            "M1",
            "M2",
            "END"
        );

        // Perform Pass I
        pass1(sourceCode);

        // Print Macro Name Table (MNT)
        System.out.println("\nMacro Name Table (MNT):");
        System.out.printf("%-15s%-15s%n", "Macro Name", "MDT Index");
        for (int i = 0; i < mntCount; i++) {
            System.out.printf("%-15s%-15s%n", MNT[i][0], MNT[i][1]);
        }

        // Print Macro Definition Table (MDT)
        System.out.println("\nMacro Definition Table (MDT):");
        for (int i = 0; i < mdtCount; i++) {
            System.out.println("MDT Entry " + i + ": " + MDT.get(i));
        }

        // Perform Pass II
        pass2();
    }
}

