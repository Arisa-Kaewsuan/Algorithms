package Hanoi_Tower;

public class Hanoi_Tower_Recursive
{
	//JAVA recursive function to solve tower of hanoi puzzle
	public static void Hanoi(int n, char from_rod,char to_rod, char aux_rod)
	{
		if (n == 0) 
		{
			return;
		}
		Hanoi(n - 1, from_rod, aux_rod, to_rod);
		System.out.println("Move disk " + n + " from rod " + from_rod + " to rod " + to_rod);
		Hanoi(n - 1, aux_rod, to_rod, from_rod);
	}

	// Driver code
	public static void main(String[] args) 
	{
		// N is number of rods
		 int N = 3;
		 
		// A, B and C are names of rods
		Hanoi(N, 'A', 'C', 'B');
	}

}

//Code Form : https://www.geeksforgeeks.org/c-program-for-tower-of-hanoi/




