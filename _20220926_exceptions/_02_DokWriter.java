
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class _02_DokWriter {

    public static void main(String[] args) {

        FileWorker.addNewVersion("Das schreiben wir rein");
    }


    
}

class FileWorker {

    private final static String FILE_NAME = "logfile";

    public static void addNewVersion(String data){

        int counter = 1;
        String filename = null;
        boolean running = true;

        while(running && counter <= Integer.MAX_VALUE){
            filename = String.format("%s_%03d",FILE_NAME,counter);
            try {
                Files.createFile(Paths.get(filename));
                System.out.printf("Datei wurde erstellt: %s%n",filename);
                running = false;
            } 

            catch(FileAlreadyExistsException e){
                System.out.printf("Datei %s existiert bereits%n",filename);
                running = true;
            }

            catch (IOException e) {
                e.printStackTrace();
                running = false;
                System.exit(1);
            }      

            PrintWriter out = null;

            try {
                out = new PrintWriter(filename);
                out.println(data);
            } catch (FileNotFoundException e) {
               System.err.printf("Konnte nicht in die Datei %s schreiben %n",filename);
            }
            finally{
                out.close();
            }

            counter ++;
            
        }

    }
}
