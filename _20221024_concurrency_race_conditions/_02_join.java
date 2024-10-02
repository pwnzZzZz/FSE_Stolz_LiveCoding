import java.util.LinkedList;

public class _02_join {
    public static void main(String[] args) {
        
        String threadName = Thread.currentThread().getName();
        System.out.printf("Starting %s Thread %n",threadName);
    
        final int COUNT_TIMES = 10000;
        final int THREADS_N = 10;
    
        Runnable runner = () -> {
            for(int i = 0; i < COUNT_TIMES; ++i) {
                Storage.counter ++;
            }
        };
        
        LinkedList<Thread> tList = new LinkedList<>();

        for(int i = 0; i < THREADS_N; ++i) {
            Thread t = new Thread(runner);
            t.start();
            tList.add(t);
        }
        /*
         * 2. Jetzt wird jeder erstellte Thread in einer Liste gespeichert. Diese Liste
         * wird dann an die forEach-Schleife mitgegeben. t.join() wartet dann also
         * auf alle Threads. Es wird also umgangen, dass der MainThread schon fertig ist, bevor alle
         * Threads fertig gezählt haben.
         * Dennoch wird bei der Ausgabe der Counter nach wie vor weit unter 100000 angezeigt. Dies ist der
         * Fall, weil die Counter zeitgleich auf den Counter zugreifen und diesen deshalb nicht einzeln und
         * genau hochzählen.
         */
        for (Thread t : tList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*
         * 1. So kommen wir zumindest auf 10000 beim Counter, da wir mit t.join() auf das letzte 
         * Thread Objekt warten. Mit der Methode join() warten wir also wirklich darauf, dass der 
         * Tread fertig ist mit seiner Arbeit. Das bedeutet aber nicht, dass das letzte Objekt auch 
         * als letztes fertig ist mit hochzählen - sprich, wird dieses z.b. als 3tes fertig, 
         * wird danach der Counter ausgegeben.
         * 
         * try {
         *      t.join();
         * } catch (InterruptedException e) {
         *      e.printStackTrace();
         * }
         */
        
        
        System.out.printf("Endstand Counter: %d %n", Storage.counter);
        
        
    
    }
}
