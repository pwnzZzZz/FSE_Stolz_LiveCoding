import java.util.LinkedList;

public class _07_synchronize_block {
    public static void main(String[] args) {
        
        String threadName = Thread.currentThread().getName();
        System.out.printf("Starting %s Thread %n",threadName);
    
        final int THREADS_N = 10;
        
        LinkedList<Thread> tList = new LinkedList<>();

        Object syncObject = tList;

        /*
         * Nun wird, wie in _05_synchronized, für jeden Thread ein neuer Runner erstellt.
         * Dies würde eigentlich, trotz verpacken des Counters in den synchronized-Block,
         * dazu führen, dass der Counter falsch hochgezählt wird, da immer nur ein Thread
         * auf das synchronized eines Objekts zugreift. 
         * Um dies zu optimieren, wurde ein syncObjekt erstellt, das allen Runnern
         * mitgegeben wird. Alle Runner haben nun also dasselbe Objekt - sprich, der
         * synchronized-Block funktioniert wieder und es kann immer nur ein Thread
         * auf den Counter zugreifen.
         */
        
        for(int i = 0; i < THREADS_N; ++i) {
            Runner4 runner = new Runner4(syncObject);
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
 * In 06_synchronized wurde in der for-Schleife das synchronized auf this - sprich
 * das jeweilige Objekt - aufgerufen. Werden nun aber mehrere Runner erstellt, wird
 * synchronized wieder auf das jeweilige Objekt angewendet - die Threads können nun 
 * also wieder gleichzeitig auf den Counter zugreifen, da jeder einen eigenen Runner
 * besitzt. 
 * Um dies zu verhindern, wird dem Objekt im Konstruktor ein syncObjekt mitgegeben
 * und in ein Datenfeld gespeichert, welches sich synchronized nun als Parameter holt.
 * Wird nun also bei mehreren Runnern das selbe syncObjekt mitgegeben, dann 
 * funktioniert synchronized wieder und immer nur ein Thread kann auf den Counter
 * zugreifen, auch wenn alle einen eigenen Runner haben.
 * 
 * Objekt übrigens deshalb, weil so ziemlich jedes Objekt (List, Runnable, etc.) 
 * irgendwo im Vererbungsbaum die Klasse Objekt stehen hat.
 */
class Runner4 implements Runnable {

    final int COUNT_TIMES = 10000;
    Object syncObject;

    public Runner4(Object syncObject) {
        this.syncObject = syncObject;
    }

    @Override
    public void run() {
        for(int i = 0; i < COUNT_TIMES; ++i) {
            synchronized(this.syncObject){
                Storage.counter ++;
            }
        }
    }
    
}
