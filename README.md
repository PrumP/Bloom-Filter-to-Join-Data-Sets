# Bloom-Filter-to-Join-Data-Sets
Bloom Filter is a probabilistic, memory-efficient data structure.Here I am implementing a Bloom Filter and use to it do some primitive document search.
Bloom Filter is implemented in two ways; 

(1) using deterministic hash functions. 

(2) using random hash functions.

False positivity of the two filters are empirically evaluated.

Later the implemented bloomfilters are used to join two tables in different servers. Main idea is to reduce the communications cost.
The first server creates a bloom filter consisting all values corresponding to the join attribute a1 and send to second server.
Second server create a new relation R with <a1,a2> if join attribute exists in its table. Then second server sends R to first server to do final joining. 
Compare to initial table size, R could be much smaller which decreases communication cost.
