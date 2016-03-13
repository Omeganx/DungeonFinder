import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;





public class Maindungen {
	
	static HashMap<Integer, HashMap<Integer, List<Integer[]>>> dongeons = new HashMap<Integer, HashMap<Integer, List<Integer[]>>>();
	static int dungeon_min  = 6;
	static String str = "";
	static List<float[]> result = new ArrayList<float[]>();
	static long seed = 812014930105396964L;
	static int range = 60;
	static DungenFram frame = new DungenFram();
	static int x;
	static boolean Gensetblock;
	static boolean insid;
	static dungen dung;
	static List<Integer[]> spawners;
	static Integer[] coord;
	static int cx;
	static int cz;
	static List<Integer[]> dungeons = new ArrayList<Integer[]>();
	static List<Integer[]> dung1;
	static Integer[] coord1;
	static Integer[] coord2;
	static int[] a;static int[] b;static int[] c;
	static float max;
	static float[] coordsfloat;
	static int[] coordsint;
	static float dist;
	static int flag;
	static float[] m;
	static float[] mfloat;
	static boolean file;
	static String output;
	
	public static void main(String[] args) {

		frame.setVisible(true);
		
	}
	
	
	public static void start(int range, long s, int num, boolean inside, boolean setblock, boolean genfile) {
		
		file = genfile;
		insid = inside;
		Gensetblock = setblock;
		result.clear();
		seed = s;
		dungeon_min = num;
		dung = new dungen(seed);
		chunk_range(range, seed);
		if(file){
			
		File file = new File("dungeons.txt");
		
	     if(!file.exists())
	    	 try {
	    		 file.createNewFile();
	    	 } catch (IOException e) {
	    		 e.printStackTrace();
	    	 }	
	        
	        try {
	        	FileWriter fw = new FileWriter(file);
	            BufferedWriter out = new BufferedWriter(fw);
	            out.write(output);
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}

	}
	
	public static void chunk_range(int range, long seed){
		for(int c_x=-range;c_x<=range;c_x++){
			
			x = c_x;
			
			frame.setLoadValue((int)(x+range)*10000/(2*range));
			frame.setChunk("Chunk: "+String.valueOf((c_x+range)*2*range)+"/"+String.valueOf((2*range)*(2*range)));
			
			for(int c_z=-range;c_z<=range;c_z++){
				
				dongeons = dung.getDungeons(c_x, c_z);
				
				if(dongeons.containsKey(c_x) && dongeons.get(c_x).containsKey(c_z)){
					
					spawners = dongeons.get(c_x).get(c_z);
					
					for(int i = 0; i<spawners.size();i++){
						
						coord = spawners.get(i);
						
						if(dungeonFindMin(coord[0],coord[1],coord[2],32)>=dungeon_min){
							CheckDungeons(coord[0],coord[1],coord[2]);
							
						}
					}
				}
				
			}
		} 
	}
	
	private static void CheckDungeons(int coordx, int coordy, int coordz) {
		
		cx = (int)(coordx - 8) / 16;
		cz = (int)(coordz - 8) / 16;

		for(int c_x = (cx-2); c_x<=(cx+2) ; c_x++){
			
			if(dongeons.containsKey(c_x)){
				
				for(int c_z = (cz-2) ; c_z<=(cz+2) ; c_z++){
					
					if(dongeons.get(c_x).containsKey(c_z)){
						
						dung1 = dongeons.get(c_x).get(c_z);
				
						for(int i = 0; i<dung1.size();i++){
							
							Integer[] d = dung1.get(i);
							
							if(Math.hypot(Math.hypot(coordx-d[0], coordy-d[1]), coordz-d[2])<32){
								
								dungeons.add(new Integer[] {d[0], d[1], d[2], d[3], d[4]});
								break;
								
							}
						}
					}
				}
			}
		}
		
		if(dungeons.size()<dungeon_min ){
			return;
		}
		if(dungeons.size()==dungeon_min && minCercleInt(dungeons)>16){
			return;
		}
		

		while(minCercleInt(dungeons)>16){
			
			dungeons = RemoveDung(dungeons);
			if(dungeons.size()<dungeon_min){
				
				return;
			}
		}
		
		//dungeons = recallDun.recallGen(dungeons, seed);
		if(dungeons.size()>=dungeon_min){
			
			if(ifOnePerChunk(dungeons) && inRange(dungeons) && isResult(dungeons)){
				
				if(!insid){
					if(isInside(dungeons)){
					
						
						setinfo(dungeons);
					}
				}else{
				
					setinfo(dungeons);
				}
				
			}
		}

		dungeons.clear();
	}
	
	
	private static void setinfo(List<Integer[]> dungeons){
		info(dungeons);
		frame.setText(str);
		frame.repaint();
		frame.revalidate();
	}
	
	private static boolean  isInside(List<Integer[]> dung) {

		for(int i=0; i<dung.size();i++){
			coord1 = dung.get(i);
			for(int k=i+1;k<dung.size();k++){
				coord2 = dung.get(k);
				if( (Math.abs(coord1[0]-coord2[0])<(coord1[3]+coord2[3]) && Math.abs(coord1[2]-coord2[2])<(coord1[4]+coord2[4]) && Math.abs(coord1[1]-coord2[1])<8) ){
					return false;
				}
			}
		}
		
		return true;
	}


	public static float[] minCercle(List<Integer[]> dung){
		
		a = new int[3];
		b = new int[3];
		c = new int[3];
		
		max = 0;
		coordsfloat = new float[3];
		
		for(int i=0; i<dung.size();i++){
			
			coord1 = dung.get(i);
			
			for(int k=i+1;k<dung.size();k++){
				
				coord2 = dung.get(k);
				
				dist = (float) Math.sqrt(Math.pow(coord1[0]-coord2[0],2)+ Math.pow(coord1[1]-coord2[1],2)+Math.pow(coord1[2]-coord2[2], 2));
				if(dist > max){
					max = dist;
					coordsfloat[0] = (coord1[0] + coord2[0])/2;
					coordsfloat[1] = (coord1[1] + coord2[1])/2;
					coordsfloat[2] = (coord1[2] + coord2[2])/2;
					
					a[0]=coord1[0];a[1]=coord1[1];a[2]=coord1[2];
					
					b[0]=coord2[0];b[1]=coord2[1];b[2]=coord2[2];
				}
			}
		}
		
		flag = 2;
		
		for(int i=0; i<dung.size();i++){
			
			coord1 = dung.get(i);
			dist = 2*(float) Math.sqrt(Math.pow(coord1[0]-coordsfloat[0],2)+ Math.pow(coord1[1]-coordsfloat[1],2)+Math.pow(coord1[2]-coordsfloat[2], 2));

			if(equals(a, coord1) && equals(b, coord1)&& dist > max){
				flag = 3;
				max = dist;
				c[0] = coord1[0];c[1] = coord1[1];c[2] = coord1[2];
				
			}
			
		}
		if(flag==3){
		
			 m = circumcenter(a, b, c);
			return m;
		}
		
		return coordsfloat;
	}
	
	
	public static float minCercleInt(List<Integer[]> dung){
		
		a = new int[3];
		b = new int[3];
		c = new int[3];
		
		max = 0;
		coordsint = new int[3];
		
		for(int i=0; i<dung.size();i++){
			
			coord1 = dung.get(i);
			
			for(int k=i+1;k<dung.size();k++){
				
				coord2 = dung.get(k);
				
				dist = (float) Math.sqrt(Math.pow(coord1[0]-coord2[0],2)+ Math.pow(coord1[1]-coord2[1],2)+Math.pow(coord1[2]-coord2[2], 2));
				if(dist > max){
					max = dist;

					coordsint[0] = (int) (coord1[0] + coord2[0])/2;
					coordsint[1] = (int) (coord1[1] + coord2[1])/2;
					coordsint[2] = (int) (coord1[2] + coord2[2])/2;
					
					a[0]=coord1[0];a[1]=coord1[1];a[2]=coord1[2];
					
					b[0]=coord2[0];b[1]=coord2[1];b[2]=coord2[2];
					
				}
			}
		}
		flag = 2;
		
		for(int i=0; i<dung.size();i++){
			
			coord1 = dung.get(i);
			
			dist = 2*(float) Math.sqrt(Math.pow(coord1[0]-coordsint[0],2)+ Math.pow(coord1[1]-coordsint[1],2)+Math.pow(coord1[2]-coordsint[2], 2));
			
			if(equals(a, coord1) && equals(b, coord1) && dist > max){
				
				flag = 3;
				max = dist;
				c[0] = coord1[0];c[1] = coord1[1];c[2] = coord1[2];
				
			}
			
		}
		
		if(flag==3){
			
			m = circumcenter(a, b, c);
			
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


	
	

	
	
	private static boolean inRange(List<Integer[]> dungeons) {
		mfloat = minCercle(dungeons);
		for(int i=0;i<dungeons.size();i++){
			Integer[] dung = dungeons.get(i);
			if(Math.sqrt(Math.pow(mfloat[0]-dung[0], 2) + Math.pow(mfloat[1]-dung[1], 2)+ Math.pow(mfloat[2]-dung[2], 2)) >= 16){
			
			 return false;
			}
		}
		
		return true;
	}


	private static boolean ifOnePerChunk(List<Integer[]> dungeons) {
		coord1 = dungeons.get(0);
		int c_x  = (coord1[0] - 8) /16 ;
		int c_z  = (coord1[2] - 8) /16 ;
		for(int i = 1; i<dungeons.size();i++){
			coord2 = dungeons.get(i);
			int d_x = (coord2[0] - 8) / 16;
			int d_z = (coord2[2] - 8) / 16;
			if(d_x==c_x && c_z==d_z){
				return false;
			}
			c_x  = d_x;
			c_x  = d_z;
		}
		return true;
	}


	private static boolean isResult(List<Integer[]> dungeons) {
		float[] circle = minCercle(dungeons);
		for(int i = 0; i<result.size(); i++){
			float[] res = result.get(i);
			if(res[0]==circle[0] && res[1]==circle[1] && res[2]==circle[2]){
				return false;
			}
		}
		result.add(circle);
		return true;
	}


	public static void info(List<Integer[]> dung){
		float[] m = minCercle(dung);
		String setblock = new String();
		
		str+=String.valueOf(dung.size())+ " Dungeons found: \n radius="+String.valueOf(minCercleInt(dung)+";\n");
		
		str+="Coordinates="+String.valueOf((int)m[0])+" "+String.valueOf((int)m[1])+" "+String.valueOf((int)m[2])+";\n";
		
		str+="chunk=" + String.valueOf((int)(m[0]-8)/16) + ";"+String.valueOf((int)(m[2]-8)/16)+";\n";
			for(int i=0; i<=dung.size()-1;i++){
				Integer[] coord1 = dung.get(i);
				str+="Dungeon at chunk" + String.valueOf((int)(coord1[0]-8)/16)+ ";" + String.valueOf((int)(coord1[2]-8)/16) +  " at:" + String.valueOf(coord1[0])+ " "+ String.valueOf(coord1[1])+ " "+ String.valueOf(coord1[2])+ ";\n";
				if(Gensetblock){
					setblock+="/setblock "+ String.valueOf(coord1[0])+ " "+ String.valueOf(coord1[1])+ " "+ String.valueOf(coord1[2])+ " stone\n";
				}
			}
			str+=setblock +"\n\n";
		String setblockout = new String();
		if(file){
			
			output+=String.valueOf(dung.size())+ " Dungeons found: \n radius="+String.valueOf(minCercleInt(dung)+";\n");
			
			output+="Coordinates="+String.valueOf((int)m[0])+" "+String.valueOf((int)m[1])+" "+String.valueOf((int)m[2])+";\n";
			
			output+="chunk=" + String.valueOf((int)(m[0]-8)/16) + ";"+String.valueOf((int)(m[2]-8)/16)+";\n";
				for(int i=0; i<=dung.size()-1;i++){
					Integer[] coord1 = dung.get(i);
					
					output+="Dungeon at chunk" + String.valueOf((int)(coord1[0]-8)/16)+ ";" + String.valueOf((int)(coord1[2]-8)/16) +  " at:" + String.valueOf(coord1[0])+ " "+ String.valueOf(coord1[1])+ " "+ String.valueOf(coord1[2])+ ";";
					output+="Size="+String.valueOf(coord1[3]+1)+";"+String.valueOf(coord1[4]+1)+";\n";
					if(Gensetblock){
						setblockout+="/setblock "+ String.valueOf(coord1[0])+ " "+ String.valueOf(coord1[1])+ " "+ String.valueOf(coord1[2])+ " stone\n";
					}
				}
				output+=setblockout +"\n\n";
			
			
			
			
			
			
			
		}
	}
	
	
	public static List<Integer[]>  RemoveDung(List<Integer[]> dung){
		max = 0;
		int[] dungeon1 = new int[4];
		int[] dungeon2 = new int[4];
		float[] moy = new float[3];

		for(int i=0; i<=dung.size()-1;i++){
			Integer[] coord1 = dung.get(i);
			moy[0] += coord1[0];
			moy[1] += coord1[1];
			moy[2] += coord1[2];
			for(int k=i+1;k<=dung.size()-1;k++){
				
				Integer[] coord2 = dung.get(k);
				if((int)Math.hypot(coord1[0]-coord2[0], Math.hypot(coord1[1]-coord2[1],coord1[2]-coord2[2])) > max){
					max = (int)Math.hypot(coord1[0]-coord2[0], Math.hypot(coord1[1]-coord2[1],coord1[2]-coord2[2]));
					dungeon1[0] = coord1[0];dungeon1[1] = coord1[1];dungeon1[2] = coord1[2];dungeon1[3] = i;
					dungeon2[0] = coord2[0];dungeon2[1] = coord2[1];dungeon2[2] = coord2[2];dungeon2[3] = k;
				}
			}
		}
		
		moy[0] = moy[0]/dung.size();
		moy[1] = moy[1]/dung.size();
		moy[2] = moy[2]/dung.size();
		
		if(Math.hypot(moy[0]-dungeon1[0], Math.hypot(moy[1]-dungeon1[1],moy[2]-dungeon1[2])) < Math.hypot(moy[0]-dungeon2[0], Math.hypot(moy[1]-dungeon2[1],moy[2]-dungeon2[2]))){
			dung.remove(dungeon2[3]);
			
		}else{
			dung.remove(dungeon1[3]);
	
		}
		
		return dung;
	}
	


	private static int dungeonFindMin(int posx, int posy,int posz, int max_range) {
		int count = 0;
		int c_x = (int)(posx - 8)/16;
		int c_z = (int)(posz - 8)/16;
		for(int x1 = (c_x-2); x1<=(c_x+2);x1++){
			if(dongeons.containsKey(x1)){
				for(int z1 = (c_z-2); z1<=(c_z+2);z1++){
					if(dongeons.get(x1).containsKey(z1)){
						List<Integer[]> spawners = dongeons.get(x1).get(z1);
						if(spawners.size()>0){
							Iterator<Integer[]> iterator = spawners.iterator();
							while(iterator.hasNext()){
								Integer[] coord = iterator.next();
								if(Math.hypot(Math.hypot(coord[0]-posx, coord[1]-posy), coord[2]-posz) <= max_range){
									count++;
									break;
								}
							}
						}
					}
				}
			}
		}
		return count;
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


	public static void clearText() {
		str = "";
		
	}
}
