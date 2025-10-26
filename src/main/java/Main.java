import java.util.ArrayList;
import java.util.List;
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

        if (matchPattern(inputLine, pattern)) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }

    public static boolean matchPattern(String inputLine, String pattern) {
        List<String> captures = new ArrayList<>();
        if (pattern.startsWith("^")) {
            return matchHere(inputLine, pattern.substring(1), captures) != -1;
        }
        for (int i = 0; i <= inputLine.length(); i++) {
            captures.clear();
            if (matchHere(inputLine.substring(i), pattern, captures) != -1) {
                return true;
            }
        }
        return false;
    }

    // ▼▼▼ NEW HELPER FUNCTION ▼▼▼
    private static int findMatchingParen(String pattern, int start) {
        if (pattern.charAt(start) != '(') return -1;
        int depth = 1;
        for (int i = start + 1; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '(') {
                depth++;
            } else if (pattern.charAt(i) == ')') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1; // Not found
    }

    private static int getTokenLength(String pattern) {
        if (pattern.isEmpty()) return 0;
        char firstChar = pattern.charAt(0);
        if (firstChar == '\\') return 2;
        if (firstChar == '[') {
            int closing = pattern.indexOf(']');
            return (closing != -1) ? closing + 1 : 1;
        }
        if (firstChar == '(') {
            // ▼▼▼ FIXED ▼▼▼
            int closing = findMatchingParen(pattern, 0);
            return (closing != -1) ? closing + 1 : 1;
        }
        return 1;
    }

    private static int matchHere(String text, String pattern, List<String> captures) {
        if (pattern.isEmpty()) return 0;

        if (pattern.startsWith("\\")
                && pattern.length() >= 2
                && Character.isDigit(pattern.charAt(1))) {
            int groupIndex = Character.getNumericValue(pattern.charAt(1)) - 1;
            if (groupIndex < 0 || groupIndex >= captures.size()) return -1;

            String capturedText = captures.get(groupIndex);
            if (text.startsWith(capturedText)) {
                int restMatchLen =
                        matchHere(
                                text.substring(capturedText.length()),
                                pattern.substring(2),
                                captures);
                if (restMatchLen != -1) {
                    return capturedText.length() + restMatchLen;
                }
            }
            return -1;
        }

        if ("$".equals(pattern)) return text.isEmpty() ? 0 : -1;

        int tokenLen = getTokenLength(pattern);
        if (pattern.length() > tokenLen) {
            char quantifier = pattern.charAt(tokenLen);
            if (quantifier == '?') return matchOptional(text, pattern, captures);
            if (quantifier == '+') return matchPlus(text, pattern, captures);
        }

        if (pattern.startsWith("(")) return matchAlternation(text, pattern, captures);
        if (text.isEmpty()) return -1;

        if (firstCharMatches(text.charAt(0), pattern)) {
            int restMatchLen = matchHere(text.substring(1), pattern.substring(tokenLen), captures);
            if (restMatchLen != -1) {
                return 1 + restMatchLen;
            }
        }
        return -1;
    }

    private static int matchAlternation(String text, String pattern, List<String> captures) {
        // ▼▼▼ FIXED ▼▼▼
        int closingParenIndex = findMatchingParen(pattern, 0);
        if (closingParenIndex == -1) {
            if (!text.isEmpty() && firstCharMatches(text.charAt(0), pattern)) {
                int restMatchLen = matchHere(text.substring(1), pattern.substring(1), captures);
                return (restMatchLen != -1) ? 1 + restMatchLen : -1;
            }
            return -1;
        }

        String content = pattern.substring(1, closingParenIndex);
        String restOfPattern = pattern.substring(closingParenIndex + 1);
        String[] alternatives = content.split("\\|");

        for (String alternative : alternatives) {
            List<String> capturesForAlternative = new ArrayList<>(captures);
            int groupMatchLen = matchHere(text, alternative, capturesForAlternative);

            if (groupMatchLen != -1) {
                String capturedText = text.substring(0, groupMatchLen);
                List<String> capturesForRest = new ArrayList<>(capturesForAlternative);
                capturesForRest.add(capturedText);
                int restMatchLen =
                        matchHere(text.substring(groupMatchLen), restOfPattern, capturesForRest);

                if (restMatchLen != -1) {
                    captures.clear();
                    captures.addAll(capturesForRest);
                    return groupMatchLen + restMatchLen;
                }
            }
        }
        return -1;
    }

    static int matchOptional(String text, String pattern, List<String> captures) {
        int tokenLen = getTokenLength(pattern);
        String restOfPattern = pattern.substring(tokenLen + 1);

        if (!text.isEmpty() && firstCharMatches(text.charAt(0), pattern)) {
            int len = matchHere(text.substring(1), restOfPattern, captures);
            if (len != -1) {
                return 1 + len;
            }
        }
        return matchHere(text, restOfPattern, captures);
    }

    private static int matchPlus(String text, String pattern, List<String> captures) {
        int tokenLen = getTokenLength(pattern);
        String token = pattern.substring(0, tokenLen);
        String restOfPattern = pattern.substring(tokenLen + 1);

        if (text.isEmpty() || !firstCharMatches(text.charAt(0), token)) {
            return -1;
        }

        int i = 1;
        while (i < text.length() && firstCharMatches(text.charAt(i), token)) {
            i++;
        }

        while (i >= 1) {
            int restMatchLen = matchHere(text.substring(i), restOfPattern, captures);
            if (restMatchLen != -1) {
                return i + restMatchLen;
            }
            i--;
        }
        return -1;
    }

    private static boolean firstCharMatches(char c, String pattern) {
        if (pattern.isEmpty()) return false;
        char patternChar = pattern.charAt(0);

        if (patternChar == '.') return true;
        if (patternChar == '[') {
            int closingBracket = pattern.indexOf(']');
            if (closingBracket == -1) return false;
            String content = pattern.substring(1, closingBracket);
            boolean negated = content.startsWith("^");
            if (negated) content = content.substring(1);
            boolean isMatch = content.indexOf(c) != -1;
            return negated != isMatch;
        }
        if (patternChar == '\\') {
            if (pattern.length() < 2) return false;
            char escapeType = pattern.charAt(1);
            if (escapeType == 'd') return Character.isDigit(c);
            if (escapeType == 'w') return Character.isLetterOrDigit(c) || c == '_';
            return c == escapeType;
        }
        return c == patternChar;
    }
}