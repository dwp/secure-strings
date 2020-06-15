package uk.gov.dwp.crypto;

public class TestClassForSerialisation {
  private int testInteger;
  private String testString;
  private boolean testBoolean;

  public TestClassForSerialisation() {
    setTestBoolean(false);
    setTestInteger(0);
    setTestString("");
  }

  int getTestInteger() {
    return testInteger;
  }

  void setTestInteger(int testInteger) {
    this.testInteger = testInteger;
  }

  String getTestString() {
    return testString;
  }

  void setTestString(String testString) {
    this.testString = testString;
  }

  boolean isTestBoolean() {
    return testBoolean;
  }

  void setTestBoolean(boolean testBoolean) {
    this.testBoolean = testBoolean;
  }
}
