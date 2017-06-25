package com.advanced.twopointer;

/**
 * Created by charles on 11/9/16.
 *
 * Given a set of n nuts of different sizes and n bolts of different sizes.
 * There is a one-one mapping between nuts and bolts.
 * Comparison of a nut to another nut or a bolt to another bolt is not allowed.
 * It means nut can only be compared with bolt and bolt can only be compared with nut to see which one is bigger/smaller.

 We will give you a compare function to compare nut with bolt.

 Example
 Given nuts = ['ab','bc','dd','gg'], bolts = ['AB','GG', 'DD', 'BC'].
 Your code should find the matching bolts and nuts.
 one of the possible return:
 nuts = ['ab','bc','dd','gg'], bolts = ['AB','BC','DD','GG'].
 we will tell you the match compare function. If we give you another compare function.
 the possible return is the following:
 nuts = ['ab','bc','dd','gg'], bolts = ['BC','AA','DD','GG'].
 So you must use the compare function that we give to do the sorting.
 The order of the nuts or bolts does not matter. You just need to find the matching bolt for each nut.
 */

import java.util.Arrays;

/**
 * Difficulty is there is no way to comparison for sort in single array
 * The comparison method is api return value between one element from nuts and the other from bolts
 *
 */
public class NutsAndBolts {
    public void sortNutsAndBolts(String[] nuts, String[] bolts, NBComparator compare) {
        if (nuts == null || bolts == null) return;
        if (nuts.length != bolts.length) return;

        quickSort(nuts, bolts, compare, 0, nuts.length - 1);
    }

    public static void main(String[] args) {
        NutsAndBolts nuts = new NutsAndBolts();
        String[] a = {"ab", "bc", "gg", "dd"};
        String[] b = {"AB", "GG", "DD", "BC"};

        NBComparator cmp = new NBComparator();
        System.out.println(cmp.compare("ab", "AB"));
        System.out.println(cmp.compare("AB", "bc"));
        System.out.println(cmp.compare("DD", "bc"));


        nuts.sortNutsAndBolts(a, b, cmp);


        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));

    }

    private void quickSort(String[] nuts, String[] bolts, NBComparator compare, int l, int r) {
        if (l >= r) return;
        // find the partition index for nuts with bolts[i]
        int partition_idx = partition(nuts, bolts[l], compare, l, r);
        // partition bolts with nuts[partition_idx]
        partition(bolts, nuts[partition_idx], compare, l, r);

        // quick sort recursively
        quickSort(nuts, bolts, compare, l, partition_idx - 1);
        quickSort(nuts, bolts, compare, partition_idx + 1, r);
    }

    private int partition(String[] str, String pivot, NBComparator compare, int l, int r) {
        // for loop to find pivot which has equal in str array
        for (int i = l; i <= r; i++) {
            if (compare.compare(str[i], pivot) == 0 || compare.compare(pivot, str[i]) == 0) {
                swap(str, i, l);
                break;
            }
        }
        String now = str[l];
        int left = l;
        int right = r;
        while (left < right) {
            // left larger, right smaller
            while (left < right &&
                    (compare.compare(str[right], pivot) == -1
                    || compare.compare(pivot, str[right]) == 1)) {
                right--;
            }
            str[left] = str[right];
            while (left < right &&
                    (compare.compare(str[left], pivot) == 1
                    || compare.compare(pivot, str[left]) == -1)) {
                left++;
            }
            str[right] = str[left];
        }
        str[left] = now;

        return left;
    }

    private void swap(String[] str, int i, int j) {
        String tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
    }
}

class NBComparator {
    public int compare(String a, String b) {
        char[] ax = a.toLowerCase().toCharArray();
        char[] bx = b.toLowerCase().toCharArray();
//        System.out.println(Arrays.toString(ax) + " " + Arrays.toString(bx));
        int i = 0;
        while (i < ax.length && i < bx.length) {
            if (ax[i] - bx[i] < 0) {
                return -1;
            } else if (ax[i] - bx[i] > 0) {
                return 1;
            }
            i++;
        }
        if (i < ax.length) {
            return 1;
        }
        if (i < bx.length) {
            return -1;
        }
        return 0;
    }
}
/**
 * public class NBCompare {
 *     public int cmp(String a, String b);
 * }
 * You can use compare.cmp(a, b) to compare nuts "a" and bolts "b",
 * if "a" is bigger than "b", it will return 1, else if they are equal,
 * it will return 0, else if "a" is smaller than "b", it will return -1.
 * When "a" is not a nut or "b" is not a bolt, it will return 2, which is not valid.
 */

