import com.google.common.truth.Truth;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }
    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void testMaxWithNaturalOrder() {
        // ✅ 正确：显式指定 Comparator 的类型
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<>(Comparator.<Integer>naturalOrder());
        deque.addLast(3);
        deque.addLast(1);
        deque.addLast(4);
        assertThat(deque.max()).isEqualTo(4); // 最大值为4
    }

    @Test
    public void testMaxWithCustomComparator() {
        // 自定义比较器（按字符串长度）
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        MaxArrayDeque61B<String> deque = new MaxArrayDeque61B<>(lengthComparator);
        deque.addLast("apple");
        deque.addLast("banana");
        deque.addLast("cherry");
        assertThat(deque.max()).isEqualTo("banana"); // 最长字符串
    }

}