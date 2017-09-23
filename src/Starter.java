import java.util.PriorityQueue;

import algorithm.Core;
import model.Candidate;

public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Core core = new Core();
		PriorityQueue<Candidate> pq = core.getTopKCandidate();
		System.out.println(pq);
	}

}
