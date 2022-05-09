import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.function.Function;

public class CoreConcept {

	public static void main(String[] args) {

		Map<String, String> hm = new LinkedHashMap<>();
		hm.put(null, null);
		hm.put(null, "Null"); // overwrite
		hm.put("name", "milind");
		System.out.println(hm);
		String s1 = "mk1";
		String s2 = "mk1";
		String s3 = new String("milind");
		String s4 = new String("milind");
		if(s1.equals(s2)) {
			System.out.println("Equal");
		}else {
			System.out.println("Not Equal");
		}
		
		Set<String> s = new HashSet<String>();
		s.add(null);
		s.add(null);
		boolean b = s.add("Milind");
		boolean bb = s.add("Milind");
		System.out.println("Set >>> "+s + "=== b" + b + "===bb "+bb);
		
		Vector<String> v = new Vector<String>();
		v.add("A");
		v.add("C");
		v.add("B");
//		Collections.fill(v, 1);
		System.out.println("before >>> "+v);
		Collections.sort(v);
		System.out.println("Sorting using intenal comparable ::::"+v);
		Collections.sort(v, (b1,b2)-> b1.equals(b2)?0:(b1.length()>b2.length()?1:-1)); // decending order
		System.out.println("Sorting using custom comparator ::::"+v);
		System.out.println("Vector ==> "+v);
		
		
		ArrayList arrayList = new ArrayList<String>();
		LinkedList<String> linkedList = new LinkedList<>();
//		linkedList.get(0);
		
		int[] nums = { 8, 7, 2, 5, 3, 1 };
		Arrays.sort(nums);
		System.out.println(Arrays.toString(nums));
        int target = 10;
        
        String desc = "My name is Milind";
        String desc1 = new String("My name is Milind").intern();
        int result = desc.compareTo(desc1);
        System.out.println("Str compare >>> "+result);
        
        char[] strArray = desc.toCharArray();
 
        findPair(nums, target);
        findDuplicateChar(strArray);
       Optional<CoreConcept> obj= getOptionalObject(true);
       if(obj.isPresent()) {
       System.out.println("Optional >>> " +obj);
       }else {
    	   System.out.println("Optional not present >>> " +obj);
       }
	}
	
	public static Optional<CoreConcept> getOptionalObject(boolean flag) {
		CoreConcept coreConcept = null;
		if(flag) {
			coreConcept = new CoreConcept();
		}
		return Optional.of(coreConcept);
	}
	
	public static void findPair(int[] nums, int target)
    {
        // create an empty HashMap
        Map<Integer, Integer> map = new HashMap<>();
 
        // do for each element
        for (int i = 0; i < nums.length; i++)
        {
            // check if pair (nums[i], target-nums[i]) exists
 
            // if the difference is seen before, print the pair
        	//10 - 8 = 2 i.e 10=8+2 that means when there 2 element 8 key present in map so we can find
        	// pair(8,2) which sum is equal to target i.e
            if (map.containsKey(target - nums[i]))
            {
                System.out.printf("Pair found (%d, %d)",
                    nums[map.get(target - nums[i])], nums[i]);
//                return;
            }
 
            // store index of the current element in the map
            map.put(nums[i], i);
        }
 
        // we reach here if the pair is not found
        System.out.println("Pair not found");
    }
	
	public static void findDuplicateChar(char[] charArray)
    {
        // create an empty HashMap
        Map<String, Integer> map = new HashMap<>();
 
        // do for each element
        for (int i = 0; i < charArray.length; i++)
        {
            // check if pair (nums[i], target-nums[i]) exists
        	
 
            // if the difference is seen before, print the pair
        	//10 - 8 = 2 i.e 10=8+2 that means when there 2 element 8 key present in map so we can find
        	// pair(8,2) which sum is equal to target i.e
            if (map.containsKey(Character.toString(charArray[i])))
            {
            	 map.put(Character.toString(charArray[i]), map.get(Character.toString(charArray[i]))+1);
                System.out.printf("Pair found");
//                return;
            }else {
 
            // store index of the current element in the map
            map.put(Character.toString(charArray[i]), 1);
            }
        }
 
        // we reach here if the pair is not found
        System.out.println("Pair not found"+map);
    }
 
}
