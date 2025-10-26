import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws Exception {
    if (args.length != 2 || !args[0].equals("-E")) {
      System.out.println("Usage: ./your_program.sh -E <pattern>");
      System.exit(1);
    }

    String pattern = args[1];
    Scanner scanner = new Scanner(System.in);
    String inputLine = scanner.nextLine();
    scanner.close();

    // You can use print statements as follows for debugging, they'll be visible
    // when running tests.
    System.err.println("Logs from your program will appear here!");

    System.err.println("MAIN inputLine=" + inputLine + " pattern=" + pattern);

    GrepResult r = GrepService.matchPattern(inputLine, pattern);
    if (r.isMatch()) {
      System.exit(0);
    } else {
      System.exit(1);
    }
  }
}