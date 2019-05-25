package cn.henau.cms.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import cn.henau.cms.annotation.Component;

/**
 * 敏感词过滤处理
 * 
 */
@Component
public class SensitiveWordService {

	// 根节点
	private TrieNode rootNode;

	// 根据资源文件，创建字典树
	{
		rootNode = new TrieNode();

		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
			InputStreamReader read = new InputStreamReader(is);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				lineTxt = lineTxt.trim();
				addWord(lineTxt);
			}
			read.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 敏感词过滤算法
	 * 
	 * @author wuu 2018年12月14日
	 */
	public Map<String, String> filter(String text) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(text)) {
			map.put("error", "课程介绍不能为空！");
			return map;
		}

		// 数的遍历指针
		TrieNode tempNode = rootNode;
		int begin = 0;// 滚动指针
		int position = 0;// 定位指针

		while (position < text.length()) {
			char c = text.charAt(position);
			tempNode = tempNode.getSubNode(c);
			// 当前位置的匹配结束
			if (tempNode == null) {
				// 跳到下一个字符开始测试
				position = begin + 1;
				begin = position;
				// 回到树初始节点
				tempNode = rootNode;
			} else if (tempNode.isKeywordEnd()) {
				// 发现敏感词
				map.put("error", "课程名字或课程介绍含有敏感词，无法发布课程！");
				return map;
			} else {
				++position;
			}
		}
		return map;

	}

	/**
	 * 为自己的树添加敏感词
	 * 
	 * @param lineTxt
	 */
	private void addWord(String lineTxt) {
		TrieNode tempNode = rootNode;
		// 循环每个字节
		for (int i = 0; i < lineTxt.length(); ++i) {
			Character c = lineTxt.charAt(i);
			TrieNode node = tempNode.getSubNode(c);

			if (node == null) { // 没初始化
				node = new TrieNode();
				tempNode.addSubNode(c, node);
			}

			tempNode = node;

			if (i == lineTxt.length() - 1) {
				// 关键词结束， 设置结束标志
				tempNode.setKeywordEnd(true);
			}
		}
	}

	class TrieNode {
		private boolean end = false;
		private Map<Character, TrieNode> subNodes = new HashMap<>();

		/**
		 * 向指定位置添加节点树
		 * 
		 * @param c
		 * @param node
		 */
		void addSubNode(Character c, TrieNode node) {
			subNodes.put(c, node);
		}

		TrieNode getSubNode(Character c) {
			return subNodes.get(c);
		}

		boolean isKeywordEnd() {
			return end;
		}

		void setKeywordEnd(boolean end) {
			this.end = end;
		}

		public int getSubNodeCount() {
			return subNodes.size();
		}
	}
}
