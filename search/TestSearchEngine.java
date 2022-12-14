package search;

import data.DataId;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestSearchEngine {
    private static final String ERROR_DOCUMENT_ID = "Error: The document ID should be a number.";
    private static final String DOCUMENT_NOT_EXIST = "Error: The document doesn't exist.";
    private SearchEngine searchEngine;

    private static final String TEST_DOCUMENT1 = "Test document";
    private static final DataId DOCUMENT1 = new DataId();
    private static final String TEST_DOCUMENT2 = "Test Test Test document!";
    private static final DataId DOCUMENT2 = new DataId();
    private static final String TEST_DOCUMENT3 = "Test document Test document Test document";
    private static final DataId DOCUMENT3 = new DataId();
    private static final String HEBREW_DOCUMENT = "בדיקה מסמך!";
    private static final DataId HEBREW_DOCUMENT4 = new DataId();
    private static final String SPECIAL_DOCUMENT = "@#$ %^& !@# ()*& .., ,       -= /\\ ";
    private static final DataId SPECIAL_DOCUMENT5 = new DataId();
    private static final String EMPTY_DOCUMENT = "";
    private static final DataId EMPTY_DOCUMENT7 = new DataId();
    private static final String UPPER_DOCUMENT = "UPPERCASE AAA.";
    private static final DataId UPPER_DOCUMENT6 = new DataId();
    private static final String PIZZA = "My favorite Pizza, I like tomato.";
    private static final DataId PIZZA_ID = new DataId();
    private static final String RICE = "I love Rice, I eat fast";
    private static final DataId RICE_ID = new DataId();
    private static final String MEAT = "I eat meat always, love meat";
    private static final DataId MEAT_ID = new DataId();
    private static final DataId ELASTIC_SEARCH_ID = new DataId();
    private static final String ELASTIC_SEARCH = "Elasticsearch can be used to search any kind of document. It provides scalable search, has near real-time search, and supports multitenancy.[4] \"Elasticsearch is distributed, which means that indices can be divided into shards and each shard can have zero or more replicas. Each node hosts one or more shards and acts as a coordinator to delegate operations to the correct shard(s). Rebalancing and routing are done automatically\".[4] Related data is often stored in the same index, which consists of one or more primary shards, and zero or more replica shards. Once an index has been created, the number of primary shards cannot be changed.[23]\n" +
            "\n" +
            "Elasticsearch is developed alongside the data collection and log-parsing engine Logstash, the analytics and visualization platform Kibana, and the collection of lightweight data shippers called Beats. The four products are designed for use as an integrated solution, referred to as the \"Elastic Stack\".[24] (Formerly the \"ELK stack\", short for \"Elasticsearch, Logstash, Kibana\".)\n" +
            "\n" +
            "Elasticsearch uses Lucene and tries to make all its features available through the JSON and Java API. It supports facetting and percolating (a form of prospective search),[25] [26] which can be useful for notifying if new documents match for registered queries. Another feature, \"gateway\", handles the long-term persistence of the index;[27] for example, an index can be recovered from the gateway in the event of a server crash. Elasticsearch supports real-time GET requests, which makes it suitable as a NoSQL datastore,[28] but it lacks distributed transactions.[29]\n" +
            "On 20 May 2019, Elastic made the core security features of the Elastic Stack available free of charge, including TLS for encrypted communications, file and native realm for creating and managing users, and role-based access control for controlling user access to cluster APIs and indexes.[30] The corresponding source code is available under the “Elastic License”, a source-available license.[31] In addition, Elasticsearch now offers SIEM[32] and Machine Learning [33] as part of its offered services." +
            "Elasticsearch is a search engine based on the Lucene library. It provides a distributed, multitenant-capable full-text search engine with an HTTP web interface and schema-free JSON documents. Elasticsearch is developed in Java and is dual-licensed under the source-available Server Side Public License and the Elastic license,[2] while other parts[3] fall under the proprietary (source-available) Elastic License. Official clients are available in Java, .NET (C#), PHP, Python, Apache Groovy, Ruby and many other languages.[4] According to the DB-Engines ranking, Elasticsearch is the most popular enterprise search engine" +
            "Shay Banon created the precursor to Elasticsearch, called Compass, in 2004.[6] While thinking about the third version of Compass he realized that it would be necessary to rewrite big parts of Compass to \"create a scalable search solution\".[6] So he created \"a solution built from the ground up to be distributed\" and used a common interface, JSON over HTTP, suitable for programming languages other than Java as well.[6] Shay Banon released the first version of Elasticsearch in February 2010.[7]\n" +
            "\n" +
            "Elastic NV was founded in 2012 to provide commercial services and products around Elasticsearch and related software.[8] In June 2014, the company announced raising $70 million in a Series C funding round, just 18 months after forming the company. The round was led by New Enterprise Associates (NEA). Additional funders include Benchmark Capital and Index Ventures. This round brought total funding to $104M.[9]\n" +
            "\n" +
            "In March 2015, the company Elasticsearch changed its name to Elastic.[10]\n" +
            "\n" +
            "In June 2018, Elastic filed for an initial public offering with an estimated valuation of between 1.5 and 3 billion dollars.[11] On 5 October 2018, Elastic was listed on the New York Stock Exchange.[12]" +
            "In January 2021, Elastic announced that starting with version 7.11, they would be relicensing their Apache 2.0 licensed code in Elasticsearch and Kibana to be dual licensed under Server Side Public License and the Elastic License, neither of which is recognized as an open-source license.[14][15] Elastic blamed Amazon Web Services (AWS) for this change, objecting to AWS offering Elasticsearch and Kibana as a service directly to consumers and claiming that AWS was not appropriately collaborating with Elastic.[15][16] Critics of the re-licensing decision predicted that it would harm Elastic's ecosystem and noted that Elastic had previously promised to \"never....change the license of the Apache 2.0 code of Elasticsearch, Kibana, Beats, and Logstash\". Amazon responded with plans to fork the projects and continue development under Apache License 2.0.[2][17] Other users of the ElasticSearch ecosystem, including Logz.io, CrateDB and Aiven, also committed to the need for a fork, leading to a discussion of how to coordinate the open source efforts.[18][19][20] Due to potential trademark issues with using the name \"Elasticsearch\", AWS rebranded their fork as OpenSearch in April 2021";
    private static final DataId BINARY_SEARCH_ID = new DataId();
    private static final String BINARY_SEARCH ="In computer science, binary search, also known as half-interval search,[1] logarithmic search,[2] or binary chop,[3] is a search algorithm that finds the position of a target value within a sorted array.[4][5] Binary search compares the target value to the middle element of the array. If they are not equal, the half in which the target cannot lie is eliminated and the search continues on the remaining half, again taking the middle element to compare to the target value, and repeating this until the target value is found. If the search ends with the remaining half being empty, the target is not in the array."+
            "Binary search runs in logarithmic time in the worst case, making  comparisons, where displaystyle n}n is the number of elements in the array.[a][6] Binary search is faster than linear search except for small arrays. However, the array must be sorted first to be able to apply binary search. There are specialized data structures designed for fast searching, such as hash tables, that can be searched more efficiently than binary search. However, binary search can be used to solve a wider range of problems, such as finding the next-smallest or next-largest element in the array relative to the target even if it is absent from the array." +
            "There are numerous variations of binary search. In particular, fractional cascading speeds up binary searches for the same value in multiple arrays. Fractional cascading efficiently solves a number of search problems in computational geometry and in numerous other fields. Exponential search extends binary search to unbounded lists. The binary search tree and B-tree data structures are based on binary search." +
            "Binary search works on sorted arrays. Binary search begins by comparing an element in the middle of the array with the target value. If the target value matches the element, its position in the array is returned. If the target value is less than the element, the search continues in the lower half of the array. If the target value is greater than the element, the search continues in the upper half of the array. By doing this, the algorithm eliminates the half in which the target value cannot lie in each iteration";
    private static final DataId INTERPOLATION_SEARCH_ID = new DataId();
    private static final String INTERPOLATION_SEARCH ="Interpolation search is an algorithm for searching for a key in an array that has been ordered by numerical values assigned to the keys (key values). It was first described by W. W. Peterson in 1957.[1] Interpolation search resembles the method by which people search a telephone directory for a name (the key value by which the book's entries are ordered): in each step the algorithm calculates where in the remaining search space the sought item might be, based on the key values at the bounds of the search space and the value of the sought key, usually via a linear interpolation. The key value actually found at this estimated position is then compared to the key value being sought. If it is not equal, then depending on the comparison, the remaining search space is reduced to the part before or after the estimated position. This method will only work if calculations on the size of differences between key values are sensible." +
            "By comparison, binary search always chooses the middle of the remaining search space, discarding one half or the other, depending on the comparison between the key found at the estimated position and the key sought — it does not require numerical values for the keys, just a total order on them. The remaining search space is reduced to the part before or after the estimated position. The linear search uses equality only as it compares elements one-by-one from the start, ignoring any sorting.\n" +
            "\n" +
            "On average the interpolation search makes about log(log(n)) comparisons (if the elements are uniformly distributed), where n is the number of elements to be searched. In the worst case (for instance where the numerical values of the keys increase exponentially) it can make up to O(n) comparisons.\n" +
            "\n" +
            "In interpolation-sequential search, interpolation is used to find an item near the one being searched for, then linear search is used to find the exact item." +
            "Using big-O notation, the performance of the interpolation algorithm on a data set of size n is O(n); however under the assumption of a uniform distribution of the data on the linear scale used for interpolation, the performance can be shown to be O(log log n).[3][4][5] However, Dynamic Interpolation Search is possible in o(log log n) time using a novel data structure.[6]\n" +
            "\n" +
            "Practical performance of interpolation search depends on whether the reduced number of probes is outweighed by the more complicated calculations needed for each probe. It can be useful for locating a record in a large sorted file on disk, where each probe involves a disk seek and is much slower than the interpolation arithmetic.\n" +
            "\n" +
            "Index structures like B-trees also reduce the number of disk accesses, and are more often used to index on-disk data in part because they can index many types of data and can be updated online. Still, interpolation search may be useful when one is forced to search certain sorted but unindexed on-disk datasets." +
            "When sort keys for a dataset are uniformly distributed numbers, linear interpolation is straightforward to implement and will find an index very near the sought value.\n" +
            "\n" +
            "On the other hand, for a phone book sorted by name, the straightforward approach to interpolation search does not apply. The same high-level principles can still apply, though: one can estimate a name's position in the phone book using the relative frequencies of letters in names and use that as a probe location.\n" +
            "\n" +
            "Some interpolation search implementations may not work as expected when a run of equal key values exists. The simplest implementation of interpolation search won't necessarily select the first (or last) element of such a run.";
    private static final DataId EXPONENTIAL_SEARCH_ID = new DataId();
    private static final String EXPONENTIAL_SEARCH = "In computer science, an exponential search (also called doubling search or galloping search or Struzik search)[1] is an algorithm, created by Jon Bentley and Andrew Chi-Chih Yao in 1976, for searching sorted, unbounded/infinite lists.[2] There are numerous ways to implement this with the most common being to determine a range that the search key resides in and performing a binary search within that range. This takes O(log i) where i is the position of the search key in the list, if the search key is in the list, or the position where the search key should be, if the search key is not in the list.\n" +
            "\n" +
            "Exponential search can also be used to search in bounded lists. Exponential search can even out-perform more traditional searches for bounded lists, such as binary search, when the element being searched for is near the beginning of the array. This is because exponential search will run in O(log i) time, where i is the index of the element being searched for in the list, whereas binary search would run in O(log n) time, where n is the number of elements in the list." +
            "Exponential search allows for searching through a sorted, unbounded list for a specified input value (the search \"key\"). The algorithm consists of two stages. The first stage determines a range in which the search key would reside if it were in the list. In the second stage, a binary search is performed on this range. In the first stage, assuming that the list is sorted in ascending order, the algorithm looks for the first exponent, j, where the value 2j is greater than the search key. This value, 2j becomes the upper bound for the binary search with the previous power of 2, 2j - 1, being the lower bound for the binary search.[3]" +
            "In each step, the algorithm compares the search key value with the key value at the current search index. If the element at the current index is smaller than the search key, the algorithm repeats, skipping to the next search index by doubling it, calculating the next power of 2.[3] If the element at the current index is larger than the search key, the algorithm now knows that the search key, if it is contained in the list at all, is located in the interval formed by the previous search index, 2j - 1, and the current search index, 2j. The binary search is then performed with the result of either a failure, if the search key is not in the list," +
            " or the position of the search key in the list. " +
            "Bentley and Yao generalize this variation into one where any number, k, of binary searches are performed during the first stage of the algorithm, giving the k-nested binary search variation. The asymptotic runtime does not change for the variations, running in O(log i) time, as with the original exponential search algorithm.\n" +
            "\n" +
            "Also, a data structure with a tight version of the dynamic finger property can be given when the above result of the k-nested binary search is used on a sorted array.[4] Using this, the number of comparisons done during a search is log (d) + log log (d) + ... + O(log *d), where d is the difference in rank between the last element that was accessed and the current element being accessed.";

    private static final DataId COMBINATORIAL_SEARCH_ID = new DataId();
    private static final String COMBINATORIAL_SEARCH = "In computer science and artificial intelligence, combinatorial search studies search algorithms for solving instances of problems that are believed to be hard in general, by efficiently exploring the usually large solution space of these instances. Combinatorial search algorithms achieve this efficiency by reducing the effective size of the search space or employing heuristics. Some algorithms are guaranteed to find the optimal solution, while others may only return the best solution found in the part of the state space that was explored.\n" +
            "\n" +
            "Classic combinatorial search problems include solving the eight queens puzzle or evaluating moves in games with a large game tree, such as reversi or chess.\n" +
            "\n" +
            "A study of computational complexity theory helps to motivate combinatorial search. Combinatorial search algorithms are typically concerned with problems that are NP-hard. Such problems are not believed to be efficiently solvable in general. However, the various approximations of complexity theory suggest that some instances (e.g. \"small\" instances) of these problems could be" +
            " efficiently solved. This is indeed the case, and such instances often have important practical ramifications." +
            "Lookahead is an important component of combinatorial search, which specifies, roughly, how deeply the graph representing the problem is explored. The need for a specific limit on lookahead comes from the large problem graphs in many applications, such as computer chess and computer Go. A naive breadth-first search of these graphs would quickly consume all the memory of any modern computer. By setting a specific lookahead limit, the algorithm's time can be carefully controlled; its time increases exponentially as the lookahead limit increases.\n" +
            "\n" +
            "More sophisticated search techniques such as alpha-beta pruning are able to eliminate entire subtrees of the search tree from consideration. When these techniques are used, lookahead is not a precisely defined quantity, but instead either the maximum depth searched or some type of average.";
    @Before
    public void createObject() {
        DataId dataId = new DataId();
        dataId.resetCounter();
        searchEngine = new SearchEngine();
    }

    @Test
    public void addAllRecipes() {
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        assertEquals(3, searchEngine.docTermsMap.size());
        assertEquals(3, searchEngine.idDocTextMap.size());

    }

    @Test
    public void simpleAdd() {
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        assertEquals(3, searchEngine.docTermsMap.size());
        assertEquals(3, searchEngine.idDocTextMap.size());

    }

    @Test
    public void specialAdd() {
        searchEngine.addDocument(SPECIAL_DOCUMENT5, SPECIAL_DOCUMENT);
        searchEngine.addDocument(HEBREW_DOCUMENT4, HEBREW_DOCUMENT);
        searchEngine.addDocument(EMPTY_DOCUMENT7, EMPTY_DOCUMENT);
        assertEquals(3, searchEngine.docTermsMap.size());
        assertEquals(3, searchEngine.idDocTextMap.size());

    }

    @Test
    public void doubleAdd() {
        searchEngine.addDocument(SPECIAL_DOCUMENT5, SPECIAL_DOCUMENT);
        searchEngine.addDocument(SPECIAL_DOCUMENT5, SPECIAL_DOCUMENT);
        assertEquals(1, searchEngine.docTermsMap.size());
        assertEquals(1, searchEngine.idDocTextMap.size());
    }

    @Test
    public void simpleDelete() {
        DataId dataId = new DataId();
        dataId.resetCounter();
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        assertEquals(3, searchEngine.docTermsMap.size());
        searchEngine.deleteDocument("1");
        assertEquals(2, searchEngine.docTermsMap.size());
        searchEngine.deleteDocument("2");
        assertEquals(1, searchEngine.docTermsMap.size());
        searchEngine.deleteDocument("3");
        assertEquals(0, searchEngine.docTermsMap.size());
    }

    @Test
    public void specialDelete() {
        createObject();
        searchEngine.addDocument(SPECIAL_DOCUMENT5, SPECIAL_DOCUMENT);
        searchEngine.addDocument(HEBREW_DOCUMENT4, HEBREW_DOCUMENT);
        assertEquals(2, searchEngine.docTermsMap.size());
        searchEngine.deleteDocument("5");
        assertEquals(1, searchEngine.docTermsMap.size());
        searchEngine.deleteDocument("4");
        assertEquals(0, searchEngine.docTermsMap.size());
    }

    @Test
    public void impossibleDelete() {
        assertEquals(DOCUMENT_NOT_EXIST, searchEngine.deleteDocument("1"));
        assertEquals(ERROR_DOCUMENT_ID, searchEngine.deleteDocument("A"));
        assertEquals(ERROR_DOCUMENT_ID, searchEngine.deleteDocument("%"));
    }

    @Test
    public void simpleGet() {
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        assertEquals(TEST_DOCUMENT1, searchEngine.getDocument("1"));
        assertEquals(TEST_DOCUMENT2, searchEngine.getDocument("2"));
        assertEquals(TEST_DOCUMENT3, searchEngine.getDocument("3"));
        assertEquals(3, searchEngine.docTermsMap.size());
    }

    @Test
    public void specialGet() {
        searchEngine.addDocument(SPECIAL_DOCUMENT5, SPECIAL_DOCUMENT);
        searchEngine.addDocument(HEBREW_DOCUMENT4, HEBREW_DOCUMENT);
        assertEquals(HEBREW_DOCUMENT, searchEngine.getDocument("4"));
        assertEquals(SPECIAL_DOCUMENT, searchEngine.getDocument("5"));
        assertEquals(2, searchEngine.docTermsMap.size());
    }

    @Test
    public void impossibleGet() {
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        assertEquals(DOCUMENT_NOT_EXIST, searchEngine.getDocument("1"));
        assertEquals(ERROR_DOCUMENT_ID, searchEngine.getDocument("A"));
        assertEquals(ERROR_DOCUMENT_ID, searchEngine.getDocument("%"));
    }

    @Test
    public void simpleUpdate() {
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        searchEngine.updateDocument("1", TEST_DOCUMENT2);
        searchEngine.updateDocument("2", TEST_DOCUMENT3);
        searchEngine.updateDocument("3", TEST_DOCUMENT1);
        assertEquals(TEST_DOCUMENT1, searchEngine.getDocument("3"));
        assertEquals(TEST_DOCUMENT3, searchEngine.getDocument("2"));
        assertEquals(TEST_DOCUMENT2, searchEngine.getDocument("1"));
        assertEquals(3, searchEngine.docTermsMap.size());
    }

    @Test
    public void specialUpdate() {
        searchEngine.addDocument(SPECIAL_DOCUMENT5, SPECIAL_DOCUMENT);
        searchEngine.addDocument(HEBREW_DOCUMENT4, HEBREW_DOCUMENT);
        searchEngine.updateDocument("5", HEBREW_DOCUMENT);
        searchEngine.updateDocument("4", SPECIAL_DOCUMENT);
        assertEquals(SPECIAL_DOCUMENT, searchEngine.getDocument("4"));
        assertEquals(HEBREW_DOCUMENT, searchEngine.getDocument("5"));
        assertEquals(2, searchEngine.docTermsMap.size());
    }

    @Test
    public void impossibleUpdate() {
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        assertEquals(DOCUMENT_NOT_EXIST, searchEngine.updateDocument("1", TEST_DOCUMENT2));
        assertEquals(ERROR_DOCUMENT_ID, searchEngine.getDocument("A"));
        assertEquals(ERROR_DOCUMENT_ID, searchEngine.getDocument("%"));
    }

    @Test
    public void testFindDocumentEmpty() {
        assertEquals(new HashSet<DataId>(), searchEngine.findDocuments("NoOne"));
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        assertEquals(new HashSet<DataId>(), searchEngine.findDocuments("StillNo"));
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        assertEquals(new HashSet<DataId>(), searchEngine.findDocuments("document."));
        assertEquals(2, searchEngine.findDocuments("document").size());
    }

    @Test
    public void testFindDocument()
    {
        HashSet<DataId> checkSet = new HashSet<>();
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(DOCUMENT3, TEST_DOCUMENT3);
        checkSet.add(DOCUMENT1);
        checkSet.add(DOCUMENT2);
        checkSet.add(DOCUMENT3);
        assertEquals(checkSet, searchEngine.findDocuments("test"));
    }

    @Test
    public void testUpperCase()
    {
        HashSet<DataId> checkSet = new HashSet<>();
        searchEngine.addDocument(UPPER_DOCUMENT6, UPPER_DOCUMENT);
        checkSet.add(UPPER_DOCUMENT6);
        assertEquals(checkSet, searchEngine.findDocuments("aaa"));
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        checkSet.add(DOCUMENT2);
        checkSet.remove(UPPER_DOCUMENT6);
        assertEquals(checkSet, searchEngine.findDocuments("TEST"));
    }

    @Test
    public void countTimes()
    {
        searchEngine.addDocument(DOCUMENT1, TEST_DOCUMENT1);
        searchEngine.addDocument(DOCUMENT2, TEST_DOCUMENT2);
        searchEngine.addDocument(SPECIAL_DOCUMENT5, SPECIAL_DOCUMENT);
        searchEngine.addDocument(EMPTY_DOCUMENT7, EMPTY_DOCUMENT);
        assertEquals(0, searchEngine.countsWordInDocument(DOCUMENT1, "NoOne"));
        assertEquals(3, searchEngine.countsWordInDocument(DOCUMENT2, "test"));
        assertEquals(1, searchEngine.countsWordInDocument(DOCUMENT2, "document"));
        assertEquals(0, searchEngine.countsWordInDocument(SPECIAL_DOCUMENT5, ","));
        assertEquals(0, searchEngine.countsWordInDocument(EMPTY_DOCUMENT7, ""));
    }


    @Test
    public void searchWordNotExistAllDocument()
    {
        searchEngine.addDocument(PIZZA_ID, PIZZA);
        searchEngine.addDocument(RICE_ID, RICE);
        searchEngine.addDocument(MEAT_ID, MEAT);
        TreeMap<DataId, Double> sortedSearch = searchEngine.search("NO ONE OF THIS WORDS DOESN'T EXIST");
        Iterator<Map.Entry<DataId, Double> > itr = sortedSearch.entrySet().iterator();
        assertEquals(0.0, itr.next().getValue(), 0.0);
        assertEquals(0.0, itr.next().getValue(), 0.0);
        assertEquals(0.0, itr.next().getValue(), 0.0);
    }
    @Test
    public void testSearch()
    {
        searchEngine.addDocument(PIZZA_ID, PIZZA);
        searchEngine.addDocument(RICE_ID, RICE);
        searchEngine.addDocument(MEAT_ID, MEAT);
        TreeMap<DataId, Double> sortedSearch = searchEngine.search("Meat");
        Iterator<Map.Entry<DataId, Double> > itr = sortedSearch.entrySet().iterator();
        Map.Entry<DataId, Double> first = itr.next();
        assertEquals(1.386, first.getValue(), 0.001);
        assertEquals(MEAT_ID, first.getKey());
        assertEquals(0.0, itr.next().getValue(), 0.0);
        assertEquals(0.0, itr.next().getValue(), 0.0);
    }


    @Test
    public void searchWordExistAllDocument()
    {
        searchEngine.addDocument(PIZZA_ID, PIZZA);
        searchEngine.addDocument(RICE_ID, RICE);
        searchEngine.addDocument(MEAT_ID, MEAT);
        TreeMap<DataId, Double> sortedSearch = searchEngine.search("i");
        Iterator<Map.Entry<DataId, Double> > itr = sortedSearch.entrySet().iterator();
        assertEquals(0.0, itr.next().getValue(), 0.0);
        assertEquals(0.0, itr.next().getValue(), 0.0);
        assertEquals(0.0, itr.next().getValue(), 0.0);
    }

    @Test
    public void wordAppearsMoreTimeInSpecific()
    {
        searchEngine.addDocument(PIZZA_ID, PIZZA);
        searchEngine.addDocument(RICE_ID, RICE);
        searchEngine.addDocument(MEAT_ID, MEAT);
        TreeMap<DataId, Double> sortedSearch = searchEngine.search("i eat ALWAYS");
        Iterator<Map.Entry<DataId, Double> > itr = sortedSearch.entrySet().iterator();
        Map.Entry<DataId, Double> first = itr.next();
        assertEquals(0.9808, first.getValue(), 0.001);
        assertEquals(MEAT_ID, first.getKey());
        Map.Entry<DataId, Double> second = itr.next();
        assertEquals(0.2876, second.getValue(), 0.001);
        assertEquals(RICE_ID, second.getKey());
        Map.Entry<DataId, Double> third = itr.next();
        assertEquals(0.0, third.getValue(), 0.0);
        assertEquals(PIZZA_ID, third.getKey());
    }

    @Test
    public void searchInLargeData()
    {
        searchEngine.addDocument(ELASTIC_SEARCH_ID, ELASTIC_SEARCH);
        searchEngine.addDocument(COMBINATORIAL_SEARCH_ID, COMBINATORIAL_SEARCH);
        searchEngine.addDocument(EXPONENTIAL_SEARCH_ID, EXPONENTIAL_SEARCH);
        searchEngine.addDocument(INTERPOLATION_SEARCH_ID, INTERPOLATION_SEARCH);
        searchEngine.addDocument(BINARY_SEARCH_ID, BINARY_SEARCH);
        long start = System.currentTimeMillis();
        TreeMap<DataId, Double> sortedSearch = searchEngine.search("sophisticated search techniques used to" +
                " search in bounded lists, usually more efficient for searching as binary" +
                "numerous ways to implement this with the biggest, while others may only return the best solution found");
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time take large data to find: " + elapsedTime/100F + " seconds.");
    }
}
