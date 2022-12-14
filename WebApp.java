import static spark.Spark.*;
import search.SearchEngine;
import data.DataId;

/**
 * WebApp class running the SearchEngine on localhost:1234, with five operations,
 * Add - localhost:1234/add
 * Update - localhost:1234/update
 * Delete - localhost:1234/delete
 * Get - localhost:1234/get
 * Search - localhost:1234/search
 */
public class WebApp {

    private static final String FILES_LOCATION = "public";
    private static final String ROOT = "/";
    private static final String ROOT_HTML = "/root";
    private static final String DELETE = "delete";
    private static final String UPDATE = "update";
    private static final String GET = "get";
    private static final String ADD = "add";
    private static final String SEARCH = "search";
    private static final String HTML = ".html";
    private static final String TEXT_HTML = "text/html";
    private static final String EMPTY_STRING = "";
    private static final String BACK_HTML = "<p class=\"ex1\"><a href=\"/\">Menu</a></p>";

    /**
     *  SearchEngine object that runs the operations.
     */
    private static final SearchEngine searchEngine = new SearchEngine();

    /**
     * Main function.
     */
    public static void main(String[] args) {
        port(Integer.parseInt(args[0]));
        System.out.println("Listen to port: " + Integer.parseInt(args[0]));
        System.out.println("Location of HTML files " + System.getProperty("user.dir") + "\\" + FILES_LOCATION);
        staticFiles.externalLocation(System.getProperty("user.dir") + "\\" + FILES_LOCATION);
        DataId dataId = new DataId();
        dataId.resetCounter();
        root();
        add();
        delete();
        update();
        getDocument();
        search();
    }

    private static void root() {
        get(ROOT, (req, res) -> {
            res.redirect(ROOT_HTML + HTML);
            return EMPTY_STRING + BACK_HTML;
        });
    }

    private static void delete() {
        get(DELETE, (req, res) ->  {res.redirect(DELETE + HTML);
            return EMPTY_STRING + BACK_HTML;
        });
        get(DELETE + "2", TEXT_HTML, (request, response)->{
            String id = request.queryParams(DELETE);
            return searchEngine.deleteDocument(id) + BACK_HTML;
        });
    }

    private static void update() {
        get(UPDATE, (req, res) ->  {res.redirect(UPDATE + HTML);
            return EMPTY_STRING;
        });
        get(UPDATE + "2", TEXT_HTML, (request, response)->{
            String id = request.queryParams(UPDATE);
            String text = request.queryParams("comment");
            return searchEngine.updateDocument(id,text) + BACK_HTML;
        });
    }

    private static void add() {
        get(ADD, (req, res) ->  {res.redirect(ADD + HTML);
            return EMPTY_STRING;
        });
        get(ADD + "2", TEXT_HTML, (request, response)->{
            String id = request.queryParams(ADD);
            return searchEngine.addDocument(new DataId(), id) + BACK_HTML;
        });
    }

    private static void getDocument() {
        get(GET, (req, res) ->  {res.redirect(GET + HTML);
            return EMPTY_STRING;
        });
        get(GET + "2", TEXT_HTML, (request, response)->{
            String id = request.queryParams(GET);
            return searchEngine.getDocument(id) + BACK_HTML;
        });
    }

    private static void search() {
        get(SEARCH, (req, res) -> {res.redirect(SEARCH + HTML);
            return EMPTY_STRING;
        });
        get("/results", TEXT_HTML, (request, response)->{
            String id = request.queryParams(SEARCH);
            String N = request.queryParams("top");
            return searchEngine.topNResults(id, N) + BACK_HTML;
        });
    }

}

