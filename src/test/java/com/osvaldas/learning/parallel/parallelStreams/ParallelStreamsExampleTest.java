package com.osvaldas.learning.parallel.parallelStreams;

import com.osvaldas.learning.parallel.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.osvaldas.learning.parallel.util.CommonUtil.startTimer;
import static com.osvaldas.learning.parallel.util.CommonUtil.timeTaken;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void testStringTransformParallel() {
        //given
        List<String> inputList = DataSet.namesList();

        //when
        startTimer();
        List<String> resultList = parallelStreamsExample.stringTransformParallel(inputList);
        timeTaken();

        //then
        assertThat(resultList.size()).isEqualTo(4);
        resultList.forEach(name -> assertThat(name.contains("-")).isTrue());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testStringTransformParallel_whenParameterized(boolean isParallel) {
        //given
        List<String> inputList = DataSet.namesList();

        //when
        startTimer();
        List<String> resultList = parallelStreamsExample.stringTransformParallel(inputList, isParallel);
        timeTaken();

        //then
        assertThat(resultList.size()).isEqualTo(4);
        resultList.forEach(name -> assertThat(name.contains("-")).isTrue());
    }
}