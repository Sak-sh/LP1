import java.util.*;

public class Round_Robbin {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        int[] id = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] remainingTime = new int[n];
        int[] completionTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i + 1;
            System.out.print("Enter Arrival Time and Burst Time for Process " + id[i] + " : ");
            arrivalTime[i] = scanner.nextInt();
            burstTime[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i];
        }

        System.out.print("Enter Time Quantum: ");
        int timeQuantum = scanner.nextInt();

        int time = 0;
        Queue<Integer> processQueue = new LinkedList<>();
        int i = 0;

        while (true) {
            while (i < n && arrivalTime[i] <= time) {
                processQueue.add(i);
                i++;
            }

            if (processQueue.isEmpty()) {
                if (i >= n) break;
                time++;
                continue;
            }

            int currentProcess = processQueue.poll();
            int timeSlice = Math.min(timeQuantum, remainingTime[currentProcess]);
            time += timeSlice;
            remainingTime[currentProcess] -= timeSlice;

            if (remainingTime[currentProcess] == 0) {
                completionTime[currentProcess] = time;
                turnaroundTime[currentProcess] = completionTime[currentProcess] - arrivalTime[currentProcess];
                waitingTime[currentProcess] = turnaroundTime[currentProcess] - burstTime[currentProcess];
            } else {
                processQueue.add(currentProcess);
            }
        }

        // Display the results
        System.out.printf("%-10s%-15s%-15s%-20s%-20s%-15s\n", "Process ID", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Waiting Time");
        for (int j = 0; j < n; j++) {
            System.out.printf("%-10d%-15d%-15d%-20d%-20d%-15d\n", id[j], arrivalTime[j], burstTime[j], completionTime[j], turnaroundTime[j], waitingTime[j]);
        }

        int avgWaitingTime = 0;
        int avgTurnaroundTime = 0;

        for (int j = 0; j < n; j++) {
            avgWaitingTime += waitingTime[j];
            avgTurnaroundTime += turnaroundTime[j];
        }

        System.out.println("Average Waiting Time : " + (avgWaitingTime / n));
        System.out.println("Average Turnaround Time : " + (avgTurnaroundTime / n));
    }
}

