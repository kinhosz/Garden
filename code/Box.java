public class Box{

	int size;
	SurfaceList sup;
	double x,y,z;
	double dx,dy,dz;
	int maximun = 8;

	Box(){
		size = 0;
		x = 0;
		y = 0;
		z = 0;
		sup = new SurfaceList();
	}

	public Surface get(int x){
		if(x >= size) return null;
		return sup.get(x);
	}
}