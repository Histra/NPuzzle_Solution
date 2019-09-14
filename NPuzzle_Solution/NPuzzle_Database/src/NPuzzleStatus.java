
public class NPuzzleStatus {
	
	public NPuzzleStatus(int size, byte[] curState) {
		this.size = size;
		this.curState = curState;
		this.SumOf_MHT_Dis();
		this.setEmpty();
	}
	
	public NPuzzleStatus(int size, byte[] curState, int empty, int heur) {
		this.size = size;
		this.curState = curState;
		this.empty = empty;
		this.heuristic = heur;
	}
	
	public int size;
	public byte[] curState;
	public byte[] preState;
	public int depth;
	public int heuristic;
	public int empty;
	
	public void setEmpty() {
		int empty = 0;
		for (int i = 0; i < curState.length; i ++) {
			if (curState[i] == 0) {
				empty = i;
				break;
			}
		}
		this.empty = empty;
	}
	
	
	/// ×îÖÕ×´Ì¬¹Ì¶¨
	public void SumOf_MHT_Dis() {
		int heur = 0;
		for (int i = 0; i < size * size; i ++) {
			if (curState[i] != 0)
				heur += MHTDis(i, curState[i] - 1);
		}
		this.heuristic = heur + 2;/// Âü¹þ¶Ù¾àÀë+2 £¬Ì«ÐþÑ§ÁË
	}
	
	public int MHTDis(int start, int end) {
		return Math.abs(start % size - end % size) + Math.abs(start / size - end / size);
	}
	
	public boolean solvable() {		
		int reverseSum = getReverseSum();
		if (size % 2 != 0) {
			if (reverseSum % 2 == 0)
				return true;
			else
				return false;
		}else {
			if (( (empty / size + 1) + reverseSum) % 2 == 0)
				return true;
			else
				return false;
		}
	}

	private int getReverseSum() {
		int ans = 0;
		for (int i = 0; i < curState.length - 1; i ++) {
			if (i != empty) {
				for (int j = i + 1; j < curState.length; j ++) {
					if (j != empty) {
						if (curState[i] > curState[j])
							ans ++;
					}
				}
			}
		}
		return ans;		
	}
	
}



