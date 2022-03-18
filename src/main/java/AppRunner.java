import ReflectionClasses.ClassForTesting;
import ReflectionClasses.ReflectionTestClass;

public class AppRunner {
    public static void main(String[] args) {
        ClassForTesting classForTest = new ClassForTesting();
        ReflectionTestClass.start(classForTest.getClass());
    }
}
