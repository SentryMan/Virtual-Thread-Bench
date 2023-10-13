# Virtual Thread Bench

Test repo that sees how the new loom/structured concurrency stuff compares to regular ThreadPool on a servlet.

 # JMX Results

Tests ran on 
 - AMD Ryzen 5 3600
 - 16 GB RAM
 - Windows 10

Running a 5-minute JMX test with 5000 threads gives these results. (Running longer seemed futile as regular threads were clearly losing by every metric.)

<img src="summary.PNG">

### Response Time Graph with regular thread pool
<img src="Response Time Graph Regular.png" width="720">

### Response Time Graph with virtual thread pool
<img src="Response Time Graph VT.png" width="720">

As expected, virtual threads are amazing

 # Frog
 
<img src="image.png" width="420">


