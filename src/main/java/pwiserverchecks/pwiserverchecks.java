package pwiserverchecks;

import commands.user.regular.pwi.PWIServerStatus;
import java.util.Timer;
import java.util.TimerTask;
import information.Bot;
import net.dv8tion.jda.api.entities.TextChannel;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;

public class pwiserverchecks {
	private String response;
	private String guildChannelID = "912229502338338816";
	private char state = '0';
	private char previousState = '0';
	private String embedImgURL = "https://pwimages-a.akamaihd.net/arc/ad/2d/ad2d2fbea57aa2e949204504788719fe1376551415_775x480.jpg";

	/**
	 * Switch checks state on startup, then after every iteration of timertask.
	 * 0 = startup Val; Assumes the server is up. 
	 * 1 = server is "up"; 
	 * 2 = server is "down".
	 * 
	 * @return
	 */

	private void checkState() {

		switch (state) {
			case '0': // assumes server is up
			break;
			case '1':
			if (previousState == '2')
			//print server status = came back online
			// set perviousState = 1
			printToDiscord();
			previousState = '1';
			break;
			case '2':
			if (previousState == '1')
			//print server status = went offline
			//set previousState = 2
			printToDiscord();
			previousState = '2';
			break;
			default:
			state = '0';
			System.out.println("Switch defaulted to 0; Possible error in this class");
		}
	}
	
	private void printToDiscord() {
		TextChannel channel = Bot.getJdaInstance().getTextChannelById(guildChannelID);
		EmbedBuilder em = new EmbedBuilder();
		em.setTitle("Server Status");
		em.setColor(Color.ORANGE);
		em.setImage(embedImgURL);
		em.setDescription(response);
		if (state == '1') {
			channel.sendMessage(em.build()).queue();		
		} else if (state == '2') {
			channel.sendMessage(em.build()).queue();
		}
	}
	
	private char parseResponseForState() {
		
		try {
			/**
			 * Convert string response to char array
			 */
			Character[] tmp = new Character[response.length()];
			for (int i = 0; i < response.length(); i++) {
				tmp[i] = response.charAt(i);
			}
			
			/**
			 * Parse array for indication of down server/state
			 */
			char x = '0';
			for (int a = 0; a < tmp.length; a++) {
				if (tmp[a] == ':' && tmp[a + 5] == 'd') {
					x = '2';
					return x;
				} else {
					x = '1';
				}
			}
			return x;
		} catch (Exception e) {
			System.out.println("Error: " + e + "\n\n Converting and parseing tmp to array failed");
			return '5';
		}
	}

	
	public void timerCheckServerStatus() {
		Timer timer = new Timer();
		PWIServerStatus pwiServerStatus = new PWIServerStatus();
		TimerTask task = new TimerTask() {
			
			public void run() {
				response = pwiServerStatus.getServersAvailabilityDisplay();
				state = parseResponseForState();
				checkState();
			};	
		};
		
		timer.scheduleAtFixedRate(task, 5000, 60000);
	}
}