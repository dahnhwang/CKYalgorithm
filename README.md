# CKYalgorithm
- Spring 2019 2nd assignment for Natural Language Processing
- Last Version : 2019-04-08 (Mon)

# INPUT FILES
1. input.txt - 마침표없이 엔터로 구분된 문장들이 입력될 수 있음
2. grammar.txt - 첫 줄은 공백으로 시작하는 문법 내용들이 엔터값으로 구분되어 입력되어 있음
이 두 파일은 모두 자바 프로젝트와 동일한 곳에 들어가있어야 함

# PROGRAM
Main.java에서 메인함수 실행 시,
1. grammar.txt에서 문법을 읽어와 grammarMap이란 이름의 HashMap으로 저장한다.
2. input.txt에서 문장을 읽어와 inputArray(사이즈 정해져있음)이란 이름의 String 배열에 저장한다.
3. DynamicTable의 매개변수로 검사할 문법이 담겨있는 grammarMap과 나중에 파스트리를 그릴 parseTreeList(LinkedList)를 전달한다.
4. initializeTable()에서 문장을 구성하는 단어의 수(n)에 맞춰 n*n 사이즈의 매트릭스를 생성한다.(다이나믹 프로그래밍으로 사용)
   또한 주어진 문장을 구성하는 단어 하나하나의 문법성분을 파악하여 매트릭스의 셀 하나하나에 저장한다.
   동시에 사용된 문법성분은 convertToCFG()안에서 parseTreeList안에 하나씩 저장된다.
5. runCKY()에서 3중 for문을 통해 Chomsky Normal Form으로 된 문법을 비교하여, calCFG()을 통해 일치하는 문법을 매트릭스의 셀에 채운다.
   동시에 일치하는 문법이 있는 경우 calCFG()안에서 parseTreeList안에 저장한다.
6. CKY table 계산이 끝나면 parseTreeList에 저장된 '사용된 문법' 리스트를 used_grammar.txt에 저장한다.
7. 6번이 끝나면 dyTable의 resultStr을 불러와 output.txt에 저장한다.

# OUTPUT EXAMPLE
InputSentence = "time flies like an arrow"
1. output.txt
(S(NP(n time))(VP(VP(v flies))(PP(P(p like))(NP(DT(det an))(NP(n arrow))))))
2. used_grammar.txt
0 (v, time)
1 (VP, 0)
2 (n, time)
3 (NP, 2)
4 (v, flies)
5 (VP, 4)
6 (n, flies)
7 (NP, 6)
8 (p, like)
9 (P, 8)
10 (v, like)
11 (VP, 10)
12 (det, an)
13 (DT, 12)
14 (n, arrow)
15 (NP, 14)
16 (VP, (1, 7))
17 (NP, (3, 5))
18 (S, (3, 5))
19 (NP, (7, 11))
20 (S, (7, 11))
21 (NP, (13, 15))
22 (PP, (9, 21))
23 (VP, (11, 21))
24 (VP, (5, 22))
25 (VP, (5, 22))
26 (NP, (7, 22))
27 (NP, (7, 23))
28 (S, (7, 23))
29 (VP, (16, 26))
30 (VP, (1, 26))
31 (NP, (3, 25))
32 (S, (3, 25))


