package commands.user.regular.pwi;

import commands.base.Category;
import commands.base.CommandWithoutSubCommands;
import events.MessageReceivedEvent;
import information.PWIServer;
import information.ownerconfiguration.Commands;
import information.ownerconfiguration.pwiItems.PWIItems;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.List;

public class PWIItemPrice extends CommandWithoutSubCommands {
	public PWIItemPrice() {
		super(Category.PWI
				, "Find the price of an item on PWCats."
				, "Server Item-name"
				, true);
		this.addName("PWIItemPrice");
		this.addName("ItemPrice");
		this.addName("Price");
		this.addExample("tt Hyper Exp", "Get a PWCats link for the price of Hyper Exp Stones on the Twilight Temple server.");
		this.addExample("etherblade perfect stone", "Get a PWCats link for the price of Perfect Stones on the Etherblade server.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
		if (args == null) {
			channel.sendMessage("You must provide the server and name of the item to search for! (example: `" + Commands.getCommandPrefix() + this.getFullNames().get(0) + " etherblade warsong marshal badge`").queue();
			return;
		}

		String serverName;
		String itemName;
		if (args.toLowerCase().startsWith("twilight temple")) {
			serverName = "tt";
			String[] argsSplit = args.split(" ", 3);
			if (argsSplit.length < 3) {
				channel.sendMessage("You must tell me the PWI server and the name of the item.").queue();
				return;
			}
			itemName = argsSplit[2];
		}
		else {
			String[] argsSplit = args.split(" ", 2);
			if (argsSplit.length < 2) {
				channel.sendMessage("You must tell me the PWI server and the name of the item.").queue();
				return;
			}
			serverName = argsSplit[0];
			itemName = argsSplit[1];
		}
		PWIServer pwiServer = PWIServer.getServer(serverName);
		if (pwiServer == null) {
			channel.sendMessage("You must specify the PWI server. **" + serverName + "** is not a valid server name.").queue();
			return;
		}

		List<information.ownerconfiguration.pwiItems.PWIItem> pwiItems = PWIItems.getMatchingItems(itemName);
		int nResults = pwiItems.size();
		if (pwiItems.size() > 10) {
			channel.sendMessage("More than 10 results! Try again but be more specific.").queue();
		}
		else if (nResults < 1) {
			channel.sendMessage("No items found by the name of **" + itemName + "**").queue();
		}
		else {
			StringBuilder resultSB = new StringBuilder();
			for (information.ownerconfiguration.pwiItems.PWIItem item : pwiItems) {
				resultSB.append("\n__").append(item.getName()).append("__: <")
						.append(item.getPriceLink(pwiServer)).append(">");
			}
			channel.sendMessage(resultSB.toString()).queue();
		}
	}
}
