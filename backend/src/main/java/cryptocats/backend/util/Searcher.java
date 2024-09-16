package cryptocats.backend.util;

import cryptocats.backend.entity.interfaces.Searchable;

import java.util.List;

public class Searcher {

    private static Searchable binarySearch(List<? extends Searchable> searchables, int start, int end, long value) {
        if (start <= end) {
            int mid = start + (end - start) / 2;

            if (searchables.get(mid).getMetric() > value) {
                return binarySearch(searchables, start, mid - 1, value);
            } else if (searchables.get(mid).getMetric() < value) {
                return binarySearch(searchables, mid + 1, end, value);
            }

            return searchables.get(mid);
        }

        return searchables.get(start);
    }

    /**
     * Returns an object in the list which has a value not less than or equals value
     *
     * @param searchableList list of searchable items
     * @param value value of item which searching
     * @return Found searchable item
     */
    public static Searchable findSearchable(List<? extends Searchable> searchableList, long value) {
        return binarySearch(searchableList, 0, searchableList.size(), value);
    }
}
