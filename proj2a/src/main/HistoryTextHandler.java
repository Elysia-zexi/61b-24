package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap ngMap;
    HistoryTextHandler(NGramMap map) {
        this.ngMap = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();
        for (String word : words) {
            TimeSeries weights = ngMap.weightHistory(word, startYear, endYear);

            response.append(word)
                    .append(": ")
                    .append(weights.toString())
                    .append("\n");
        }
        return response.toString();
    }
}
