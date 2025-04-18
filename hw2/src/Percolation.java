import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocParser;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.checkerframework.checker.index.qual.PolyUpperBound;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] grid;   //网格数组
    private int N;              //网格维度
    private WeightedQuickUnionUF wquuf; //并查集


    //构造函数
    public Percolation(int N) {
        // TODO: Fill in this constructor.
        this.N = N;
        check(N);//检查
        this.grid = new boolean[N][N];//初始化数组
        this.wquuf = new WeightedQuickUnionUF(N*N);//初始化并查集



    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row > N - 1 || col < 0 || col > N -1) {
            throw new IndexOutOfBoundsException();
        }
        else {
            if (!grid[row][col]) {
                grid[row][col] = true;
            }
            else {
                return;
            }
        }

        int index = xyTo1D(row, col);
        if (row > 0 && isOpen(row -1, col)) {
            wquuf.union(xyTo1D(row - 1, col), index);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row > N - 1 || col < 0 || col > N -1) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
    }

    public boolean percolates() {
        // TODO: Fill in this method.
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    private void check(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
    }
    //创建唯一·索引
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }
}