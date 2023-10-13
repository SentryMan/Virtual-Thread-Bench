# Virtual Thread Bench

Test repo that sees how the new loom/structured concurrency stuff compares to regular THreaPool on a servlet.


 # JMX Results

Running a 5 minute JMX test with 5000 threads gives this for me. Running longer seemed futile as regular threads were clearly losing by every metric.

<img src="summary.PNG">

As expected, virtual threads are legendary

 # Frog
 
<img src="image.png" width="420">


