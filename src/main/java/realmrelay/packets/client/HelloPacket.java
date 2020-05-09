package realmrelay.packets.client;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import realmrelay.packets.Packet;

/* Thanks to me, Maat, Faintmako, Gratin and pieoewieoe, for their contribution. */

public class HelloPacket extends Packet {
	
 public String buildVersion = new String();
        public int gameId = 0;
        public String guid = "";
        public String password = "";
        public String secret = new String();
        public int keyTime = 0;
        public byte[] key = new byte[0];
        public String mapJSON = new String();
        public String entrytag = "";
        public String gameNet = "rotmg";
        public String gameNetUserId = "";
        public String playPlatform = "rotomg";
        public String platformToken = "";
        public String userToken = "";
        public String trailer = "XTeP7hERdchV5jrBZEYNebAqDPU6tKU6";
	
	@Override
	public void parseFromInput(DataInput in) throws IOException {
//		this.buildVersion = in.readUTF();
//		//this.obf0 = in.readInt();
//		this.gameId = in.readInt();
//		this.guid = in.readUTF();
//		this.Random1 = in.readInt();
//		this.password = in.readUTF();
//		this.Random2 = in.readInt();
//		this.secret = in.readUTF();
//		this.keyTime = in.readInt();
//		this.key = new byte[in.readShort()];
//		in.readFully(this.key);
//                int jsonLen = in.readInt();
//                byte[] mapJsonTemp = new byte[jsonLen];
//                in.readFully(mapJsonTemp);
//                mapJson = new String(mapJsonTemp);
////                int obf1len = in.readInt();
////                byte[] obf1temp = new byte[obf1len];
//           
////		in.readFully(this.obf1);
//		this.obf2 = in.readUTF();
//		this.obf3 = in.readUTF();
//		this.obf4 = in.readUTF();
//		this.obf5 = in.readUTF();
//		this.obf6 = in.readUTF();
//		this.obf7 = in.readUTF();
//                this.obf8 = in.readUTF();
//                this.guid = in.readUTF();
	}
	
	@Override
	public void writeToOutput(DataOutput out) throws IOException {
            DataOutput _arg_1 = out;
            _arg_1.writeUTF(this.buildVersion);
            _arg_1.writeInt(this.gameId);
            _arg_1.writeUTF(this.guid);
            _arg_1.writeInt((int) Math.floor(Math.random() * 1000000000));
            _arg_1.writeUTF(this.password);
            _arg_1.writeInt((int) Math.floor(Math.random() * 1000000000));
            _arg_1.writeUTF(this.secret);
            _arg_1.writeInt(this.keyTime);
            _arg_1.writeShort(this.key.length);
            _arg_1.write(key);   
            _arg_1.writeInt(this.mapJSON.length());
            _arg_1.write(this.mapJSON.getBytes("UTF-8"));
            _arg_1.writeUTF(this.entrytag);
            _arg_1.writeUTF(this.gameNet);
            _arg_1.writeUTF(this.gameNetUserId);
            _arg_1.writeUTF(this.playPlatform);
            _arg_1.writeUTF(this.platformToken);
            _arg_1.writeUTF(this.userToken);
            _arg_1.writeUTF(this.trailer);
            _arg_1.writeUTF("");
	}
	
}
