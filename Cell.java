import java.util.ArrayList;

public class Cell {

	int startPoint;
	int endPoint;

	ArrayList<String> CKYresult = new ArrayList<String>();

	public Cell(int startPoint, int endPoint, ArrayList<String> CKYresult) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.CKYresult = CKYresult;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}

	public int getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
	}

	public ArrayList<String> getCKYresult() {
		return CKYresult;
	}

	public void setCKYresult(ArrayList<String> cKYresult) {
		CKYresult = cKYresult;
	}

}
