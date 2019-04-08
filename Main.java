import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	static String pathStr = Main.class.getResource("").getPath();

	public static void main(String[] args) throws IOException {

		// Read grammar.txt -> Save to grammarMap
		Map<String, ArrayList<String>> grammarMap = new HashMap<String, ArrayList<String>>();

		File file = new File(pathStr + "grammar.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

		String grammar = null;
		grammar = br.readLine();

		while ((grammar = br.readLine()) != null) {

			String[] grammar1 = grammar.split("->");
			String rule = grammar1[1].trim();
			String term = grammar1[0].trim();
			if (grammarMap.get(term) == null) {
				ArrayList<String> rules = new ArrayList<String>();
				rules.add(rule);
				grammarMap.put(term, rules);
			} else {
				grammarMap.get(term).add(rule);
				grammarMap.put(term, grammarMap.get(term));
			}

		}

		for (String s : grammarMap.keySet()) {
			System.out.println(s + " : " + grammarMap.get(s));
		}
		System.out.println("=================Read grammar.txt -> Save to grammarMap [Finished]====================");
		// Read input.txt
		file = new File(pathStr + "input.txt");
		br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String input = null;
		String[] inputArray = new String[3];
		int line = 0;
		while ((input = br.readLine()) != null) {
			inputArray[line] = input;
			line++;
		}
		for (int i = 0; i < inputArray.length; i++) {
			System.out.println(i + "번째 문장: " + inputArray[i]);
		}
		System.out.println("=================Read input.txt -> Save to inputArray [Finished]====================");

		LinkedList parseTreeList = new LinkedList();

		DynamicTable dyTable = new DynamicTable(grammarMap, parseTreeList);
		dyTable.run(inputArray[2]);

		System.out.println("=================Calculated CKY table [Finished]====================");
		fileWriter writer = new fileWriter();
		for (int i = 0; i < parseTreeList.size(); i++) {
			System.out.println((i) + " " + parseTreeList.get(i));
			writer.fileWrite(new File(pathStr + "used_grammar.txt"), (i) + " " + parseTreeList.get(i));
		}
		System.out.println(pathStr);
		System.out.println("=================Print used_grammar.txt [Finished]====================");
		writer.fileWrite(new File(pathStr + "output.txt"), dyTable.getResultStr());

	}

	public static String calCFG(Map<String, ArrayList<String>> grammarMap, String word, LinkedList list) {
		String matched = "";
		String words[] = word.split(" ");
		String word1[] = words[0].split("-");
		String word2[] = words[1].split("-");
		for (String s : grammarMap.keySet()) {
			for (int i = 0; i < grammarMap.get(s).size(); i++) {
				if (grammarMap.get(s).get(i).equals(word1[0] + " " + word2[0])) {
					matched = s + "-" + list.size();
					System.out.println("(" + s + ", (" + word1[1] + ", " + word2[1] + "))");
					list.addLast("(" + s + ", (" + word1[1] + ", " + word2[1] + "))");
				}
			}
		}
		return matched;
	}

	public static ArrayList<String> convertToCFG(Map<String, ArrayList<String>> grammarMap, String word,
			LinkedList list) {
		ArrayList<String> matched = new ArrayList<String>();
		for (String s : grammarMap.keySet()) {
			for (int i = 0; i < grammarMap.get(s).size(); i++) {
				if (grammarMap.get(s).get(i).equals(word)) {

					// list.addLast(s + ", " + word);
					list.addLast("(" + s + ", " + word + ")");
					matched.add(s + "-" + (list.size() - 1));
					for (String s2 : grammarMap.keySet()) {
						for (int j = 0; j < grammarMap.get(s2).size(); j++) {
							if (grammarMap.get(s2).get(j).equals(s)) {
								list.addLast("(" + s2 + ", " + (list.size() - 1) + ")");
								matched.add(s2 + "-" + (list.size() - 1));
							}
						}
					}

				}
			}
		}
		return matched;
	}

}
