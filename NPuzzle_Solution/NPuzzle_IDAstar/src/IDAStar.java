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
		//long a = System.currentTimeMillis();
		byte[] newState = Arrays.copyOf(curStatus.curState, curStatus.curState.length);/// 可优化0.181s左右
		
//		byte temp = newState[curStatus.empty];
//		newState[curStatus.empty] = newState[curStatus.empty + dir];
//		newState[curStatus.empty + dir] = temp;
		
//		newState[curStatus.empty] = (byte) (newState[curStatus.empty]^newState[curStatus.empty + dir]);
//		newState[curStatus.empty + dir] = (byte) (newState[curStatus.empty]^newState[curStatus.empty + dir]);
//		newState[curStatus.empty] = (byte) (newState[curStatus.empty]^newState[curStatus.empty + dir]);
		
		newState[curStatus.empty] = newState[curStatus.empty + dir];
		newState[curStatus.empty + dir] = 0;
	
		//NPuzzleStatus newStatus = new NPuzzleStatus(size, newState);
		
		int newMHTDis = curStatus.heuristic;
		newMHTDis -= curStatus.MHTDis(curStatus.empty + dir, curStatus.curState[curStatus.empty + dir] - 1);
		newMHTDis += curStatus.MHTDis(curStatus.empty, curStatus.curState[curStatus.empty + dir] - 1);
//		long a = System.currentTimeMillis();
//		NPuzzleStatus newStatus = new NPuzzleStatus(size, newState, curStatus.empty + dir, newMHTDis);
//		long b = System.currentTimeMillis();
//		ans += (b - a);
		if (/*newStatus.heuristic*/ newMHTDis + depth <= limitDepth && !flag) {

			NPuzzleStatus newStatus = new NPuzzleStatus(size, newState, curStatus.empty + dir, newMHTDis);
		
			if (Record.size() > depth)
				Record.set(depth, newStatus);/*add() Reference： https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html*/
			else 
				Record.add(depth, newStatus);
			RecordDir[depth] = dir;
			
			DFS(newStatus, depth + 1, dir);
			if (flag)
				return ;
		}
		//还得交换回来，回溯，此处并不需要，因为我们并没有改动原先数组。
//		long b = System.currentTimeMillis();
//		ans += (b - a);
	}
	
	public void PrintAns() {
		if (flag) {
			System.out.println("Find! The Following is the Answer: " + ans/1000.0 + "s");
			FormatPrint(Record.get(0).curState);
			for (int i = 1; i < Record.size(); i ++) {
				System.out.println("   ↓");
				System.out.println("   ↓" + (++time) + "(#, " + getDirection(RecordDir[i - 1]) + ")");
				System.out.println("   ↓");
				FormatPrint(Record.get(i).curState);
			}
		}else {
			System.out.println("No Answer!");
		}
		System.out.print(Record.get(0).size+" ");
		for (int i = 0; i < Record.get(0).curState.length; i ++) {
			System.out.print(Record.get(0).curState[i]+" ");
		}
		for (int i = 0; i < Record.size(); i ++) {
			System.out.print( Record.get(i).empty+" ");
		}
		System.out.print("\n");
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
