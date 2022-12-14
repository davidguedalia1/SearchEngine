package data;

/**
 * DataId is class represents a document, that represent by serial number.
 */
public class DataId {

    /**
     * Counter that represent each document.
     */
    public static int idCounter = 1;
    private final int id;

    /**
     * Constructor document id class.
     */
    public DataId() {
        this.id = idCounter;
        idCounter++;
    }

    /**
     * Constructor document id class.
     * @param id number of the ID.
     */
    public DataId(int id) {
        this.id = id;
    }

    /**
     * Reset the counter of the documents.
     */
    public void resetCounter(){
        idCounter = 1;
    }

    @Override
    public String toString() {
        return "Document Id: " + id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DataId other = (DataId) obj;
        return id == other.id;
    }

    public int compareTo(DataId dataId1) {
        if (this.id > dataId1.id){
            return 1;
        }
        else {
            return 0;
        }
    }
}

