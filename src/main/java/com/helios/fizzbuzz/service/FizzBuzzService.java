package com.helios.fizzbuzz.service;

import com.helios.fizzbuzz.entity.dto.FizzBuzz;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

@Service
public class FizzBuzzService {

    private final static ConcurrentHashMap<FizzBuzz, Integer> statistics = new ConcurrentHashMap<>();

    public List<String> generateFizzBuzz(FizzBuzz fizzBuzz) {
        List<String> fizzBuzzValues = buildListFromOneToLimit(fizzBuzz.getLimit());
        List<Integer> multipleOfInt1AndInt2Index = getIndexOfParamMultiple(fizzBuzzValues, fizzBuzz.getInt1(), fizzBuzz.getInt2());
        List<Integer> multipleOfInt1Index = getIndexOfParamMultiple(fizzBuzzValues, fizzBuzz.getInt1(), multipleOfInt1AndInt2Index);
        List<Integer> multipleOfInt2Index = getIndexOfParamMultiple(fizzBuzzValues, fizzBuzz.getInt2(), multipleOfInt1AndInt2Index);

        fizzBuzzValues = replace(fizzBuzzValues, multipleOfInt1AndInt2Index, fizzBuzz.getStr1() + fizzBuzz.getStr2());
        fizzBuzzValues = replace(fizzBuzzValues, multipleOfInt1Index, fizzBuzz.getStr1());
        fizzBuzzValues = replace(fizzBuzzValues, multipleOfInt2Index, fizzBuzz.getStr2());

        doStatistics(fizzBuzz);
        return fizzBuzzValues;
    }

    public FizzBuzz getMostUsedRequest() {
        Map<FizzBuzz, Integer> sortedDescByValue = statistics.entrySet()
                .stream()
                .sorted(Map.Entry.<FizzBuzz, Integer>comparingByValue().reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.setCount(0);
        Optional<FizzBuzz> optionalFizzBuzz = sortedDescByValue.keySet().stream().findFirst();

        if (optionalFizzBuzz.isPresent()) {
            fizzBuzz = optionalFizzBuzz.get();
            int count = statistics.get(fizzBuzz);
            fizzBuzz.setCount(count);
        }

        return fizzBuzz;
    }

    private void doStatistics(FizzBuzz fizzBuzz) {
        if (statistics.containsKey(fizzBuzz)) {
            int count = statistics.get(fizzBuzz) + 1;
            statistics.put(fizzBuzz, count);
        } else {
            statistics.put(fizzBuzz, 1);
        }
    }

    private List<String> replace(List<String> fizzBuzzValues, List<Integer> multipleIndex, String value) {
        multipleIndex.forEach(index -> {
            fizzBuzzValues.set(index, value);
        });
        return fizzBuzzValues;
    }

    private List<String> buildListFromOneToLimit(int limit) {
        return IntStream.range(1, limit + 1).mapToObj(i -> i).map(integer -> integer.toString()).collect(Collectors.toList());
    }

    private List<Integer> getIndexOfParamMultiple(List<String> fizzBuzzValues, int int1, int int2) {
        List<Integer> multipleOfInt1AndInt2Index = new ArrayList<>();
        for (int i = 0; i < fizzBuzzValues.size(); i++) {
            int fizzBuzzValue = Integer.valueOf(fizzBuzzValues.get(i));
            if (fizzBuzzValue % int1 == 0 && fizzBuzzValue % int2 == 0) {
                multipleOfInt1AndInt2Index.add(i);
            }
        }
        return multipleOfInt1AndInt2Index;
    }

    private List<Integer> getIndexOfParamMultiple(List<String> fizzBuzzValues, int val, List<Integer> multipleOfInt1AndInt2Index) {
        List<Integer> multipleOfValIndex = new ArrayList<>();
        for (int i = 0; i < fizzBuzzValues.size(); i++) {
            int fizzBuzzValue = Integer.valueOf(fizzBuzzValues.get(i));
            if (fizzBuzzValue % val == 0 && !multipleOfInt1AndInt2Index.contains(i)) {
                multipleOfValIndex.add(i);
            }
        }
        return multipleOfValIndex;
    }
}
