import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _08_synchronize_lock_object {
    public static void main(String[] args) {
        
        String threadName = Thread.currentThread().getName();
        System.out.printf("Starting %s Thread %n",threadName);

        /*
         * 1. Es wird der ReentrantLock verwendet, da es Grundsätzlich möglich ist,
         * auf diesen Lock einen weiteren Lock aufzurufen. Ich muss also nicht 
         * prüfen, ob ich bereits einen Lock erzeugt habe. 
         * 
         * Nicht relevant für Test!
         */
        ReentrantLock lock = new ReentrantLock();
    
        final int THREADS_N = 10;
        
        LinkedList<Thread> tList = new LinkedList<>();

        /*
         * 4. Damit der Counter korrekt hochgezählt wird, muss darauf geachtet werden, 
         * dass alle Threads den selben Locker mitbekommen.
         */

        for(int i = 0; i < THREADS_N; ++i) {
            Runner5 runner = new Runner5(lock);
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
 * 2. Das Datenfeld wird von Objekt auf Lock geändert, sodass nicht die Typensicherheit
 * von Java gefährdet ist bzw. dass kein unschöner Code geschrieben wurde.
 */
class Runner5 implements Runnable {

    final int COUNT_TIMES = 10000;
    Lock lock;

    public Runner5(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for(int i = 0; i < COUNT_TIMES; ++i) {
            lock.lock();
            Storage.counter ++;
            lock.unlock();
        }
    }
}
/*
 * 3. Nun wird synchronized entfernt, da ansonsten nur auf das mitgegebene Objekt 
 * synchronisiert werden würde und Lock sinnlos wäre. Wenn also auf das lock Objekt
 * die Methode lock() aufgerufen wird, dann kann kein anderer Runner bzw. Thread, 
 * der ebenfalls das lock-Objekt verwendet, die lock() Methode aufrufen. Das heißt, 
 * dass jeder andere Thread, der versucht lock.lock() aufzurufen, warten muss, 
 * bis das lock-Objekt über unlock() wieder freigegeben wurde.
 */
