public class _01_counter {

public static void main(String[] args) {
    // Das Programm läuft im main Thread, gestartete Threads kommen hinzu
    String threadName = Thread.currentThread().getName();
    System.out.printf("Starting %s Thread %n",threadName);

    final int COUNT_TIMES = 10000;
    final int THREADS_N = 10;
    /*
     * In einem Runnable wird angegeben, was der Thread zur Laufzeit erledigen soll.
     * Wird dann ein Tread erstellt, so wird diesem das Runnable mitgegeben.
     */
    Runnable runner = () -> {
        for(int i = 0; i < COUNT_TIMES; ++i) {
            Storage.counter ++;
        }
    };
    /*
     * Hier werden 10mal neue Threads gestartet, die jeweils bis 10000 zählen.
     */
    for(int i = 0; i < THREADS_N; ++i) {
        Thread t = new Thread(runner);
        t.start();
    }
    
    System.out.printf("Endstand Counter: %d %n", Storage.counter);
    /*
     * Wird nicht 10000 sein, da es möglich ist, dass zwei Threads gleichzeitig 
     * den Counter hochzählen und sich somit ungenauigkeiten bilden können.
     * Weiters kommt es auf die Anzahl der Kerne an.
     * Außerdem wird der Counter möglicherweise schon ausgegeben, bevor alle 
     * Threads fertig sind, da nach der for-Schleife keine Pause gemacht wird und
     * der MainThread, nachdem er alle Threads gestartet hat, einfach weiter läuft.
     */
    

}

}

class Storage {
    public static long counter;
}