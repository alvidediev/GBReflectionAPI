package ReflectionClasses;

import Annotations.AfterSuite;
import Annotations.BeforeSuite;
import Annotations.Test;

public class ClassForTesting {
    @BeforeSuite
    public void beforeSuiteMethod() {
        System.out.println("Before...Idk зачем нужны эти методы с этими аннотациями");
    }

    @Test(priority = 1)
    public void integer1(String a) {
        System.out.println("Method 1 " + a);
    }

    @Test(priority = 3)
    public void integer3(String a) {
        System.out.println("Method 3 " + a);
    }

    @Test(priority = 2)
    public void String(String a) {
        System.out.println("Method 2 " + a);
    }


    @AfterSuite
    public void afterSuiteMethod() {
        System.out.println("@Arter suite ... Idk зачем нужны эти методы с этими аннотациями");
    }
}
