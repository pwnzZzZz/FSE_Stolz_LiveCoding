import java.util.LinkedList;

public class _06_synchronize_block_this {
    public static void main(String[] args) {
        
        String threadName = Thread.currentThread().getName();
        System.out.printf("Starting %s Thread %n",threadName);
    
        final int THREADS_N = 10;
        
        LinkedList<Thread> tList = new LinkedList<>();

        Runner3 runner = new Runner3();

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
/*
 * Nun funktioniert das Programm wie in _04_synchronized. Der Unterschied liegt darin,
 * dass der Counter zuerst in eine andere Methode verschoben wurde, die synchronized 
 * wurde. Nun ist der Counter wieder in der Methode run() - mit dem Unterschied,
 * dass er im synchronized-block gelagert ist. Dieser synchronized Block wird dabei
 * immer auf das aktuelle Objekt aufgerufen, der das Runner-Objekt in dem Moment 
 * verwendet. Es wird also die richtige Stelle synchronisiert, ohne die Nebenläufig-
 * keit ganz auszuschalten.
 */
class Runner3 implements Runnable {

    final int COUNT_TIMES = 10000;
    @Override
    public void run() {
        for(int i = 0; i < COUNT_TIMES; ++i) {
            synchronized(this){
                Storage.counter ++;
            }
        }
    }
    
}
