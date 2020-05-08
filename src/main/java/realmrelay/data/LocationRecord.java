package realmrelay.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class LocationRecord extends Location {
    
        public LocationRecord(int ar1, float ar2, float ar3)
        {
            this.time=ar1;
            this.x = ar2;
            this.y = ar3;
        }
	public LocationRecord()
        {
        }
	public int time;
	
	@Override
	public void parseFromInput(DataInput in) throws IOException {
		this.time = in.readInt();
		super.parseFromInput(in);
	}
	
	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeInt(this.time);
		super.writeToOutput(out);
	}

}
