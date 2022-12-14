package search;

import compareScore.CompareTfIdf;
import data.DataId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * SearchEngine is class provides search capabilities small
 * datasets of free text fields.
 */
public class SearchEngine
{
    private static final String ERROR_DOCUMENT_ID = "Error: The document ID should be a number.";
    private static final String DOCUMENT_NOT_EXIST = "Error: The document doesn't exist.";
    private static final String UPDATE_DOCUMENT = "The Document has updated.";
    private static final String DELETE_DOCUMENT = "The Document has deleted.";
    private static final String ADD_DOCUMENT = "The Document has added.";
    private static final String ERROR_TOP_N = "Error: Should be a number.";
    private static final String TOP_RESULTS_1 = "The top ";
    private static final String TOP_RESULTS_2 = " results </BR> ============== </BR>";
    private static final String EMPTY_STRING = "";

    /**
     *  HashMap between Id document to HashMap how many times each word appears in the document.
     */
    protected Map<DataId, Map<String, Integer>> docTermsMap;
    /**
     *  HashMap between Id document and the original text.
     */
    protected Map<DataId, String> idDocTextMap;

    /**
     * Constructor for the search engine class.
     */
    public SearchEngine()
    {
        docTermsMap = new ConcurrentHashMap<>();
        idDocTextMap = new ConcurrentHashMap<>();
    }

    /**
     * Get the text of a document according his ID.
     * @param id - number as a String represent an document.
     * @return String of the status.
     */
    public String getDocument(String id) {
        try {
            int intId = Integer.parseInt(id);
            DataId dataId = new DataId(intId);
            if (docTermsMap.containsKey(dataId)){
                return idDocTextMap.get(dataId);
            }
        } catch (NumberFormatException e) {
            return ERROR_DOCUMENT_ID;
        }
        return DOCUMENT_NOT_EXIST;
    }

    /**
     * Update the text document according his ID.
     * @param id - number as a String represent an document.
     * @param text - number as a String represent an document.
     * @return String of the status.
     */
    public String updateDocument(String id, String text) {
        try {
            int intId = Integer.parseInt(id);
            DataId dataId = new DataId(intId);
            if (docTermsMap.containsKey(dataId)){
                addDocument(dataId, text);
                return UPDATE_DOCUMENT;
            }
        } catch (NumberFormatException e) {
            return ERROR_DOCUMENT_ID;
        }
        return DOCUMENT_NOT_EXIST;
    }


    /**
     * Adding a new document to the database, add the document to both HashMaps.
     * @param dataId the new id document.
     * @param text the text of the document.
     * @return String of the status.
     */
    public String addDocument(DataId dataId, String text)
    {
        Map<String, Integer> map2 = new HashMap<>();
        docTermsMap.put(dataId, map2);
        idDocTextMap.put(dataId, text);
        String[] words = text.toLowerCase().split("\\W+");
        for (String word : words)
        {
            if (word.equals("")){
                continue;
            }
            if (map2.containsKey(word))
            {
                map2.put(word, map2.get(word) + 1);
            } else
            {
                map2.put(word, 1);
            }
        }
        return ADD_DOCUMENT;
    }

    /**
     * Deleting a document form database.
     * @param id id document to delete.
     * @return String of the status.
     */
    public String deleteDocument(String id)
    {
        try {
            int intId = Integer.parseInt(id);
            DataId dataId = new DataId(intId);
            if (docTermsMap.containsKey(dataId)){
                docTermsMap.remove(dataId);
                idDocTextMap.remove(dataId);
                return DELETE_DOCUMENT;
            }
        } catch (NumberFormatException e) {
            return ERROR_DOCUMENT_ID;
        }
        return DOCUMENT_NOT_EXIST;
    }


    /**
     * Search a sentence in all the documents and sorted by the tf\idf score.
     * @param sentence sentence to search.
     * @return TreeMap of documents sorted in from top score to low.
     */
    public TreeMap<DataId, Double> search(String sentence)
    {
        String[] sentenceList = sentence.split("\\W+");
        Map<String, Set<DataId>> mapWordToDataId = findDocumentEachWord(sentenceList);
        Map<DataId, Double> scorePerDoc = calculateScoreDoc(mapWordToDataId);
        TreeMap<DataId, Double> sorted = new TreeMap<>(new CompareTfIdf(scorePerDoc));
        sorted.putAll(scorePerDoc);
        return sorted;
    }

    /**
     * This method find for each word in the input the document have that word.
     * @param sentence search this sentence on whole documents.
     * @return HashMap for each word to a set of the all dataId.
     */
    protected Map<String, Set<DataId>> findDocumentEachWord(String[] sentence){
        Map<String, Set<DataId>> mapTermToDataId = new HashMap<>();
        for(String word : sentence){
            Set<DataId> docSet = findDocuments(word);
            mapTermToDataId.put(word, docSet);
        }
        return mapTermToDataId;
    }


    /**
     * For a specific word find all documents have this word.
     * @param word word to search in documents.
     * @return Set of document that contain a given word.
     */
    protected Set<DataId> findDocuments(String word)
    {
        word = word.toLowerCase();
        Set<DataId> docs = new HashSet<>();
        for (DataId d : docTermsMap.keySet())
        {
            Map<String, Integer> map2 = docTermsMap.get(d);
            if (map2.containsKey(word))
            {
                docs.add(d);
            }
        }
        return docs;
    }

    /**
     * This method get document and word and returns how many times the word appears on the document.
     * @param dataId document.
     * @param word string to search in the document.
     * @return how many times the word appears in the document..
     */
    protected int countsWordInDocument(DataId dataId, String word)
    {
        word = word.toLowerCase();
        Map<String, Integer> frequencyWord = docTermsMap.get(dataId);
        int count = 0;
        if (frequencyWord.containsKey(word))
        {
            count = frequencyWord.get(word);
        }
        return count;
    }


    /**
     * This method get calculate the score of each document base on TF\IDF formula.
     * @param mapWordToDoc map between word and the documents had that word.
     * @return HashMap of the score of each document.
     */
    protected Map<DataId, Double> calculateScoreDoc(Map<String, Set<DataId>> mapWordToDoc){
        Map<DataId, Double> scorePerDoc = initTableScore();
        for (String term : mapWordToDoc.keySet()){
            Set<DataId> setDataId = mapWordToDoc.get(term);
            for (DataId dataId : setDataId){
                Double scoreForTerm = tfIdf(dataId, term, mapWordToDoc);
                scorePerDoc.put(dataId, scorePerDoc.get(dataId) + scoreForTerm);
            }
        }
        return scorePerDoc;
    }

    /**
     * The method initialize the score of each word.
     * @return HashMap between document Id and 0.0.
     */
    protected Map<DataId, Double> initTableScore() {
        Map<DataId, Double> scorePerDoc = new HashMap<>();
        for (DataId dataId : docTermsMap.keySet()){
            scorePerDoc.put(dataId, 0.0);
        }
        return scorePerDoc;
    }

    /**
     * Calculate the IDF - (Inverse document frequency)
     * @param docs Set of the documents have this term.
     * @return the IDF of the word.
     */
    protected double calculateIDF(Set<DataId> docs)
    {
        int m = docs.size();
        int N = docTermsMap.size();
        double val = (double) (1 + N) / (double) (1 + m);
        return Math.log(val);
    }

    /**
     * Calculate tf*idf formula, as tf is the raw count itself.
     * @param dataId the documentId
     * @param word word to calculate this formula.
     * @return the tfidf of the the word and document.
     */
    protected double tfIdf(DataId dataId, String word, Map<String, Set<DataId>> mapTermToDoc)
    {
        int tf = countsWordInDocument(dataId, word);
        double idf = calculateIDF(mapTermToDoc.get(word));
        return tf * idf;
    }


    /**
     * This method creates an html code to display for the user.
     * @param sentence Sentence to search in the database.
     * @param N The top N results.
     * @return String of the results.
     */
    public StringBuilder topNResults(String sentence, String N) {
        StringBuilder result = new StringBuilder();
        int counter = 1;
        int topN;
        try {
            topN = Integer.parseInt(N);
        } catch (NumberFormatException e) {
            result.append(ERROR_TOP_N);
            return result;
        }
        TreeMap<DataId, Double> sortedSearch = search(sentence);
        result.append(TOP_RESULTS_1);
        result.append(N);
        result.append(TOP_RESULTS_2);
        for (Map.Entry<DataId, Double> entry : sortedSearch.entrySet()) {
            if (counter > topN){
                break;
            }
            result.append(counter).append("). ").append(entry.getKey().toString()).append(" Score: ").append(entry.getValue()).append(EMPTY_STRING);
            result.append("</BR>");
            counter++;
        }
        return result;
    }
}