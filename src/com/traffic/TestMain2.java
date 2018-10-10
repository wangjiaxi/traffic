package com.traffic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestMain2 {

	public static void main(String[] args) {
		// 通过文件phone.txt获取到手机号前七位对应的省份的map
		Map<String, String> map1 = getNumProvince();
		// System.out.println(map1.size());

		// 获取手机号对应的流量
		Map<String, Long> map2 = getPNumSum();

		// 用来存放省份对应的流量
		Map<String, Long> map3 = new HashMap<>();

		Set<Entry<String, Long>> entrySet = map2.entrySet();
		for (Entry<String, Long> entry : entrySet) {
			String key = entry.getKey();
			Long value = entry.getValue();
			String pNum7 = key.substring(0, 7);
			String province = map1.get(pNum7);
			
			Long sum = map3.getOrDefault(province, 0L);
			sum+=value;
			map3.put(province, sum);
		}
		/*for (Entry<String, Long> entry : map3.entrySet()) {
			System.out.println(entry);
		}*/
		
		//排序 map -> list
		Set<Entry<String,Long>> entrySet2 = map3.entrySet();
		ArrayList<Entry<String,Long>> arrayList = new ArrayList<>(entrySet2);
		SortUtils.sortBySum(arrayList);
		/*for (Entry<String, Long> entry : arrayList) {
			System.out.println(entry);
		}*/
		for(int i = 0;i<3;i++) {
			System.out.println(arrayList.get(i));
		}
	}

	/**
	 * 获取手机号对应的流量
	 * 
	 * @return
	 */
	private static Map<String, Long> getPNumSum() {
		Map<String, Long> map = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader("D:/data/http.log"));) {
			String line = null;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] split = line.split("\t");
				String pNum = split[0];// 截取？？？
				String[] split2 = split[1].split(" ");
				String up = split2[1];
				String down = split2[2];
				Long upDown = Long.parseLong(up) + Long.parseLong(down);
				// System.out.println(sum);
				// 相应的做累加
				Long sum = map.getOrDefault(pNum, 0L);
				sum = sum + upDown;
				map.put(pNum, sum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 通过文件phone.txt获取到手机号前七位对应的省份的map
	 * 
	 * @return
	 */
	private static Map<String, String> getNumProvince() {
		Map<String, String> map = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader("D:/data/phone.txt"));) {
			String line = null;
			br.readLine();// 跳过第一行
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] split = line.split("\t");
				String pNum7 = split[1];
				String province = split[2];
				// System.out.println(province);
				map.put(pNum7, province);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
