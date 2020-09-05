package com.ezbob.test.serviceshuffle.server.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShuffleService {
    public List<Integer> generateRandomArray(int numberForRandomArray) {
        List<Integer> list = Stream.iterate(1, i -> i != numberForRandomArray + 1, i -> i + 1).collect(Collectors.toList());

        // According the implementation, this is O(n)
        Collections.shuffle(list);
        return list;
    }
}
