package com.osvaldas.learning.parallel.parallelStreams;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.osvaldas.learning.parallel.util.CommonUtil.startTimer;
import static com.osvaldas.learning.parallel.util.CommonUtil.timeTaken;

public class LinkedListSpliteratorExample {

    public List<Integer> multiplyEachValue(LinkedList<Integer> inputList, int multiplyValue, boolean isParallel) {
        Stream<Integer> integerStream = inputList.stream();

        if (isParallel) integerStream.parallel();

        startTimer();
        List<Integer> resultList = integerStream.map(i -> i * multiplyValue)
                .collect(Collectors.toList());
        timeTaken();

        return resultList;
    }
}
