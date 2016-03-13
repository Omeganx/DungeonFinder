import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class dungen {
	static int chunk_x_range;
	static int chunk_z_range;
	static long seed;
	static Random rand = new Random();
	static Random guess = new Random();
	static HashMap<Integer, HashMap<Integer, List<Integer[]>>> dongeons = new HashMap<Integer, HashMap<Integer, List<Integer[]>>>();
	
	
	public dungen(long seed2) {
		seed = seed2; 
		
		
		}

	public void print(int x, int z){
		for(int c_x = x-3; c_x<=x+3; ++c_x){
			for(int c_z = z-3; c_z<=z+3; ++c_z){
				if(dongeons.containsKey(c_x)){
					if(dongeons.get(c_x).containsKey(c_z)){
						System.out.println("True");
						Map<Integer, List<Integer[]>> xChunk = dongeons.get(c_x);
						List<Integer[]> spawners = xChunk.get(c_z);
						Iterator<Integer[]> iterator = spawners.iterator();
						System.out.println("Dungeon at chunk"+String.valueOf(c_x)+";"+String.valueOf(c_z)+" {");
						while (iterator.hasNext()) {
							Integer[] coord = iterator.next();
							System.out.println(String.valueOf(coord[0])+";"+String.valueOf(coord[1])+";"+String.valueOf(coord[2]));
						}
					}
				}
			}
		}
	}
	
	public HashMap<Integer, HashMap<Integer, List<Integer[]>>> getDungeons(int x, int z){
		start(x, z);
		return dongeons;
	}
	private void start(int x, int z) {
		dongeons.clear();
		for(int c_x = x-3; c_x<=x+3; ++c_x){
			for(int c_z = z-3; c_z<=z+3; ++c_z){
				guess(c_x, c_z, seed);
			}
		}
		
		//delete mineshafts chunks:

		for(int c_x = x-3; c_x<x+3; ++c_x){
			for(int c_z = z-3; c_z<z+3; ++c_z){
				if(mineshaft(c_x, c_z)){
					delete(c_x, c_z);
				}
			}
		}
		
	} 


	private void delete(int c_x, int c_z) {
		int mineshaft_range = 4;
		HashMap<Integer, HashMap<Integer, List<Integer[]>>> tempdung = dongeons;
		for(int x=c_x-mineshaft_range;x<c_x+mineshaft_range;x++){
			if(dongeons.containsKey(x)){
				for(int z=c_z-mineshaft_range;z<c_z+mineshaft_range;z++){
				 if(dongeons.get(x).containsKey(z));
				 	tempdung.get(x).remove(z);

				}
			}
		}
		dongeons = tempdung;
	}
	



	private boolean mineshaft(int c_x, int c_z) {
		
		
		rand.setSeed(seed);
		long seedMod1 = rand.nextLong();
		long seedMod2 = rand.nextLong();
		
		long seedMod3 = (long)c_x * seedMod1;
		long seedMod4 = (long)c_z * seedMod2;
		
		rand.setSeed(seedMod3 ^ seedMod4 ^ seed);
		
		rand.nextInt();
		if(rand.nextDouble() < 0.01D && rand.nextInt(80) < Math.max(Math.abs(c_x), Math.abs(c_z))){
			return true;
		}
		
		return false;
		
	}


	@SuppressWarnings("unused")
	private static void guess(int chunk_x, int chunk_z, long seed){
		
	    int i = chunk_x * 16;
	    int j = chunk_z * 16;
	    guess.setSeed(seed);
	    long k = guess.nextLong() / 2L * 2L + 1L;
	    long l = guess.nextLong() / 2L * 2L + 1L;
	    guess.setSeed((long)chunk_x * k + (long)chunk_z * l ^ seed);
	    
	    if(guess.nextInt(4) == 0){ //water lakes
	    	int i1 = guess.nextInt(16) + 8;
	        int j1 = guess.nextInt(256);
	        int k1 = guess.nextInt(16) + 8;
	        int i2 = guess.nextInt(4) + 4;

	        for (int j2 = 0; j2 < i2; ++j2)
	        {

	            double d0 = guess.nextDouble() * 6.0D + 3.0D;
	            double d1 =	guess.nextDouble() * 4.0D + 2.0D;
	            double d2 = guess.nextDouble() * 6.0D + 3.0D;
	            double d3 = guess.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
	            double d4 = guess.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
	            double d5 = guess.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;
	        }
	    }
	    if(guess.nextInt(80 / 10)==0){ //lava lakes
	    	int i2 = guess.nextInt(16) + 8;
	        int l2 = guess.nextInt(guess.nextInt(248) + 8);
	        int k3 = guess.nextInt(16) + 8;
	        if (l2 < 63 || guess.nextInt(80 / 8) == 0){
	        	i2 = guess.nextInt(4) + 4;
	        	for (int j2 = 0; j2 < i2; ++j2)
	            {
	                double d0 = guess.nextDouble() * 6.0D + 3.0D;
	                double d1 =	guess.nextDouble() * 4.0D + 2.0D;
	                double d2 = guess.nextDouble() * 6.0D + 3.0D;
	                double d3 = guess.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
	                double d4 = guess.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
	                double d5 = guess.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;
	            }
	        }
	        
		}
	    if (!dongeons.containsKey(chunk_x )) {
			dongeons.put(chunk_x , new HashMap<Integer, List<Integer[]>>());
		}
	     List<Integer[]> coord = new ArrayList<Integer[]>();
	    for (int j2 = 0; j2 < 8; ++j2)
	    {

	        int posx = guess.nextInt(16) + 8;
	        int posy = guess.nextInt(256);
	        int posz = guess.nextInt(16) + 8; 
	        int size_x = guess.nextInt(2) + 2;
	        int size_z = guess.nextInt(2) + 2;
	        if(checkSize(posx, posz, size_x, size_z, chunk_x, chunk_z) && posy>80){
	        	coord.add(new Integer[] {posx + chunk_x * 16, posy, posz + chunk_z * 16, size_x, size_z});
	        }
	    }
	    dongeons.get(chunk_x).put(chunk_z, coord);
	}


	private static boolean checkSize(int posx, int posz, int size_x, int size_z, int cx, int cz) {
		int min_x = cx * 16 + posx - size_x - 1;
		int max_x = cx * 16 + posx + size_x + 1;
		int min_z = cz * 16 + posz - size_z -1;
		int max_z = cz * 16 + posz + size_z + 1;
		if(((min_x -(min_x%16))/16 == (max_x -(max_x%16))/16) && ((min_z -(min_z%16))/16 == (max_z -(max_z%16))/16)){
			return true;
		}
		else{
			return false;
		}
		
	}
}
