package realmrelay;

import java.io.IOException;
import java.util.HashMap;
import realmrelay.data.Entity;
import realmrelay.data.Location;
import realmrelay.data.PlayerData;
import realmrelay.data.PortalData;
import realmrelay.data.StatData;
import realmrelay.data.Status;
import realmrelay.packets.Packet;
import realmrelay.packets.client.HelloPacket;
import realmrelay.packets.client.InvSwapPacket;
import realmrelay.packets.client.PlayerTextPacket;
import realmrelay.packets.client.PongPacket;
import realmrelay.packets.client.UseItemPacket;
import realmrelay.packets.client.UsePortalPacket;
import realmrelay.packets.server.AllyShootPacket;
import realmrelay.packets.server.CreateSuccessPacket;
import realmrelay.packets.server.NewTickPacket;
import realmrelay.packets.server.PingPacket;
import realmrelay.packets.server.QuestObjIdPacket;
import realmrelay.packets.server.ReconnectPacket;
import realmrelay.packets.server.TextPacket;
import realmrelay.packets.server.UpdatePacket;
import realmrelay.script.PacketScriptEvent;

public class PacketManager {
	
	public static PlayerData playerData = new PlayerData();
	
	private static long startTime = 0;
	
	private static Packet lastUseItemPacket;
	
	private static boolean canGo;
	
	private static int myQuestId;
	
	private static Location myQuestPos;
	
	public static HashMap<Integer, PortalData> portals = new HashMap<Integer, PortalData>();
	
	private static boolean enteredInRealm;
	
	private static int tryingToJoinPortalId = 0;
	
	private static int maxPopulation = 85;
	
	private static boolean wantToConnect = false;
	
	private static boolean isUsingAutoCon;
	
	private static boolean wantToBeReconnected = true;
	
	private static boolean showPackets = false;
	
	public static void onClientPacketEvent(final PacketScriptEvent event) throws Exception {
		final Packet packet = event.getPacket();
		if(showPackets && !(packet instanceof TextPacket) && !(packet instanceof AllyShootPacket) 
				&& !(packet instanceof PingPacket) && !(packet instanceof PongPacket)
				&& !(packet instanceof UpdatePacket) && !(packet instanceof NewTickPacket))
			System.out.println("C2S: " + packet.id() + " : " + packet.getName() + " : " + packet.getBytes());
		
		if (packet instanceof UsePortalPacket) {
			
			if (wantToConnect == true) {
				tellToPlayer(event, "There is already a waiting pending.");
			} else {
				
				wantToConnect = true;
				
				UsePortalPacket upk = (UsePortalPacket) packet;
				System.out.println(upk.objectId);
				
				final PortalData portalData = portals.get(upk.objectId);
				if (portalData != null && portalData.population < maxPopulation) { //TESTING
					event.cancel();
					tellToPlayer(event, "You will be automatically connected soon.");
					tryingToJoinPortalId = upk.objectId;
				}
				
			}
			
		} else if (packet instanceof HelloPacket) {
			HelloPacket hpkt = (HelloPacket) packet;
			if (isUsingAutoCon) {
				isUsingAutoCon = false;
			}
			
			//HelloPacket mpk = (HelloPacket) event.getPacket();
			
			wantToConnect = false; //reset
			
		} else if (packet instanceof InvSwapPacket) {
			
			// inv debugger
			/*InvSwapPacket isp = (InvSwapPacket) event.getPacket();
			
			SlotObject slotObject1 = new SlotObject();
			slotObject1.objectType = isp.slotObject1.objectType;
			slotObject1.slotId = isp.slotObject1.slotId;
			
			System.out.println("from ObjectId (myId) : " + slotObject1.objectId);
			System.out.println("from OjectType (myId) : " + slotObject1.objectType);
			System.out.println("from SlotId (i) : " + slotObject1.slotId);*/
			
		}else if (packet instanceof PlayerTextPacket){
			PlayerTextPacket textPacket = (PlayerTextPacket) packet;
			String command = "/togpkt";
			try{
			if(textPacket.text.substring(0, command.length()).equalsIgnoreCase(command)){
				event.cancel();
				showPackets = !showPackets;
			}
			}catch(Exception e){
				//do nothing
			}
		}
		
		return;
	}
	
	public static int currentTime() {
		return (int) (System.currentTimeMillis() - startTime);
	}
	
	public static void echo(String text) {
		System.out.println(text);
	}
	
	public static void onServerPacketEvent(PacketScriptEvent event) throws Exception {
		Packet packet = event.getPacket();
		if(showPackets && !(packet instanceof TextPacket) && !(packet instanceof AllyShootPacket) 
				&& !(packet instanceof PingPacket) && !(packet instanceof PongPacket)
				&& !(packet instanceof UpdatePacket) && !(packet instanceof NewTickPacket))
			System.out.println("S2C: " + packet.id() + " : " + packet.getName() + " : " + packet.getBytes());
		if (packet instanceof CreateSuccessPacket) {
			CreateSuccessPacket csp = (CreateSuccessPacket) packet;
			//echo("My ID : " + csp.objectId);
			playerData.id = csp.objectId;
		} else if (packet instanceof QuestObjIdPacket) {
			QuestObjIdPacket qoid = (QuestObjIdPacket) packet;
			myQuestId = qoid.objectId;
		} else if (packet instanceof UpdatePacket) {
			UpdatePacket update = (UpdatePacket) packet;
			for (Entity newObjs : update.newObjs) {
				
				for (StatData j : newObjs.status.data) {
					
					for (StatData data : newObjs.status.data) {
						if (data.id == 31 && data.stringValue.contains("NexusPortal")) { /* WTF IS "DARKPORTAL" ? */
							if (!enteredInRealm) {
								
								String portalName = data.stringValue.substring(data.stringValue.indexOf(".") + 1, data.stringValue.indexOf(" ("));
								int portalPopulation = Integer.parseInt(data.stringValue.substring(data.stringValue.indexOf("(") + 1, data.stringValue.indexOf("/")));
								
								PortalData portal = new PortalData(portalPopulation, newObjs.status.objectId, newObjs.status.pos, portalName);
								
								portals.put(newObjs.status.objectId, portal);
								
								//echo("Found portal \"" + portal.name + "\" (" + portal.population + "/85).");
								
							}
						}
					}
					
					for (int drop : update.drops) {
						if (portals.containsKey(drop)) {
							portals.remove(drop);
							echo("Dropped a realm");
						}
					}
					
				}
				
				for (int drop : update.drops) {
					if (portals.containsKey(drop)) {
						portals.remove(drop);
						echo("Dropped a realm");
					}
				}
				
			}
			
			for (Entity ent : update.newObjs) {
				
				for (int drop : update.drops) {
					if (portals.containsKey(drop)) {
						portals.remove(drop);
						echo("Dropped a realm");
					}
				}
				
			}
		} else if (packet instanceof NewTickPacket) {
			NewTickPacket ent = (NewTickPacket) packet;
			
			for (Status he : ent.statuses) {
				for (StatData data : he.data) {
					
					if (data.id == 31 && data.stringValue.contains("NexusPortal")) { /* WTF IS "DARKPORTAL" ? */
						
						//System.out.println("FOUND PORTAL");
						
						if (!enteredInRealm) {
							
							int objectId = he.objectId;
							
							String portalName = data.stringValue.substring(data.stringValue.indexOf(".") + 1, data.stringValue.indexOf(" ("));
							int portalPopulation = Integer.parseInt(data.stringValue.substring(data.stringValue.indexOf("(") + 1, data.stringValue.indexOf("/")));
							
							PortalData portal = new PortalData(portalPopulation, objectId, he.pos, portalName);
							
							portals.put(objectId, portal);
							
							//echo("Found portal \"" + portal.name + "\" (" + portal.population + "/" + maxPopulation + ").");
							
							if (objectId == tryingToJoinPortalId) {
								//System.out.println("Found portal that we are trying to join.");
								if (wantToConnect) {
									if (portals.get(objectId).population < maxPopulation) {
										
										isUsingAutoCon = true;
										tellToPlayer(event, "Connecting...");
										UsePortalPacket upk = new UsePortalPacket();
										upk.objectId = tryingToJoinPortalId;
										event.sendToServer(upk);
									}
									
								}
							}
							
						}
					}
					
					if (playerData.pos != null && portals.get(tryingToJoinPortalId) != null) {
						
						if (!enteredInRealm && wantToBeReconnected && playerData.pos.distanceSq(portals.get(tryingToJoinPortalId).loc) < 2) {
							
							tellToPlayer(event, "Auto Reconnecting...");
							UsePortalPacket upk = new UsePortalPacket();
							upk.objectId = tryingToJoinPortalId;
							event.sendToServer(upk);
						}
					}
					
					/*
					if (he.objectId == tryingToJoinPortalId) {
						tellToPlayer(event, "Get closer to " + portals.get(tryingToJoinPortalId).name + " to autoreconnect.");
					}
					*/
					
					if (he.objectId == playerData.id) {
						
						playerData.parseNewTICK(data.id, data.intValue, data.stringValue);
					}
					
				}
			}
			
		}else if(packet instanceof ReconnectPacket){
			ReconnectPacket reconPacket = (ReconnectPacket) packet;
			reconPacket.printPacket();
		}
		return;
	}
	
	public static void tellToPlayer(final PacketScriptEvent event, String text) throws IOException {
		TextPacket notificationPacket = new TextPacket();
		notificationPacket.bubbleTime = -1;
		notificationPacket.cleanText = "";
		notificationPacket.name = "";
		notificationPacket.numStars = -1;
		notificationPacket.objectId = -1;
		notificationPacket.recipient = "";
		notificationPacket.text = text;
		event.sendToClient(notificationPacket);
	}
	
}
