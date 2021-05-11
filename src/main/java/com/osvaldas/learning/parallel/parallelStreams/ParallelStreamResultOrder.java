package com.osvaldas.learning.parallel.parallelStreams;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.osvaldas.learning.parallel.util.CommonUtil.startTimer;
import static com.osvaldas.learning.parallel.util.CommonUtil.timeTaken;

@Slf4j
public class ParallelStreamResultOrder {
    public static void main(String[] args) {
//        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8);
//        log.info("Input: {}", integers);
//        List<Integer> result = listOrder(integers);
//        log.info("Result: {}", result);

        Set<Integer> integers = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
        log.info("Input: {}", integers);
        Set<Integer> result = setOrder(integers);
        log.info("Result: {}", result);
    }

    public static List<Integer> listOrder(List<Integer> inputList) {
        startTimer();
        List<Integer> returnList = inputList.stream().map(i -> i * 2).collect(Collectors.toList());
        timeTaken();
        return returnList;
    }

    public static Set<Integer> setOrder(Set<Integer> inputList) {
        startTimer();
        Set<Integer> returnList = inputList.stream().map(i -> i * 2).collect(Collectors.toSet());
        timeTaken();
        return returnList;
    }
}
