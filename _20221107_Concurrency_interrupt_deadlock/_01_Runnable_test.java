public class _01_Runnable_test {

    public static void main(String[] args) {
    
        Thread t1 = new Thread(new Runner());
    
        final int THREADS_N = 10;
    
        for (int i = 0; i < THREADS_N; i++) {
            t1.start();
        }
    
    }
}
    
class Runner implements Runnable {
    
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
    
}

/*
 * Beispiel für Test - Programm lässt sich kompillieren aber produziert einen 
 * Laufzeitfehler. Ein Thread darf nicht mehrmals gestartet werden.
 */