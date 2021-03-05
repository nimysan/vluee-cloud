package com.vluee.cloud.commons.distributedlock;

import cn.hutool.core.thread.ThreadUtil;
import com.vluee.cloud.commons.common.data.id.SnowflakeIdGenerator;
import com.vluee.cloud.commons.distributedlock.mem.InMemMutexLockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
class MutexLockFactoryTest {

    @Test
    public void testWait() {
        String wait = forWait();
        Assertions.assertEquals("111", wait);
    }

    public String forWait() {
        long start = System.nanoTime();
        for (; ; ) {
            try {
                return "111";

            } catch (Throwable e) {

            } finally {
                if ((System.nanoTime() - start) > TimeUnit.SECONDS.toNanos(3)) {
                    throw new RuntimeException("Wait timeout");
                }
            }
        }
    }

    @Test
    public void test() {
        int x = 1;
        String x_copy = x + "";
        x = 1;
        String x_copy2 = x + "";
        Assertions.assertFalse(x_copy == x_copy2);
    }

    /**
     * 通过多线程模拟
     */
    @Test
    public void testLockResources() throws InterruptedException {
        final KeyResource keyResource = new KeyResource();
        final MutexLockFactory mutexLockFactory = new MutexLockFactory(new InMemMutexLockRepository(), new SnowflakeIdGenerator());
        //模拟多线程
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new MockResourceTask(keyResource, mutexLockFactory));
        }
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        Assertions.assertTrue(keyResource.history.size() > 10, "所有进程都无法获取锁，导致无法处理资源");
        validation(keyResource);
    }

    private void validation(KeyResource keyResource) {
        log.info("Check times {}", keyResource.history.size());
        int noProcessStatic = 0;
        int oneProcessStatic = 0;
        for (String handleProcess : keyResource.history) {
            if ("0".equals(handleProcess)) {
                noProcessStatic++;
            } else if ("1".equals(handleProcess)) {
                oneProcessStatic++;
            }
        }
        log.info("Handle statistics {}", keyResource.history);
        Assertions.assertTrue(noProcessStatic >= 0, "资源未被处理");
        Assertions.assertTrue(oneProcessStatic > 0, "没有启动过资源处理器?");
        Assertions.assertTrue(noProcessStatic + oneProcessStatic == keyResource.history.size(), "锁控制失败，有同时大于两个处理器在处理保护资源");
    }

    /**
     * 模拟器。 每个线程都每隔200ms尝试处理一次资源
     */
    @AllArgsConstructor
    class MockResourceTask implements Runnable {
        private final KeyResource keyResource;
        private final MutexLockFactory lock;

        public void run() {
            long start = System.nanoTime();
            for (; ; ) {
                try {
                    lock.workWithLock("mockresource-id", TimeUnit.MILLISECONDS, 0, new OperationWithinLock() {
                        @Override
                        public void execute() {
                            keyResource.process();
                            keyResource.releaseProcess();
                        }
                    });
                } catch (MutexLockLockException e) {
                    log.error("无法获取锁", e);
                } catch (Throwable e) {
                    log.info("Again again and again");
                } finally {
                    ThreadUtil.safeSleep(300);//暂停300ms不工作
                }
            }
        }
    }

    class KeyResource {

        /**
         * 记录处理“被保护资源”的数量， 该数量要永远<=1 (永远只有0或1个处理器能够处理资源)
         */
        private int handleProcess = 0;

        private List<String> history = new ArrayList<>();

        public synchronized void process() {
            handleProcess += 1;
            statisticsProcess();
            ThreadUtil.safeSleep(100);//模拟真实操作时间
        }

        public synchronized void statisticsProcess() {
            history.add("" + handleProcess);
        }

        public synchronized void releaseProcess() {
            handleProcess -= 1;
        }


    }

}