import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DynamicTable {
	private Cell[][] matrix;
	private Map<String, ArrayList<String>> grammarMap;
	private LinkedList parseTreeList;
	private String resultStr = "";

	public String getResultStr() {
		return resultStr;
	}

	public DynamicTable(Map<String, ArrayList<String>> grammarMap, LinkedList parseTreeList) {
		this.grammarMap = grammarMap;
		this.parseTreeList = parseTreeList;
	}

	public void run(String sentence) {
		initializeTable(sentence); // 대각선에 있는 애들을 먼저 다 채워넣음
		System.out.println("=================Initialize CKY table [Finished]====================");
		int result = runCKY(sentence);
		if (result == -1) {
			resultStr = "There is no completed parse tree.";
		} else {
			drawParseTree(result);
		}
		System.out.println();

	}

	public void initializeTable(String sentence) {
		String[] split = sentence.split(" ");
		ArrayList<String> matched = new ArrayList<String>();
		matrix = new Cell[split.length][split.length + 1]; // matrix의 행, 열 수를 설정하여 5*6 2차원 배열 인스턴스를 생성한다.
		for (int i = 1; i < split.length + 1; i++) { // 일단 대각선 라인 성분을 채운다.
			matched = Main.convertToCFG(grammarMap, split[i - 1], parseTreeList);

			// words[i]번째 단어 성분 무엇인지 가져온다
			Cell cell = new Cell(i - 1, i, matched); // 가져온 단어 성분을 Cell에 저장한다.
			matrix[i - 1][i] = cell;
			System.out.println("(" + matrix[i - 1][i].getStartPoint() + "," + matrix[i - 1][i].getEndPoint() + ") : "
					+ split[i - 1] + " " + matrix[i - 1][i].getCKYresult());
		}
	}

	public int runCKY(String sentence) {
		int completed = 0;
		String[] words = sentence.split(" ");
		String noneStr = "None";
		Cell cell = null;
		for (int j = 2; j < words.length + 1; j++) {
			for (int i = j - 2; i > -1; i--) {
				for (int k = j - 1; k > i; k--) {
					System.out.println("(" + i + "," + j + ")");
					System.out.println("비교셀: (" + i + "," + k + ") & (" + (i + 1) + "," + j + ")");
					// 현재 위치의 (i, j-1)와 (i+1,j)를 비교해서 일치하는 grammar가 있으면 새로운 값을 세팅하고, 없으면 "None"

					Iterator<String> itr = matrix[i][k].getCKYresult().iterator();
					ArrayList<String> matched = new ArrayList<String>();
					String matchedStr = "";
					while (itr.hasNext()) {
						String element = itr.next();
						Iterator<String> itr2 = matrix[i + 1][j].getCKYresult().iterator();

						while (itr2.hasNext()) {

							String element2 = itr2.next();
							String findGrammarStr = element + " " + element2;
							matchedStr = Main.calCFG(grammarMap, findGrammarStr, parseTreeList);
							if (matchedStr != "") {
								matched.add(matchedStr);
							}

						}

					}
					if (matched.isEmpty()) {
						matched.add(noneStr);
					}
					cell = new Cell(i, j, matched);
					matrix[i][j] = cell;
					System.out.println("result : " + matrix[i][j].getCKYresult());
					// 여기서부터 이제 마지막 셀의 결과가 S로 나온 것의 parseTree를 찾아서 프린트 해야 함...
					if (j == (words.length) && i == 0) {
						Iterator<String> itr2 = matched.iterator();
						String[] finished = new String[2];
						while (itr2.hasNext()) {
							finished = itr2.next().split("-");
							if (finished[0].equals("S")) {
								completed = Integer.parseInt(finished[1]);
							} else {
								completed = -1;
							}
						}
					}
				} // for k

			} // for i
		} // for j

		return completed;
	}

	public void drawParseTree(int result) {

		String[] drawing = ((String) parseTreeList.get(result)).split(",");
		System.out.print(drawing[0]);
		resultStr += drawing[0];
		if (drawing.length > 2) {
			int new1 = Integer.parseInt(StringReplace(drawing[1]).trim());
			drawParseTree(new1);
			int new2 = Integer.parseInt(StringReplace(drawing[2]).trim());
			drawParseTree(new2);
			System.out.print(")");
			resultStr += ")";
		} else {
			if (isNumber(StringReplace(drawing[1]).trim())) {
				int new2 = Integer.parseInt(StringReplace(drawing[1]).trim());
				drawParseTree(new2);
			} else {
				System.out.print(drawing[1] + ")");
				resultStr += drawing[1] + ")";
			}

		}

	}

	public static String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		return str;
	}

	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
