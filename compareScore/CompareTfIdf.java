package compareScore;

import data.DataId;
import java.util.Comparator;
import java.util.Map;

/**
 * CompareTfIdf is a comparator class, that implement from Comparator.
 */
public class CompareTfIdf implements Comparator<DataId> {
    private final Map<DataId, Double> map;

    /**
     * Constructor for CompareTfIdf class.
     * @param map HashMap between document to score.
     */
    public CompareTfIdf(Map<DataId, Double> map)
    {
        this.map = map;
    }


    @Override
    public int compare(DataId dataId1, DataId dataId2) {
        int result = map.get(dataId1).compareTo(map.get(dataId2));
        return result != 0 ? -result : dataId1.compareTo(dataId2);
    }
}
