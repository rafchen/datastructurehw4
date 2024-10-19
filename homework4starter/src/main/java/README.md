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

Part II: 
Strategy 1 is correct. Inserting all of the elements into a max heap then removing the best element k times would get you the kth greatest element. Inserting into the heap takes O(nlogn) time because each insertion takes O(logn) time, and you need to repeat it n times. Removing from the max heap is a O(logn) operation since everytime you remove the largest value you need to siftdown to maintain maxheap properties. Repeating this k times costs O(klogn) would get you the kth largest value. Since a heap is a complete BST, it would take O(logn) to get the max value to the root. The time complexity is O(nlogn) + O(klogn) = O(nlogn) time. The space complexity is O(n) since each element of the array of n intergers needs to be stored on the heap.

Time Complexity: O(nlogn)
Space Complexity: O(n)

Strategy 2 is also correct. If you insert the first k elements into a min heap then compare each reamining n-k element to the min value, replacing it with the larger element, you are essentially saving the k largest numbers in a min heap after all comparisons are made, with the smallest of the k largest numbers being at the root. This means that this is the n-kth largest element, aka the kth largest element in the array. When you remove from a heap, you need to maintain the min heap property, which takes O(logk) time since the height of a heap is a complete tree. The space complexity is O(k) because the heap stores k elements.


Time Complexity: O(nlogk)
Space Complexity: O(k) 