package com.osvaldas.learning.parallel.parallelStreams;

import com.osvaldas.learning.parallel.util.DataSet;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.osvaldas.learning.parallel.util.CommonUtil.*;

@Slf4j
public class ParallelStreamsExample {
    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

        startTimer();
        List<String> resultListParallel = parallelStreamsExample.stringTransformParallel(namesList);
        timeTaken();

        log.info("resultListParallel : {}", resultListParallel);
    }

    public List<String> stringTransformParallel(List<String> namesList) {
        return namesList
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> stringTransformParallel(List<String> namesList, boolean isParallel) {
        Stream<String> namesStream = namesList.stream();
        if (isParallel) namesStream.parallel();
        return namesStream
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }
}
