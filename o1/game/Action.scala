package o1.game

import o1.characters._

class Action(input: String) {
	
	private val commandText = input.trim.toLowerCase
	private val verb = commandText.takeWhile(_ != ' ')
	private val modifiers = commandText.drop(verb.length).trim
	
	def execute(actor: Player): Option[String] = {
		this.verb match {
			case "go"  => if(!this.modifiers.isEmpty()) Some(actor.go(this.modifiers)) else Action.specifyCommand
			case "get" => if(!this.modifiers.isEmpty()) Some(actor.get(this.modifiers)) else Action.specifyCommand
			case "examine" => if(!this.modifiers.isEmpty()) Some(actor.examine(this.modifiers)) else Action.specifyCommand
			case "drop" => if(!this.modifiers.isEmpty()) Some(actor.drop(this.modifiers)) else Action.specifyCommand
			case "ask" => if(!this.modifiers.isEmpty()) Some(actor.ask(this.modifiers)) else Action.specifyCommand
			case "fight" => Some(actor.fight())
			case "area" => Some(actor.areainfo())
			case "inventory" => Some(actor.makeInventory())
			case "exits" => Some(actor.exits())
			case "quit" => Some(actor.quit())
			case _ => None
		}
	}
	
	def executeFightCommands(actor: Player): Option[String] = {
		this.verb match {
			case "use" => Some(actor.hit(this.modifiers))
		}
	}
}

object Action {
	private val specifyCommand: Option[String] = Some("Specify your command.")
}