/**Programma per il calcolo della data della pasqua
 * sì... è un argomento che mi piace!
 */
package provaepsilon;

public class Pasqua {

	private int anno;
	
	public Pasqua(int anno) {
		super();
		this.anno = anno;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		if(anno>=1583){
			this.anno = anno;
		}
		else {
			anno = 1583;		/**Solo le date del calendario gregoriano sono valide*/
		}
	}

	@SuppressWarnings("null")
	public int[] calcolaData(){
		int a = anno%19;	
		int b = anno/100;	
		int c = anno%100;	
		int d = b/4;
		int e = b%4;
		int f = (b+8)/25;
		int g = (b-f+1)/3;
		int h = (19*a+b-d-g+15)%30;
		int i = c/4;
		int k = c%4;
		int l = (32+2*e+2*i-h-k)%7;
		int m = (a+11*h+22*l)/451;
		int n = (h+l-7*m+114)/31;	/**mese*/
		int p = (h+l-7*m+114)%31;
		p = p+1;					/**Giorno del mese*/
		int[] data = new int[2];
		data[0] = p;
		data[1] = n;
		return data;
	}

}

