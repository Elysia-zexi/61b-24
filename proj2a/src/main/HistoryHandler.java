package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;
public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap ngMap;

    // 构造函数接收 NGramMap 实例
    public HistoryHandler(NGramMap map) {
        this.ngMap = map;
    }

    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear();
        int endYear = query.endYear();

        // 收集每个单词的权重历史数据
        List<TimeSeries> timeSeriesList = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (String word : words) {
            TimeSeries weights = ngMap.weightHistory(word, startYear, endYear);
            if (!weights.isEmpty()) {
                timeSeriesList.add(weights);
                labels.add(word);
            }
        }

        // 生成图表
        XYChart chart = Plotter.generateTimeSeriesChart(labels, timeSeriesList);
        return Plotter.encodeChartAsString(chart);
    }
}
