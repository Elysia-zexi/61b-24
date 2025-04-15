package deque;

import net.sf.saxon.expr.Component;
import net.sf.saxon.trans.SymbolicName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;
import java.util.Objects;
import java.util.Collections;
import java.util.Comparator;


public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private final Comparator<T> comparator;
    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        this.comparator = c;
    }
    public T max() {
        return max(comparator);
    }
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        Iterator<T> iterator = iterator();
        T maxtem = iterator.next();
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (c.compare(current, maxtem) > 0) {
                maxtem = current;
            }
        }
        return maxtem;

    }

}