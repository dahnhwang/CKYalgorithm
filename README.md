# CKYalgorithm
Spring 2019 2nd assignment for Natural Language Processing

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
   동시에 사용된 문법성분은 convertToCFG안에서 parseTreeList안에 하나씩 저장된다.
5. runCKY()에서 3중 for문을 통해 


# OUTPUT FILES

