package ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        //subMap为TreeMap中的方法，作用是提取star-end范围的映射，true是是否包含值本身。
        //forEach((参数)->(方法))，对全部参数使用其方法，相当于lambda
        ts.subMap(startYear, true, endYear, true)
                .forEach((key, value) -> this.put(key, value));
        // TODO: Fill in this constructor.
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        //keySet返回所有键的set集合。
        return new ArrayList<>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        //values返回所有值
        return new ArrayList<>(this.values());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     * 返回此时间序列与给定时间序列按年份求和的结果。
     * 换句话说，对于每一年，将此时间序列中的数据与给定时间序列中的数据相加。
     * 应返回一个新的时间序列（不会修改此时间序列）。
     * 如果两个时间序列都不包含任何年份，则返回一个空的时间序列。
     * 如果一个时间序列包含另一个时间序列所没有的年份，
     * 则返回的时间序列应存储包含该年份的时间序列中的值。
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();

        //储存所有年份
        Set<Integer> allyear = new TreeSet<>(ts.keySet());
        allyear.addAll(this.keySet());

        for (int year: allyear) {
            double sum = 0.0;
            if (this.containsKey(year)) sum += this.get(year);
            if (ts.containsKey(year)) sum += ts.get(year);
            result.put(year, sum);
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     * 返回此时间序列中每个年份的值除以 TS 中相同年份的值所得的商。
     * 应返回一个新的时间序列（不会修改此时间序列）。
     * 如果 TS 中缺少此时间序列中存在的年份，则抛出 IllegalArgumentException 异常。
     * 如果 TS 中存在此时间序列中没有的年份，则忽略它。
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();

        //Entry为Map的键值对接口。entrySet返回map中所有的键值对集合
        for (Map.Entry<Integer, Double> all: this.entrySet()) {
            int year = all.getKey();
            if (!containsKey(year)) {
                throw new IllegalArgumentException("Missing");
            }
            double curvalue = all.getValue() / ts.get(year);
            result.put(year, curvalue);
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
