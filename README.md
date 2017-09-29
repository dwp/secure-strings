This project is to make it easy to store passwords in encrypted form within configuration files in other projects.
CheckMarx has highlighted this as an issue and the use case and therefore code is similar across many projects.

Example of use:

public class MyConfiguration {

    private SecureStrings secureStrings = null;

    private SealedObject aPassword = null;

    private String aPath; 
    
    MyConfiguration(SecureStrings localSecureStrings){
        secureStrings = localSecureStrings;
    }
    
    public String getAPassword(){
        return secureStrings.revealString(aPassword, "aPassword");
    }
    public void setAPassword(String input){
        aPassword = secureStrings.sealString(input, "aPassword");
    }
    
    public String getAPath(){ return aPath; }
    public void setAPath(String input) { aPath = input; }
}



//************Within application****************//

    MyConfiguration config = new MyConfiguration(new SecureStrings);
    
    config.setAPassword("Some Password");
    config.setAPath("What_Ever_Path");