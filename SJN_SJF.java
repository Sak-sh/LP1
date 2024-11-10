import java.util.Scanner;

public class SJN_SJF {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        
        // Process data
        int[] id = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] remainingTime = new int[n];
        int[] completionTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];
        
        // Take input for arrival time and burst time for each process
        for (int i = 0; i < n; i++) {
            id[i] = i + 1;
            System.out.print("Enter Arrival Time and Burst Time for Process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            burstTime[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i]; // Initially, remaining time is the same as burst time
        }

        int completed = 0;
        int currentTime = 0;
        int minBurst = Integer.MAX_VALUE;
        int shortestProcess = -1;
        boolean processFound = false;

        // Process scheduling loop
        while (completed != n) {
            // Find the process with the shortest remaining time that has arrived
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && remainingTime[i] > 0 && remainingTime[i] < minBurst) {
                    minBurst = remainingTime[i];
                    shortestProcess = i;
                    processFound = true;
                }
            }

            if (!processFound) {
                // If no process has arrived, increment current time
                currentTime++;
                continue;
            }

            // Execute the found process for one unit of time
            remainingTime[shortestProcess]--;
            minBurst = remainingTime[shortestProcess];

            // Reset minBurst if the process completes
            if (minBurst == 0) {
                minBurst = Integer.MAX_VALUE;
            }

            // If the process is completed
            if (remainingTime[shortestProcess] == 0) {
                completed++;
                processFound = false;
                completionTime[shortestProcess] = currentTime + 1;
                turnaroundTime[shortestProcess] = completionTime[shortestProcess] - arrivalTime[shortestProcess];
                waitingTime[shortestProcess] = turnaroundTime[shortestProcess] - burstTime[shortestProcess];
            }

            currentTime++;
        }

        // Display the results
        System.out.printf("%-12s%-15s%-15s%-20s%-20s%-20s\n", "Process ID", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Waiting Time");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-12d%-15d%-15d%-20d%-20d%-20d\n", id[i], arrivalTime[i], burstTime[i], completionTime[i], turnaroundTime[i], waitingTime[i]);
        }

        // Calculate average waiting time and average turnaround time
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        
        for (int i = 0; i < n; i++) {
            totalWaitingTime += waitingTime[i];
            totalTurnaroundTime += turnaroundTime[i];
        }

        // Output average times
        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / n);
        
        scanner.close();
    }
}

