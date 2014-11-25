package o1.game

import o1.characters._

class Battlefield(player: Player, enemy: Character) {
	
	private val battling = true
	
	def battleFieldMessage = "\nYou have stepped into the battlefield\nagainst evil " + this.enemy.fullName + ".\n"

	def playBattleTurn(command: String): String = {
		val action = new Action(command)
		val actionReport = action.executeFightCommands(this.player)
		actionReport.getOrElse("Unknown command: \"" + command + "\".")
	}
	
	def isBattling(): Boolean = {
		this.battling
	}
}