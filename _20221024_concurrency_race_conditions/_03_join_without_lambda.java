import java.util.LinkedList;

public class _03_join_without_lambda {
    public static void main(String[] args) {
        
        String threadName = Thread.currentThread().getName();
        System.out.printf("Starting %s Thread %n",threadName);
    
        final int THREADS_N = 10;
        
        LinkedList<Thread> tList = new LinkedList<>();
        /*
         * Die vorherige Lambda Expression wurde nun durch eine Klasse ersetzt. Die Funktion dahinter ist
         * dieselbe. 
         */
        Runner runner = new Runner();

        for(int i = 0; i < THREADS_N; ++i) {
            Thread t = new Thread(runner);
            t.start();
            tList.add(t);
        }
        
        for (Thread t : tList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.printf("Endstand Counter: %d %n", Storage.counter);
        
        
    
    }
}

class Runner implements Runnable {

    final int COUNT_TIMES = 10000;
    @Override
    public void run() {
        for(int i = 0; i < COUNT_TIMES; ++i) {
            Storage.counter ++;
        }
    }
    
}
