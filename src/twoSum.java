import java.util.HashMap;
import java.util.Map;


public class twoSum {
    public static int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();  
        int n = numbers.length;  
        int[] result = new int[2];  
        for (int i = 0; i < numbers.length; i++)  
        {  
            if (map.containsKey(target - numbers[i]))  
            {  
                result[0] = map.get(target-numbers[i]) + 1;  //map中不能存放相同key的元素(会被抹掉)，因此应该在放入之前比较 
                result[1] = i + 1;  //或者可以只需要判断是否找到的index与i不同
                break;  
            }  
            else  
            {  
                map.put(numbers[i], i);  
            }  
        }  
        return result;
//        HashMap<Integer,Integer> mapping=new HashMap<Integer, Integer>();
//        int i=0;
//        int res[]=new int[2];
//        for(i=0; i<numbers.length;i++){
//            //mapping.get(numbers[i]);//key=numbers[i]; value=i??
//            mapping.put(numbers[i], i);
//        }
//        for(i=0; i<numbers.length;i++){//hashmap change the order?
//            int tt=target-numbers[i];
//            if(mapping.containsKey(tt)&&mapping.get(numbers[i])!=mapping.get(tt)){
//                res[0]=mapping.get(numbers[i])+1;
//                res[1]=mapping.get(tt)+1;
//                break;
//            }
//        }
//        return res;
    }
    public static void main (String[] arg){
    	int numbers[]={0,4,3,0,2};
    	int t=2;
    	twoSum(numbers,t);
    }
}
