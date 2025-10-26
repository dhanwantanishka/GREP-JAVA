public class StartOfLinePatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        return pattern.length() == 1 && pattern.equals("^");
    }

    @Override
    public PatternInt newPatternInt() {
        return new StartOfLinePattern();
    }
}