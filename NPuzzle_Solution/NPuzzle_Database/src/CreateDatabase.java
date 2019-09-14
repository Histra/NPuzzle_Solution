import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;




public class CreateDatabase {
	public static int size = 4;
	public static long[][] zob;
	public CreateDatabase() {
		//getZob();
	}
	private static void InitZob() {
		// TODO Auto-generated method stub
		zob = new long[size*size][size*size];
		Random r = new Random();
		/// 写入
		try {
			File writeName = new File(System.getProperty("user.dir") + "/src/zob.txt");
			writeName.createNewFile();
			try (FileWriter writer = new FileWriter(writeName);
	             BufferedWriter out = new BufferedWriter(writer)
	            ) {
					for (int i = 0; i < size * size; i ++) {
						for (int j = 0; j < size * size; j ++) {
							zob[i][j] = r.nextLong();	
							out.write(zob[i][j] + " ");
						}
						out.write("\r\n");
					}
	                out.flush(); // 把缓存区内容压入文件
	            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("Zoberist get finished...");
	}
	static HashMap<Long, Integer> out1 = new HashMap<Long, Integer>();
	static HashMap<Long, Integer> out2 = new HashMap<Long, Integer>();
	static HashMap<Long, Integer> out3 = new HashMap<Long, Integer>();
	static HashMap<Long, Integer> out4 = new HashMap<Long, Integer>();
	static byte[] init1 = new byte[] {	1,  2, -1, -1,
									    5,  6, -1, -1,
									   -1, -1, -1, -1,
									   -1, -1, -1,  0};
	static byte[] init2 = new byte[] { -1, -1,  3,  4,
									   -1, -1,  7,  8,
									   -1, -1, -1, -1,
									   -1, -1, -1,  0};
	static byte[] init3 = new byte[] { -1, -1, -1, -1,
									   -1, -1, -1, -1,
									    9, 10, -1, -1,
									   13, 14, -1,  0};
	static byte[] init4 = new byte[] { -1, -1, -1, -1,
									   -1, -1, -1, -1,
									   -1, -1,  11, 12,
									   -1, -1,  15,  0};
	
	public static void Run() {
		DBState dbs1 = new DBState(init1);
		BFS(dbs1, out1, 1);
		DBState dbs2 = new DBState(init2);
		BFS(dbs2, out2, 2);
		DBState dbs3 = new DBState(init3);
		BFS(dbs3, out3, 3);
		DBState dbs4 = new DBState(init4);
		BFS(dbs4, out4, 4);
	}
	public static void BFS(DBState dbs, HashMap<Long, Integer>out, int sequence) {
		out.put(dbs.zoberist, 0);
		Queue<DBState> q = new LinkedList<DBState>();
		HashSet<Long>vis = new HashSet<Long>();
		q.add(dbs);
		
		while (!q.isEmpty()) {
			DBState cur = q.poll();
			if (!vis.contains(cur.zoberist_0)) {
				if (out.containsKey(cur.zoberist)) {
					int temp = out.get(cur.zoberist);
					if (temp > cur.h) {
						out.replace(cur.zoberist, temp, cur.h);
					}
				}else {
					out.put(cur.zoberist, cur.h);
				}
			}
//			if (ans -- == 0)
//				break;
			vis.add(cur.zoberist_0);
			int empty = cur.empty;
			int newEmpty;
			if (empty / size > 0) {// up
				newEmpty = empty - size;
				int ad = 1; if (cur.s[newEmpty] == -1) ad = 0;
				cur.s[empty] = cur.s[newEmpty];
				cur.s[newEmpty] = 0;
				DBState child = new DBState(cur.s);
				if (vis.contains(child.zoberist_0)) {
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
					child = null;
				}else {
					child.h = cur.h + ad;
					q.add(child);
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
				}
			}
			if (empty % size > 0) {// left
				newEmpty = empty - 1;
				int ad = 1; if (cur.s[newEmpty] == -1) ad = 0;
				cur.s[empty] = cur.s[newEmpty];
				cur.s[newEmpty] = 0;
				DBState child = new DBState(cur.s);
				if (vis.contains(child.zoberist_0)) {
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
					child = null;
				}else {
					child.h = cur.h + ad;
					q.add(child);
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
				}
			}
			if (empty / size < size - 1) {// down
				newEmpty = empty + size;
				int ad = 1; if (cur.s[newEmpty] == -1) ad = 0;
				cur.s[empty] = cur.s[newEmpty];
				cur.s[newEmpty] = 0;
				DBState child = new DBState(cur.s);
				if (vis.contains(child.zoberist_0)) {
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
					child = null;
//					continue;
				}else {
					child.h = cur.h + ad;
					q.add(child);
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
				}
			}
			if (empty % size < size - 1) {// right
				newEmpty = empty + 1;
				int ad = 1; if (cur.s[newEmpty] == -1) ad = 0;
				cur.s[empty] = cur.s[newEmpty];
				cur.s[newEmpty] = 0;
				DBState child = new DBState(cur.s);
				if (vis.contains(child.zoberist_0)) {
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
					child = null;
//					continue;
				}else {
					child.h = cur.h + ad;
					q.add(child);
					cur.s[newEmpty] = cur.s[empty];
					cur.s[empty] = 0;
				}
			}
		}
		System.out.println("out" + sequence + ".txt = " + out.size());
		//Iterator it = out.keySet().iterator();
		WriteOutN(sequence, out);
	}
    private static void WriteOutN(int i, HashMap<Long, Integer>out_) {
		// TODO Auto-generated method stub
		String filename = System.getProperty("user.dir") + "/src/out" + i + ".txt";
		System.out.println(filename);
		try {
			File writeName = new File(filename);
			writeName.createNewFile();
			try (FileWriter writer = new FileWriter(writeName);
	             BufferedWriter out = new BufferedWriter(writer)
	            ) {
					for (Long l : out_.keySet()) {
						out.write(l + " "+ out_.get(l) + "\r\n");
					}
	                out.flush(); // 把缓存区内容压入文件
	            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("out"+ i + ".txt get finished...");
	}
	//	private static long getZobrist(long oldzober, byte[] s) {
//		// TODO Auto-generated method stub
//		int[] pos = new int[4];
//		int[] num = new int[4];
//		int posNum = 0;
//		int empty = 0;
//		int emptyValue = 0;
//		for (int i = 0; i < s.length; i ++) {
//			if (s[i] == 0) {
//				empty = i;
//				emptyValue = s[i];
//			}
//			else if (s[i] != -1) {
//				pos[posNum] = i;
//				num[posNum] = s[i];
//				posNum ++;
//			}
//		}
//		long newzober = oldzober;
//		for (int i = 0; i < 4; i ++) {
//			newzober = (newzober^zob[pos[i]][num[i]]);
//		}
//		newzober = (newzober^zob[empty][emptyValue]);
//		return newzober;
//	}
	public static void ReadFile() {
		String pathname = System.getProperty("user.dir") + "/src/input.txt";
		try(FileReader reader = new FileReader(pathname);
			BufferedReader br = new BufferedReader(reader))
		{
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void WriteFile() {
        try {
            File writeName = new File(System.getProperty("user.dir") + "/src/output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write("我会写入文件啦1\r\n"); // \r\n即为换行
                out.write("我会写入文件啦2\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
//		WriteFile();
//		ReadFile();
		InitZob();
		Run();
	}
}


//https://blog.csdn.net/nickwong_/article/details/51502969 
