import java.lang.Math;

public class Ponto{

	double x;
	double y;
	double z;

	Ponto(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public void setX(double x){
		this.x = x;
	}

	public void setY(double y){
		this.y = y;
	}

	public void setZ(double z){
		this.z = z;
	}

	public void eulerRotation(double alpha, double beta, double gamma){

		double nx,ny,nz;
		nx = x*(Math.cos(gamma)*Math.cos(alpha) - Math.sin(gamma)*Math.cos(beta)*Math.sin(alpha));
		nx += y*(-Math.cos(gamma)*Math.sin(alpha) - Math.sin(gamma)*Math.cos(beta)*Math.cos(alpha));
		nx += z*(Math.sin(gamma)*Math.sin(beta));

		ny = x*(Math.sin(gamma)*Math.cos(alpha) + Math.cos(gamma)*Math.cos(beta)*Math.sin(alpha));
		ny += y*(-Math.sin(gamma)*Math.sin(alpha) + Math.cos(gamma)*Math.cos(beta)*Math.cos(alpha));
		ny += z*(-Math.cos(gamma)*Math.sin(beta));

		nz = x*(Math.sin(alpha)*Math.sin(beta));
		nz += y*(Math.sin(beta)*Math.cos(alpha));
		nz += z*(Math.cos(beta));

		x = nx;
		y = ny;
		z = nz;
	}
}