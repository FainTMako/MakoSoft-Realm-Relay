package realmrelay.packets.server;

import com.google.common.io.LittleEndianDataInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.unix.Buffer;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import realmrelay.packets.Packet;


public class MapInfoPacket extends Packet {
	
	public int width;
	public int height;
	public String name;
        public String displayName;
        public String realmName;
	public String obf0;
	public int obf1;
	public long fp;
	public int background;
        public int difficulty;
	public boolean allowPlayerTeleport;
	public boolean showDisplays;
	public List<String> clientXML = new ArrayList<>();
	public List<String> extraXML = new ArrayList<>();
        public short maxPlayers;
        public String connectionGuid;

	@Override
	public void parseFromInput(DataInput in) throws IOException {
//            LittleEndianDataInputStream lin = new LittleEndianDataInputStream(in);
		this.width = in.readInt();
		this.height = in.readInt();
		this.name = in.readUTF();
                this.displayName = in.readUTF();
                this.realmName = in.readUTF();
//		this.obf0 = in.readUTF();

                byte[] unsignedFP = new byte[4];
                in.readFully(unsignedFP);
                ByteBuf buf = Unpooled.copiedBuffer(unsignedFP);
		this.fp = buf.getUnsignedInt(0);
                
		this.background = in.readInt();
		this.difficulty = in.readInt();
		this.allowPlayerTeleport = in.readBoolean();
		this.showDisplays = in.readBoolean();
                this.maxPlayers = in.readShort();
                this.connectionGuid = in.readUTF();
                DataInput _arg_1 = in;
                int _local_2 = 0;
                int _local_3 = 0;
                int _local_4 = 0;
                _local_2 = _arg_1.readShort();
//                this.clientXML_.length = 0
;
                _local_3 = 0;
                while (_local_3 < _local_2)
                {
                    _local_4 = _arg_1.readInt();
                    byte[] xmlbytes = new byte[_local_4];
                    in.readFully(xmlbytes);
                    buf = Unpooled.copiedBuffer(xmlbytes);
//                    for(int i = 0; i < _local_4; i++)
//                    {
//                        buf.writeByte(in.readByte());
//                    }
////                    in.readFully(xmlbytes);
                    
                    this.clientXML.add(buf.toString());
                    _local_3++;
                }
                _local_2 = _arg_1.readShort();
//                this.extraXML_.length = 0;
                _local_3 = 0;
                while (_local_3 < _local_2)
                {
                    _local_4 = _arg_1.readInt();
                     byte[] xmlbytes = new byte[_local_4];
                    in.readFully(xmlbytes);
                    buf = Unpooled.copiedBuffer(xmlbytes);
                    this.extraXML.add(buf.toString());
                    _local_3++;
                };
//                this.maxPlayers = in.readShort();
//                this.connectionGuid = in.readUTF();
//                
//		this.clientXML = new String[in.readShort()];
//		for (int i = 0; i < this.clientXML.length; i++) {
//			byte[] utf = new byte[in.readInt()];
//                        in.readFully(utf);
//                        buf = Unpooled.copiedBuffer(utf);
//			
//			this.clientXML[i] = buf.toString();
//		}
//		this.extraXML = new String[in.readShort()];
//		for (int i = 0; i < this.extraXML.length; i++) {
//			byte[] utf = new byte[in.readInt()];
//			in.readFully(utf);
//			this.extraXML[i] = new String(utf, "UTF-8");
//		}
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeInt(this.width);
		out.writeInt(this.height);
		out.writeUTF(this.name);
		out.writeUTF(this.displayName);
                out.writeUTF(this.realmName);
		out.writeInt((int) this.fp);
		out.writeInt(this.background);
		out.writeInt(this.difficulty);
		out.writeBoolean(this.allowPlayerTeleport);
		out.writeBoolean(this.showDisplays);
                out.writeShort(this.maxPlayers);
                out.writeUTF(this.connectionGuid);
                
		for (String xml: this.clientXML) {
			byte[] utf = xml.getBytes("UTF-8");
			out.writeInt(utf.length);
			out.write(utf);
		}
//		out.writeShort(this.extraXML.length);
		for (String xml: this.extraXML) {
			byte[] utf = xml.getBytes("UTF-8");
			out.writeInt(utf.length);
			out.write(utf);
		}
	}

}
