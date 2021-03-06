package zombiecraft.Client;

import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.src.ModLoader;

import java.io.File;

import zombiecraft.Core.GameLogic.ZCGame;
import zombiecraft.Forge.ZCClientTicks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;




@SideOnly(Side.CLIENT)
public class ZCSoundManager {

	//keep ticksBeforeMusic high
	
	//load music into streaming channel
	
	//randomize between them somehow getting song lengths for continual play
	
	public int curSoundTicks;
	public int songLengthTicks;
	
	public boolean soundSwapped;
	
	public SoundPool soundPoolMusicBkp;
	public SoundPool zcSoundPool;
	
	public ZCSoundManager() {
		
		
	}
	
	public void tick() {
		
		if (SoundManager.sndSystem != null) {
		
			if (ZCClientTicks.mc.thePlayer != null && ZCClientTicks.mc.thePlayer.dimension == ZCGame.ZCDimensionID/*ZCClientTicks.zcGame.gameActive*/) {
				//ZCUtil.setPrivateValueBoth(SoundManager.class, ZCClientTicks.mc.sndManager, "j", "ticksBeforeMusic", SoundManager.MUSIC_INTERVAL);
				
				if (!soundSwapped) {
					soundSwapped = true;
					soundPoolMusicBkp = ZCClientTicks.mc.sndManager.soundPoolMusic;
					ZCClientTicks.mc.sndManager.soundPoolMusic = new SoundPool();
					addLevelMusic();
					zcSoundPool = ZCClientTicks.mc.sndManager.soundPoolMusic;
					
					ZCClientTicks.mc.sndManager.sndSystem.stop("BgMusic");
				}
				
				if (SoundManager.sndSystem.playing("BgMusic")) {
					//System.out.println("not playing");
				} else {
					
					if (ZCClientTicks.mc.gameSettings.musicVolume != 0.0F) {
						//get random song
						SoundPoolEntry var1 = ZCClientTicks.mc.sndManager.soundPoolMusic.getRandomSound();
						
						
						
						//music playing code
						if (var1 != null) {
							System.out.println("start playing -> " + var1.soundName);
			                //var1 = ModCompatibilityClient.audioModPickBackgroundMusic(ZCClientTicks.mc.sndManager, var1);
							SoundManager.sndSystem.backgroundMusic("BgMusic", var1.soundUrl, var1.soundName, false);
							SoundManager.sndSystem.setVolume("BgMusic", ZCClientTicks.mc.gameSettings.musicVolume);
							SoundManager.sndSystem.play("BgMusic");
							//SoundManager.sndSystem.play("music.zc");
						} else {
							//System.out.println("FAIL TO PLAY RANDOM MUSIC");
						}
					} else {
						
					}
				}
				
			} else {
				if (soundSwapped) {
					soundSwapped = false;
					ZCClientTicks.mc.sndManager.soundPoolMusic = soundPoolMusicBkp;
				}
			}
		}
	}
	
	public void addLevelMusic() {
		
		String path = "mod/music/zc/";
		
		tryInstallSound(path + "Bent and Broken.ogg");
		tryInstallSound(path + "Return of Lazarus.ogg");
		tryInstallSound(path + "The House of Leaves.ogg");
		
	}
	
	public void tryInstallSound(String str) {
		File soundFile = new File(ModLoader.getMinecraftInstance().mcDataDir,
				"resources/" + str);

		if (!soundFile.exists()) {
			
		} else {
			if (soundFile.canRead() && soundFile.isFile()) {
				System.out.println("Installing " + str);
				ZCClientTicks.mc.sndManager.addMusic(str, soundFile);
			} else {
				System.err.println("Could not load file: " + soundFile);
			}
		}
	}
	
	public void playRandom() {
		
	}
	
	public void playSong(String str) {
		
	}
	
}
