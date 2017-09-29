package gov.dwp.utilities.crypto;

public class TestClassForSerialisation {
    private int testInteger;
    private String testString;
    private boolean testBoolean;

    public TestClassForSerialisation(){
        setTestBoolean(false);
        setTestInteger(0);
        setTestString("");
    }

    public int getTestInteger() {
        return testInteger;
    }

    public void setTestInteger(int testInteger) {
        this.testInteger = testInteger;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public boolean isTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(boolean testBoolean) {
        this.testBoolean = testBoolean;
    }
}
