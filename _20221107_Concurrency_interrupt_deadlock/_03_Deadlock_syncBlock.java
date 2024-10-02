public class _03_Deadlock_syncBlock {

    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {
        Runnable run1 = () -> {

            while(true){
                synchronized(lock1){
                    synchronized(lock2){
                        System.out.printf("%s runs %n",Thread.currentThread().getName());

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
            }

        };

        Runnable run2 = () -> {

           
            while(true){
                synchronized(lock2){
                    synchronized(lock1){
                        System.out.printf("%s runs %n",Thread.currentThread().getName());

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                
            }
            

        };

        new Thread(run1, "run1").start();
        new Thread(run2, "run2").start();
        System.out.println("MainThread fertig");
    }
    
}

