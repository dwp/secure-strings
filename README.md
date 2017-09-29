## SecureStrings
This project is to make it easy to store passwords in an encrypted form using a cipher that is constructed inside the application and is different for every instance.

This was created to mitigate the Checkmarx vulnerability **Heap_Inspection**:-

`String variables are immutable - in other words, once a string variable is assigned, its value cannot be changed or removed. Thus, these strings may remain around in memory, possibly in multiple locations, for an indefinite period of time until the garbage collector happens to remove it. Sensitive data, such as passwords, will remain exposed in memory as plaintext with no control over their lifetime.`

####Project inclusion

properties entry in pom

    <properties>
        <dwp.securestrings.version>x.x</dwp.securestrings.version>
    </properties>
    
internal Artifactory repository reference is required (plugin reference required for OWASP checks)

    <repositories>
        <repository>
            <id>dwp internal</id>
            <url>###REPOSITORY_URL###</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>dwp internal</id>
            <url>###REPOSITORY_URL###</url>
        </pluginRepository>
    </pluginRepositories>

dependency reference

    <dependency>
        <groupId>gov.dwp.software-engineering</groupId>
        <artifactId>securestrings</artifactId>
        <version>${dwp.securestrings.version}</version>
    </dependency>
####Example of use

    import gov.dwp.utilities.crypto.SecureStrings;
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
