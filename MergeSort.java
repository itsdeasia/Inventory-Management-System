import java.util.List;

public class MergeSort {
    public static void sort(List<Product> items, boolean ascending) {
        if (items.size() <= 1) {
            return;
        }

        int mid = items.size() / 2;
        List<Product> left = items.subList(0, mid);
        List<Product> right = items.subList(mid, items.size());

        sort(left, ascending);
        sort(right, ascending);

        merge(items, left, right, ascending);
    }

    private static void merge(List<Product> items, List<Product> left, List<Product> right, boolean ascending) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if ((ascending && left.get(i).cost < right.get(j).cost) ||
                    (!ascending && left.get(i).cost > right.get(j).cost)) {
                items.set(k++, left.get(i++));
            } else {
                items.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            items.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            items.set(k++, right.get(j++));
        }
    }
}
