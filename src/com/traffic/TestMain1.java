package com.traffic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain1 {
	public static void main(String[] args) {
		// 获取URL以及对应的流量和
		Map<String, Long> map = getUrlSum();
		// 转化为list进行排序
		Set<Entry<String, Long>> entrySet = map.entrySet();
		List<Entry<String, Long>> list = new ArrayList<>(entrySet);
		// 排序
		SortUtils.sortBySum(list);
		/*
		 * for (Entry<String, Long> entry : list) { System.out.println(entry); }
		 */
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\data\\1"));) {
			for (int i = 0; i < 3; i++) {
				Entry<String, Long> entry = list.get(i);
				//使用toString方法转化为toString
				bw.write(entry.toString());
				bw.newLine();
				System.out.println(list.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取URL以及对应的流量和
	 * 
	 * @return
	 */
	private static Map<String, Long> getUrlSum() {
		// 用来存储URL以及对应的流量数据
		Map<String, Long> map = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\http.log"));) {
			String line = null;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String string = line.split("\t")[1];
				String[] split = string.split(" ");
				String oldUrl = split[0];
				String up = split[1];
				String down = split[2];
				String url = getUrlByRgex(oldUrl);

				Long upDown = Long.parseLong(up) + Long.parseLong(down);
				// 如果通过URL直接找到了流量则直接返回，否则就是0
				Long sum = map.getOrDefault(url, 0L);
				sum = sum + upDown;
				map.put(url, sum);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 正则截取URL
	 * 
	 * @param oldUrl
	 * @return
	 */
	private static String getUrlByRgex(String oldUrl) {
		Pattern compile = Pattern.compile("(\\w+\\.)?(\\w+\\.){1}\\w+");
		Matcher matcher = compile.matcher(oldUrl);
		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
}
