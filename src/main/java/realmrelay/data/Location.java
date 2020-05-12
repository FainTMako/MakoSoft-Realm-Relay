package realmrelay.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class Location implements IData {
	
	public double x, y;
	
	@Override
	public void parseFromInput(DataInput in) throws IOException {
		this.x = in.readFloat();
		this.y = in.readFloat();
	}
	
	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeFloat((float) this.x);
		out.writeFloat((float) this.y);
	}
	
	public float distanceSquaredTo(Location location) {
		float dx = (float) (location.x - this.x);
		float dy = (float) (location.y - this.y);
		return dx * dx + dy * dy;
	}
	
	public float distanceTo(Location location) {
		return (float) Math.sqrt(this.distanceSquaredTo(location));
	}

}
