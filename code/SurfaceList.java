public class SurfaceList{

	Surface[] lista;
	int size;
	int maximun;

	SurfaceList(){
		lista = new Surface[8];
		size = 0;
		maximun = 8;
	}

	public void add(Surface s){

		Surface[] aux;

		if(size == maximun){
			maximun = 2*maximun;
			aux = new Surface[maximun];
			for(int i=0;i<size;i++){
				aux[i] = lista[i];
			}
			lista = aux;
		}	

		lista[size] = s;
		size++;
	}

	public Surface get(int x){
		if(x >= size) return null;
		return lista[x];
	}

	public void clear(){
		size = 0;
	}

	public void drop(Surface s){

		for(int i=0;i<size;i++){
			if(lista[i] == s){
				lista[i] = lista[size-1];
				size--;
				break;
			}
		}
	}
}