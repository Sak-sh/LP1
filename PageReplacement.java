import java.util.*;

public class PageReplacement {

    // FIFO Page Replacement
    public static void FIFO(List<Integer> pages, int numFrames) {
        List<Integer> frames = new ArrayList<>(Collections.nCopies(numFrames, -1));
        int pageFaults = 0;
        int index = 0;
        System.out.println("\nFIFO Page Replacement");
        for (int i = 0; i < pages.size(); i++) {
            if (!frames.contains(pages.get(i))) {
                frames.set(index, pages.get(i));
                index = (index + 1) % numFrames;
                pageFaults++;
            }
            System.out.print("Frames: ");
            for (int j = 0; j < numFrames; j++) {
                if (frames.get(j) != -1)
                    System.out.print(frames.get(j) + " ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println("Total Page Faults (FIFO): " + pageFaults);
    }

    // LRU Page Replacement
    public static void LRU(List<Integer> pages, int numFrames) {
        List<Integer> frames = new ArrayList<>(Collections.nCopies(numFrames, -1));
        Map<Integer, Integer> lastUsed = new HashMap<>();
        int pageFaults = 0;
        System.out.println("\nLRU Page Replacement");
        for (int i = 0; i < pages.size(); i++) {
            if (!frames.contains(pages.get(i))) {
                int lru = i, lruIndex = -1;
                for (int j = 0; j < numFrames; j++) {
                    if (frames.get(j) == -1) {
                        lruIndex = j;
                        break;
                    }
                    if (lastUsed.get(frames.get(j)) < lru) {
                        lru = lastUsed.get(frames.get(j));
                        lruIndex = j;
                    }
                }
                frames.set(lruIndex, pages.get(i));
                pageFaults++;
            }
            lastUsed.put(pages.get(i), i);
            System.out.print("Frames: ");
            for (int j = 0; j < numFrames; j++) {
                if (frames.get(j) != -1)
                    System.out.print(frames.get(j) + " ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println("Total Page Faults (LRU): " + pageFaults);
    }

    // Optimal Page Replacement
    public static void Optimal(List<Integer> pages, int numFrames) {
        List<Integer> frames = new ArrayList<>(Collections.nCopies(numFrames, -1));
        int pageFaults = 0;
        System.out.println("\nOptimal Page Replacement");
        for (int i = 0; i < pages.size(); i++) {
            if (!frames.contains(pages.get(i))) {
                int farthest = i, replaceIndex = -1;
                for (int j = 0; j < numFrames; j++) {
                    if (frames.get(j) == -1) {
                        replaceIndex = j;
                        break;
                    }
                    int future = pages.subList(i + 1, pages.size()).indexOf(frames.get(j));
                    if (future > farthest || future == -1) {
                        farthest = future;
                        replaceIndex = j;
                    }
                }
                frames.set(replaceIndex, pages.get(i));
                pageFaults++;
            }
            System.out.print("Frames: ");
            for (int j = 0; j < numFrames; j++) {
                if (frames.get(j) != -1)
                    System.out.print(frames.get(j) + " ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println("Total Page Faults (Optimal): " + pageFaults);
    }

    public static void main(String[] args) {
        // Create a scanner object to take input from user
        Scanner scanner = new Scanner(System.in);
        
        // Take input for number of frames
        System.out.print("Enter the number of frames: ");
        int numFrames = scanner.nextInt();
        
        // Take input for page reference string
        System.out.print("Enter the page reference string (space separated): ");
        scanner.nextLine(); // consume the leftover newline character
        String input = scanner.nextLine();
        
        // Convert the input into a list of integers
        List<Integer> pages = new ArrayList<>();
        String[] pageStrings = input.split(" ");
        for (String page : pageStrings) {
            pages.add(Integer.parseInt(page));
        }
        
        // Call the page replacement algorithms
        FIFO(pages, numFrames);
        LRU(pages, numFrames);
        Optimal(pages, numFrames);
        
        // Close the scanner
        scanner.close();
    }
}

