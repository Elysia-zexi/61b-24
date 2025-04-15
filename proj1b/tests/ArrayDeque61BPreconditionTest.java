import com.google.common.truth.Truth;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BPreconditionTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    // 测试1：验证初始指针位置和空队列
    @Test
    public void testInitialState() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // 根据图片设计，初始 nextFirst=4, nextLast=5（数组长度8）
        Truth.assertThat(deque.isEmpty()).isTrue();
        Truth.assertThat(deque.size()).isEqualTo(0);
    }
    // 测试2：添加元素到队首（验证指针左移和循环）
    @Test
    public void testAddFirstPointerMovement() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        // 初始指针：nextFirst=4（索引4左侧）
        deque.addFirst("A"); // 插入到索引4，nextFirst=3
        deque.addFirst("B"); // 插入到索引3，nextFirst=2
        deque.addFirst("C"); // 插入到索引2，nextFirst=1
        deque.addFirst("D"); // 插入到索引1，nextFirst=0
        deque.addFirst("E"); // 插入到索引0，nextFirst=7（循环到末尾）

        // 验证元素顺序（队首是最后插入的"E"）
        Truth.assertThat(deque.get(0)).isEqualTo("E"); // 索引0
        Truth.assertThat(deque.get(4)).isEqualTo("A"); // 原始位置索引4
    }

    // 测试3：添加元素到队尾（验证指针右移和循环）
    @Test
    public void testAddLastPointerMovement() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // 初始指针：nextLast=5（索引5右侧）
        deque.addLast(10); // 插入到索引5，nextLast=6
        deque.addLast(20); // 插入到索引6，nextLast=7
        deque.addLast(30); // 插入到索引7，nextLast=0（循环到开头）
        deque.addLast(40); // 插入到索引0，nextLast=1

        // 验证元素顺序（队尾是最后插入的40）
        Truth.assertThat(deque.get(3)).isEqualTo(40); // 索引3对应循环后的位置0
    }

    // 测试2：空队列移除返回 null
    @Test
    public void testRemoveFromEmptyDeque() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        Truth.assertThat(deque.removeFirst()).isNull();
        Truth.assertThat(deque.removeLast()).isNull();
    }
    // 测试5：混合操作（addFirst + addLast）
    @Test
    public void testMixedOperations() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(3);  // 插入到索引4 → nextFirst=3
        deque.addLast(5);   // 插入到索引5 → nextLast=6
        deque.addFirst(2);  // 插入到索引3 → nextFirst=2
        deque.addLast(7);   // 插入到索引6 → nextLast=7
        deque.addFirst(1);  // 插入到索引2 → nextFirst=1
        deque.addLast(9);   // 插入到索引7 → nextLast=0（循环到开头）

        // 预期顺序：1,2,3,5,7,9（实际需通过get方法验证）
        Truth.assertThat(deque.get(0)).isEqualTo(1);
        Truth.assertThat(deque.get(2)).isEqualTo(3);
        Truth.assertThat(deque.get(5)).isEqualTo(9);
    }

    // 测试6：指针循环到数组边界
    @Test
    public void testCircularBoundary() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        // 填充数组到指针循环
        for (int i = 0; i < 7; i++) {
            deque.addFirst("X"); // nextFirst 从4→3→2→1→0→7→6
        }
        // 此时 nextFirst=6，继续添加会循环到索引5
        deque.addFirst("Y"); // 插入到索引6 → nextFirst=5

        // 验证指针正确循环
        Truth.assertThat(deque.get(0)).isEqualTo("Y");
        Truth.assertThat(deque.size()).isEqualTo(8); // 已填满初始容量
    }


    // 测试3：指针循环边界处理（移除导致指针循环）
    @Test
    public void testCircularRemoval() {
        ArrayDeque61B<Character> deque = new ArrayDeque61B<>();
        // 填充到指针边界（nextFirst=0, nextLast=7）
        for (int i = 0; i < 8; i++) {
            deque.addLast((char) ('A' + i));
        }
        // 移除队首元素（指针从0循环到7）
        Truth.assertThat(deque.removeFirst()).isEqualTo('A');
        // 验证指针正确循环后添加新元素
        deque.addFirst('Z');
        Truth.assertThat(deque.get(0)).isEqualTo('Z'); // 插入到索引7
    }


    // 测试4：触发缩容（容量≥16且利用率<25%）
    @Test
    public void testShrinkCapacity() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // 填充到容量16（添加16个元素）
        for (int i = 0; i < 16; i++) {
            deque.addLast(i);
        }
        // 移除13个元素（剩余3个，3/16=18.75% <25%）
        for (int i = 0; i < 13; i++) {
            deque.removeFirst();
        }
        // 验证缩容到8（16/2=8）
        Truth.assertThat(deque.size()).isEqualTo(3);
        // 假设有方法获取容量：Truth.assertThat(deque.capacity()).isEqualTo(8);
        Truth.assertThat(deque.toList()).containsExactly(13, 14, 15).inOrder();
    }

}
