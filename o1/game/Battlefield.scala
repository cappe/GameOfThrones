package o1.game

import o1.characters._

class Battlefield(player: Player, enemy: Enemy) {
	
	private var canPassTurn: Boolean = false

	def battleFieldMessage: String = {
		var message = "\nYou have stepped into the battlefield\nagainst evil " +
			this.enemy.fullName + ".\n\n" + "Your weapons of choice are:"
		this.player.getWeapons.foreach(message += "\n" + _.toString())
		message
	}

	def playPlayerTurn(command: String): Option[String] = {
		val action = new Action(command)
		val actionSuccessful = action.executePlayerFightCommands(this.player, this.enemy)
		if (actionSuccessful.isDefined)
			canPassTurn = true
		else
			canPassTurn = false
		actionSuccessful
	}

	def playEnemyTurn(): Option[String] = {
		if (canPassTurn)
			Action.executeEnemyFightCommand(this.enemy, this.player)
		else
			None
	}

	def isBattling(): Boolean = {
		this.player.isAlive() && this.enemy.isAlive()
	}

	def setWinResults(): String = {
		var description = ""
		val relative = enemy.freeHostage()
		if (relative._1.isDefined)
			this.player.addRelative(relative._1.get)
		description += relative._2
		description
	}
}