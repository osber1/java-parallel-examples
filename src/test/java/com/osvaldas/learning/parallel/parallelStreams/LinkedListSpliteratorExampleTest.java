package com.osvaldas.learning.parallel.parallelStreams;

import com.osvaldas.learning.parallel.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    @RepeatedTest(5)
    void testMultiplyEachValue_whenNotParallel() {
        //given
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        //when
        List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        //then
        assertThat(resultList.size()).isEqualTo(size);
    }

    @RepeatedTest(5)
    void testMultiplyEachValue_whenParallel() {
        //given
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        //when
        List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        //then
        assertThat(resultList.size()).isEqualTo(size);
    }
}