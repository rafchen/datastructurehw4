# Discussion
Part I:
hotel_california.txt
Benchmark                  Mode  Cnt  Score    
JmhRuntimeTest.arrayMap    avgt    2  0.365       
JmhRuntimeTest.avlTreeMap  avgt    2  0.280      
JmhRuntimeTest.bstMap      avgt    2  0.282      
JmhRuntimeTest.treapMap    avgt    2  0.349          

moby_dick.txt
Benchmark                  Mode  Cnt     Score          
JmhRuntimeTest.arrayMap    avgt    2  3794.439        
JmhRuntimeTest.avlTreeMap  avgt    2   150.512         
JmhRuntimeTest.bstMap      avgt    2   162.893      
JmhRuntimeTest.treapMap    avgt    2   225.010  

federalost01.txt
JmhRuntimeTest.arrayMap    avgt    2  2.662         
JmhRuntimeTest.avlTreeMap  avgt    2  1.399     
JmhRuntimeTest.bstMap      avgt    2  1.347   
JmhRuntimeTest.treapMap    avgt    2  1.780   

From this, I found that all of them took approximately the same time to run (4 minutes) likely due to how JmhRuntime is set up.
With shorter texts, the choice of map structure doesnt matter as much, but the differences become more apparent with longer texts.
The longer texts like Moby Dick generated higher scores because it has more words that need to be mapped. The AVL TreeMap and BST Map performed consistently well, with AVL trees getting an edge, notably in longer texts. This is because AVL tree maps are self balancing, so the performance of its operations will be O(logN), BST maps can be O(N) in worst case. Treap is pretty good, however, its slower than the binary trees likely because it requires the priority balancing overhead, which comes with a slight computational time. ArrayMaps have insert and remove operations at O(N) so as the number of words increases, the time that it grows linearly, significantly slower than the other data structures. 
