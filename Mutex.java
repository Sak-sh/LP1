import java.util.concurrent.Semaphore;
import java.util.Scanner;

public class Mutex {
    
    // Mutex for updating readCount safely
    static Semaphore mutex = new Semaphore(1);
    
    // Semaphore for ensuring exclusive write access
    static Semaphore wrt = new Semaphore(1);
    
    // Count of active readers
    static int readCount = 0;
    
    // Shared resource message
    static String message = "Hello";
    
    // Scanner for input (if needed)
    static Scanner SC = new Scanner(System.in);

    // Reader class implementing Runnable
    static class Reader implements Runnable {
        public void run() {
            try {
                // Acquire mutex to update readCount safely
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    // If this is the first reader, block writers
                    wrt.acquire();
                }
                mutex.release();  // Release mutex
                
                // Simulate reading
                System.out.println("Thread " + Thread.currentThread().getName() + " is READING: " + message);
                Thread.sleep(1500);  // Simulate time taken to read
                System.out.println("Thread " + Thread.currentThread().getName() + " has FINISHED READING");
                
                // Acquire mutex to update readCount safely
                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    // If this is the last reader, release write permission for writers
                    wrt.release();
                }
                mutex.release();  // Release mutex
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Writer class implementing Runnable
    static class Writer implements Runnable {
        public void run() {
            try {
                // Acquire wrt semaphore to get exclusive access for writing
                wrt.acquire();
                
                // Simulate writing
                message = "Good Morning";
                System.out.println("Thread " + Thread.currentThread().getName() + " is WRITING: " + message);
                Thread.sleep(1500);  // Simulate time taken to write
                System.out.println("Thread " + Thread.currentThread().getName() + " has FINISHED WRITING");
                
                // Release wrt semaphore to allow other writers or readers
                wrt.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Create instances of Reader and Writer
        Reader read = new Reader();
        Writer write = new Writer();
        
        // Create threads for readers and writers
        Thread r1 = new Thread(read);
        r1.setName("Reader1");
        Thread r2 = new Thread(read);
        r2.setName("Reader2");
        Thread r3 = new Thread(read);
        r3.setName("Reader3");

        Thread w1 = new Thread(write);
        w1.setName("Writer1");
        Thread w2 = new Thread(write);
        w2.setName("Writer2");
        Thread w3 = new Thread(write);
        w3.setName("Writer3");

        // Start all threads
        w1.start();
        r1.start();
        w2.start();
        r2.start();
        w3.start();
        r3.start();
    }
}

