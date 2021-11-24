package boot;

import commands.CommandExecutor;
import database.base.DatabaseManager;
import events.EventsManager;
import information.Bot;
import information.admins.AdminsManager;
import information.clocks.ClockRegister;
import information.ownerconfiguration.Commands;
import information.ownerconfiguration.Embeds;
import information.ownerconfiguration.Guilds;
import information.ownerconfiguration.Users;
import information.ownerconfiguration.pwiItems.PWIItems;
import information.ownerconfiguration.roles.Roles;
import information.triggers.TriggerRegister;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import questionnaire.base.QuestionnaireRegister;
import timer.HalfMinutely;
import timer.Minutely;
import pwiserverchecks.pwiserverchecks;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

import static information.ownerconfiguration.Database.*;

/**
 * Program boot-up operator.
 */
public class Bootstrap {
	private static final String CONFIG_FILE_NAME = "config.cfg";
	private static final String TOKEN_FILE_NAME = "token.cfg";
	private static final String PWI_ITEMS_FILE_NAME = "ItemIDList.cfg";

	private Bootstrap() {
	}

	/**
	 * Boots the program up.
	 *
	 * @return if the loading was successful.
	 */
	public static boolean load() {
		// NOTE: The order matters!
		System.out.println("Booting-up....");

		/** LOAD CONFIG FILES */
		String token = loadToken();

		if (token == null)
			return false;
		if (!launchJDA(token))
			return false;
		if (!loadConfigFile())
			return false;
		if (!loadPWIItems()) {
			return false;
		}
		pwiserverchecks pwiServerChecks = new pwiserverchecks();
		pwiServerChecks.timerCheckServerStatus();

		/** LOAD CONFIG FILES */

		/** LOAD CLASS INFO/SINGLETONS */
		CommandExecutor.initialize();
		AdminsManager.initialize();
		Roles.initialize();
		ClockRegister.initialize();
		QuestionnaireRegister.initialize();
		TriggerRegister.initialize();
		/** LOAD CLASS INFO/SINGLETONS */

		/** LOAD DABATASE AND TABLES */
		if (!DatabaseManager.initialize())
			return false;
		/** LOAD DABATASE AND TABLES */

		/** LOAD TIMERS */
		new Minutely().start();
		new HalfMinutely().start();

		/** LOAD TIMERS */

		configJDA();
		System.out.println("Boot-up complete");
		return true;

	}

	/**
	 * Loads the bot token into the memory.
	 */
	public static String loadToken() {
		try (BufferedReader br = new BufferedReader(new FileReader(TOKEN_FILE_NAME))) {
			return br.readLine().trim();
		} catch (Exception e) {
			System.out.println("No token in the first line of the config file (or no config file).");
		}
		return null;
	}

	/**
	 * Logs the bot into Discord.
	 */
	private static boolean launchJDA(String botToken) {
		boolean success = true;

		try {
			Bot.setJdaInstance(new JDABuilder(AccountType.BOT).setToken(loadToken()).build().awaitReady());
		} catch (Exception e) {
			System.out.println("FAILED TO LOG-IN THE BOT");
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	/**
	 * Configures JDA by: <br>
	 * * Setting the event listener <br>
	 * * Setting the game that the bot is playing
	 */
	private static void configJDA() {
		Bot.getJdaInstance().addEventListener(new EventsManager());

		String game = Bot.getGame();
		if (game != null && !game.trim().isEmpty())
			Bot.getJdaInstance().getPresence().setActivity(Activity.playing(game));
	}

	/**
	 * Loads the owner configuration file.
	 */
	private static boolean loadConfigFile() {
		boolean success = true;
		int nLine = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_FILE_NAME))) {
			String str;
			while ((str = br.readLine()) != null) {
				nLine++;
				if (str.contains("=")) {
					String[] config = str.split("=");
					config[0] = config[0].toLowerCase().replace(" ", "");
					config[1] = config[1].trim();
					switch (config[0]) {
					case "databaseurl":
						databaseUrl = config[1];
						break;
					case "databasename":
						databaseName = config[1];
						break;
					case "databaseusername":
						databaseUsername = config[1];
						break;
					case "databasepassword":
						databasePassword = config[1];
						break;
					case "commandsprefix":
						Commands.setPrefix(getQuoteMarkedConfig(config[1]));
						break;
					case "helpembedcolor":
						Embeds.setDefaultEmbedColor(Color.decode(config[1]));
						break;
					case "helpembedfooterimageurl":
						Embeds.setHelpEmbedFooterImageUrl(config[1]);
						break;
					case "helpembedfootertext":
						Embeds.setHelpEmbedFooterText(config[1]);
						break;
					case "mainguildid":
						Guilds.mainGuildId = Long.parseLong(config[1]);
						break;
					case "ownerid":
						Users.ownerId = Long.parseLong(config[1]);
						break;
					case "game":
						Bot.setGame(config[1]);
						break;
					}
				}
			}

		} catch (

		Exception e) {
			System.out.println("ERROR READING THE CONFIGURATION FILE. LINE " + nLine);
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	private static String getQuoteMarkedConfig(String cfg) {
		return cfg.split("\"")[1];
	}

	private static boolean loadPWIItems() {
		String[] itemInfo;
		{
			try (BufferedReader br = new BufferedReader(new FileReader(PWI_ITEMS_FILE_NAME))) {

				for (int i = 0; i < 1; i++) {
					itemInfo = br.readLine().split(";");
					PWIItems.addItem(itemInfo[0], itemInfo[1]);
				}
			} catch (Exception e) {
				System.out.println("FAILED TO LOAD PWI ITEMS");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
};