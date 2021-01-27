import java.lang.Math;

public class Surface{

	int size;
	double[][] vertex;
	double[][] textureVertex;
	double[] normal;
	String path;
	double min_x;
	double max_x;
	double min_y;
	double max_y;
	double min_z;
	double max_z;

	Surface(int size,String path){
		this.size = size;
		vertex = new double[size][3];
		textureVertex = new double[size][2];
		normal = new double[3];
		this.path = path;
	}

	public boolean isInside(double x,double y,double z){

		if(Math)
	}

	public void setLocation(double dx,double dy,double dz){

	}
}