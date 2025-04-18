package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private final Map<String, TimeSeries> wordcount;  //单词到年份计数的映射
    private final TimeSeries totalcount;              //年份到总单词数的映射
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordcount = new HashMap<>();
        totalcount = new TimeSeries();

        //将word file文件添加到映射
        //In类用于读写数据，用wordFilename文件创建一个In类的hasword，
        In hasword = new In(wordsFilename);
        //hasNextLine检查hasword是否有下一行
        while (hasword.hasNextLine()) {
            //In类中readLine读取一行数据，并以字符串的形式返回。
            //String中trim用于除去字符串两边的空白字符
            String line = hasword.readLine().trim();
            //若下一行为空，跳过
            if (line.isEmpty()) continue;
            //将处理后的一行按制表符分割
            String[] parts = line.split("\t");
            //检查是否为四个数据
            if (line.length() < 4) continue;
            //储存单词
            String word = parts[0];
            //年份
            int year = Integer.parseInt(parts[1]);
            //计数
            double count = Double.parseDouble(parts[2]);
            //putIfAbsent为map接口的方法
            //将指定的key插入到map中，若没有，创建一个新对象
            wordcount.putIfAbsent(word, new TimeSeries());
            //找到单词，插入键值对  年份-计数
            wordcount.get(word).put(year, count);
        }


        //count file
        In countfile = new In(countsFilename);
        while (countfile.hasNextLine()) {
            String cline = countfile.readLine().trim();
            if (cline.isEmpty()) continue;
            String[] table = cline.split(",");
            if (cline.length() < 2) continue;
            int year = Integer.parseInt(table[0]);
            double count = Double.parseDouble(table[1]);
            totalcount.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     * 提供从 STARTYEAR 到 ENDYEAR（包括两端）期间 WORD 的历史记录。
     * 返回的时间序列应为副本，而非指向此 NGramMap 的时间序列的链接。
     * 换句话说，对该函数返回的对象所做的更改不应影响 NGramMap。
     * 这也被称为“防御性复制”。
     * 如果该词不在数据文件中，则返回一个空的时间序列。
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (! wordcount.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordcount.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     * 提供单词“WORD”的历史记录。返回的时间序列应为副本，而非指向此 NGramMap 的时间序列的链接。
     * 换句话说，对由该函数返回的对象所做的更改不应影响 NGramMap。
     * 这也被称为“防御性复制”。
     * 如果该单词不在数据文件中，则返回一个空的时间序列。
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes
     * *** 返回的是关于所有卷册每年所记录的单词总数的防御性副本。.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return new TimeSeries(totalcount, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        //单词在指定年份的出现次数
        TimeSeries counts = countHistory(word, startYear, endYear);
        //储存频率
        TimeSeries weigth = new TimeSeries();
        for (Map.Entry<Integer, Double> tem: counts.entrySet()) {
            //获取当前年份
            int year = tem.getKey();
            //获取当前年份单词出现的总次数
            Double total = totalcount.get(year);
            if (total != null) {
                //当前年份出现的次数，除以总次数。
                weigth.put(year, tem.getValue() / total);
            }
        }
        return weigth;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     * **提供一个包含每年中“WORD”这个词出现的相对频率的时间序列，
     * 该频率是相对于当年记录的所有单词而言的。
     * 如果该词不在数据文件中，则返回一个空的时间序列。
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     * 提供从 STARTYEAR 到 ENDYEAR（包括这两个端点）期间（均含端点）所有单词的累加相对频率。
     * 如果某个单词在此时间段内不存在，则忽略它，而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries  sums = new TimeSeries();
        for (String word: words) {
            TimeSeries weight = weightHistory(word, startYear, endYear);
            for (Map.Entry<Integer, Double> tem: weight.entrySet()) {
                int year = tem.getKey();
                //getOrDefault获取当前year的值。
                sums.put(year, sums.getOrDefault(year, 0.0) + tem.getValue());
            }
        }
        return sums;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     * 返回 WoRDS 中所有单词每年的累加相对频率。
     * 如果某个单词在此时间段内不存在，则忽略它，而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
