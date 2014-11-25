package o1.game

import o1.characters._

class Battlefield(player: Player, enemy: Enemy) {

	private val battling = true

	def battleFieldMessage = "\nYou have stepped into the battlefield\nagainst evil " + this.enemy.fullName + ".\n"

	def playBattleTurn(command: String): (Option[String], Option[String]) = {
		val playerReport = playPlayerTurn(command)
		val enemyReport = playEnemyTurn()
		(playerReport, enemyReport)
	}

	private def playPlayerTurn(command: String): Option[String] = {
		val action = new Action(command)
		action.executePlayerFightCommands(this.player, this.enemy)
	}

	private def playEnemyTurn(): Option[String] = {
		Action.executeEnemyFightCommand(this.enemy, this.player)
	}

	def isBattling(): Boolean = {
		this.battling
	}
}