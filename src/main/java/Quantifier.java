public enum Quantifier {
    EXACTLY_ONE(Character.MAX_VALUE, false), // Default, for patterns without a quantifier
    ONE_OR_MORE('+', true), // The '+' quantifier
    ZERO_OR_MORE('*', true), // The '*' quantifier can be added here later
    ZERO_OR_ONE('?', false); // The '?' quantifier can be added here later

    public char c;
    public boolean multiple;

    Quantifier(char c, boolean multiple) {
        this.c = c;
        this.multiple = multiple;
    }

}