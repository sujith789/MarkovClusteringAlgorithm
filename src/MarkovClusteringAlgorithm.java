import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;



public class MarkovClusteringAlgorithm {

	public static void main(String args[]) throws Exception
	{
		String filename=null;
		int mNumLines=0;
		double inflationParameter;
		Scanner scan= new Scanner(System.in);
		System.out.println(" Markov Clustering Algorithm");
		System.out.println();
		System.out.println(" Enter the inflation parameter: ");
		inflationParameter= Double.parseDouble(scan.nextLine());
		System.out.println(" Enter the dataset ");
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        filename = reader.readLine();
        
       if(filename.equals("att.txt"))
        {
    	   int mNodes=180;
        BufferedReader readfile = new BufferedReader(new FileReader(filename));
		while(readfile.readLine()!=null)	
		{
			mNumLines++;
		}
		System.out.println(" The number of edges = "+mNumLines);
		double adjacency_matrix[][] = new double[mNodes][mNodes];
		double markov_matrix[][] = new double[mNodes][mNodes];
		
		
		BufferedReader readfile2 = new BufferedReader(new FileReader(filename));
		String read1;
		int row1=0;
		int column1=0;
		Set<String> edges = new HashSet<String>();
		Map<Integer,Integer> finalMap = new LinkedHashMap<Integer, Integer>();
		
		while ((read1 = readfile2.readLine()) != null) 
		{
			String tokens1[]=read1.split("\\s");
			column1=0;
			for(int i=0;i<=1;i++)
			{   
				String rownum=tokens1[0];
				String columnnum=tokens1[1];
				finalMap.put(Integer.parseInt(rownum), Integer.parseInt(rownum));
				finalMap.put(Integer.parseInt(columnnum), Integer.parseInt(columnnum));
				String addset = rownum+"+"+columnnum;
				String addset1 = columnnum+"+"+rownum;
				edges.add(addset);
				edges.add(addset1);
			}
			row1++;
		}//end of while
		
		//creation of adjacency matrix
		for(int i=0;i<mNodes;i++)
		{
			for(int j=0;j<mNodes;j++)
			{
				String checkval = Integer.toString(i)+"+"+Integer.toString(j);
				if(edges.contains(checkval)||(i==j))
				{
					adjacency_matrix[i][j] = 1;
				}
				else
				{
					adjacency_matrix[i][j] = 0;
				}
			}
		}
		
		markov_matrix=returnMatrix(mNodes, adjacency_matrix,inflationParameter);
		/*System.out.println(" The Final markov matrix is as follows..");
		for(int i=0;i<mNodes;i++)
		{
			for(int j=0;j<mNodes;j++)
			{
				System.out.print(markov_matrix[i][j]);
			}
			System.out.println();
		}*/
		
		int countCluster=0;
		for (int i = 0; i < mNodes; i++) 
		{
			for (int j = 0; j < mNodes; j++) 
			{
				if(markov_matrix[i][j]!=0)
				{
					countCluster++;
					break;
				}
				
			}
		}
		System.out.println();
		System.out.println("Number of clusters = " + countCluster);
		int clusternum=1;
		
		Map<Integer,Integer> clusterMap = new TreeMap<Integer, Integer>();
		for(int i=0;i<mNodes;i++)
		{
			int flag=0;
			for(int j=0;j<mNodes;j++)
			{
				if(markov_matrix[i][j]!=0)
				{
					flag=1;
					clusterMap.put(j, clusternum);
				}
			}
			if(flag==1)
			{
				clusternum++;
				flag=0;
			}
			
		}
		
		
		
		/*System.out.println("... Clustering results....");
		for(Map.Entry<Integer, Integer> entry: clusterMap.entrySet())
		  {
		   
			System.out.println(entry.getKey()+"\t"+entry.getValue());

		  }*/

		for(Map.Entry<Integer, Integer> entry: finalMap.entrySet())
		  {
		   
			if(clusterMap.containsKey(entry.getKey()))
			{
				int clusterValue=clusterMap.get(entry.getKey());
				finalMap.put(entry.getKey(), clusterValue);
			}

		  }
		System.out.println();
		System.out.println("... Clustering results....");
		System.out.println();
		FileWriter outfile = new FileWriter("attweb_net.clu");
		PrintWriter out = new PrintWriter(outfile);
		out.println("*Vertices 180");
		for(Map.Entry<Integer, Integer> entry: finalMap.entrySet())
		  {
			System.out.println("Node = "+entry.getKey()+" Cluster = " +entry.getValue());
			//System.out.println(entry.getValue());
			out.println(entry.getValue());

		  }
		
		out.close();
		
		
       }//end of if
       
       else if(filename.equals("phy.txt"))
       {
    	   BufferedReader readfile = new BufferedReader(new FileReader(filename));
   		while(readfile.readLine()!=null)	
   		{
   			mNumLines++;
   		}
   		System.out.println(" The number of edges = "+mNumLines);
   		System.out.println();
    	   HashMap<String,Integer> storeMap=new LinkedHashMap<String,Integer>();
    	   int mNodes=142;
    	   BufferedReader one=new BufferedReader(new FileReader(filename));
    	   String read;
   		int value=0;
   		
   		while((read=one.readLine())!=null)
   		{   
   			String tokens[]=read.split("\\s");
   			if(!storeMap.containsKey(tokens[0]))
   			{
   				storeMap.put(tokens[0], value++);
   			}
   			if(!storeMap.containsKey(tokens[1]))
   			{   
   			
   			    storeMap.put(tokens[1], value++);
   			}
   		}
   		double adjacency_matrix[][] = new double[mNodes][mNodes];
   		for(int i=0;i<mNodes;i++)
   		{
   			for(int j=0;j<mNodes;j++)
   			{
   				if(i==j)
   				{
   					adjacency_matrix[i][j]=1;
   				}
   				else
   				{
   					adjacency_matrix[i][j]=0;
   				}
   			}
   		}
		double markov_matrix[][] = new double[mNodes][mNodes];
   		one=new BufferedReader(new FileReader(filename));
   		
		 while((read=one.readLine())!=null)
		 {
			 String tokens[]=read.split("\\s");
			 int k1=storeMap.get(tokens[0]);
			 int k2=storeMap.get(tokens[1]);
			 adjacency_matrix[k1][k2]=1;
			 adjacency_matrix[k2][k1]=1;
		 }
		 
		 markov_matrix=returnMatrix(mNodes, adjacency_matrix,inflationParameter);
			/*System.out.println(" The Final markov matrix is as follows..");
			for(int i=0;i<mNodes;i++)
			{
				for(int j=0;j<mNodes;j++)
				{
					System.out.print(markov_matrix[i][j]);
				}
				System.out.println();
			}*/
			
			int countCluster=0;
			for (int i = 0; i < mNodes; i++) 
			{
				for (int j = 0; j < mNodes; j++) 
				{
					if(markov_matrix[i][j]!=0)
					{
						countCluster++;
						break;
					}
					
				}
			}
			System.out.println("Number of clusters = " + countCluster);
			System.out.println();
			int clusternum=1;
			
			Map<Integer,Integer> clusterMap = new TreeMap<Integer, Integer>();
			for(int i=0;i<mNodes;i++)
			{
				int flag=0;
				for(int j=0;j<mNodes;j++)
				{
					if(markov_matrix[i][j]!=0)
					{
						flag=1;
						clusterMap.put(j, clusternum);
					}
				}
				if(flag==1)
				{
					clusternum++;
					flag=0;
				}
				
			}
			
			for(Map.Entry<String, Integer> entry: storeMap.entrySet())
			  {
			   
				int valueCluster = storeMap.get(entry.getKey());
				if(clusterMap.containsKey(valueCluster))
				{
					int clusterValue=clusterMap.get(valueCluster);
					storeMap.put(entry.getKey(), clusterValue);
				}
				
			  }
			
			FileWriter outfile = new FileWriter("physics_collaboration_net.clu");
			PrintWriter out = new PrintWriter(outfile);
			out.println("*Vertices 142");
			System.out.println("... Clustering results....");
			System.out.println();
			/*for(Map.Entry<Integer, Integer> entry: clusterMap.entrySet())
			  {
			  //System.out.println((entry.getKey()+1) +"\t"+entry.getValue());
			  System.out.println("Node = "+entry.getKey()+" Cluster = " +entry.getValue());
			  out.println(entry.getValue());
			  }*/
			
			for(Map.Entry<String, Integer> entry: storeMap.entrySet())
			  {
			  //System.out.println((entry.getKey()+1) +"\t"+entry.getValue());
			  System.out.println("Node = "+entry.getKey()+" Cluster = " +entry.getValue());
			  out.println(entry.getValue());
			  }
			out.close();
    	   
       }
       else if(filename.equals("yeast.txt"))
       {
    	   BufferedReader readfile = new BufferedReader(new FileReader(filename));
   		while(readfile.readLine()!=null)	
   		{
   			mNumLines++;
   		}
   		System.out.println(" The number of Edges = "+mNumLines);
   		System.out.println();
    	   HashMap<String,Integer> storeMap=new LinkedHashMap<String,Integer>();
    	   int mNodes=359;
    	   BufferedReader one=new BufferedReader(new FileReader(filename));
    	   String read;
   		int value=0;
   		
   		while((read=one.readLine())!=null)
   		{   
   			String tokens[]=read.split("\\s");
   			if(!storeMap.containsKey(tokens[0]))
   			{
   				storeMap.put(tokens[0], value++);
   			}
   			if(!storeMap.containsKey(tokens[1]))
   			{   
   			
   			    storeMap.put(tokens[1], value++);
   			}
   		}
   		double adjacency_matrix[][] = new double[mNodes][mNodes];
   		for(int i=0;i<mNodes;i++)
   		{
   			for(int j=0;j<mNodes;j++)
   			{
   				if(i==j)
   				{
   					adjacency_matrix[i][j]=1;
   				}
   				else
   				{
   					adjacency_matrix[i][j]=0;
   				}
   			}
   		}
		double markov_matrix[][] = new double[mNodes][mNodes];
   		one=new BufferedReader(new FileReader(filename));
   		
		 while((read=one.readLine())!=null)
		 {
			 String tokens[]=read.split("\\s");
			 int k1=storeMap.get(tokens[0]);
			 int k2=storeMap.get(tokens[1]);
			 adjacency_matrix[k1][k2]=1;
			 adjacency_matrix[k2][k1]=1;
		 }
		 
		 markov_matrix=returnMatrix(mNodes, adjacency_matrix,inflationParameter);
			//System.out.println(" The Final markov matrix is as follows..");
			/*for(int i=0;i<mNodes;i++)
			{
				for(int j=0;j<mNodes;j++)
				{
					System.out.print(markov_matrix[i][j]);
				}
				System.out.println();
			}*/
			
			int countCluster=0;
			for (int i = 0; i < mNodes; i++) 
			{
				for (int j = 0; j < mNodes; j++) 
				{
					if(markov_matrix[i][j]!=0)
					{
						countCluster++;
						break;
					}
					
				}
			}
			System.out.println("Number of clusters = " + countCluster);  
			System.out.println();
			
			int clusternum=1;
			
			Map<Integer,Integer> clusterMap = new TreeMap<Integer, Integer>();
			for(int i=0;i<mNodes;i++)
			{
				int flag=0;
				for(int j=0;j<mNodes;j++)
				{
					if(markov_matrix[i][j]!=0)
					{
						flag=1;
						clusterMap.put(j, clusternum);
					}
				}
				if(flag==1)
				{
					clusternum++;
					flag=0;
				}
				
			}
			for(Map.Entry<String, Integer> entry: storeMap.entrySet())
			  {
			   
				int valueCluster = storeMap.get(entry.getKey());
				if(clusterMap.containsKey(valueCluster))
				{
					int clusterValue=clusterMap.get(valueCluster);
					storeMap.put(entry.getKey(), clusterValue);
				}
				
			  }
			FileWriter outfile = new FileWriter("yeast_undirected_metabolic.clu");
			PrintWriter out = new PrintWriter(outfile);
			out.println("*Vertices 359");
			System.out.println("... Clustering results....");
			System.out.println();
			/*for(Map.Entry<Integer, Integer> entry: clusterMap.entrySet())
			  {
			   
				System.out.println("Node = "+entry.getKey()+" Cluster = " +entry.getValue());
				//System.out.println(entry.getValue());
				out.println(entry.getValue());
			  }*/
			for(Map.Entry<String, Integer> entry: storeMap.entrySet())
			  {
			   
				System.out.println("Node = "+entry.getKey()+" Cluster = " +entry.getValue());
				//System.out.println(entry.getValue());
				out.println(entry.getValue());
			  }
			out.close();
       }
       else
       {
    	   System.out.println(" Wrong file");
    	   System.exit(0);
       }
		
	}
	
	public static double[][] returnMatrix(int nodes, double[][] initialMatrix, double inflationParameter)
	{
		
		
		double markov_matrix[][] = new double[nodes][nodes];
		double expand_matrix[][] = new double[nodes][nodes];
		double inflate_matrix[][] = new double[nodes][nodes];
		
		
		
		for(int i=0;i<nodes;i++)
		{
			for(int j=0;j<nodes;j++)
			{
				markov_matrix[i][j]=initialMatrix[i][j];
			}
		}
		

		//markov matrix creation
		//normalize the matrix
		//calculate the total number of connections in each column and give the probability accordingly
		for(int i=0;i<nodes;i++)
		{
			double sumColumn=0;
			for(int j=0;j<nodes;j++)
			{
				if(markov_matrix[j][i]==1)
				{
					sumColumn++;
				}
			}
			
			for(int j=0;j<nodes;j++)
			{
				if(markov_matrix[j][i]==1)
				{
					markov_matrix[j][i]=(double)1/sumColumn;
				}
			}
		}
		
		int level=0;
		
		//creation of expanded matrix
		while(true)
		{
			int flag=0;
			level++;
			//System.out.println(" level = "+level);
			RealMatrix matrix1 = new Array2DRowRealMatrix(markov_matrix);
			RealMatrix matrix2 = new Array2DRowRealMatrix(markov_matrix);
			RealMatrix mulMatrix = matrix1.multiply(matrix2);
			expand_matrix=mulMatrix.getData();
			
				
		//inflate matrix creation
				for(int i=0;i<nodes;i++)
				{
					for(int j=0;j<nodes;j++)
					{
						inflate_matrix[i][j]=expand_matrix[i][j];
					}
				}
				
				
				for(int i=0;i<nodes;i++)
				{
					double sumSquare=0;
					for(int j=0;j<nodes;j++)
					{
						sumSquare = sumSquare + Math.pow(inflate_matrix[j][i],inflationParameter);
					}
					for(int j=0;j<nodes;j++)
					{
						if(sumSquare!=0)
						inflate_matrix[j][i]= (Math.pow(inflate_matrix[j][i],inflationParameter))/sumSquare;
					}
				}
				
				//check whether both the matrices converge
				for(int i=0;i<nodes;i++)
				{
					for(int j=0;j<nodes;j++)
					{
						if(inflate_matrix[i][j]!=markov_matrix[i][j])
						{
							flag=1;
							break;
						}
					}
					if(flag==1)
					{
						break;
					}
				}
				
				if(flag==0)
				{
					break;//break from while
				}
				else
				{
					RealMatrix newMat = new Array2DRowRealMatrix(inflate_matrix);
					markov_matrix=newMat.getData();
				}
				
		}//end of while
		return markov_matrix;
	}
}
