import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Test Runner สำหรับ BoundedStack
 * ออกแบบมาเพื่อทดสอบ BoundedStack.java ตาม Specification ที่กำหนด
 */
public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    /** Helper สำหรับตรวจสอบเงื่อนไขและสะสมคะแนน PASS/FAIL */
    private static void check(String testName, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + testName);
        } else {
            failed++;
            System.out.println("[FAIL] " + testName);
        }
    }
}