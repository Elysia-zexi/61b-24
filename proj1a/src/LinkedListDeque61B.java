import java.util.List;
import java.util.ArrayList; // import the ArrayList class
import java.util.SplittableRandom;

public class LinkedListDeque61B<T> implements Deque61B<T>{

    private Node sentinel;//哨兵节点
    private int size;//链表大小

    //构造函数
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    //节点类
    public class Node {
        public Node prev;
        public T item;
        public Node next;
        //节点构造函数
        Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    @Override
    public void addFirst(T x) {
        Node newnode = new Node(sentinel, x, sentinel.next);
        sentinel.next.prev = newnode;
        sentinel.next = newnode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node newnode = new Node(sentinel.prev, x, sentinel);//创造新节点
        sentinel.prev.next = newnode;//哨兵的上一个节点指向新节点
        sentinel.prev = newnode;//哨兵上指向新节点
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node cur = sentinel.next;
        while (cur != sentinel) {
            returnList.add(cur.item);
            cur = cur.next;
        }
        return returnList;
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
        Node firstnode = sentinel.next;
        sentinel.next = sentinel.next.next;
        firstnode.next.prev = sentinel;
        size--;
        return firstnode.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node lastnode = sentinel.prev;
        sentinel.prev = lastnode.prev;
        lastnode.prev.next = sentinel;
        size--;
        return lastnode.item;
    }

    @Override
    public T get(int index) {
        Node cur = sentinel.next;
        if (index >= size || index < 0) {
            return null;
        }
        else {
            while (index > 0) {
                cur = cur.next;
                index--;
            }
        }
        return cur.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursizehelper(sentinel.next, index);
    }
    private T getRecursizehelper(Node cur, int curindex) {
        if (curindex == 0) {
            return cur.item;
        }
        return getRecursizehelper(cur.next, curindex - 1);
    }
}
