public interface PatternInt {

    public PatternResult matchPattern(String input);

    public PatternResult matchPattern(String input, String pattern);

    void setQuantifier(Quantifier quantifier);

    Quantifier getQuantifier();

    public void setPatternString(String currentRawPattern);

    public String getPatternString();

    public int countMatches();

    public void decrementCountMatches();
    
    public void setCapturedText(String capturedText);
    
    public String getCapturedText();

}