package src.game

import src.characters._

class Action(input: String) {

	private val commandText = input.trim.toLowerCase
	private val verb = commandText.takeWhile(_ != ' ')
	private val modifiers = commandText.drop(verb.length).trim

	def execute(actor: Player): Option[String] = {
		this.verb match {
			case "go" => if (!this.modifiers.isEmpty()) Some(actor.go(this.modifiers)) else Action.specifyCommand
			case "get" => if (!this.modifiers.isEmpty()) Some(actor.get(this.modifiers)) else Action.specifyCommand
			case "examine" => if (!this.modifiers.isEmpty()) Some(actor.examine(this.modifiers)) else Action.specifyCommand
			case "drop" => if (!this.modifiers.isEmpty()) Some(actor.drop(this.modifiers)) else Action.specifyCommand
			case "ask" => if (!this.modifiers.isEmpty()) Some(actor.ask(this.modifiers)) else Action.specifyCommand
			case "fight" => Some(actor.fight())
			case "area" => Some(actor.areainfo())
			case "inventory" => Some(actor.makeInventory())
			case "exits" => Some(actor.exits())
			case "quit" => Some(actor.quit())
			case "items" => Some(actor.itemsInThisArea())
			case "enemies" => Some(actor.enemiesInThisArea())
			case "tips" => Some(actor.tips())
			case "hp" => Some(actor.getHp())
			case "help" => Some(actor.getHelp())
			case "refill" => Some(actor.refill())
			case "home" => Some(actor.getHome())
			case _ => None
		}
	}

	/* Battle commands */
	def executePlayerFightCommands(player: Player, enemy: Enemy): Option[String] = {
		this.verb match {
			case "use" => player.hit(this.modifiers, enemy)
			case "exit" => player.exitFight()
			case _ => None
		}
	}
}

object Action {
	private val specifyCommand: Option[String] = Some("Specify your command.")

	/* Enemy's battle method */
	def executeEnemyFightCommand(enemy: Enemy, player: Player): Option[String] = {
		Some(enemy.hit(player))
	}

	val commands: Map[String, String] = Map("go" -> "Gets you to places.\nUse example: 'go east' or 'go king's landing'",
		"get" -> "Lets you pick up stuff from the ground.\nPossible only if the item exists in the current place.\nUse example: 'get needle'",
		"examine" -> "Lets you examine items,i.e. gives you\nthe description of the item.\nUse example: 'examine needle'",
		"drop" -> "Lets you drop an item in the\ncurrent place. Possible only if you have at least\none item in the first place.\nUse case: 'drop needle'",
		"ask" -> "Gives you a possibility to speak with people.\nHelpful when trying to find your relatives.\nUse example: 'ask'",
		"fight" -> "Step into a battlefield against your enemy.\nPossible if enemy present and they\nhave your relative in hostage.\nUse example: 'fight",
		"area" -> "Gives a description of the current area.\nUse example: 'area'",
		"inventory" -> "Lists what you are carrying\nor accompanied by at the moment.\nUse example: 'inventory'",
		"exits" -> "Lists the places where you\nare able to go from the current place.\nUse example: 'exits'",
		"quit" -> "Quits the game.\nUse example: 'quit'",
		"items" -> "Gives a list of the items\nyou may see in the current area.\nUse example: 'items'",
		"enemies" -> "Gives a list of an enemy you\nmay see in the current area.\nUse example: 'enemies'",
		"tips" -> "Lists tips to survive.\nUse example: 'tips'",
		"hp" -> "Knows your Health Points!\nUse example: 'hp'",
		"help" -> "Lists all the commands available.\nUse example: 'help'",
		"refill" -> "Revills your Health Points.\nPossible only at home.",
		"home" -> "Your home.")
}