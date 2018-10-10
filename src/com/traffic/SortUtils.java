package com.traffic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class SortUtils {

	/**
	 * 根据sun进行排序
	 * @param list
	 */
	public static void sortBySum(List<Entry<String, Long>> list) {
		Collections.sort(list, new Comparator<Entry<String, Long>>() {

			@Override
			public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
				return o2.getValue() - o1.getValue() > 0 ? 1 : -1;
			}
		});
	}



}
