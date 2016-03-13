import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*  au début 1 donjon; + 1 set de dongeons généré avec le programme;
 *  fonction qui récupère le nombres de dongeons à générer;
 *  fonction qui parcourt tout les chunks restants et les blocks restant en essayant de générer de nouveaux dongeons;
 *  à la fin, récupère tout les "dongeon candidat" (en ayant éliminé les dongeons impossibles (bordure de chunks, mauvaise hauteur, pas au bon endroit..)
 * 	puis teste les dongeons et retourne le bon
 * 
*/



public class recallDun {
	static Random guess;
	static long seed;
	public recallDun(long s){
		seed = s;
	}
	

		
	
	private static List<Integer[]> findbest(Integer[] coord, List<Integer[]> dungeons){
		//int order = getOrder(coord);

		for(int k = 0; k<(coord[3] * 2 + 1)*(coord[4] * 2 + 1)+13;k++){
			Integer[] newdung = reGenDun(coord, k);
			if(newdung!=null){

				if(newdung[1] > 80 && checkSize(newdung)){
					List<Integer[]> coords = copy(dungeons);
					coords.add(newdung);
					if(minCercleInt(coords) <16 && isInside(coords)){
						System.out.println("Found a 2nd dungeon");
						return coords;
					}
				}
			}
			
		}return dungeons;
	}
	
	

	static List<Integer[]> recallGen(List<Integer[]> dungeons, long s) {
		seed = s;
		for(int i = 0; i<dungeons.size();i++){
			Integer[] coord = dungeons.get(i);
				dungeons = findbest(coord,dungeons);
				
			
		}return dungeons;
		
	}

	
	
	private static List<Integer[]> copy(List<Integer[]> d){
		List<Integer[]> c = new ArrayList<Integer[]>();
		for(int i =0;i<d.size();i++){
			Integer[] tab = d.get(i);
			c.add(tab);
		}
		return c;
	}
		
	
	
	@SuppressWarnings("unused")
	private static int getOrder(Integer[] coord){

		int cx = (int)(coord[0] - 8) /16;
		int cz =  (int)(coord[2] - 8)/16;
		if(coord[0]<0){
			cx = cx -1;
		}
		if(coord[2]<0){
			cz = cz -1;
		}

		
	    guess.setSeed(seed);
	    long k = guess.nextLong() / 2L * 2L + 1L;
	    long l = guess.nextLong() / 2L * 2L + 1L;
	    guess.setSeed((long)cx * k + (long)cz * l ^ seed);
	    
	    if(guess.nextInt(4) == 0){ //water lakes
	    	guess.nextInt(16);
	        guess.nextInt(256);
	        guess.nextInt(16);
	        int i2 = guess.nextInt(4) + 4;

	        for (int j2 = 0; j2 < i2; ++j2)
	        {

	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	        }
	    }
	    if(guess.nextInt(80 / 10)==0){ //lava lakes
	    	int i2 = guess.nextInt(16) + 8;
	        int l2 = guess.nextInt(guess.nextInt(248) + 8);
	        guess.nextInt(16);
	        if (l2 < 63 || guess.nextInt(80 / 8) == 0){
	        	i2 = guess.nextInt(4) + 4;
	        	for (int j2 = 0; j2 < i2; ++j2)
	            {
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	            }
	        }
	        
		}
	    
	    int i = 0;
	    for (int j2 = 0; j2 < 8; ++j2)
	    {
	    	
	        int posx = guess.nextInt(16) + 8;
	        int posy = guess.nextInt(256);
	        int posz = guess.nextInt(16) + 8;
	        guess.nextInt();
	        guess.nextInt();
		    
	        if(coord[0] == (posx+cx*16) && coord[1] == posy && coord[2] == (posz+cz*16)){
	        	i = j2 +1;
	        	return i;
	        }
	    }
		return i;
	}
	
	

	private static Integer[] reGenDun(Integer[] coord, int count) {
		int cx = (int)(coord[0] - 8) /16;
		int cz = (int)(coord[2] - 8) /16;
		if(coord[0]<0){
			cx = cx -1;
		}
		if(coord[2]<0){
			cz = cz -1;
		}
		Random guess = new Random();
	    guess.setSeed(seed);
	    long k = guess.nextLong() / 2L * 2L + 1L;
	    long l = guess.nextLong() / 2L * 2L + 1L;
	    guess.setSeed((long)cx * k + (long)cz * l ^ seed);
	    
	    if(guess.nextInt(4) == 0){ //water lakes
	    	guess.nextInt(16);
	        guess.nextInt(256);
	        guess.nextInt(16);
	        int i2 = guess.nextInt(4) + 4;

	        for (int j2 = 0; j2 < i2; ++j2)
	        {

	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	            guess.nextDouble();
	        }
	    }
	    if(guess.nextInt(80 / 10)==0){ //lava lakes
	    	int i2 = guess.nextInt(16) + 8;
	        int l2 = guess.nextInt(guess.nextInt(248) + 8);
	        guess.nextInt(16);
	        if (l2 < 63 || guess.nextInt(80 / 8) == 0){
	        	i2 = guess.nextInt(4) + 4;
	        	for (int j2 = 0; j2 < i2; ++j2)
	            {
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	                guess.nextDouble();
	            }
	        }
	        
		}

	    for (int j2 = 0; j2 < 8; ++j2)
	    {
	    	
	        int posx = guess.nextInt(16) + 8;
	        int posy = guess.nextInt(256);
	        int posz = guess.nextInt(16) + 8;
	        guess.nextInt();
	        guess.nextInt();

	        //System.out.println("Dungeon:"+String.valueOf(posx+cx*16)+";"+String.valueOf(posy)+";"+String.valueOf(posz+cz*16)+" size:"+String.valueOf(size_x)+";"+String.valueOf(size_z));
		      
	        if(coord[0] == (posx+cx*16) && coord[1] == posy && coord[2] == (posz+cz*16)){

	        	for (int i = 0; i<count+13;i++) {
	        			guess.nextInt();
	        		}
	        	int posx1 = guess.nextInt(16) + 8;
     	        int posy1 = guess.nextInt(256);
     	        int posz1 = guess.nextInt(16) + 8;
     	        int size_x1 = guess.nextInt(2) +3;
     	        int size_z1 = guess.nextInt(2) + 3;
     	        Integer[]  coords = {posx1, posy1,posz1,size_x1,size_z1};
     	        return coords;
	        	}
	        
	    }
		return null;
		
	}

	

	private static boolean  isInside(List<Integer[]> dung) {

		for(int i=0; i<dung.size();i++){
			Integer[] coord1 = dung.get(i);
			for(int k=i+1;k<dung.size();k++){
				Integer[] coord2 = dung.get(k);
				if( (Math.abs(coord1[0]-coord2[0])<(coord1[3]+coord2[3]) && Math.abs(coord1[2]-coord2[2])<(coord1[4]+coord2[4]) && Math.abs(coord1[1]-coord2[1])<8) ){
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	

	
	public static float minCercleInt(List<Integer[]> dung){
		
		int[] a = new int[3];
		int[] b = new int[3];
		int[] c = new int[3];
		
		float max = 0;
		int[] coords = new int[3];
		
		for(int i=0; i<dung.size();i++){
			
			Integer[] coord1 = dung.get(i);
			for(int k=i+1;k<dung.size();k++){
				
				Integer[] coord2 = dung.get(k);
				float dist = (float) Math.sqrt(Math.pow(coord1[0]-coord2[0],2)+ Math.pow(coord1[1]-coord2[1],2)+Math.pow(coord1[2]-coord2[2], 2));
				if(dist > max){
					max = dist;

					coords[0] = (int) (coord1[0] + coord2[0])/2;
					coords[1] = (int) (coord1[1] + coord2[1])/2;
					coords[2] = (int) (coord1[2] + coord2[2])/2;
					
					a[0]=coord1[0];a[1]=coord1[1];a[2]=coord1[2];
					
					b[0]=coord2[0];b[1]=coord2[1];b[2]=coord2[2];
					
				}
			}
		}
		int flag = 2;
		
		for(int i=0; i<dung.size();i++){
			Integer[] coord1 = dung.get(i);
			float dist = 2*(float) Math.sqrt(Math.pow(coord1[0]-coords[0],2)+ Math.pow(coord1[1]-coords[1],2)+Math.pow(coord1[2]-coords[2], 2));
			if(equals(a, coord1) && equals(b, coord1) && dist > max){
				flag = 3;
				max = dist;
				c[0] = coord1[0];c[1] = coord1[1];c[2] = coord1[2];
			}
			
		}
		if(flag==3){
			float[] m = circumcenter(a, b, c);
			
			return (float) Math.sqrt(Math.pow(m[0]-a[0], 2) + Math.pow(m[1]-a[1], 2) + Math.pow(m[2]-a[2], 2));
		}
		
		return max/2;
	}
	
	
	private static boolean equals(int[] a, Integer[] coord1) {
		if(coord1[0]==a[0] && coord1[1]==a[1] && coord1[2]==a[2]){
			return false;
		}else{
			return true;
		}
	}

	
	
	
	
	
	

	
	@SuppressWarnings("unused")
	private static boolean checkSize(Integer[] dung) {
		int cx = (int) (dung[0])  - 8 / 16;
		int cz = (int) (dung[2]) - 8 / 16;
		int posx = dung[0];
		int posz = dung[2];
		int posy = dung[1];
		int size_x = dung[3];
		int size_z = dung[4];
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
	
	
	
	
	public static int[] substract(int[] a, int[] b){
		int [] s = new int[3];
		s[0] = a[0] - b[0];
		s[1] = a[1] - b[1];
		s[2] = a[2] - b[2];
		return s;
		
	}
	
	public static float scalar(int[] a, int[] b){
		return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
	}
	
	public static int carre(int[] a){
		return (int)(Math.pow(a[0],2)+ Math.pow(a[1],2)+ Math.pow(a[2],2));
	}
	
	public static int[] prodvect(int[] a, int[] b){
		int[] x= new int[3];
		x[0] = a[1]*b[2] - b[1]*a[2];
		x[1] = a[2]*b[0] - a[0]*b[2];
		x[2] = a[0]*b[1] - b[0]*a[1];
		
		return x;
	}
	
	public static float[] circumcenter(int[] a, int[] b, int[]c){
		float den = 2 * carre(prodvect(substract(a,b),substract(b,c)));
		float j = carre(substract(b, c))*scalar(substract(a, b),substract(a, c))/ den;
		float k = carre(substract(a, c))*scalar(substract(b, a),substract(b, c))/ den;
		float l = carre(substract(a, b))*scalar(substract(c, a),substract(c, b))/ den;
		float[] m = new float[3];
		m[0] = (j*a[0] + k*b[0] + l*c[0]);
		m[1] = (j*a[1] + k*b[1] + l*c[1]);
		m[2] = (j*a[2] + k*b[2] + l*c[2]);
		return m;
		
	}
}
