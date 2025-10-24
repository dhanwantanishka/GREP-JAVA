import java.util.Scanner;

public class Main {
  public static void main(String[] args){
    if (args.length != 2 || !args[0].equals("-E")) {
      System.out.println("Usage: ./your_program.sh -E <pattern>");
      System.exit(1);
    }

    String pattern = args[1];  
    Scanner scanner = new Scanner(System.in);
    String inputLine = scanner.nextLine();
    
    if (matchPattern(inputLine, pattern)) {
        System.exit(0);
    } else {
        System.exit(1);
    }
  }

  public static boolean matchPattern(String inputLine, String pattern) {
    if(pattern.equals("\\d")){
      return hasDigit(inputLine);
    }else if(pattern.equals("\\w")){
      return hasWord(inputLine);
    }else if(pattern.startsWith("[^") && pattern.endsWith("]")){
      return isNegativeCharacterGroup(inputLine, pattern.substring(1,pattern.length()-1));
    }else if(pattern.startsWith("[") && pattern.endsWith("]")){
      return isPositiveCharacterGroup(inputLine, pattern.substring(1,pattern.length()-1));
    }else if(pattern.startsWith("^")){
      return matchStart(inputLine, pattern.substring(1));
    }

    if (pattern.length() > 0) {
      System.out.println("matching");
      for(int i=0; i<inputLine.length(); ++i){
        if(matchStart(inputLine.substring(i), pattern)){
          return true;
        }
      }
      return false;
      // return inputLine.contains(pattern);
    } else {
      throw new RuntimeException("Unhandled pattern: " + pattern);
    }
  }

  public static boolean matchStart(String inputLine, String pattern){
    if(pattern.length() == 0) return true;
    if(pattern.startsWith("\\d")){
      return inputLine.length()>0 && isDigit(inputLine.charAt(0)) && matchStart(inputLine.substring(1), pattern.substring(2));
    } else if(pattern.startsWith("\\w")){
      return inputLine.length()>0 && isWordCharacter(inputLine.charAt(0)) && matchStart(inputLine.substring(1), pattern.substring(2));
    } else if(pattern.equals("$")){
      return inputLine.length()==0;
    }
    else{
      // System.out.println("pattern : " + pattern);
      // System.out.println("inputLine : " + inputLine);
      return inputLine.length()>0 && pattern.charAt(0) == inputLine.charAt(0) && matchStart(inputLine.substring(1), pattern.substring(1));
    }
  }


  private static boolean hasDigit(String inputLine){
    for(int i=0; i<10; ++i){
      if(inputLine.contains(""+i)) return true;
    }
    return false;
  }

  private static boolean isPositiveCharacterGroup(String inputLine, String chars){
    for(char c : chars.toCharArray()){
      if(inputLine.contains(""+c)) return true;
    }
    return false;
  }

  private static boolean isNegativeCharacterGroup(String inputLine, String chars){
    for(char c: inputLine.toCharArray()){
      if(!chars.contains(""+c)) return true;
    }
    return false;
  }

  private static boolean isLowerAlphabet(char c){
    return c-'a'>=0 && c-'a'<26;
  }

  private static boolean isUpperAlphabet(char c){
    return c-'A'>=0 && c-'A'<26;
  }

  private static boolean isDigit(char c){
    return c-'0'>=0 && c-'0'<=9;
  }

  private static boolean hasWord(String inputLine){
    for(char c: inputLine.toCharArray()){
      if(isWordCharacter(c)) return true;
    }
    return false;
  }

  private static boolean isWordCharacter(char c){
    return (isLowerAlphabet(c) || isUpperAlphabet(c) || isDigit(c) || c == '_');
  }
}