package realmrelay.packets.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import realmrelay.data.Location;
import realmrelay.packets.Packet;



public class ShowEffectPacket extends Packet {

    
                public static final int UNKNOWN_EFFECT_TYPE = 0;
        public static final int HEAL_EFFECT_TYPE = 1;
        public static final int TELEPORT_EFFECT_TYPE = 2;
        public static final int STREAM_EFFECT_TYPE = 3;
        public static final int THROW_EFFECT_TYPE = 4;
        public static final int NOVA_EFFECT_TYPE = 5;
        public static final int POISON_EFFECT_TYPE = 6;
        public static final int LINE_EFFECT_TYPE = 7;
        public static final int BURST_EFFECT_TYPE = 8;
        public static final int FLOW_EFFECT_TYPE = 9;
        public static final int RING_EFFECT_TYPE = 10;
        public static final int LIGHTNING_EFFECT_TYPE = 11;
        public static final int COLLAPSE_EFFECT_TYPE = 12;
        public static final int CONEBLAST_EFFECT_TYPE = 13;
        public static final int JITTER_EFFECT_TYPE = 14;
        public static final int FLASH_EFFECT_TYPE = 15;
        public static final int THROW_PROJECTILE_EFFECT_TYPE = 16;
        public static final int SHOCKER_EFFECT_TYPE = 17;
        public static final int SHOCKEE_EFFECT_TYPE = 18;
        public static final int RISING_FURY_EFFECT_TYPE = 19;
        public static final int NOVA_NO_AOE_EFFECT_TYPE = 20;
    
    
	public int effectType;
	public int targetObjectId;
	public Location pos1 = new Location();
	public Location pos2 = new Location();
	public int color;
        public float duration;
        
        

	@Override
	public void parseFromInput(DataInput in) throws IOException {
		this.effectType = in.readUnsignedByte();
		this.targetObjectId = in.readInt();
		this.pos1.parseFromInput(in);
		this.pos2.parseFromInput(in);
		this.color = in.readInt();
                this.duration = in.readFloat();
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeByte(this.effectType);
		out.writeInt(this.targetObjectId);
		this.pos1.writeToOutput(out);
		this.pos2.writeToOutput(out);
		out.writeInt(this.color);
                out.writeFloat(duration);
	}

}
