import com.google.common.truth.Truth;
import deque.ArrayDeque61B;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import deque.Deque61B;
public class ArrayDeque61BTest {
    // ---------------------- equals() 测试 ----------------------
    @Test
    public void testDifferentSizesNotEqual() {
        Deque61B<String> deque1 = new ArrayDeque61B<>();
        Deque61B<String> deque2 = new ArrayDeque61B<>();
        deque1.addLast("A");
        deque1.addLast("B");
        deque2.addLast("A");
        assertThat(deque1).isNotEqualTo(deque2);
    }

    @Test
    public void testSelfEquality() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque).isEqualTo(deque);
    }

    @Test
    public void testMixedNullElementsEquality() {
        Deque61B<Object> deque1 = new ArrayDeque61B<>();
        Deque61B<Object> deque2 = new ArrayDeque61B<>();
        deque1.addLast("A");
        deque1.addLast(null);
        deque2.addLast("A");
        deque2.addLast(null);
        assertThat(deque1).isEqualTo(deque2);
    }

    // ---------------------- toString() 测试 ----------------------
    @Test
    public void testCircularBufferToString() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addFirst("C");
        deque.addFirst("B");
        deque.addFirst("A");
        assertThat(deque.toString()).isEqualTo("[A, B, C]");
    }

    @Test
    public void testResizedDequeToString() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 100; i++) {
            deque.addLast(i);
        }
        StringBuilder expected = new StringBuilder("[");
        for (int i = 0; i < 99; i++) {
            expected.append(i).append(", ");
        }
        expected.append("99]");
        assertThat(deque.toString()).isEqualTo(expected.toString());
    }
}