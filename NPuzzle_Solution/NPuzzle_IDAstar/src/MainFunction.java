
public class MainFunction {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		/*The following is stage_2 Samples*/
		//byte[] initState = new byte[] {6, 4, 7, 8, 5, 0, 3, 2, 1};/// 0.041s 31 || 0.028s 31 || 0.024s 31 || 0.012s 31
		//byte[] initState = new byte[] {8, 6, 7, 2, 5, 4, 3, 0, 1};/// 0.042s 31 || 0.031s 31 || 0.027s 31 || 0.014s 31
		//byte[] initState = new byte[] {8, 13, 0, 6, 1, 15, 9, 14, 3, 4, 5, 11, 7, 2, 10, 12};//0.576s 52 || 0.283s 52 || 0.262s 52 || 0.105s 52
		//byte[] initState = new byte[] {2, 9, 5, 11, 8, 3, 4, 14, 7, 10, 1, 12, 0, 15, 6, 13};//6.154s 51 || 1.922s 51 || 1.754s 51 || 0.684s 51
		byte[] initState = new byte[] {4, 7, 0, 9, 12, 10, 11, 8, 14, 6, 15, 1, 2, 5, 3, 13};//33.689s 56 || 9.514s 56 || 9.037s 56s || 5.7s 56
		//byte[] initState = new byte[] {12, 10, 3, 2, 0, 7, 14, 9, 1, 15, 5, 6, 8, 4, 13, 11};//205.39s 57 || 43.498s 57 || 39.137s 57 || 17.15s 57
		//byte[] initState = new byte[] {12, 1, 5, 6, 2, 11, 7, 9, 14, 10, 0, 4, 15, 3, 13, 8};//16.294s 50 || 3.87s 50 || 3.566s 50 || 1.581s 50  
		//byte[] initState = new byte[] {4, 6, 15, 13, 12, 9, 10, 2, 8, 0, 7, 3, 14, 5, 1, 11};//1729.19s 61 || 469.141s 61 || 432.3s 61 || 143.896s 61
		//byte[] initState = new byte[] {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 1, 2};//unknown
		//byte[] initState = new byte[] {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 4, 2, 1, 0, 5, 3};//unknown
		int size = (int) Math.sqrt(initState.length);
		IDAStar ida = new IDAStar(size);
		NPuzzleStatus initStatus = new NPuzzleStatus(size, initState);
		if (!initStatus.solvable()) {
			System.out.println("The Problem is UNSOLVABLE!");
		}else {
			ida.search(initStatus);
		}
		long endTime = System.currentTimeMillis();
		
		
//		IDAStar_Adv ida = new IDAStar_Adv(size);
//		NPuzzleStatus initStatus = new NPuzzleStatus(size, initState);
//		if (!initStatus.solvable()) {
//			System.out.println("The Problem is UNSOLVABLE!");
//		}else {
//			ida.search(initState, initStatus.heuristic, initStatus.empty);
//		}
//		long endTime = System.currentTimeMillis();
		
		
		System.out.println("Time Consuming: " + (endTime - startTime) / 1000.0 + "s");
		
		ida.PrintAns();
		System.out.println("Time Consuming: " + (endTime - startTime) / 1000.0 + "s");
		System.out.println("Press any key to continue...");
	}

}
