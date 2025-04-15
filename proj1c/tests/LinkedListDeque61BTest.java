import com.google.common.truth.Truth;
import deque.ArrayDeque61B;
import deque.LinkedListDeque61B;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import deque.Deque61B;
public class LinkedListDeque61BTest {
    // ---------------------- equals() 测试 ----------------------
    @Test
    public void testEmptyDequesEqual() {
        Deque61B<String> deque1 = new LinkedListDeque61B<>();
        Deque61B<String> deque2 = new LinkedListDeque61B<>();
        assertThat(deque1).isEqualTo(deque2);
    }

    @Test
    public void testDifferentElementOrderNotEqual() {
        Deque61B<Integer> deque1 = new LinkedListDeque61B<>();
        Deque61B<Integer> deque2 = new LinkedListDeque61B<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque2.addLast(2);
        deque2.addLast(1);
        assertThat(deque1).isNotEqualTo(deque2);
    }

    @Test
    public void testCrossImplementationEquality() {
        Deque61B<String> linkedDeque = new LinkedListDeque61B<>();
        Deque61B<String> arrayDeque = new ArrayDeque61B<>();
        linkedDeque.addLast("A");
        linkedDeque.addLast("B");
        arrayDeque.addLast("A");
        arrayDeque.addLast("B");
        assertThat(linkedDeque).isEqualTo(arrayDeque);
    }

    // ---------------------- toString() 测试 ----------------------
    @Test
    public void testEmptyDequeToString() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        assertThat(deque.toString()).isEqualTo("[]");
    }

    @Test
    public void testSingleElementToString() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(100);
        assertThat(deque.toString()).isEqualTo("[100]");
    }

    @Test
    public void testMultipleElementsToString() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addLast("front");
        deque.addLast("middle");
        deque.addLast("back");
        assertThat(deque.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void testNullElementToString() {
        Deque61B<Object> deque = new LinkedListDeque61B<>();
        deque.addLast("A");
        deque.addLast(null);
        deque.addLast("B");
        assertThat(deque.toString()).isEqualTo("[A, null, B]");
    }
}