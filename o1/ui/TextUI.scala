package o1.ui

import o1.game._
import o1.characters._
import o1.items._

object TextUI extends App {

	private val game = new Game
	private val player = game.player
	private var currentArea = this.player.location
	this.run()

	/* Runs the game */
	private def run() = {
		println(this.game.welcomeMessage)
		this.printAreaInfo(this.currentArea)
		while (!this.game.isOver) {
			this.getAreaInfo()
			this.playTurn()
		}
		println(this.game.goodByeMessage)
	}

	private def getAreaInfo() = {
		val newArea = this.player.location
		if (newArea != this.currentArea) {
			this.currentArea = newArea
			this.printAreaInfo(this.currentArea)
		}
	}

	private def printAreaInfo(area: Area) = {
		println(area.fullDescription())
	}

	/* Plays normal turn */
	private def playTurn() = {
		val turnReport = this.game.playTurn(getNextCommand())
		println()
		if (turnReport.equals(Game.battleFieldCommand))
			playBattleTurn()
		else if (!turnReport.isEmpty())
			println(turnReport)
	}

	/* Plays battle turn, i.e. when the player is fighting against a given enemy */
	private def playBattleTurn() = {
		val battleField = game.getNewBattlefield()
		if (battleField.isDefined) {
			printBattleReport(battleField.get)
		} else {
			println(Area.noEnemies)
		}
	}

	/* Prints battle report as it proceeds */
	private def printBattleReport(battleField: Battlefield) = {
		println(battleField.battleFieldMessage)
		while (battleField.isBattling()) {
			println(battleField.playPlayerTurn(getNextCommand()))
			if (!battleField.player.exitCommandGiven && battleField.isBattling()) {
				println(battleField.playEnemyTurn())
			}
		}
		println(printBattleResults(battleField))
	}

	/* Prints results after the battle has ended */
	private def printBattleResults(battleField: Battlefield): String = {
		var results = ""
		if (battleField.player.isAlive() && !battleField.enemy.isAlive())
			results += battleField.getWinResults()
		results
	}

	/* Requests next command from the user */
	private def getNextCommand(): String = {
		println()
		readLine("Next command: ")
	}

}