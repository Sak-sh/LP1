import java.util.*;

public class Memory_fit {

   
    // First Fit Allocation
// First Fit Allocation
public static void firstFit(int[] blockSize, int m, int[] processSize, int n) {
    // Create a copy of block sizes to track remaining space
    int[] remainingBlockSize = Arrays.copyOf(blockSize, blockSize.length);
    int[] allocation = new int[n];
    Arrays.fill(allocation, -1);  // Initialize all allocations to -1

    // Allocate memory to processes
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            // Check if the block has enough space
            if (remainingBlockSize[j] >= processSize[i]) {
                allocation[i] = j;
                remainingBlockSize[j] -= processSize[i];  // Reduce the block's remaining space
                break;
            }
        }
    }

    System.out.println("\nFirst Fit Allocation");
    System.out.println("Process No.\tProcess Size\tBlock no.");
    for (int i = 0; i < n; i++) {
        System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
        if (allocation[i] != -1)
            System.out.println(allocation[i] + 1);  // Block numbers start from 1
        else
            System.out.println("Not Allocated");
    }
}

//Best Fit Allocation
    public static void bestFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        Arrays.fill(allocation, -1);  // Initialize all allocations to -1

        // Allocate memory to processes
        for (int i = 0; i < n; i++) {
            int bestIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestIdx == -1 || blockSize[j] < blockSize[bestIdx])
                        bestIdx = j;
                }
            }
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
            }
        }

        System.out.println("\nBest Fit Allocation");
        System.out.println("Process No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.println(allocation[i] + 1);
            else
                System.out.println("Not Allocated");
        }
    }

    // Worst Fit Allocation
    public static void worstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        Arrays.fill(allocation, -1);  // Initialize all allocations to -1

        // Allocate memory to processes
        for (int i = 0; i < n; i++) {
            int worstIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (worstIdx == -1 || blockSize[j] > blockSize[worstIdx])
                        worstIdx = j;
                }
            }
            if (worstIdx != -1) {
                allocation[i] = worstIdx;
                blockSize[worstIdx] -= processSize[i];
            }
        }

        System.out.println("\nWorst Fit Allocation");
        System.out.println("Process No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.println(allocation[i] + 1);
            else
                System.out.println("Not Allocated");
        }
    }

    // Next Fit Allocation
    public static void nextFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        Arrays.fill(allocation, -1);  // Initialize all allocations to -1
        int lastAllocatedIdx = 0;

        // Allocate memory to processes
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int idx = (lastAllocatedIdx + j) % m;
                if (blockSize[idx] >= processSize[i]) {
                    allocation[i] = idx;
                    blockSize[idx] -= processSize[i];
                    lastAllocatedIdx = idx;
                    break;
                }
            }
        }

        System.out.println("\nNext Fit Allocation");
        System.out.println("Process No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.println(allocation[i] + 1);
            else
                System.out.println("Not Allocated");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Take input for block sizes
        System.out.print("Enter the number of memory blocks: ");
        int m = sc.nextInt();
        int[] blockSize = new int[m];
        System.out.println("Enter the size of each memory block:");
        for (int i = 0; i < m; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blockSize[i] = sc.nextInt();
        }

        // Take input for process sizes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        int[] processSize = new int[n];
        System.out.println("Enter the size of each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            processSize[i] = sc.nextInt();
        }

        // Call the allocation methods
        firstFit(blockSize.clone(), m, processSize, n);
        bestFit(blockSize.clone(), m, processSize, n);
        worstFit(blockSize.clone(), m, processSize, n);
        nextFit(blockSize.clone(), m, processSize, n);
    }
}

