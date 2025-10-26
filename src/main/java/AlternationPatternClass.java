public class AlternationPatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        // System.err.println("AlternationPatternClass pattern=" + pattern + " i " +
        // pattern.indexOf('(', 1));
        if (-1 == pattern.indexOf('(', 1)) {
            return pattern.startsWith("(") && pattern.endsWith(")") && pattern.contains("|");
        } else {
            return false;
        }
    }

    @Override
    public PatternInt newPatternInt() {
        return new AlternationPattern();
    }
}