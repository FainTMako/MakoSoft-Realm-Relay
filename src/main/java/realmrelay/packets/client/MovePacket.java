package realmrelay.packets.client;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import realmrelay.data.Location;
import realmrelay.data.LocationRecord;
import realmrelay.packets.Packet;



public class MovePacket extends Packet {
	
	public int tickId;
	public int time;
	public Location newPosition = new Location();
	public List<LocationRecord> records = new ArrayList<>();

	@Override
	public void parseFromInput(DataInput in) throws IOException {
		this.tickId = in.readInt();
		this.time = in.readInt();
		this.newPosition.parseFromInput(in);
//		this.records = new LocationRecord[in.readShort()];
                int len = in.readShort();
		for (int i = 0; i < len; i++) {
			LocationRecord record = new LocationRecord();
			record.parseFromInput(in);
			this.records.add(record);
		}
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeInt(this.tickId);
		out.writeInt(this.time);
		this.newPosition.writeToOutput(out);
		out.writeShort(this.records.size());
		for (LocationRecord record: this.records) {
			record.writeToOutput(out);
		}
	}

}
