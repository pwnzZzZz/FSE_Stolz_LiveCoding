
public class _01_String_format {

    public static void main(String[] args) {


        // Erklärung von String.format()

        String name = "Stefan";
        String name2 = "Seppele";
        System.out.print("Hello " + name + " wie geht es und dir " + name2 + "?"+System.lineSeparator());        
        System.out.println(String.format("Hello %s wie geht es und dir %s?%n",name,name2));
        System.out.printf("Hello %s wie geht es und dir %s?%n",name,name2);
        System.out.printf("Hello %s wie geht es und dir %s?%n",name,name2);
        
    }


    
}