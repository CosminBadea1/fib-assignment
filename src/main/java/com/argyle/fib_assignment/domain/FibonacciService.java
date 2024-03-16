package com.argyle.fib_assignment.domain;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class FibonacciService {

    public BigInteger calculateNthFibonacci(Integer position) {
        BigInteger previousFib = BigInteger.ONE;
        BigInteger currentFib = BigInteger.ONE;

        for (int termPosition = 3; termPosition <= position; termPosition++) {
            BigInteger nextFib = previousFib.add(currentFib);
            previousFib = currentFib;
            currentFib = nextFib;
        }

        return currentFib;
    }
}
