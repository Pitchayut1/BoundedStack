import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * BoundedStack — ADT แทนสแต็กรถยนต์ (ระบบจอดรถยนต์แบบ LIFO) ที่มีความจุจำกัด
 *
 *   Abstraction Function:
 *   AF(cars ) = สแต็กรถยนต์ที่มีความจุสูงสุด MAX_CARS คัน
 *                        โดยเรียงจากคันล่างสุด cars.get(0) ถึงคันบนสุด cars.get(cars.size() - 1)
 *
 * Representation Invariant:
 *   1. cars != null
 *   2. cars.size() <= MAX_CARS
 *   3. ไม่มีสมาชิกใดใน cars เป็น null
 *
 * Safety from rep exposure:
 *   1. field cars ถูกประกาศเป็น final
 *   2. ไม่มี method ใดคืน reference ของ cars ออกไปโดยตรง
 */
public class BoundedStack<E> {

    private final List<E> cars; 
    
    public static final int MAX_CARS = 100;
    
    private void checkRep() {
        assert cars != null : "cars list must not be null";
        assert cars.size() <= MAX_CARS : "size exceeds MAX_CARS capacity";
        for (E car : cars) {
            assert car != null : "car must not be null";
        }
    }


   /**
    * สร้างสแต็กรถยนต์ว่าง โดยมีความจุสูงสุดเท่ากับ MAX_CARS (100 คัน)
    */
    
    public BoundedStack() {
    this.cars = new ArrayList<>();
    checkRep();
    }
}