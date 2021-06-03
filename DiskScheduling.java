import java.io.*;
import java.util.*;

interface DiskScheduler
{
	int HEAD = 50;
	int LOW = 0;
	int HIGH = 199;
	int serviceRequests(int x[]);
}

class FCFS implements DiskScheduler
{
	public int serviceRequests(int referenceString[])
	{
		int headMovement = 0;
		int x = 0;
		for(int index = 0; index < referenceString.length; index++)
		{
			if(index == 0)
			{
				if(referenceString[index] > HEAD)
				{
					x = referenceString[index] - HEAD;
				}
			}
			else
			{	if(referenceString[index] > referenceString[index - 1])
				{
					x = referenceString[index] - referenceString[index - 1];
				}
				else if(referenceString[index] < referenceString[index - 1])
				{
					x = referenceString[index - 1] - referenceString[index];
				}
				else
					x = 0;
			}
			headMovement += x;
		}
		return(headMovement);
	}

}

class SSTF implements DiskScheduler
{
	int findNearest(int key, List<Integer> list)
	{
		int value, min = 999, n = 0;
		for(int i=0; i<list.size(); i++)
		{
			if(list.get(i) > key)
				value = list.get(i) - key;
			else if(list.get(i) < key)
				value = key - list.get(i);
			else
				value = 0; 
			if(min > value)
			{
				min = value;
				n = list.get(i);
			}
		}
		return(n);
	}

	public int serviceRequests(int referenceString[])
	{
		int headMovement = 0;
		int x = 0, i;
		int key = 0, temp = 0;
		List<Integer> list = new ArrayList<Integer>();
		for(int j = 0; j < referenceString.length; j++)
			list.add(referenceString[j]);

		for(int index = 0; index < referenceString.length; index++)
		{
			if(index == 0)
			{
				key = HEAD;
				temp = findNearest(key, list);
				if(temp > key)
					x = temp - key;
				else if(temp < key)
					x = key - temp;
				else
					x = 0;
			}
			else
			{
				key = temp;
				list.remove(new Integer(temp));
				temp = findNearest(key, list);
				if(temp > key)
					x = temp - key;
				else if(temp < key)
					x = key - temp;
				else
					x = 0;
			}
			headMovement += x;
		}
		return(headMovement);
	}
}

class SCAN implements DiskScheduler
{
	public int serviceRequests(int referenceString[])
	{
		Arrays.sort(referenceString);
		int headMovement = (HIGH - HEAD) + (HIGH - referenceString[0]);
		return(headMovement);
	}
}

class CSCAN implements DiskScheduler
{
	int largestValue(int array[])
	{
		int n = 0;
		for(int i = 0; i < array.length; i++)
		{
			if(array[i] > HEAD)
			{
				n = array[i-1];
				break;
			}
		}
		return(n);
	}
	public int serviceRequests(int referenceString[])
	{
		Arrays.sort(referenceString);
		int n = largestValue(referenceString);
		int headMovement = (HIGH - HEAD) + (HIGH - LOW) + (n - LOW);
		return(headMovement);
	}
}

class LOOK implements DiskScheduler
{
	public int serviceRequests(int referenceString[])
	{
		Arrays.sort(referenceString);
		int n = referenceString[referenceString.length - 1];
		int headMovement = (n - HEAD) + (n - referenceString[0]);
		return(headMovement);
	}
}

class DiskScheduling
{
	public static void main(String args[])
	{
		int referenceString[] = {82, 170, 43, 140, 24, 16, 190};
		System.out.println("Reference String = " + Arrays.toString(referenceString));
		FCFS fcfs = new FCFS();
		System.out.println("FCFS = " + fcfs.serviceRequests(referenceString));
		SSTF sstf = new SSTF();
		System.out.println("SSTF = " + sstf.serviceRequests(referenceString));
		SCAN scan = new SCAN();
		System.out.println("SCAN = " + scan.serviceRequests(referenceString));
		CSCAN cscan = new CSCAN();
		System.out.println("CSCAN = " + cscan.serviceRequests(referenceString));
		LOOK look = new LOOK();
		System.out.println("LOOK = " + look.serviceRequests(referenceString));
	}
}