## secure-strings
[![Build Status](https://travis-ci.org/dwp/secure-strings.svg?branch=master)](https://travis-ci.org/dwp/secure-strings) [![Known Vulnerabilities](https://snyk.io/test/github/dwp/secure-strings/badge.svg)](https://snyk.io/test/github/dwp/secure-strings)

This project is to make it easy to store passwords in an encrypted form using a cipher that is constructed inside the application and is different for every instance.

This was created to mitigate the **Heap_Inspection** vulnerability:-

`String variables are immutable - in other words, once a string variable is assigned, its value cannot be changed or removed. Thus, these strings may remain around in memory, possibly in multiple locations, for an indefinite period of time until the garbage collector happens to remove it. Sensitive data, such as passwords, will remain exposed in memory as plaintext with no control over their lifetime.`

#### Project inclusion

properties entry in pom

    <properties>
        <dwp.securestrings.version>x.x</dwp.securestrings.version>
    </properties>

dependency reference

    <dependency>
        <groupId>uk.gov.dwp.crypto</groupId>
        <artifactId>secure-strings</artifactId>
        <version>${dwp.securestrings.version}</version>
    </dependency>
#### Example of use

    import uk.gov.dwp.crypto.SecureStrings;
    import javax.crypto.SealedObject;

_Standard implementation_

    public class Pojo {
        private SecureStrings secureStrings = new SecureStrings();
        private SealedObject password = null;
    
        public String getPassword() {
            return secureStrings.revealString(password);
        }
    
        public void setPassword(String password) {
            this.password = secureStrings.sealString(password);
        }
    }

_Injected class_

    public class Pojo {
        private SecureStrings secureStrings = null;
        private SealedObject password = null;
    
        @Inject
        public Pojo(SecureStrings secureStrings) {
            this.secureStrings = secureStrings;
        }
    
        public String getPassword() {
            return secureStrings.revealString(password);
        }
    
        public void setPassword(String password) {
            this.password = secureStrings.sealString(password);
        }
    }
