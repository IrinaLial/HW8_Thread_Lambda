import library.Library;
import library.Generator;
public class Main {
    public static void main ( String[] args ) {
        Library library= new Library();
        library.enterAll (Generator.generateUsers(20));
    }
}
