# Bloom-Filter-to-Join-Data-Sets
Bloom Filter is a probabilistic, memory-efficient data structure.Here I am implementing a Bloom Filter and use to it do some primitive document search.
Bloom Filter is implemented in two ways; 

(1) using deterministic hash functions. 

(2) using random hash functions.


The implemented bloomfilters are used to join two tables in different servers. Main idea is to reduce the communications cost.
The first server creates a bloom filter consisting all values corresponding to the join attribute a1 and send to second server.
Second server create a new relation R with <a1,a2> if join attribute exists in its table. Then second server sends R to first server to do final joining. 
Compare to initial table size, R could be much smaller which decreases communication cost.

### False positivity of the two filters

| BpE | N     | T    | FPDetF  | FPRanF  |
|-----|-------|------|---------|---------|
| 4   | 25000 | 2500 | 0.16360 | 0.16040 |
| 4   | 30000 | 3000 | 0.14800 | 0.15567 |
| 8   | 25000 | 2500 | 0.02440 | 0.02920 |
| 8   | 30000 | 3000 | 0.02367 | 0.02600 |
| 10  | 25000 | 2500 | 0.00880 | 0.01200 |
| 10  | 30000 | 3000 | 0.01000 | 0.01267 |

* Bpe - Bits per Element*
* N - Random strings 
* T - Random strings not in N
* FPDetF - False Positives using Detemnistic Filter
* FPRanF - False Positives using Random Filter
