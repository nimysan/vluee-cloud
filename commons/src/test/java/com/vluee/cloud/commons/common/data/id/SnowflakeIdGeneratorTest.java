package com.vluee.cloud.commons.common.data.id;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 雪花ID生成器， 不要使用同一个WorkerId, 否则会出现重复。
 */
@Slf4j
class SnowflakeIdGeneratorTest {


    /**
     * 不要同时有多个worker id的snowflake生成ID
     */
    @Test
    @DisplayName("测试雪花算法-相同的workerId会重复")
    public void testShareWorkerId() {
        Assertions.assertTrue(generateIdInMultiThread(true), "相同的WorkerId会重复");
    }

    @Test
    @DisplayName("测试雪花算法-不同的workerId不会重复")
    public void testNotShareWorkerId() {
        Assertions.assertFalse(generateIdInMultiThread(false), "不同的WorkedId不会重复");
    }


    /**
     * 10个线程，产生ID
     *
     * @param sameWorkerId 是否使用相同的workerId
     * @return 重复为true
     */
    private boolean generateIdInMultiThread(boolean sameWorkerId) {
        int totalNumberOfTasks = 10;
        int threadIdCount = 200;
        CountDownLatch latch = new CountDownLatch(totalNumberOfTasks);

        final Set<Long> ids = Collections.synchronizedSet(new HashSet<>());
        ExecutorService executorService = Executors.newFixedThreadPool(totalNumberOfTasks);
        java.util.List<GeneratorThread> generatorThreadList = new ArrayList<>();
        for (int i = 0; i < totalNumberOfTasks; i++) {
            long workerId = 2l;
            if (!sameWorkerId) {
                workerId = workerId + i;
            }
            generatorThreadList.add(new GeneratorThread(workerId, 22, threadIdCount, ids, latch));
        }
        generatorThreadList.stream().forEach(t -> executorService.execute(t));

        try {
            latch.await();
            final Integer reduce = generatorThreadList.stream().map(t -> t.getDuplicateCount()).reduce(0, (integer, integer2) -> integer + integer2);
            log.info("--- Generated id count: {}, duplicate count: {} ", ids.size(), reduce);
            return reduce > 0;
//            Assertions.assertEquals(0, reduce);
//            Assertions.assertEquals(totalNumberOfTasks * 200, ids.size());
        } catch (InterruptedException E) {
            // handle
            throw new RuntimeException(E);
        }
    }

    public static class GeneratorThread implements Runnable {

        private final Set<Long> ids;
        private final Snowflake snowflake;
        private int duplicateCount = 0;
        private final long count;
        private CountDownLatch latch;
        private long workerId;

        private int generatedCounts = 0;

        public int getDuplicateCount() {
            return duplicateCount;
        }

        GeneratorThread(long workerId, long dataCenterId, long count, Set<Long> ids, CountDownLatch latch) {
            this.ids = ids;
            this.workerId = workerId;
            snowflake = new Snowflake(workerId, dataCenterId);
            this.count = count;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                for (long i = 0; i < count; i++) {
                    long id = snowflake.nextId();
                    if (ids.contains(id)) {
//                        log.info("--- {} ", id);
                        duplicateCount++;
//                        log.info("duplicate {}", duplicateCount);
                    } else {
                        ids.add(id);
                    }
                    generatedCounts++;
                }
            } catch (Exception e) {

            } finally {
                log.info(" worker id {} generated {}", workerId, generatedCounts);
                latch.countDown();
            }
        }
    }

}