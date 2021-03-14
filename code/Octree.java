import java.lang.Math;

public class Octree{

	int depth;
	double x0,xf;
	double y0,yf;
	double z0,zf;
	Octree oc_I;
	Octree oc_II;
	Octree oc_III;
	Octree oc_IV;
	Octree oc_V;
	Octree oc_VI;
	Octree oc_VII;
	Octree oc_VIII;
	SurfaceList lista;
	final int limite = 30;
	final double eps = 1e-9;

	Octree(double x0,double xf,double y0,double yf,double z0,double zf,int depth){
		this.x0 = x0;
		this.xf = xf;
		this.y0 = y0;
		this.yf = yf;
		this.z0 = z0;
		this.zf = zf;
		this.depth = depth;
		oc_I = null;
	    oc_II = null;
	    oc_III = null;
	    oc_IV = null;
	    oc_V = null;
	    oc_VI = null;
	    oc_VII = null;
	    oc_VIII = null;
	}

	public static Octree create(Octree o,int idx){

		double x0 = o.x0;
		double y0 = o.y0;
		double z0 = o.z0;
		double xf = o.xf;
		double yf = o.yf;
		double zf = o.zf;
		double mx = (x0+xf)/2;
		double my = (y0+yf)/2;
		double mz = (z0+zf)/2;

		Octree aux;
		if(idx == 1){
			x0 = mx;
			y0 = my;
			z0 = mz;
		}
		else if(idx == 2){
			xf = mx;
			y0 = my;
			z0 = mz;
		}
		else if(idx == 3){
			xf = mx;
			yf = my;
			z0 = mz;
		}
		else if(idx == 4){
			x0 = mx;
			yf = my;
			z0 = mz;
		}
		else if(idx == 5){
			x0 = mx;
			y0 = my;
			zf = mz;
		}
		else if(idx == 6){
			xf = mx;
			y0 = my;
			zf = mz;
		}
		else if(idx == 7){
			xf = mx;
			yf = my;
			zf = mz;
		}
		else if(idx == 8){
			x0 = mx;
			yf = my;
			zf = mz;
		}

		aux = new Octree(x0,xf,y0,yf,z0,zf,o.depth+1);
		return aux;
	}

	public boolean add(Surface s){
		
		if(x0 > s.min_x || xf < s.max_x) return false;
		if(y0 > s.min_y || yf < s.max_y) return false;
		if(z0 > s.min_z || zf < s.max_z) return false;

		if(depth == limite){
			lista.add(s);
			return true;
		}

		if(oc_I == null) oc_I = create(this,1);
		if(oc_II == null) oc_II = create(this,2);
		if(oc_III == null) oc_III = create(this,3);
		if(oc_IV == null) oc_IV = create(this,4);
		if(oc_V == null) oc_V = create(this,5);
		if(oc_VI == null) oc_VI = create(this,6);
		if(oc_VII == null) oc_VII = create(this,7);
		if(oc_VIII == null) oc_VIII = create(this,8);

		if(oc_I.add(s)) return true;
		if(oc_II.add(s)) return true;
		if(oc_III.add(s)) return true;
		if(oc_IV.add(s)) return true;
		if(oc_V.add(s)) return true;
		if(oc_VI.add(s)) return true;
		if(oc_VII.add(s)) return true;
		if(oc_VIII.add(s)) return true;

		lista.add(s);
		return true;
	}

	public void addBox(Box box){

		for(int i=0;i<box.size;i++){
			Surface s = box.get(i);
			s.setLocation(box.dx,box.dy,box.dz);
			boolean b = add(s);
			if(b == false) System.out.println("impossible to add this surface");
		}
	}

	public static double checkHit(Surface s,Ponto p,double x,double y,double z){
		if(s == null) return -1;

		double d;
		double a,b,c;
		a = s.normal[0];
		b = s.normal[1];
		c = s.normal[2];
		double x1,y1,z1;
		x1 = s.vertex[0][0];
		y1 = s.vertex[0][1];
		z1 = s.vertex[0][2];

		d = -(a*x1 + b*y1 + c*z1);

		double den;

		den = (a*p.x + b*p.y + c*p.z);

		if(Math.abs(den) < eps) return -1; // nao intercepta
		double t = -d/den;

		double nx,ny,nz;
		nx = x + p.x*t;
		ny = y + p.y*t;
		nz = z + p.z*t;

		if(s.isInside(nx,ny,nz)) return t;
		return -1;
	}

	public Surface getSurface(Ponto p, double x,double y, double z){
		double k0,kf;

		k0 = -1e18;
		kf = 1e18;

		if(Math.abs(p.x) > eps){
			k0 = Math.max(k0, (this.x0 - x)/p.x);
			kf = Math.min(kf, (this.xf - x)/p.x);
		}
		else if(x < this.x0 || x > this.xf) return null;

		if(Math.abs(p.y) > eps){
			k0 = Math.max(k0, (this.y0 - y)/p.y);
			kf = Math.min(kf, (this.yf - y)/p.y);
		}
		else if(y < this.y0 || y > this.yf) return null;

		if(Math.abs(p.z) > eps){
			k0 = Math.max(k0, (this.z0 - z)/p.z);
			kf = Math.min(kf, (this.zf - z)/p.z);
		}
		else if(z < this.z0 || z > this.zf) return null;

		if(kf < k0) return null;

		double time = 1e18;
		Surface candidate = null;

		for(int i=0;i<lista.size;i++){
			double t = checkHit(lista.get(i),p,x,y,z);
			if(t < 0) continue;
			if(t > time) continue;
			candidate = lista.get(i);
			time = t;
		}

		if(this.oc_I != null){
			Surface aux = this.oc_I.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}
		if(this.oc_II != null){
			Surface aux = this.oc_II.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}
		if(this.oc_III != null){
			Surface aux = this.oc_III.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}
		if(this.oc_IV != null){
			Surface aux = this.oc_IV.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}
		if(this.oc_V != null){
			Surface aux = this.oc_V.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}
		if(this.oc_VI != null){
			Surface aux = this.oc_VI.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}
		if(this.oc_VII != null){
			Surface aux = this.oc_VII.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}
		if(this.oc_VIII != null){
			Surface aux = this.oc_VIII.getSurface(p,x,y,z);
			double t = checkHit(aux,p,x,y,z);
			if(t >= 0 && t < time){
				time = t;
				candidate = aux;
			}
		}

		return candidate;
	}
}
