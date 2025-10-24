import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
  public static void main(String[] args){
    if (args.length != 2 || !args[0].equals("-E")) {
      System.out.println("Usage: ./your_program.sh -E <pattern>");
      System.exit(1);
    }

    String pattern = args[1];  
    Scanner scanner = new Scanner(System.in);
    String inputLine = scanner.nextLine();

    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");

     //Uncomment this block to pass the first stage

     if (matchPattern(inputLine, pattern)) {
         System.exit(0);
     } else {
         System.exit(1);
     }
  }

  public static boolean matchPattern(String inputLine, String pattern) {
    if (pattern.length() == 1) {
      return inputLine.contains(pattern);
    } else if(pattern.equals("\\d")){
      for(char ch: inputLine.toCharArray()) if(Character.isDigit(ch)) return true;
      return false;
    } else if(pattern.equals("\\w")){
      for(char ch: inputLine.toCharArray()) if(Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_') return true;
      return false;
    } else if(pattern.startsWith("[") && pattern.endsWith("]")){
      Set<Character> set = new HashSet<>();
      char[] chars = pattern.substring(1, pattern.length()-1).toCharArray();
      for(char ch: chars) set.add(ch);
      if(chars[0] == '^') {
        for(char ch: inputLine.toCharArray()) if(!set.contains(ch)) return true;
        return false;
      }
      else {
        for(char ch: inputLine.toCharArray()) if(set.contains(ch)) return true;
        return false;
      }
    } else if(!pattern.isEmpty()){
        return match(inputLine, pattern);
    } else {
      throw new RuntimeException("Unhandled pattern: " + pattern);
    }
  }

  public static boolean match(String str, String pattern) {
    int n = str.length(), i = 0;
    if(pattern.charAt(0) == '^') return matchStr(str, pattern.substring(1), 0, 0);
    while(i < n){
      if(matchStr(str.substring(i), pattern, 0, 0)) return true;
      i++;
    }
    return false;
  }

  private static boolean matchStr(String str, String pattern, int s, int p) {
    if(p >= pattern.length()) return true;
    if(s >= str.length()) return false;
    char cur_str_ch = str.charAt(s), cur_pattern_ch = pattern.charAt(p);
    if(cur_str_ch == cur_pattern_ch) return matchStr(str, pattern, s+1, p+1);
    else if(cur_pattern_ch == '\\'){
      char next_patter_char = pattern.charAt(p+1);
      if(next_patter_char == 'd' && Character.isDigit(cur_str_ch)) return matchStr(str, pattern, s+1, p+2);
      else if(next_patter_char == 'w' && Character.isAlphabetic(cur_str_ch)) return matchStr(str, pattern, s+1, p+2);
      else return false;
    }
    return false;
  }
}