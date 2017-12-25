

/**
 * @author Administrator
 *
 */
public class Bike {

	public static final int tyre = 2;
	protected int tyres;
	protected String color;
	public Bike(int tyres, String color) {
		this.tyres = tyres;
		this.color = color;
	}
	
	public  void print() {
		System.out.print("bike");
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**   1.
		Bike[] bikes = new OfoBike[5];
		OfoBike[] ofoBikes = (OfoBike[]) bikes;*/
		
		/**   2.
		OfoBike[] ofoBikes2 = new OfoBike[5];
		Bike[] bikes2 = ofoBikes2;
		OfoBike[] ofoBikes3 = (OfoBike[])bikes2;
		
		/**   3.
		Bike[] bikes3 = new Unibike[5];
		OfoBike[] ofoBikes4 = (OfoBike[])bikes3;
		Bike[] bikes4 = ofoBikes4;    
		*/
		new OfoBike(1, "ss").print();
		System.out.print("!!!!!");
	}

}
class OfoBike extends Bike implements Bigtyre {

	public OfoBike(int tyres, String color) {
		super(tyres, color);
		// TODO Auto-generated constructor stub
	}
	
	public  void print() {
		//System.out.print(this.tyre);
		System.out.print(super.tyre);
		System.out.print(Bike.tyre);
		System.out.print(Bigtyre.tyre);
	}
	

}
class Unibike extends Bike {

	public Unibike(int tyres, String color) {
		super(tyres, color);
		// TODO Auto-generated constructor stub
	}
}
interface Bigtyre {
	public static final int tyre = 4;
}