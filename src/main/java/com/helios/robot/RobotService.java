package com.helios.robot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RobotService {

    private ConcurrentHashMap<Integer, Integer> articlesGroupOccurrence;
    private static final int BOX_SIZE = 10;

    public Robot packageArticle(String articles) {
        fillArticlesGroupOccurrence(articles);
        List<Integer> distinctArticlesWeight = articlesGroupOccurrence.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        Collections.sort(distinctArticlesWeight, Collections.reverseOrder());

        List<String> boxContents = distinctArticlesWeight.stream()
                .map(this::fillBox)
                .collect(Collectors.toList());

        return new Robot(boxContents.size(), boxContents.stream().collect(Collectors.joining("/")));
    }

    private void fillArticlesGroupOccurrence(String articles) {
        articlesGroupOccurrence = new ConcurrentHashMap<>();
        String[] articleTab = articles.split("");
        List<Integer> articleList = Arrays.stream(articleTab).map(Integer::parseInt).collect(Collectors.toList());
        articleList.forEach(article -> {
            if (articlesGroupOccurrence.containsKey(article)) {
                int nbOccurrence = articlesGroupOccurrence.get(article) + 1;
                articlesGroupOccurrence.put(article, nbOccurrence);
            } else {
                articlesGroupOccurrence.put(article, 1);
            }
        });
    }

    private String fillBox(int article) {
        int maxWeightOfArticleCandidate = BOX_SIZE - article;

        List<Integer> articlesCandidate = new ArrayList<>();
        articlesGroupOccurrence.entrySet().stream()
                .filter(e -> e.getKey() <= maxWeightOfArticleCandidate)
                .forEach(e -> {
                    for (int i = 0; i < e.getValue(); i++) {
                        articlesCandidate.add(e.getKey());
                    }
                });


        int sum = 0;
        String articleInBox = Integer.toString(article);
        decrementArticleOccurrence(article);
        // if i we have to return one value we take the bigger one
        if (articlesCandidate.size() > 1 && (articlesCandidate.get(0) + articlesCandidate.get(1)) > maxWeightOfArticleCandidate /*&& !articlesGroupOccurrence.containsKey(maxWeightOfArticleCandidate)*/) {
            int currentArticle = articlesCandidate.get(1);
            decrementArticleOccurrence(currentArticle);
            return Integer.toString(article) + currentArticle;
        }
        for (int i = 0; i < articlesCandidate.size(); i++) {
            sum = sum + articlesCandidate.get(i);
            if (sum <= maxWeightOfArticleCandidate) {
                int currentArticle = articlesCandidate.get(i);
                articleInBox = articleInBox + currentArticle;
                decrementArticleOccurrence(currentArticle);
            } else {
                break;
            }
        }

        return articleInBox;
    }

    private void decrementArticleOccurrence(int key) {
        int nbOccurrence = articlesGroupOccurrence.get(key);
        nbOccurrence--;
        articlesGroupOccurrence.put(key, nbOccurrence);
    }

    public static void main(String[] args) {
        RobotService service = new RobotService();
        System.out.println(service.packageArticle("163841689525773"));
    }
}
