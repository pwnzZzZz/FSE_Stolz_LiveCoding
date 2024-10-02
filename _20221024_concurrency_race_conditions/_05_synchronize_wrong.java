import java.util.LinkedList;

public class _05_synchronize_wrong {
    public static void main(String[] args) {
        
        String threadName = Thread.currentThread().getName();
        System.out.printf("Starting %s Thread %n",threadName);
    
        final int THREADS_N = 10;
        
        LinkedList<Thread> tList = new LinkedList<>();

        /*
         * synchronized synchronisiert auf das Runnable Objekt.
         * Das heißt, wenn ein neues Runnable Objekt erstellt wird, wird nicht 
         * zwischen den verschiedenen Objekten synchronisiert.
         * 
         * In diesem Fall wird für jeden Thread ein neuer Runner erstellt. Dieser 
         * sorgt dann zwar dafür, dass der eigene Thread nicht zeitgleich den
         * Counter erhöht (was bei einem Thread sowieso nicht passiert), aber 
         * mehrere paralell laufende Threads können dann trotzdem wieder gleichzeitig
         * auf den Counter zugreifen.
         * 
         * Es gibt Ausnahmefälle, in denen man möchte, dass jeder Thread einen
         * eigenen Runner hat. Wenn der Runner z.B. über Datenfelder verfügt, die
         * bestimmte Werte speichern und jeder Thread eigene Werte speichern möchte.
         * Dann funktioniert allerdings synchronized nicht mehr, da es sich zwar in 
         * der gleichen Klasse befindet, nicht aber im selben Objekt. 
         */
        for(int i = 0; i < THREADS_N; ++i) {
            Runner2 runner = new Runner2();
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

class Runner2 implements Runnable {

    final int COUNT_TIMES = 10000;
    @Override
    public void run() {
        for(int i = 0; i < COUNT_TIMES; ++i) {
            count();
        }
    }

    synchronized public void count() {
        Storage.counter ++;
    }
    
}
