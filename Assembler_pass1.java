import java.util.*;

public class Assembler_pass1{
    // Constants
    private static final int MAX_ENTRIES = 100;

    // Input Matrix and MOT
    private static String[][] inputMatrix = {
        {"100", "START", "", "", "200"},
        {"200", "ALPHA", "MOVER", "A", "='05'"},
        {"201", "BETA", "MOVEM", "B", "='10'"},
        {"202", "GAMMA", "ADD", "A", "DELTA"},
        {"203", "", "LTORG", "", ""},
        {"204", "DELTA", "ORIGIN", "", "ALPHA"},
        {"205", "", "MOVER", "C", "='20'"},
        {"206", "ZETA", "MOVEM", "D", "='30'"},
        {"207", "", "END", "", ""}
    };

    private static String[][] MOT = {
        {"START", "AD,01"},
        {"MOVER", "IS,01"},
        {"MOVEM", "IS,02"},
        {"ADD", "IS,03"},
        {"ORIGIN", "AD,03"},
        {"LTORG", "AD,05"},
        {"END", "AD,04"}
    };

    private static String[][] REG = {
        {"A", "RG,01"},
        {"B", "RG,02"},
        {"C", "RG,03"},
        {"D", "RG,04"}
    };

    // Symbol, Literal, and Pool Tables
    
    private static String[][] symtab = new String[MAX_ENTRIES][2];
    private static String[][] littab = new String[MAX_ENTRIES][2];
    private static int[] pooltab = new int[MAX_ENTRIES];
    private static int symtabCount = 0, littabCount = 0, pooltabCount = 1, LC = 0;

    // Intermediate Code and Machine Code Storage
    
    private static String[] IC = new String[MAX_ENTRIES];
    private static String[] MC = new String[MAX_ENTRIES];

    // Find opcode in MOT and return the corresponding entry
    
    private static String getMOTCode(String mnemonic)
     {
    // Iterate only up to the valid range of the MOT table
    for (int i = 0; i < MOT.length; i++) 
    {
        if (MOT[i][0].equals(mnemonic)) 
        {
            return MOT[i][1];
        }
    }
    return "";  // Return an empty string if not found
}


    private static String getREGCode(String reg)
     {
        for (int i = 0; i < 4; i++) 
        {
            if (REG[i][0].equals(reg)) 
            {
                return REG[i][1];
            }
        }
        return "";
    }

    // Add symbol to symbol table
    
    private static void addSymbol(String label, int address) 
    {
        symtab[symtabCount][0] = label;
        symtab[symtabCount][1] = Integer.toString(address);
        symtabCount++;
    }

    // Add literal to literal table
    private static void addLiteral(String literal, int address)
     {
        for (int i = 0; i < littabCount; i++) {
            if (littab[i][0].equals(literal)) {
                return; // If literal already exists, return
            }
        }
        littab[littabCount][0] = literal;
        littab[littabCount][1] = Integer.toString(address);
        littabCount++;
    }

    // Print Intermediate Code
    private static void printIC()
     {
        System.out.println("\nIntermediate Code (IC):");
        for (int i = 0; i < 9; i++)
         {
            String LC_str = inputMatrix[i][0];
            String label = inputMatrix[i][1];
            String opcode = inputMatrix[i][2];
            String reg = inputMatrix[i][3];
            String operand = inputMatrix[i][4];

            String motCode = getMOTCode(opcode);

            if (!motCode.isEmpty()) 
            {
                String instruction = "(" + motCode + ")";
                if (!reg.isEmpty()) 
                {
                    instruction += "(" + getREGCode(reg) + ")";
                }
                if (operand.contains("=")) 
                {
                    // Literal found
                    instruction += "(L," + operand + ")";
                } 
                else if (!operand.isEmpty()) 
                {
                    instruction += "(C," + operand + ")";
                }
                IC[i] = instruction; // Store in IC array
            }
        }

        for (int i = 0; i < 10; i++)
         {
            System.out.println(IC[i]);
        }
    }

    // Print Symbol, Literal, and Pool Tables
    
    private static void printTables() 
    {
        System.out.println("\nSymbol Table:");
        System.out.printf("%-10s%-10s\n", "Symbol", "Address");
        for (int i = 0; i < symtabCount; i++) {
            System.out.printf("%-10s%-10s\n", symtab[i][0], symtab[i][1]);
        }

        System.out.println("\nLiteral Table:");
        System.out.printf("%-10s%-10s\n", "Literal", "Address");
        for (int i = 0; i < littabCount; i++) {
            System.out.printf("%-10s%-10s\n", littab[i][0], littab[i][1]);
        }

        System.out.println("\nPool Table:");
        System.out.printf("%-10s\n", "Index");
        for (int i = 0; i < pooltabCount; i++) {
            System.out.printf("%-10s\n", pooltab[i]);
        }
    }

    // Simulate Pass 1
    private static void pass1()
     {
        for (int i = 0; i < 9; i++) {
            String LC_str = inputMatrix[i][0];
            String label = inputMatrix[i][1];
            String opcode = inputMatrix[i][2];
            String reg = inputMatrix[i][3];
            String operand = inputMatrix[i][4];

            // Update Location Counter (LC)
            LC = Integer.parseInt(LC_str);

            if (!label.isEmpty()) {
                addSymbol(label, LC); // Add labels to symbol table
            }

            if (opcode.equals("START")) {
                LC = Integer.parseInt(operand); // Set LC to operand of START
            } else if (opcode.equals("LTORG") || opcode.equals("END")) {
                for (int j = pooltab[pooltabCount - 1]; j < littabCount; j++) {
                    if (littab[j][1].equals("0")) {
                        littab[j][1] = Integer.toString(LC++);
                    }
                }
                pooltab[pooltabCount++] = littabCount; // Update pool table
            } else if (opcode.equals("ORIGIN")) {
                for (int j = 0; j < symtabCount; j++) {
                    if (symtab[j][0].equals(operand)) {
                        LC = Integer.parseInt(symtab[j][1]);
                        break;
                    }
                }
            } else {
                // Check for literals in operand
                if (operand.contains("=")) {
                    addLiteral(operand, 0); // Add literal with address 0 (unassigned)
                }

                // Normal Instruction Processing
                if (opcode.equals("MOVER") || opcode.equals("MOVEM") || opcode.equals("ADD")) {
                    LC++; // Increment LC for each instruction
                }
            }
        }
    }

    // Get Literal Address
    private static String getLiteralAddress(String lit) 
    {
        for (int i = 0; i < 10; i++) {
            if (littab[i][0].equals(lit)) {
                return littab[i][1];
            }
        }
        return "";
    }

    // Get Symbol Address
    private static String getSymbolAddress(String sym)
     {
        for (int i = 0; i < 10; i++) {
            if (symtab[i][0].equals(sym)) {
                return symtab[i][1];
            }
        }
        return "";
    }

    
   // Simulate Pass 2: Generate Machine Code from Intermediate Code
   
private static void pass2(String[] IC)
 {
    System.out.println("Machine Code:");

    // Process each line of Intermediate Code
    
    for (int i = 0; i < 9; i++) 
    {  // Adjust to correct loop bounds
        String ICline = IC[i];
        if (ICline == null || ICline.isEmpty()) continue;  // Skip empty lines

        StringBuilder machineCode = new StringBuilder();

        // Extract the opcode part: e.g., "(IS,01)" or "(AD,01)"
        
        int startIdx = ICline.indexOf('(') + 1;
        int endIdx = ICline.indexOf(')');
        String opCode = ICline.substring(startIdx, endIdx);  // Extracting opcode (e.g., IS,01)

        // Add the opcode to machine code
        machineCode.append(opCode.split(",")[1]).append(" ");  // Extract only the numeric part

        // Check for register part, e.g., "(RG,01)"
        if (ICline.length() > endIdx + 1) 
        {
            startIdx = ICline.indexOf('(', endIdx + 1) + 1;
            endIdx = ICline.indexOf(')', endIdx + 1);
            if (startIdx != -1 && endIdx != -1) {
                String regCode = ICline.substring(startIdx, endIdx);  // Extract register (e.g., RG,01)
                machineCode.append(regCode.split(",")[1]).append(" ");
            }
        }

        // Check for operand part (literal or symbol), e.g., "(L,05)" or "(C,DELTA)"
        
        if (ICline.length() > endIdx + 1) 
        {
            startIdx = ICline.indexOf('(', endIdx + 1) + 1;
            endIdx = ICline.indexOf(')', endIdx + 1);
            if (startIdx != -1 && endIdx != -1)
             {
                String operand = ICline.substring(startIdx, endIdx);  // Extract operand (e.g., L,05)
                if (operand.startsWith("L")) 
                {
                    // Literal address (e.g., L,05)
                    String litAddress = getLiteralAddress(operand.substring(2));  // Extract literal address
                    machineCode.append(litAddress);
                } 
                else if (operand.startsWith("C"))
                 {
                    // Symbol address (e.g., C,DELTA)
                    String symAddress = getSymbolAddress(operand.substring(2));  // Extract symbol address
                    machineCode.append(symAddress);
                }
            }
        }

        // Print out the machine code for this instruction
        System.out.println(machineCode.toString().trim());
    }
}

    public static void main(String[] args) {
        // Perform Pass 1
        pass1();

        // Print IC, Symbol Table, Literal Table, and Pool Table
        printIC();
        printTables();

        // Perform Pass 2
        pass2(IC);
    }
}

