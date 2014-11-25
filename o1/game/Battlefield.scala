package o1.game

import o1.characters._

class Battlefield(player: Player, enemy: Enemy) {
	
	private val battling = true
	
	def battleFieldMessage = "\nYou have stepped into the battlefield\nagainst evil " + this.enemy.fullName + ".\n"

	def playBattleTurn(command: String): String = {
//		val action = new Action(command)
		val playerReport = playPlayerTurn(command)
		val enemyReport = playEnemyTurn()
		
//		val actionReport = action.executePlayerFightCommands(this.player)
//		actionReport.getOrElse("Unknown command: \"" + command + "\".")
		""
	}
	
	private def playPlayerTurn(command: String): String = {
		val action = new Action(command)
		action.executePlayerFightCommands(this.player)
	}
	
	private def playEnemyTurn(): String = {
		Action.executeEnemyFightCommands(this.enemy)
	}
	
	def isBattling(): Boolean = {
		this.battling
	}
}