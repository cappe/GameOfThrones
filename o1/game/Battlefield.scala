package o1.game

import o1.characters._

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

	def playPlayerTurn(command: String): Option[String] = {
		val action = new Action(command)
		val actionReport = action.executePlayerFightCommands(this.player, this.enemy)
		if (actionReport.isDefined) {
			println(actionReport.get)			
			canPassTurn = true
		}
		else
			canPassTurn = false
		actionReport
	}

	def playEnemyTurn(): Option[String] = {
		if (canPassTurn) {
			val actionReport = Action.executeEnemyFightCommand(this.enemy, this.player)
			if (actionReport.isDefined)
				println(actionReport.get)
			actionReport
		}
		else
			None
	}

	def isBattling(): Boolean = {
		this.player.isAlive() && this.enemy.isAlive() && !this.player.exitCommandGiven
	}

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