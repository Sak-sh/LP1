import java.util.Scanner;

public class Priority {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Get the number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] id = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];
        int[] completionTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        // Input the arrival time, burst time, and priority for each process
        for (int i = 0; i < n; i++) {
            id[i] = i + 1;
            System.out.print("Enter Arrival Time, Burst Time, and Priority for Process " + id[i] + " : ");
            arrivalTime[i] = sc.nextInt();
            burstTime[i] = sc.nextInt();
            priority[i] = sc.nextInt();
        }

        // Sorting processes based on priority using bubble sort
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (priority[j] > priority[j + 1]) {
                    // Swap priority and corresponding values
                    int tempId = id[j];
                    int tempPriority = priority[j];
                    int tempArrival = arrivalTime[j];
                    int tempBurst = burstTime[j];

                    id[j] = id[j + 1];
                    priority[j] = priority[j + 1];
                    arrivalTime[j] = arrivalTime[j + 1];
                    burstTime[j] = burstTime[j + 1];

                    id[j + 1] = tempId;
                    priority[j + 1] = tempPriority;
                    arrivalTime[j + 1] = tempArrival;
                    burstTime[j + 1] = tempBurst;
                }
            }
        }

        // Calculate completion time, waiting time, and turnaround time
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            completionTime[i] = currentTime + burstTime[i];
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
            currentTime = completionTime[i];
        }

        // Display the process table
        System.out.printf("%-12s%-18s%-18s%-18s%-18s%-18s%-18s%n", 
            "Process ID", "Arrival Time", "Burst Time", "Priority", "Completion Time", "Waiting Time", "Turnaround Time");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-12d%-18d%-18d%-18d%-18d%-18d%-18d%n", 
                id[i], arrivalTime[i], burstTime[i], priority[i], completionTime[i], waitingTime[i], turnaroundTime[i]);
        }

        // Calculate average waiting time and turnaround time
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (int i = 0; i < n; i++) {
            totalWaitingTime += waitingTime[i];
            totalTurnaroundTime += turnaroundTime[i];
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / n);

        sc.close();
    }
}

