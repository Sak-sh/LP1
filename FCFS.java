import java.util.Scanner;

public class FCFS{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        
        int[] id = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];
        
        // Input arrival time and burst time for each process
        for (int i = 0; i < n; i++) {
            id[i] = i + 1;
            System.out.print("Enter Arrival Time and Burst Time for Process " + id[i] + ": ");
            arrivalTime[i] = scanner.nextInt();
            burstTime[i] = scanner.nextInt();
        }
        
        int currentTime = 0;
        
        // Calculate completion time, turnaround time, and waiting time
        for (int i = 0; i < n; i++) {
            completionTime[i] = currentTime + burstTime[i];
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
            currentTime = completionTime[i];
        }
        
        // Display the results in a tabular format
        System.out.printf("%-10s%-15s%-15s%-15s%-15s%-15s%n", "Process ID", "Arrival Time", "Burst Time", 
                          "Completion Time", "Turnaround Time", "Waiting Time");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-10d%-15d%-15d%-15d%-15d%-15d%n", id[i], arrivalTime[i], burstTime[i], 
                              completionTime[i], turnaroundTime[i], waitingTime[i]);
        }
        
        // Calculate average waiting time and average turnaround time
        int avgWaitingTime = 0;
        int avgTurnaroundTime = 0;
        
        for (int i = 0; i < n; i++) {
            avgWaitingTime += waitingTime[i];
            avgTurnaroundTime += turnaroundTime[i];
        }
        
        System.out.println("Average Waiting Time : " + (avgWaitingTime / n));
        System.out.println("Average Turnaround Time : " + (avgTurnaroundTime / n));
        
        scanner.close();
    }
}

