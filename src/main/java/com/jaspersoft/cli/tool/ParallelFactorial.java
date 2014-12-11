//package com.jaspersoft.cli.tool;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Future;
//
//import static java.lang.Math.min;
//import static java.lang.Runtime.getRuntime;
//import static java.math.BigInteger.ONE;
//import static java.math.BigInteger.valueOf;
//import static java.util.concurrent.Executors.newFixedThreadPool;
//
///**
// * @author Alexander Krasnyanskiy
// */
//public class ParallelFactorial {
//
//
//    public static void main(String[] args) {
//        //System.out.println(factorial(100000));
//        System.out.println(parallelfactorial(100000));
//    }
//
//
//    public static BigInteger factorial(long n) {
//        BigInteger result = ONE;
//        for (long i = 2; i <= n; i++) {
//            result = result.multiply(valueOf(i));
//        }
//        return result;
//    }
//
//
//    public static BigInteger parallelfactorial(long n) {
//        int processors = getRuntime().availableProcessors();
//        if (n < processors * 2) {
//            return factorial(n);
//        }
//        long batchSize = (n + processors - 1) / processors; // размер пакета/партии
//        ExecutorService service = newFixedThreadPool(processors);
//        try {
//            List<Future<BigInteger>> results = new ArrayList<>();
//            for (long i = 1; i <= n; i += batchSize) {
//                final long start = i,
//                           end = min(n + 1, i + batchSize);
//                results.add(service.submit(() -> {
//                    BigInteger n1 = valueOf(start);
//                    for (long j = start + 1; j < end; j++) {
//                        n1 = n1.multiply(valueOf(j));
//                    }
//                    return n1;
//                }));
//            }
//            BigInteger result = ONE;
//            for (Future<BigInteger> future : results) {
//                result = result.multiply(future.get());
//            }
//            return result;
//        } catch (Exception e) {
//            throw new AssertionError(e);
//        } finally {
//            service.shutdown();
//        }
//    }
//}
