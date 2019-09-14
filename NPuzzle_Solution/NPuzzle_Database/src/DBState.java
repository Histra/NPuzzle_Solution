

public class DBState {
	public DBState() {
		
	}
	public DBState(byte[] s) {
		long newzob = 0;/// ø…”≈ªØ
		this.s = new byte[s.length];
		for (int i = 0; i < s.length; i ++) {
			if (s[i] == 0) {
				empty = i;
			}else
			if (s[i] != -1) {
				newzob = (newzob^zob[i][s[i]]);
			}
			this.s[i] = s[i];
		}
		this.zoberist_0 = (newzob^zob[empty][0]);
		this.zoberist = newzob;
	}
	public long[][] zob = CreateDatabase.zob;
	public int size = CreateDatabase.size;
	public byte[] s;
	public long zoberist_0;
	public long zoberist;
	public int empty;
	public int h = 0;
}
