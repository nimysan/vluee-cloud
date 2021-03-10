### 错误分析

###

>>> [分析方法](https://www.jianshu.com/p/b9241e7a9eda)

```
2021-03-10 18:28:38.282ERROR 25048---[ioEventLoop-4-2]io.netty.util.ResourceLeakDetector:LEAK:ByteBuf.release()was not called before it's garbage-collected. See https://netty.io/wiki/reference-counted-objects.html for more information.
        Recent access records:
        Created at:
        io.netty.buffer.PooledByteBufAllocator.newDirectBuffer(PooledByteBufAllocator.java:363)
        io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:187)
        io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:178)
        io.netty.buffer.AbstractByteBufAllocator.ioBuffer(AbstractByteBufAllocator.java:139)
        io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator$MaxMessageHandle.allocate(DefaultMaxMessagesRecvByteBufAllocator.java:114)
        io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:147)
        io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:714)
        io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650)
        io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576)
        io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)
        io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
        io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
        io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        java.base/java.lang.Thread.run(Thread.java:832)
        2021-03-10 18:35:54.883WARN 25048---[ctor-http-nio-6]reactor.netty.channel.FluxReceive:[id:0xe8fa8768,L:/127.0.0.1:8080-R:/127.0.0.1:49284]An exception has been observed post termination,use DEBUG level to see the full stack:java.net.SocketException:Connection reset
        2021-03-10 18:35:54.886WARN 25048---[ctor-http-nio-4]reactor.netty.channel.FluxReceive:[id:0xc8d547b6,L:/127.0.0.1:8080-R:/127.0.0.1:49282]An exception has been observed post termination,use DEBUG level to see the full stack:java.net.SocketException:Connection reset
        2021-03-10 18:35:54.886WARN 25048---[ctor-http-nio-8]reactor.netty.channel.FluxReceive:[id:0x809af045,L:/127.0.0.1:8080-R:/127.0.0.1:49278]An exception has been observed post termination,use DEBUG level to see the full stack:java.net.SocketException:Connection reset
        2021-03-10 18:35:54.886WARN 25048---[ctor-http-nio-3]reactor.netty.channel.FluxReceive:[id:0x365ce457,L:/127.0.0.1:8080-R:/127.0.0.1:49281]An exception has been observed post termination,use DEBUG level to see the full stack:java.net.SocketException:Connection reset
        2021-03-10 18:35:54.886WARN 25048---[ctor-http-nio-7]reactor.netty.channel.FluxReceive:[id:0x78a2f3a0,L:/127.0.0.1:8080-R:/127.0.0.1:49277]An exception has been observed post termination,use DEBUG level to see the full stack:java.net.SocketException:Connection reset
        2021-03-10 18:35:54.886WARN 25048---[ctor-http-nio-1]reactor.netty.channel.FluxReceive:[id:0x78096ec4,L:/127.0.0.1:8080-R:/127.0.0.1:49279]An exception has been observed post termination,use DEBUG level to see the full stack:java.net.SocketException:Connection reset

```