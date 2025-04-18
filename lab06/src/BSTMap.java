import org.eclipse.jetty.security.SpnegoUserIdentity;
import java.util.*;
import java.util.Deque;
import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    //节点类
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root;//根节点
    private int size;//计数器

    //构造函数
    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V value) {
        //子树为空，创建新节点
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }

        int res = key.compareTo(node.key);//比较

        if (res > 0) {
            node.right = put(node.right, key, value);
        } else if (res < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        BSTNode cur = get(root, key);
        return (cur == null) ? null : cur.value;
    }

    private BSTNode get(BSTNode node, K key) {
        //子树为空，返回空
        if (node == null) {
            return null;
        }

        int res = key.compareTo(node.key);//比较

        if (res > 0) {
            return get(node.right, key);
        } else if (res < 0) {
            return get(node.left, key);
        } else {
            return node;
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        return get(root, key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>(); // 使用TreeSet保证按键排序
        inorderCollect(root, keys);
        return keys;
    }
    /** 中序遍历收集所有键 */
    private void inorderCollect(BSTNode node, Set<K> keys) {
        if (node == null) return;
        inorderCollect(node.left, keys);
        keys.add(node.key);
        inorderCollect(node.right, keys);
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }

        BSTNode node = get(root, key);
        if (node == null) {
            return null;
        }
        V oldnode = node.value;
        root = remove(root, key);
        size--;
        return oldnode;
    }

    private BSTNode remove(BSTNode node, K key) {
        if (node == null) return null;
        int res = key.compareTo(node.key);//比较

        if (res > 0) {
            node.right = remove(node.right, key);
        } else if (res < 0) {
            node.left = remove(node.left, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            //两个节点的情况
            //用右子树的最小节点替换当前节点
            BSTNode min = findmin(node.right);
            node.value = min.value;
            node.key = min.key;
            node.right = remove(node.right, min.key);
        }
        return node;
    }

    private BSTNode findmin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    @Override
    public Iterator<K> iterator() {
        return new Bstiterator();
    }

    private class Bstiterator implements Iterator<K> {
        private final Deque<BSTNode> iter = new ArrayDeque<>();

        public Bstiterator() {
            left(root);
        }

        //升序
        private void left(BSTNode node) {
            while (node != null) {
                iter.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !iter.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }
            BSTNode cur = iter.pop();
            left(cur.right);//右子树最短路径
            return cur.key;
        }
    }


    public void printInOrder() {
        printInOrder(root);
    }

    // 中序遍历打印树结构（按键升序）
    private void printInOrder(BSTNode node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.println("[" + node.key + "] = " + node.value);
        printInOrder(node.right);
    }
}