package realmrelay.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class StatData implements IData {
	
	public int id;
	public int intValue;
	public String stringValue;
         public static int MAX_HP_STAT = 0;
        public static int HP_STAT = 1;
        public static int SIZE_STAT = 2;
        public static int MAX_MP_STAT = 3;
        public static int MP_STAT = 4;
        public static int NEXT_LEVEL_EXP_STAT = 5;
        public static int EXP_STAT = 6;
        public static int LEVEL_STAT = 7;
        public static int ATTACK_STAT = 20;
        public static int DEFENSE_STAT = 21;
        public static int SPEED_STAT = 22;
        public static int INVENTORY_0_STAT = 8;
        public static int INVENTORY_1_STAT = 9;
        public static int INVENTORY_2_STAT = 10;
        public static int INVENTORY_3_STAT = 11;
        public static int INVENTORY_4_STAT = 12;
        public static int INVENTORY_5_STAT = 13;
        public static int INVENTORY_6_STAT = 14;
        public static int INVENTORY_7_STAT = 15;
        public static int INVENTORY_8_STAT = 16;
        public static int INVENTORY_9_STAT = 17;
        public static int INVENTORY_10_STAT = 18;
        public static int INVENTORY_11_STAT = 19;
        public static int VITALITY_STAT = 26;
        public static int WISDOM_STAT = 27;
        public static int DEXTERITY_STAT = 28;
        public static int CONDITION_STAT = 29;
        public static int NUM_STARS_STAT = 30;
        public static int NAME_STAT = 31;
        public static int TEX1_STAT = 32;
        public static int TEX2_STAT = 33;
        public static int MERCHANDISE_TYPE_STAT = 34;
        public static int CREDITS_STAT = 35;
        public static int MERCHANDISE_PRICE_STAT = 36;
        public static int ACTIVE_STAT = 37;
        public static int ACCOUNT_ID_STAT = 38;
        public static int FAME_STAT = 39;
        public static int MERCHANDISE_CURRENCY_STAT = 40;
        public static int CONNECT_STAT = 41;
        public static int MERCHANDISE_COUNT_STAT = 42;
        public static int MERCHANDISE_MINS_LEFT_STAT = 43;
        public static int MERCHANDISE_DISCOUNT_STAT = 44;
        public static int MERCHANDISE_RANK_REQ_STAT = 45;
        public static int MAX_HP_BOOST_STAT = 46;
        public static int MAX_MP_BOOST_STAT = 47;
        public static int ATTACK_BOOST_STAT = 48;
        public static int DEFENSE_BOOST_STAT = 49;
        public static int SPEED_BOOST_STAT = 50;
        public static int VITALITY_BOOST_STAT = 51;
        public static int WISDOM_BOOST_STAT = 52;
        public static int DEXTERITY_BOOST_STAT = 53;
        public static int OWNER_ACCOUNT_ID_STAT = 54;
        public static int RANK_REQUIRED_STAT = 55;
        public static int NAME_CHOSEN_STAT = 56;
        public static int CURR_FAME_STAT = 57;
        public static int NEXT_CLASS_QUEST_FAME_STAT = 58;
        public static int LEGENDARY_RANK_STAT = 59;
        public static int SINK_LEVEL_STAT = 60;
        public static int ALT_TEXTURE_STAT = 61;
        public static int GUILD_NAME_STAT = 62;
        public static int GUILD_RANK_STAT = 63;
        public static int BREATH_STAT = 64;
        public static int XP_BOOSTED_STAT = 65;
        public static int XP_TIMER_STAT = 66;
        public static int LD_TIMER_STAT = 67;
        public static int LT_TIMER_STAT = 68;
        public static int HEALTH_POTION_STACK_STAT = 69;
        public static int MAGIC_POTION_STACK_STAT = 70;
        public static int BACKPACK_0_STAT = 71;
        public static int BACKPACK_1_STAT = 72;
        public static int BACKPACK_2_STAT = 73;
        public static int BACKPACK_3_STAT = 74;
        public static int BACKPACK_4_STAT = 75;
        public static int BACKPACK_5_STAT = 76;
        public static int BACKPACK_6_STAT = 77;
        public static int BACKPACK_7_STAT = 78;
        public static int HASBACKPACK_STAT = 79;
        public static int TEXTURE_STAT = 80;
        public static int PET_INSTANCEID_STAT = 81;
        public static int PET_NAME_STAT = 82;
        public static int PET_TYPE_STAT = 83;
        public static int PET_RARITY_STAT = 84;
        public static int PET_MAXABILITYPOWER_STAT = 85;
        public static int PET_FAMILY_STAT = 86;
        public static int PET_FIRSTABILITY_POINT_STAT = 87;
        public static int PET_SECONDABILITY_POINT_STAT = 88;
        public static int PET_THIRDABILITY_POINT_STAT = 89;
        public static int PET_FIRSTABILITY_POWER_STAT = 90;
        public static int PET_SECONDABILITY_POWER_STAT = 91;
        public static int PET_THIRDABILITY_POWER_STAT = 92;
        public static int PET_FIRSTABILITY_TYPE_STAT = 93;
        public static int PET_SECONDABILITY_TYPE_STAT = 94;
        public static int PET_THIRDABILITY_TYPE_STAT = 95;
        public static int NEW_CON_STAT = 96;
        public static int FORTUNE_TOKEN_STAT = 97;
        public static int SUPPORTER_POINTS_STAT = 98;
        public static int SUPPORTER_STAT = 99;
        public static int CHALLENGER_STARBG_STAT = 100;
	
	public boolean isUTFData() {
		switch (id) {
			case 31:
			case 62:
			case 82:
			case 38:
			case 54: {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void parseFromInput(DataInput in) throws IOException {
		id = in.readUnsignedByte();
		if (isUTFData()) {
			stringValue = in.readUTF();
		} else {
			intValue = in.readInt();
		}
	}
	
	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeByte(id);
		if (isUTFData()) {
			out.writeUTF(stringValue);
		} else {
			out.writeInt(intValue);
		}
	}
	
}
