package realmrelay.packets.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import realmrelay.packets.Packet;


public class DamagePacket extends Packet {
	
	public int targetId;
	public List<Integer> effects = new ArrayList<>();
	public int damageAmount;
	public boolean kill;
        public boolean armorPierce_;
	public int bulletId;
	public int objectId;
        

	@Override
	public void parseFromInput(DataInput in) throws IOException {
		this.targetId = in.readInt();
                int len = in.readUnsignedByte();
//		this.effects = new int[in.readUnsignedByte()];
		for (int i = 0; i < len; i++) {
			this.effects.set(i, in.readUnsignedByte());
		}
		this.damageAmount = in.readUnsignedShort();
		this.kill = in.readBoolean();
                this.armorPierce_ = in.readBoolean();
		this.bulletId = in.readUnsignedByte();
		this.objectId = in.readInt();
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeInt(this.targetId);
		out.writeByte(this.effects.size());
		for (int effect: this.effects) {
			out.writeByte(effect);
		}
		out.writeShort(this.damageAmount);
		out.writeBoolean(this.kill);
                out.writeBoolean(this.armorPierce_);
		out.writeByte(this.bulletId);
		out.writeInt(this.objectId);
	}

}
