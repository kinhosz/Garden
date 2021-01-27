import java.lang.Math;

public class CampoVisao{

	int[][] campo;
	int width;
	int height;
	double x,y,z;
	double conv = Math.PI/180;
	double grauH = 150*conv;
	double grauV = 150*conv;
	double alpha = 0.0; // horizontal
	double beta = 0.0; // vertical
	double gamma = 0.0;
	double lbeta = Math.PI/2; // lim beta
	
	CampoVisao(int hor,int vert){
		 campo = new int[hor][vert];
		 width = hor;
		 height = vert;
		 x = 0;
		 y = 0;
		 z = 0;
	}

	public void addAlpha(double dx){
		alpha += dx;

		if(alpha > 2.0*Math.PI){
			alpha -= 2.0*Math.PI;
		}

		if(alpha < -2.0*Math.PI){
			alpha += 2.0*Math.PI;
		}
	}

	public void addBeta(double dx){
		beta += dx;

		if(beta > lbeta){
			beta = lbeta;
		}

		if(beta < -lbeta){
			beta = -lbeta;
		}
	}

	public int[][] getContext(){

		double h0 = grauH/2;
		double v0 = grauV/2;

		double dh = grauH/width;
		double dv = grauV/height;

		for(int i=0;i<width;i++){
			h0 = grauH/2;
			for(int j=0;j<height;j++){
				Ponto p = new Ponto();
				p.setY(1);
				p.eulerRotation(alpha,beta+dv,dh);
				RayTracing rt = new RayTracing(p,x,y,z);

				int color = rt.getColor();
				campo[i][j] = color;
				h0 -= dh;
			}
			v0 -= dv;
		}

		return campo;
	}

	public static void main(String[] args){
		CampoVisao CP = new CampoVisao(10,10);
		int[][] teste = new int[10][10];
		teste = CP.getContext();
	}
}