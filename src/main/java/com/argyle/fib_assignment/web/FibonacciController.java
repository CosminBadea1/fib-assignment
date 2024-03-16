package com.argyle.fib_assignment.web;

import com.argyle.fib_assignment.domain.FibonacciService;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Validated
@RestController
@RequestMapping("/fib")
@RequiredArgsConstructor
public class FibonacciController {

    private final FibonacciService fibonacciService;

    @GetMapping
    @TimeLimiter(name = "calculateFibonacci")
    public CompletionStage<BigInteger> calculateFibonacciNumber(@RequestParam @Positive Integer n) {
        return CompletableFuture.supplyAsync(() -> fibonacciService.calculateNthFibonacci(n));
    }
}
