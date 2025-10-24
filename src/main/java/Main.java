import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("-E")) {
            System.out.println("Usage: ./your_program.sh -E <pattern>");
            System.exit(1);
        }

        String pattern = args[1];
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        scanner.close();

        if (matchPattern(inputLine, pattern)) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }

    public static boolean matchPattern(String inputLine, String pattern) {
        if (pattern.length() == 1) {
            return inputLine.contains(pattern);
        } else if (pattern.equals("\\d")) {
            return inputLine.chars().anyMatch(Character::isDigit);
        } else if (pattern.equals("\\w")) {
            return inputLine.chars().anyMatch(ch -> Character.isLetterOrDigit(ch) || ch == '_');
        } else if (pattern.charAt(0) == '[' && pattern.charAt(pattern.length() - 1) == ']') {
            String charClass = pattern.substring(1, pattern.length() - 1);
            return inputLine.chars().anyMatch(ch -> charClass.indexOf(ch) != -1);
        } else {
            throw new RuntimeException("Unhandled pattern: " + pattern);
        }
    }
}
