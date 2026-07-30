# Lab — สร้าง ADT: BoundedStack

**หัวข้อ:** Abstract Data Types (ADTs), Abstraction Function, Representation Invariant
**ADT ที่พัฒนา:** `BoundedStack<E>` — สแต็กรถยนต์ (ระบบจอดรถยนต์แบบ LIFO) ที่มีความจุจำกัด

---

## เป้าหมาย

สร้าง ADT ชื่อ `BoundedStack<E>` ที่แทนสแต็กรถยนต์แบบ LIFO ซึ่งมีความจุจำกัด โดยต้อง

1. ระบุการดำเนินการให้ครบทั้ง 4 บทบาท (Creators / Producers / Observers / Mutators)
2. เขียน Abstraction Function (AF) และ Representation Invariant (RI)
3. เขียนเมธอด `checkRep()` ด้วย assertions
4. ป้องกัน Representation Exposure ทั้งขาเข้าและขาออก
5. ทดสอบให้ครอบคลุม รวมถึงเทสต์ที่พิสูจน์ว่าไม่มี rep exposure

---

## ไฟล์ในโฟลเดอร์นี้

| ไฟล์ | คำอธิบาย |
|---|---|
| `BoundedStack.java` | ไฟล์ ADT หลัก (implementation ครบทุกเมธอด) |
| `BoundedStackTest.java` | ชุดทดสอบสำหรับตรวจสอบความถูกต้องของทุกเมธอด |
| `README.md` | ไฟล์นี้ |

เมื่อคอมไพล์และรันด้วยแฟล็ก `-ea` (ดูหัวข้อ "การรันเทสต์") จะได้ผลลัพธ์แบบนี้

```
=== Summary ===
Passed: 30
Failed: 0
Total : 30
>>> ALL TESTS PASSED <<<
```

> **หมายเหตุเรื่องสไตล์โค้ด:** คอมเมนต์และเอกสารประกอบเขียนเป็นภาษาไทย
> ส่วนข้อความที่โปรแกรมพิมพ์ออกทาง console (`[PASS]` / `[FAIL]`) เป็นภาษาอังกฤษ
> เพื่อเลี่ยงปัญหา console บน Windows แสดงภาษาไทยเพี้ยน

---

## สเปคของ ADT

### ค่านามธรรม (A)

สแต็กรถยนต์ที่มีความจุสูงสุด `MAX_CARS` (100) คัน โดยเรียงจากคันล่างสุดถึงคันบนสุด — คันที่เข้าจอดล่าสุดคือคันที่จะออกก่อน (LIFO)

### Representation (R)

```java
private final List<E> cars;
public static final int MAX_CARS = 100;
```

### กฎที่สแต็กต้องรักษาไว้เสมอ

- `cars` ต้องมีอยู่จริง (ไม่เป็น `null`)
- จำนวนรถใน `cars` ต้องไม่เกิน `MAX_CARS`
- ไม่มีรถคันใดใน `cars` เป็น `null`

---

## รายละเอียดการทำงาน

### งานที่ 1 — Abstraction Function, Representation Invariant และ Safety from rep exposure

เขียนเป็นคอมเมนต์ไว้เหนือ `private final List<E> cars;`

**AF** บอกว่า `cars` **หมายถึงอะไร** ไม่ใช่บอกว่ามันเป็นตัวแปรชนิดอะไร

```
AF(cars) = สแต็กรถยนต์ที่มีความจุสูงสุด MAX_CARS คัน
           โดยเรียงจากคันล่างสุด cars.get(0) ถึงคันบนสุด cars.get(cars.size() - 1)
```

**RI** ครบทั้ง 3 ข้อตามหัวข้อ "กฎที่สแต็กต้องรักษาไว้เสมอ" ด้านบน

**Safety from rep exposure**

- ฟิลด์ `cars` ถูกประกาศเป็น `final`
- ไม่มีเมธอดใดคืนค่า reference ของ `cars` ออกไปโดยตรง

---

### งานที่ 2 — `checkRep()`

แปลง RI ทุกข้อให้เป็น `assert` พร้อมข้อความอธิบาย เรียกใช้หลังทุกการดำเนินการที่แก้ไข rep (constructor, `push`, `pop`)

```java
private void checkRep() {
    assert cars != null : "cars list must not be null";
    assert cars.size() <= MAX_CARS : "size exceeds MAX_CARS capacity";
    for (E car : cars) {
        assert car != null : "car must not be null";
    }
}
```

---

### งานที่ 3 — Creators

| Constructor | พฤติกรรม |
|---|---|
| `BoundedStack()` | สร้างสแต็กว่าง ความจุสูงสุด `MAX_CARS` |
| `BoundedStack(List<E> initial)` | สร้างจากรายการเริ่มต้น (index 0 = คันล่างสุด) ทำ defensive copy ให้ ไม่เก็บ reference ของ `initial` ตรง ๆ |

`BoundedStack(List<E> initial)` ต้อง `throw new IllegalArgumentException` เมื่อ:

- `initial == null`
- `initial.size() > MAX_CARS`
- มีสมาชิกตัวใดใน `initial` เป็น `null`

> **ทำไมต้อง throw ไม่ใช่ assert?**
> `initial` มาจาก client ถ้าเขาส่งค่าผิดคือ**ความผิดของ client** ต้องใช้ exception
> ส่วน `assert` ใช้ตรวจ rep ของเราเอง ซึ่งถ้าพังแปลว่า**เราเขียนบั๊ก**
> อีกเหตุผลคือ `assert` ถูกปิดอัตโนมัติเมื่อไม่ใส่ `-ea` ตอนรันจริงจะไม่มีการตรวจเลย

---

### งานที่ 4 — Mutators

| เมธอด | พฤติกรรม |
|---|---|
| `push(E car)` | เพิ่มรถที่ยอดบนสุด · คืน `true` สำเร็จ, `false` ถ้าสแต็กเต็ม · `throw IllegalArgumentException` ถ้า `car` เป็น `null` |
| `pop()` | ดึงรถคันบนสุดออก (LIFO) · คืนรถที่ถูกดึง · `throw NoSuchElementException` ถ้าสแต็กว่าง |

เรียก `checkRep()` ก่อน `return` ทุกเส้นทางที่แก้ไข rep

---

### งานที่ 5 — Observers

| เมธอด | พฤติกรรม |
|---|---|
| `size()` | จำนวนรถปัจจุบันในสแต็ก |
| `capacity()` | ความจุสูงสุด (`MAX_CARS`) |
| `contains(E car)` | มีรถคันนี้ในสแต็กหรือไม่ · คืน `false` ถ้า `car` เป็น `null` (ไม่ throw) |
| `peek()` | ดูรถคันบนสุดโดยไม่นำออก · `throw NoSuchElementException` ถ้าสแต็กว่าง |

Observer ทุกตัวไม่แก้ rep จึงไม่ต้องเรียก `checkRep()`

---

### งานที่ 6 — Producer

`reversed()` คืนสแต็ก**ตัวใหม่**ที่มีรถชุดเดียวกันแต่กลับลำดับ

- **ไม่แก้สแต็กเดิม** (`this`) — หัวใจของคำว่า producer
- คัดลอก `cars` ออกมาเป็น list ใหม่ก่อน แล้วค่อย `Collections.reverse(...)` บน list ที่คัดลอกมา
- สร้างสแต็กใหม่ผ่าน constructor ที่รับ `List<E>` (ได้ defensive copy และการตรวจ RI ให้ฟรีอีกชั้น)

---

## การรันเทสต์

```bash
javac BoundedStack.java BoundedStackTest.java
java -ea BoundedStackTest
```

> **หมายเหตุ:** ต้องรันด้วยแฟล็ก `-ea` (enable assertions) เพื่อให้ `checkRep()` ทำงานตรวจสอบ RI ระหว่างการทดสอบ มิฉะนั้นโปรแกรมจะแจ้งเตือนว่า assertion ถูกปิดอยู่ และเทสต์อาจ "ผ่าน" ทั้งที่โค้ดพัง

`BoundedStackTest.java` ครอบคลุม 6 กลุ่มการทดสอบ:

1. **Constructors & Defensive Copy** — default constructor, constructor จาก list, การป้องกันการแก้ไขจากภายนอก, list ว่าง, list ขนาดเท่ากับ `MAX_CARS` พอดี
2. **Push & LIFO Order** — ลำดับ LIFO หลัง push หลายครั้ง
3. **Pop & Peek** — ค่าที่ถูกดึงออกและขนาดที่เปลี่ยนแปลง
4. **Contains & Observers** — `contains`, `contains(null)`, และว่า observer methods ไม่มี side-effect
5. **Reversed Method** — สแต็กเดิมไม่ถูกแก้ไข, ลำดับการ pop ของสแต็กที่กลับด้าน, reversed ของสแต็กว่าง, mutate สแต็กใหม่ไม่กระทบสแต็กเดิม
6. **Exceptions & Boundary Cases** — `null` argument, สแต็กว่าง, สแต็กเต็ม (`push` คืน `false`), list เกินความจุ

---

