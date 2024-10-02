import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _02_DeadLock_lockobj {

    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();
    /*
     * Wird meist verwendet, da ein Thread auch mehrmals das Lock-Objekt holen kann, 
     * um in den geschützten Bereich zu gelangen. Dies funktioniert nur mit einem 
     * ReentrantLock. Dies wird dazu verwendet, dass ein Thread, der den Locker gerade nutzt,
     * wieder mit diesem Locker in den geschützten Bereich kann, ohne sich selbst auszusperren.
     */

    public static void main(String[] args) {

        Runnable run1 = () -> {

            while(true){
                lock1.lock();
                lock2.lock();

                System.out.printf("%s runs%n",Thread.currentThread().getName());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*
                 * Mit dem sleep wird die Wahrscheinlichkeit eines DeadLock verringert,
                 * da sich der Thread länger mit sout und sleep beschäftigt, als
                 * mit dem Locken der Aufgaben. Ein DeadLock kann aber dennoch passieren.
                 */

                lock1.unlock();
                lock2.unlock();
            }

        };

        Runnable run2 = () -> {

            while(true){
                lock2.lock();
                lock1.lock();                

                System.out.printf("%s runs%n",Thread.currentThread().getName());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock1.unlock();
                lock2.unlock();
            }

        };

        new Thread(run1, "run1").start();
        new Thread(run2, "run2").start();
        
    }
    
}


