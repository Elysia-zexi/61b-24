package gh2;
import deque.Deque61B;
import deque.ArrayDeque61B;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque61B implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
    private Deque61B<Double> buffer;
    private int capacity;
    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Initialize the buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer with zeros.
        //待办事项：使用容量 = SR / 频率来初始化缓冲区。您需要将此除法运算的结果强制转换为 int 类型。
        // 为了提高精度，在强制转换之前请使用 Math.round() 函数。您应该先用零填充缓冲区。

        // 计算缓冲区容量：采样率 / 频率，四舍五入取整
        // 最少保留一个位置，避免除零错误
        this.capacity = Math.max(1, (int) Math.round(SR / frequency));
        //初始化缓冲区
        this.buffer = new ArrayDeque61B<>();
        for (int i = 0;i < capacity; i++) {
            buffer.addFirst(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        while (!buffer.isEmpty()) {
            buffer.removeFirst();
        }

        for (int i = 0; i < capacity; i++) {
            double noise = Math.random() - 0.5;
            buffer.addLast(noise);
        }
    }

    /*
     * 推进模拟一步：执行 Karplus-Strong 算法
     * 算法步骤：
     * 1. 取出缓冲区第一个元素
     * 2. 计算第一个元素与下一个元素的平均值
     * 3. 应用能量衰减因子
     * 4. 将计算结果添加回缓冲区末尾
     */
    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**
        if (buffer.isEmpty()) {
            return;
        }
        double first = buffer.removeFirst();
        double second = buffer.isEmpty()? 0.0 :buffer.get(0);
        double newvalue = (first + second) * 0.5 * DECAY;
        buffer.addLast(newvalue);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return buffer.isEmpty() ? 0.0 : buffer.get(0);
    }
}
    // TODO: Remove all comments that say TODO when you're done.
