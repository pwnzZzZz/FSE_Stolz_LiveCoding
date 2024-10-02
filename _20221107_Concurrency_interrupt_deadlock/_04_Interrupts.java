public class _04_Interrupts {

    public static void main(String[] args) {
        
        Runnable runner = () -> {

            while(!Thread.currentThread().isInterrupted()){
                System.out.println("I am running!");        
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Shutting down!");
                    Thread.currentThread().interrupt();
                    /*
                     * Würde man den Thread nicht nochmals unterbrechen, würde das Programm 
                     * weiterlaufen.
                     * In Java ist es so, wenn eine InterruptedException auftritt,
                     * wird der Thread zwar unterbrochen und wir landen im catch-Block, sobald
                     * dies aber der Fall ist, wird das Interrupted Signal zurückgesetzt.
                     * In unserem Fall bedeutet dies, dass die Bedingung der while-Schleife
                     * nach wie vor true ist und die Schleife, auch nachdem sie "Shutting down!"
                     * ausgegeben hat, noch weiterläuft.
                     * t1.interrupt() wird also korrekt ausgeführt - der Thread wird unterbrochen
                     * und die Ausführung landet im catch-Block - jedoch wird zeitgleich
                     * das Interrupted Signal zurückgesetzt und die Schleifenbedingung bleibt true.
                     * Mit Thread.currentThread().interrupt(); senden wir nun erneuert das Interrupted
                     * Signal und beim nächsten Prüfen der Schleifenbedingung ist diese nun false, 
                     * da nach senden des Thread.currentThread().interrupt() keine Exception mehr
                     * geworfen wurde.
                     */
                }        
            }

        };

        Thread t1 = new Thread(runner);
        t1.start();
        /*
         * Mit einem Interrupt kann man kontrolliert einen Thread beenden.
         * Ein Interrupt bedeutet aber nicht zwangsläufig, dass er auch 
         * wirklich beendet wird. Er wird zunächst nur unterbrochen - 
         * es liegt dann am Programmierer, wie er mit der Unterbrechung umgeht.
         */
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*
         * Arbeitet man in einer Umgebung mit Betriebssystem, so möchte man den aktuellen
         * Thread immer mit sleep() unterbrechen. In dieser Zeit ist nämlich der Prozessor
         * dann für andere Threads verfügbar. Rein theoretisch könnte man das Programm auch
         * mit einer Schleife unterbrechen, die für einen bestimmten Zeitraum irgendeinen
         * Nonsens berechnet/bearbeitet - dies würde aber sinnlos CPU Ressourcen verschwenden.
         * Arbeitet man z.B. mit einem Mikrocontroller (kein Betriebssystem), so wird das 
         * Programm mit einer Schleife unterbrochen.
         */

        t1.interrupt();

        /*
         * Ist interrupt() mit einem Programm-/Threadabbruch gleichzusetzen?
         * Nein - er ist dafür gedacht, das Interupted Signal zu nutzen, um dann
         * den Thread zu stoppen.
         * Ein Interrupt kann aber auch zum "aufwecken" verwendet werden. Z.B. schläft ein Thread 
         * solange, bis dieser interrupted wird - erst dann fängt dieser an zu arbeiten.
         */

    }
    
}

