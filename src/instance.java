public class instance {
	private String cls;
	private double[] fea;
	public instance() {}
	public instance(String cls, double[] features)
	{
		this.cls=cls;
		this.fea=features.clone();
	}
	public void setcls(String cls) {this.cls=cls;}
	public void setfea(double[] features) {this.fea=features;}//
	public String getcls () {return cls;}
	public double[] getfea() {return fea;}	
}