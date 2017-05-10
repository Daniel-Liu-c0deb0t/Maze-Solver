
public class Point {
	public int r, c;
	
	public Point(int r, int c){
		this.r = r;
		this.c = c;
	}
	
	public Point(Point other){
		this.r = other.r;
		this.c = other.c;
	}
	
	public boolean pointOutside(int width, int height){
		if(r < 0 || c < 0 || c >= width || r >= height)
			return true;
		return false;
	}
	
	public int distTo(Point other){
		return Math.abs(r - other.r) + Math.abs(c - other.c);
	}
	
	public Point getRight(){
		return new Point(c, -r);
	}
	
	public Point getLeft(){
		return new Point(-c, r);
	}
	
	public Point add(Point other){
		return new Point(r + other.r, c + other.c);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + c;
		result = prime * result + r;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Point other = (Point)obj;
		if(c != other.c)
			return false;
		if(r != other.r)
			return false;
		return true;
	}
}
