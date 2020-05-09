package realmrelay.crypto;

;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;


public class RC4 {
	
    public static String DefaultIncomingKey = "6a39570cc9de4ec71d64821894c79332b197f92ba85ed281a023".substring(26);
    public static String DefaultOutgoingKey = "6a39570cc9de4ec71d64821894c79332b197f92ba85ed281a023".substring(0, 26);
    
private final StreamCipher rc4;
	
	public RC4(String key) {
		this(hexStringToBytes(key));
	}
	
	public RC4(byte[] bytes) {
		this.rc4 = new RC4Engine();
		KeyParameter keyParam = new KeyParameter(bytes);
		this.rc4.init(true, keyParam);
	}
	
	/**
	 * Cipher bytes and update cipher
	 * 
	 * @param bytes
	 */
	public void cipher(byte[] bytes) {
		this.rc4.processBytes(bytes, 0, bytes.length, bytes, 0);
	}
	
	private static byte[] hexStringToBytes(String key) {
		if (key.length() % 2 != 0) {
			throw new IllegalArgumentException("invalid hex string");
		}
		byte[] bytes = new byte[key.length() / 2];
		char[] c = key.toCharArray();
		for (int i = 0; i < c.length; i += 2) {
			StringBuilder sb = new StringBuilder(2).append(c[i]).append(c[(i + 1)]);
			int j = Integer.parseInt(sb.toString(), 16);
			bytes[(i / 2)] = (byte) j;
		}
		return bytes;
	}
}
    
    
//     private static int STATE_LENGTH = 256;
//
//        private byte[] engineState;
//        private int x;
//        private int y;
//        private byte[] workingKey;
//
//        public RC4(byte[] key)
//        {
//            workingKey = key;
//            SetKey(workingKey);
//        }
//
//        public RC4(String hexString)
//        {
//        try {
//            workingKey = HexStringToBytes(hexString);
//            SetKey(workingKey);
//        } catch (Exception ex) {
//            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        }
//
//        public void cipher(byte[] packet)
//        {
//            ProcessBytes(packet, 5, packet.length - 5, packet, 5);
//        }
//
//        public void Reset()
//        {
//            SetKey(workingKey);
//        }
//
//        private void ProcessBytes(byte[] input, int inOff, int length, byte[] output, int outOff)
//        {
//            /*
//            if ((inOff + length) > input.Length)
//                throw new ArgumentException("input buffer too short");
//            if ((outOff + length) > output.Length)
//                throw new ArgumentException("output buffer too short");
//            */
//            for (int i = 0; i < length; i++)
//            {
//                x = (x + 1) & 0xff;
//                y = (engineState[x] + y) & 0xff;
//
//                // swap
//                byte tmp = engineState[x];
//                engineState[x] = engineState[y];
//                engineState[y] = tmp;
//
//                // xor
//                output[i + outOff] = (byte)(input[i + inOff]
//                        ^ engineState[(engineState[x] + engineState[y]) & 0xff]);
//            }
//        }
//
//        private void SetKey(byte[] keyBytes)
//        {
//            workingKey = keyBytes;
//            x = y = 0;
//
//            if (engineState == null)
//                engineState = new byte[STATE_LENGTH];
//
//            // reset the state of the engine
//            for (int i = 0; i < STATE_LENGTH; i++)
//                engineState[i] = (byte)i;
//
//            int i1 = 0, i2 = 0;
//
//            for (int i = 0; i < STATE_LENGTH; i++)
//            {
//                i2 = ((keyBytes[i1] & 0xff) + engineState[i] + i2) & 0xff;
//                // do the byte-swap inline
//                byte tmp = engineState[i];
//                engineState[i] = engineState[i2];
//                engineState[i2] = tmp;
//                i1 = (i1 + 1) % keyBytes.length;
//            }
//        }
//
//        public static byte[] HexStringToBytes(String key) throws Exception
//        {
//            if (key.length() % 2 != 0)
//                throw new Exception("Invalid hex string!");
//
//            byte[] bytes = new byte[key.length() / 2];
//            char[] c = key.toCharArray();
//            for (int i = 0; i < c.length; i += 2)
//            {
//                StringBuilder sb = new StringBuilder(2).append(c[i]).append(c[(i + 1)]);
//                int j = Integer.parseInt(sb.toString(), 16);
//                bytes[(i / 2)] = (byte)j;
//            }
//            return bytes;
//        }
//    }

//private byte[] a;
//	private int b;
//	private int c;
//
//	/**
//	 * Creates new RC4 cipher object with string key
//	 * @param key
//	 */
//	public RC4(String key) {
//		this(hexStringToBytes(key));
//	}
//
//	/**
//	 * Creates new RC4 cipher object with byte array key
//	 * @param bytes
//	 */
//	public RC4(byte[] bytes) {
//		this.a = new byte[256];
//		for (int i = 0; i < this.a.length; i++) {
//			this.a[i] = (byte) i;
//		}
//		this.b = 0;
//		this.c = 0;
//		int i = 0;
//		int j = 0;
//		if (bytes.length == 0) {
//			throw new IllegalArgumentException("invalid rc4 key");
//		}
//		for (int k = 0; k < this.a.length; k++) {
//			j = (bytes[i] & 0xFF) + (this.a[k] & 0xFF) + j & 0xFF;
//			int l = this.a[k];
//			this.a[k] = this.a[j];
//			this.a[j] = (byte) l;
//			i = (i + 1) % bytes.length;
//		}
//	}
//
//	/**
//	 * Cipher bytes and update cipher
//	 * @param bytes
//	 */
//	public void cipher(byte[] bytes) {
//		for (int i = 0; i < bytes.length; i++) {
//			this.b = this.b + 1 & 0xFF;
//			this.c = (this.a[this.b] & 0xFF) + this.c & 0xFF;
//			int j = this.a[this.b];
//			this.a[this.b] = this.a[this.c];
//			this.a[this.c] = (byte) j;
//			j = (this.a[this.b] & 0xFF) + (this.a[this.c] & 0xFF) & 0xFF;
//			bytes[i] = (byte) (bytes[i] ^ this.a[j]);
//		}
//	}
//
//	private static byte[] hexStringToBytes(String key) {
//		if (key.length() % 2 != 0) {
//			throw new IllegalArgumentException("invalid hex string");
//		}
//		byte[] arrayOfByte = new byte[key.length() / 2];
//		char[] c = key.toCharArray();
//		for (int i = 0; i < c.length; i += 2) {
//			String localStringBuilder = String.valueOf(Character.toString(c[i])) + Character.toString(c[(i + 1)]);
//			int j = Integer.parseInt(localStringBuilder, 16);
//			arrayOfByte[(i / 2)] = (byte) j;
//		}
//		return arrayOfByte;
//	}
	
//}
