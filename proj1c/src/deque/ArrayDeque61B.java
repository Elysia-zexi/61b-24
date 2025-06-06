package deque;

import net.sf.saxon.expr.Component;
import net.sf.saxon.trans.SymbolicName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;
import java.util.Objects;

public class ArrayDeque61B<T> implements Deque61B<T> {
    public int size;
    public int nextFirst;//队首
    public int nextLast;//队尾
    public T[] items;//大小
    private static final int INIT_CAPACITY = 8;
    //构造函数
    public ArrayDeque61B() {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = INIT_CAPACITY/2;
        nextLast = INIT_CAPACITY/2 + 1;
    }
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size++;
    }

    public void resize(int big) {
        T[] newArray = (T[]) new Object[big];
        // 正确遍历原数组中的有效元素
        int current = Math.floorMod(nextFirst + 1, items.length);
        for (int i = 0; i < size; i++) {
            newArray[big/2 - size/2 + i] = items[current];
            current = Math.floorMod(current + 1, items.length);
        }
        // 重置指针到新数组中心
        nextFirst = big/2 - size/2 - 1; // 指向新起始位置的左侧
        nextLast = big/2 + size/2;
        items = newArray;
    }
    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        int start = Math.floorMod(nextFirst + 1, items.length);
        for (int a = 0; a < size; a++) {
            list.add(items[start]);
            start = Math.floorMod(start + 1, items.length);//从start开始加
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int firstIdx = Math.floorMod(nextFirst + 1, items.length);
        T item = items[firstIdx];
        items[firstIdx] = null; // 清除引用
        nextFirst = firstIdx;   // 指针右移（逻辑左移）
        size--;
        check(); // 检查是否需要缩容
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        // 计算实际队尾索引
        int lastIdx = Math.floorMod(nextLast - 1, items.length);
        T item = items[lastIdx];
        items[lastIdx] = null; // 清除引用
        nextLast = lastIdx;    // 指针左移（逻辑右移）
        size--;
        check(); // 检查是否需要缩容
        return item;
    }

    public void check() {
        if (items.length >= 16 && size * 4 < items.length) {
            resize(items.length/2);
        }
    }
    @Override
    public T get(int index) {
        if (index > size || index < 0) {
            return null;
        }
        //计算元素下标
        int a = Math.floorMod(nextFirst + index + 1, items.length);
        return items[a];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    //迭代器实现
    // 新增迭代器实现
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int currentPos = Math.floorMod(nextFirst + 1, items.length); // 起始位置
        private int count = 0; // 已遍历元素计数器

        @Override
        public boolean hasNext() {
            return count < size; // 遍历完所有有效元素
        }

        @Override
        public T next() {
            T item = items[currentPos];
            currentPos = Math.floorMod(currentPos + 1, items.length); // 循环移动
            count++;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        // 同上，代码与 LinkedListDeque61B 完全一致
        if (this == o) return true;
        if (!(o instanceof Deque61B)) return false;
        Deque61B<?> otherDeque = (Deque61B<?>) o;
        if (this.size() != otherDeque.size()) return false;

        Iterator<T> thisIterator = this.iterator();
        Iterator<?> otherIterator = otherDeque.iterator();

        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            T thisItem = thisIterator.next();
            Object otherItem = otherIterator.next();
            if (!Objects.equals(thisItem, otherItem)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        Iterator<T> iterator = iterator();
        if (! iterator.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
