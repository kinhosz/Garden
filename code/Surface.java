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
	final double eps = 1e-9;

	Surface(int size,String path){
		this.size = size;
		vertex = new double[size][3];
		textureVertex = new double[size][2];
		normal = new double[3];
		this.path = path;
	}

	/*
		pseudocode for to detect a point inside a convex polygon
		RIGHT = "RIGHT"
		LEFT = "LEFT"

		def inside_convex_polygon(point, vertices):
		    previous_side = None
		    n_vertices = len(vertices)
		    for n in xrange(n_vertices):
		        a, b = vertices[n], vertices[(n+1)%n_vertices]
		        affine_segment = v_sub(b, a)
		        affine_point = v_sub(point, a)
		        current_side = get_side(affine_segment, affine_point)
		        if current_side is None:
		            return False #outside or over an edge
		        elif previous_side is None: #first segment
		            previous_side = current_side
		        elif previous_side != current_side:
		            return False
		    return True

		def get_side(a, b):
		    x = cosine_sign(a, b)
		    if x < 0:
		        return LEFT
		    elif x > 0: 
		        return RIGHT
		    else:
		        return None

		def v_sub(a, b):
		    return (a[0]-b[0], a[1]-b[1])

		def cosine_sign(a, b):
		    return a[0]*b[1]-a[1]*b[0]


	*/

	public boolean isInsidePolygon(int dontUse,double x,double y){

		int previous_side = -1;

		for(int i=0;i<size;i++){
			double vx,vy,px,py;
			if(dontUse == 0){
				vx = vertex[(i+1)%size][1] - vertex[i][1];
				vy = vertex[(i+1)%size][2] - vertex[i][2];
				px = x - vertex[i][1];
				py = y - vertex[i][2];
			}
			else if(dontUse == 1){
				vx = vertex[(i+1)%size][0] - vertex[i][0];
				vy = vertex[(i+1)%size][2] - vertex[i][2];
				px = x - vertex[i][0];
				py = y - vertex[i][2];
			}
			else{
				vx = vertex[(i+1)%size][0] - vertex[i][0];
				vy = vertex[(i+1)%size][1] - vertex[i][1];
				px = x - vertex[i][0];
				py = y - vertex[i][1];
			}

			double side = vx*py - vy*px;
			int aux;
			if(side < 0) aux = 0;
			else aux = 1;

			if(previous_side == -1) previous_side = aux;
			else if(previous_side != aux) return false;
		}

		return true;
	}

	public boolean isInside(double x,double y,double z){

		if(Math.abs(normal[2]) > eps) return isInsidePolygon(2,x,y);
		else if(Math.abs(normal[1]) > eps) return isInsidePolygon(1,x,z);
		else return isInsidePolygon(0,y,z);
	}

	public void setLocation(double dx,double dy,double dz){

	}
}