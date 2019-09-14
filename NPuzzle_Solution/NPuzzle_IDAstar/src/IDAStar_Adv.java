import java.util.ArrayList;
import java.util.Arrays;

public class IDAStar_Adv {
	
	public IDAStar_Adv(int size) {
		this.size = size;
		setGoalState();
	}
	public static int searchCount = 0;
	private int time = 0;
	private int size;
	private byte[] goalState;
	private int limitDepth;
	private boolean flag = false;
	private ArrayList<byte[]>Record;
	private int[] RecordDir;
	
	public void setGoalState() {
		goalState = new byte[size * size];
		for (byte i = 0; i < size * size - 1; i ++) {
			goalState[i] = (byte) (i + 1);
		}
		goalState[size * size - 1] = 0;
	}
	
	public void search(byte[] initState, int heuristic, int empty) {
		Record = new ArrayList<byte[]>();
		RecordDir = new int[100];
		limitDepth = heuristic;
		while (!flag && limitDepth < 100) {
			DFS(initState, empty, heuristic, 0, 0);
			if (!flag) {
				limitDepth ++;
			}
		}
		Record.add(0, initState);
	}
	
	public void DFS(byte[] curState, int empty, int heuristic, int depth, int preDir) {
		searchCount ++;
		if (flag)
			return ;
		if (Arrays.equals(curState, goalState)) {
			flag = true;
			limitDepth = depth;
			return ;
		}
		if (depth >= limitDepth) {
			return ;
		}
		if (empty / size > 0) {// up
			move(curState, empty, heuristic, -size, preDir, depth);
		}
		if (empty % size > 0) {// left
			move(curState, empty, heuristic, -1, preDir, depth);
		}
		if (empty / size < size - 1) {// down
			move(curState, empty, heuristic, size, preDir, depth);
		}
		if (empty % size < size - 1) {// right
			move(curState, empty, heuristic, 1, preDir, depth);
		}
	}
	
	long ans = 0;
	private void move(byte[] curState, int empty, int heuristic, int dir, int preDir, int depth) {
		if (preDir + dir == 0)
			return ;
		
		curState[empty] = (byte) (curState[empty]^curState[empty + dir]);
		curState[empty + dir] = (byte) (curState[empty]^curState[empty + dir]);
		curState[empty] = (byte) (curState[empty]^curState[empty + dir]);
		
		int newMHTDis = heuristic;
		newMHTDis -= MHTDis(empty + dir, curState[empty + dir] - 1);
		newMHTDis += MHTDis(empty, curState[empty + dir] - 1);
		if (newMHTDis + depth <= limitDepth && !flag) {
			if (Record.size() > depth)
				Record.set(depth, curState);/*add() Reference£º https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html*/
			else 
				Record.add(depth, curState);
			RecordDir[depth] = dir;
			
			DFS(curState, empty + dir, newMHTDis, depth + 1, dir);
			if (flag)
				return ;
		}
		curState[empty] = (byte) (curState[empty]^curState[empty + dir]);
		curState[empty + dir] = (byte) (curState[empty]^curState[empty + dir]);
		curState[empty] = (byte) (curState[empty]^curState[empty + dir]);
	}
	
	public int MHTDis(int start, int end) {
		return Math.abs(start % size - end % size) + Math.abs(start / size - end / size);
	}
	
	public void PrintAns() {
		if (flag) {
			System.out.println("Find! The Following is the Answer: " + ans/1000.0 + "s");
			FormatPrint(Record.get(0));
			for (int i = 1; i < Record.size(); i ++) {
				System.out.println("   ¡ý");
				System.out.println("   ¡ý" + (++time) + "(#, " + getDirection(RecordDir[i - 1]) + ")");
				System.out.println("   ¡ý");
				FormatPrint(Record.get(i));
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
