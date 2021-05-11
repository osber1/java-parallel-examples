package com.osvaldas.learning.parallel.parallelStreams;

import com.osvaldas.learning.parallel.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

    @RepeatedTest(5)
    void testMultiplyEachValue_whenNotParallel() {
        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when
        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        //then
        assertThat(resultList.size()).isEqualTo(size);
    }

    @RepeatedTest(5)
    void testMultiplyEachValue_whenParallel() {
        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when
        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        //then
        assertThat(resultList.size()).isEqualTo(size);
    }
}