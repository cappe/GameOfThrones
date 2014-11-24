package o1.game

import o1.characters._

class Action(input: String) {
	
	private val commandText = input.trim.toLowerCase
	private val verb = commandText.takeWhile(_ != ' ')
	private val modifiers = commandText.drop(verb.length).trim
	
	def execute(actor: Player): Option[String] = {
		this.verb match {
			case "go" => Some(actor.go(this.modifiers))
			case "get" => Some(actor.get(this.modifiers))
			case "fight" => Some(actor.fight())
			case "examine" => Some(actor.examine(this.modifiers))
			case "drop" => Some(actor.drop(this.modifiers))
			case "ask" => Some(actor.ask(this.modifiers))
			case "inventory" => Some(actor.makeInventory())
			case "quit" => Some(actor.quit())
			case _ => None
		}
	}
}