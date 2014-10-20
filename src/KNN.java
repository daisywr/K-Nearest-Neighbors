import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.io.*;

public class KNN {
	
	// private static
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList readFile(String fileName, ArrayList traincls) {
		// read fileName(could be train or test) in this project, return an
		// ArrayList<instance> storing the train data both features and class
		// included in instance class
		// For train file it would return a non-empty traincls storing class
		// types.
		ArrayList result = new ArrayList();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String str = null;
			for (int j = 0; j < 3; j++) {
				str = br.readLine();
			}
			while (str != null) {
//				sp = new String[] {};
				String[] sp = str.split(",");// split input stream into a string
												// array
				double[] f = new double[sp.length - 1];
				for (int i = 0; i < 4; i++) {
					f[i] = Double.parseDouble(sp[i]);
				}
				instance tmp = new instance(sp[4], f);
				if (sp[4] != "?") {
					if (!traincls.contains(sp[4]))
						traincls.add(sp[4]);
				}
				result.add(tmp);
				str = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();//note!
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<instance> euclideanDist(double[] testTmp, ArrayList train) {
		// calculate the distance of each instances in test to all train data and return the distance and type
		ArrayList<instance> res = new ArrayList<instance>();
		for (int i = 0; i < train.size(); i++) {
			instance trainTmp = (instance) train.get(i);
			double[] sum = new double[1];
			for (int j = 0; j < testTmp.length; j++)
				sum[0] += Math.sqrt(Math.abs(Math.pow(testTmp[j], 2)-Math.pow(trainTmp.getfea()[j], 2)));
			instance tmp=new instance(trainTmp.getcls(),sum);
			res.add(tmp);
		}
		return res;
	}

	public static ArrayList<ArrayList<instance>> dist(ArrayList<instance> train, ArrayList<instance> test) {
		// calculate distances of all items in test to train data
		ArrayList<ArrayList<instance>> distance = new ArrayList<ArrayList<instance>>();
		ArrayList<instance> dtt = new ArrayList<instance>();
		for (int i = 0; i < test.size(); i++) {
			instance tmp = (instance) test.get(i);
			dtt = euclideanDist(tmp.getfea(), train);
			distance.add(dtt);
		}
		return distance;
	}
	public static instance Max(ArrayList<instance> tmp){
		instance max=new instance();
		double m=-1;
		for(int i=0; i<tmp.size();i++){
			if(tmp.get(i).getfea()[0]>m){
				max=tmp.get(i);
				m=max.getfea()[0];
			}
		}
		return max;
	}
	public static void knn(String trainFile, String testFile, int k) {
		// implement to read the train and test file into array, then all
		// calculate the distance
		// for loop is to firstly create map<distance,traincls> for each test
		// instance
		// next create a treeMap to sort the map according to the distance
		// next extract first k items in treeMap and calculate the occurence of
		// each class in traincls
		ArrayList<String> traincls = new ArrayList<String>();
		ArrayList nulltest = new ArrayList();
		ArrayList changedtest = new ArrayList();
		ArrayList allTrainInstance = readFile(trainFile, traincls); //return train instance
		ArrayList allTestInstance = readFile(testFile, nulltest);
		ArrayList<ArrayList<instance>> testDisToTrain = dist(allTrainInstance, allTestInstance);//return distance of all tests element to train
		ArrayList<instance> KQue=new ArrayList<instance>();
		for (int i = 0; i < allTestInstance.size(); i++) {// for every instance															// in test
			ArrayList<instance> n = testDisToTrain.get(i);
			for (int j = 0; j < n.size(); j++) {// for every instance in train
				if(KQue.size()>=k){
					if(Max(KQue).getfea()[0]>n.get(j).getfea()[0]){
						KQue.remove(Max(KQue));
						KQue.add(n.get(j));
					}
				}
				else{
					KQue.add(n.get(j));
				}
			}
			int[] countcls = new int[traincls.size()];
			instance[] kItems=new instance[k];
			instance[] tt=KQue.toArray(kItems);
			for(int e=0; e<tt.length;e++) {
				for(int ss=0;ss<traincls.size();ss++){
					String a=tt[e].getcls();
					String b=traincls.get(ss);
					if(a.equals(b))//note!
						countcls[ss]++;
				}
			}
			int maxIndex = 0;
			for (int y = 0; y < countcls.length; y++) {
				if (countcls[y] > countcls[maxIndex]) {
					maxIndex =y;
				}
			}
			instance curTestInstance = (instance) allTestInstance.get(i);
			curTestInstance.setcls(traincls.get(maxIndex));
			changedtest.add(curTestInstance);
		}
		writeFile(testFile, changedtest);
	}

	public static void writeFile(String testFile,
			ArrayList<instance> changedtest) {
		try {
			PrintWriter write = new PrintWriter("Changed"+testFile, "UTF-8");
			BufferedReader rd = new BufferedReader(new FileReader(testFile));
			String str = rd.readLine();
			write.println(str);
			str = rd.readLine();
			write.println(str);
			for (int i = 0; i < changedtest.size(); i++) {
				instance ins = (instance) changedtest.get(i);
				for (double f : ins.getfea()) {
					write.print(f + ", ");
				}
				write.println(ins.getcls());
			}
			write.close();
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Please enter the K value:");
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		String ks=in.readLine();
		int k=Integer.parseInt(ks);
		knn("IrisTrain.csv", "IrisTest.csv", k);
		in.close();
	}

}