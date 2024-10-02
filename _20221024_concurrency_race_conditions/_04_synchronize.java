import java.util.LinkedList;

public class _04_synchronize {
    public static void main(String[] args) {
        
        String threadName = Thread.currentThread().getName();
        System.out.printf("Starting %s Thread %n",threadName);
    
        final int THREADS_N = 10;
        
        LinkedList<Thread> tList = new LinkedList<>();
        
        Runner1 runner = new Runner1();

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

class Runner1 implements Runnable {

    final int COUNT_TIMES = 10000;
    @Override
    public void run() {
        for(int i = 0; i < COUNT_TIMES; ++i) {
            count();
        }
        
    }
    /*
     * Nicht so schöne Variante - Effekt der Nebenläufigkeit wurde zerstört:
     * Synchronized sorgt nun dafür, dass die einzelnen Threads nicht gleichzeitig
     * auf den counter zugreifen können - also jeder Thread muss zuerst ablaufen, 
     * bevor der nächste starten kann. Dadurch wird der counter schlussendlich auf
     * 100000 hochgezählt.
     */
    //synchronized public void count() {
    //    for(int i = 0; i < COUNT_TIMES; ++i) {
    //        Storage.counter ++;
    //    }
    //}
    
    /*
     * Auf diese Weise wird nur das synchronized, was wirklich vor mehrfachem Zugriff
     * geschützt werden muss. Die for-Schleife kann von mehreren Threads gleichzeitig
     * aufgerufen werden, ohne das schlussendliche Ergebnis zu verändern. Lediglich
     * der Counter muss vor dem mehrfachen Zugriff der Threads geschütz werden, sodass
     * dieser korrekt hochzählt.
     * Werden zuviele Programmteile synchroniziert, schaltet man sinnloserweise für
     * zu viel Code die Nebenläufigkeit aus - das Programm wird dadurch langsamer.
     * Die Nebenläufigkeit soll nur da deaktiviert werden, wo diese wirklich zu 
     * Problemen führt.
     */
    synchronized public void count() {
        Storage.counter ++;
    }
    
}
