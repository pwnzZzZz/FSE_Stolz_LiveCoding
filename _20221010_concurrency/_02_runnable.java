
public class _02_runnable {

    // TODO Lambda Expressions wiederholen
    public static void main(String[] args) {
        
        int tCounter = 1;
        Thread t1 = new Thread(new Job(20),"Thread 1");
        //Achtung! run() würde keinen eigenen Thread starten
        t1.start();
        // Ein Thread darf nur einmal gestartet werden, sonst IllegalThreadStateException
        // t1.start();
        Thread t2 = new Thread(new Job(20),"Thread 2");
        t2.start();

        // TODO füge eine Schleife hinzu, die so viele Threads startet, wie angegeben

        for(int tAnzahl = 20; tCounter <= tAnzahl; tCounter++){
            String tName = "thread " + tCounter;
            (new Thread(new Job(20),tName)).start();
        }

    }
}

class Job implements Runnable {

    int runs;

    public Job(int runs){
        this.runs = runs;
    }

    @Override
    public void run() {

        for (int i = 0; i < runs; i++) {

            String threadName = Thread.currentThread().getName();
            System.out.printf("%s - %d %n",threadName,i);
           
        }
                
    }

    
}