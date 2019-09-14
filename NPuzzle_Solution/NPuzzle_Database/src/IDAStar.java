import java.util.ArrayList;
import java.util.Arrays;

public class IDAStar {
	
	public IDAStar(int size) {
		this.size = size;
		setGoalState();
		goalStatus = new NPuzzleStatus(size, goalState);
	}
	
	public static int searchCount = 0;
	private int time = 0;
	private int size;
	private byte[] goalState;
	private NPuzzleStatus goalStatus;
	private int limitDepth;
	private boolean flag = false;
	private ArrayList<NPuzzleStatus>Record;
	private int[] RecordDir;
	static public long[][] zob;
	
	public void setGoalState() {
		goalState = new byte[size * size];
		for (byte i = 0; i < size * size - 1; i ++) {
			goalState[i] = (byte) (i + 1);
		}
		goalState[size * size - 1] = 0;
	}
	
	public void search(NPuzzleStatus initStatus) {
		Record = new ArrayList<NPuzzleStatus>();
		RecordDir = new int[100];
		limitDepth = initStatus.heuristic;
		while (!flag && limitDepth < 100) {
			DFS(initStatus, 0, 0);
			if (!flag) {
				limitDepth ++;
			}
		}
		Record.add(0, initStatus);
	}
	
	public void DFS(NPuzzleStatus curStatus, int depth, int preDir) {
		searchCount ++;
		if (flag)
			return ;
		if (Arrays.equals(curStatus.curState, goalState)) {
			flag = true;
			limitDepth = depth;
			return ;
		}
		if (depth >= limitDepth) {
			return ;
		}
		int empty = curStatus.empty;
		if (empty / size > 0) {// up
			move(curStatus, -size, preDir, depth);
		}
		if (empty % size > 0) {// left
			move(curStatus, -1, preDir, depth);
		}
		if (empty / size < size - 1) {// down
			move(curStatus, size, preDir, depth);
		}
		if (empty % size < size - 1) {// right
			move(curStatus, 1, preDir, depth);
		}
	}	
	long ans = 0;
	private void move(NPuzzleStatus curStatus, int dir, int preDir, int depth) {
		if (preDir + dir == 0)
			return ;
		byte[] newState = Arrays.copyOf(curStatus.curState, curStatus.curState.length);/// ¿ÉÓÅ»¯0.181s×óÓÒ
		
		newState[curStatus.empty] = newState[curStatus.empty + dir];
		newState[curStatus.empty + dir] = 0;

//		int newMHTDis = curStatus.heuristic;
//		newMHTDis -= curStatus.MHTDis(curStatus.empty + dir, curStatus.curState[curStatus.empty + dir] - 1);
//		newMHTDis += curStatus.MHTDis(curStatus.empty, curStatus.curState[curStatus.empty + dir] - 1);
		
		int newMHTDis = getNewH(newState);
		if (newMHTDis + depth <= limitDepth && !flag) {

			NPuzzleStatus newStatus = new NPuzzleStatus(size, newState, curStatus.empty + dir, newMHTDis);
		
			if (Record.size() > depth)
				Record.set(depth, newStatus);/*add() Reference£º https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html*/
			else 
				Record.add(depth, newStatus);
			RecordDir[depth] = dir;
			
			DFS(newStatus, depth + 1, dir);
			if (flag)
				return ;
		}
	}
	
	private int getNewH(byte[] newState) {
		// TODO Auto-generated method stub
//		FormatPrint(newState);
		byte[] temp = new byte[size * size];
//		System.out.println("Size" + size);
		for (byte i = 0; i < size * size; i ++) {
			temp[newState[i]] = i;
//			System.out.println(i+"---"+newState[i]);
		}
		long h1 = 0;
		long h2 = 0;
		long h3 = 0;
		long h4 = 0;
		for (int i = 0; i < size * size; i ++) {
			if (i == 1 || i == 2 || i == 5 || i == 6) {
				h1 = (h1^zob[temp[i]][i]);
			}
			if (i == 3 || i == 4 || i == 7 || i == 8) {
				h2 = (h2^zob[temp[i]][i]);
			}
			if (i == 9 || i == 10 || i == 13 || i == 14) {
				h3 = (h3^zob[temp[i]][i]);
			}
			if (i == 11 || i == 12 || i == 15) {
				h4 = (h4^zob[temp[i]][i]);
			}
		}
		int newh = MainFunction.hm1.get(h1) +
				MainFunction.hm2.get(h2) +
				MainFunction.hm3.get(h3) +
				MainFunction.hm4.get(h4);
		return newh;
	}

	public void PrintAns() {
		if (flag) {
			System.out.println("Find! The Following is the Answer: " + ans/1000.0 + "s");
			FormatPrint(Record.get(0).curState);
			for (int i = 1; i < Record.size(); i ++) {
				System.out.println("   ¡ý");
				System.out.println("   ¡ý" + (++time) + "(#, " + getDirection(RecordDir[i - 1]) + ")");
				System.out.println("   ¡ý");
				FormatPrint(Record.get(i).curState);
			}
		}else {
			System.out.println("No Answer!");
		}
	}
	
	private char getDirection(int dir) {
		char Dir = ' ';
		if (dir == -size)
			Dir = 'U';
		if (dir == -1)
			Dir = 'L';
		if (dir == size)
			Dir = 'D';
		if (dir == 1)
			Dir = 'R';
		return Dir;
	}

	public void FormatPrint(byte[] state) {
		System.out.print("+");
		for (int i = 0; i < size; i ++) {
			System.out.print("---+");
		}
		System.out.println();
		
		for (int i = 0; i < size; i ++) {
			System.out.print("|");
			for (int j = 0; j < size; j ++) {
				if (state[i* size +j] != 0) {
					System.out.printf("%2d |", state[i * size + j]);
				}else {
					System.out.printf(" # |");
				}
			}
			System.out.println();
			
			System.out.print("+");
			for (int ii = 0; ii < size; ii ++) {
				System.out.print("---+");
			}
			System.out.println();
		}
	}
}
