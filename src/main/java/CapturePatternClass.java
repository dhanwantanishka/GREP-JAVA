public class CapturePatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        // test si pattern commence par \ suivi d'un chiffre
        return pattern.length() == 2 && pattern.startsWith("\\") && Character.isDigit(pattern.charAt(1));
    }

    @Override
    public PatternInt newPatternInt() {
        return new CapturePattern();
    }
}