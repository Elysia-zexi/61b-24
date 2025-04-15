import javax.sound.sampled.*;
import java.util.*;

class GuitarString {
    private static final double DECAY = 0.996; // 衰减系数
    private Queue<Double> buffer;
    private int capacity;

    public GuitarString(double frequency) {
        capacity = (int) (44100 / frequency); // 采样率44100Hz
        buffer = new LinkedList<>();
        for (int i=0; i<capacity; i++) buffer.add(0.0);
    }

    public void pluck() {
        // 模拟拨弦（白噪声初始化）
        Random rand = new Random();
        for (int i=0; i<capacity; i++) {
            buffer.add(rand.nextDouble() - 0.5);
            buffer.poll();
        }
    }

    public double sample() { return buffer.peek(); }

    public void tic() { // 振动衰减
        double first = buffer.poll();
        double second = buffer.peek();
        buffer.add((first + second) * 0.5 * DECAY);
    }
}

public class BocchiRockPlayer {
    public static void main(String[] args) throws LineUnavailableException {
        // 星座になれたら前奏部分（简化为单音旋律）
        double[][] melody = {
                {329.63, 0.5},   // E4（1弦空弦）[6](@ref)
                {246.94, 0.5},   // B3（2弦空弦）
                {392.00, 1.0},   // G4（3弦第5品）[6](@ref)
                {440.00, 1.0}    // A4（参考官方谱标记）[1](@ref)
        };

        playMelody(melody);
    }

    private static void playMelody(double[][] notes) throws LineUnavailableException {
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();

        for (double[] note : notes) {
            GuitarString string = new GuitarString(note[0]);
            string.pluck();
            int duration = (int) (note[1] * 44100); // 采样数=时间×采样率

            for (int t=0; t<duration; t++) {
                short sample = (short) (string.sample() * 32767);
                byte[] buffer = new byte[]{
                        (byte) (sample & 0xFF),
                        (byte) ((sample >> 8) & 0xFF)
                };
                line.write(buffer, 0, 2);
                string.tic();
            }
        }
        line.drain();
        line.close();
    }
}
