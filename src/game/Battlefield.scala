package src.game

import src.characters._

/* This class is used when the player decides to fight against a given enemy */
class Battlefield(val player: Player, val enemy: Enemy) {

	private var canPassTurn: Boolean = false

	def battleFieldMessage: String = {
		var enemyHp = "You have stepped into the battlefield\nagainst evil " +
			this.enemy.fullName + ". He has " + this.enemy.hp + " hp."
		var playerHp = "\n\n" + this.player.getHp()

		var availableWeapons = "\n\n" + "You may choose from the following weapons:"
		this.player.getWeapons.foreach(availableWeapons += "\n" + _.toString())

		var weaponCommand = "\n\n" + "It is possible to use these by typing\n" +
			"'use [weapon_name]'. Use example: 'use needle'."

		var exitCommand = "\n\n" + "It is also possible to exit in the middle of this fight by typing\n" +
			"'exit'. Your and your enemy's current Health Points will be saved."

		enemyHp + playerHp + availableWeapons + weaponCommand + exitCommand
	}

	/* Player turn */
	def playPlayerTurn(command: String): String = {
		val action = new Action(command)
		val actionReport = action.executePlayerFightCommands(this.player, this.enemy)
		if (actionReport.isDefined && !actionReport.get.isEmpty()) {
			canPassTurn = true
			actionReport.get
		} else {
			canPassTurn = false
			"Unknown command: \"" + command + "\"."
		}
		actionReport.getOrElse("Unknown command: \"" + command + "\".")
	}

	/* Enemy turn */
	def playEnemyTurn(): String = {
		var actionReport: Option[String] = None
		if (canPassTurn)
			actionReport = Action.executeEnemyFightCommand(this.enemy, this.player)
		actionReport.getOrElse("")
	}

	/* Defines if either one is already dead or if the player exits the fight */
	def isBattling(): Boolean = {
		this.player.isAlive() && this.enemy.isAlive() && !this.player.exitCommandGiven
	}

	/* Is called when the player wins the battle */
	def getWinResults(): String = {
		var description = ""
		val relative = enemy.freeHostage()
		this.player.getCurrentLocation().removeEnemy()
		if (relative._1.isDefined) {
			this.player.addRelative(relative._1.get)
		}
		description += relative._2
		description
	}
}