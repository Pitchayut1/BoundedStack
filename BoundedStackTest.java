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

   public static void main(String[] args) {
        // ตรวจสอบสถานะ Assertion ในการรัน
        boolean assertsEnabled = false;
        assert assertsEnabled = true;
        if (!assertsEnabled) {
            System.out.println("WARNING: Assertions disabled - re-run with: java -ea BoundedStackTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testConstructors();
       

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? ">>> ALL TESTS PASSED <<<" : ">>> SOME TESTS FAILED <<<");

        if (failed > 0) {
            System.exit(1);
        }
    }
// --- Partition 1: Constructors & Defensive Copy ---
    private static void testConstructors() {
        System.out.println("-- Test Constructors --");

        // Default Constructor
        BoundedStack<String> stack1 = new BoundedStack<>();
        check("Default constructor -> size 0", stack1.size() == 0);
        check("Default constructor -> capacity 100", stack1.capacity() == 100);

        // Constructor with Initial List
        List<String> initList = new ArrayList<>(Arrays.asList("Toyota", "Honda"));
        BoundedStack<String> stack2 = new BoundedStack<>(initList);
        check("List constructor -> size 2", stack2.size() == 2);
        check("List constructor -> top is Honda", "Honda".equals(stack2.peek()));

        // Defensive Copy Test (แก้ list ภายนอก ต้องไม่กระทบ stack)
        initList.add("BMW");
        check("Defensive copy -> modifications outside do not affect stack size", stack2.size() == 2);

        // Constructor with empty list -> ต้องสร้าง stack ว่างได้ปกติ ไม่ throw
        BoundedStack<String> stack3 = new BoundedStack<>(new ArrayList<>());
        check("List constructor with empty list -> size 0", stack3.size() == 0);

        // Constructor with list exactly at MAX_CARS -> ต้องสร้างได้ปกติ (boundary, ไม่ throw)
        List<String> fullList = new ArrayList<>();
        for (int i = 0; i < BoundedStack.MAX_CARS; i++) {
            fullList.add("Car#" + i);
        }
        BoundedStack<String> stack4 = new BoundedStack<>(fullList);
        check("List constructor with size == MAX_CARS -> size MAX_CARS", stack4.size() == BoundedStack.MAX_CARS);
    }
}
