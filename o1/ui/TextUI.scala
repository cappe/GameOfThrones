package o1.ui

import o1.game._
import o1.characters._
import o1.items._


object TextUI extends App {
	
	private val game = new Game
	private val player = game.player
	private var currentArea = this.player.location
	this.run()
	
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
	
	private def playTurn() = {
		val turnReport = this.game.playTurn(getNextCommand())
		println()
		if (turnReport.equals(Game.battleFieldCommand))
			playBattleTurn()
		else if (!turnReport.isEmpty())
			println(turnReport)
	}
	
	private def playBattleTurn() = {
		val battleField = game.getNewBattlefield()
		if (battleField.isDefined) {
			printBattleReport(battleField.get)
		} else {
			println(Area.noEnemies)
		}
	}
	
	private def printBattleReport(battleField: Battlefield) = {
		println(battleField.battleFieldMessage)
		while(battleField.isBattling()) {
			val playerBattleReport = battleField.playPlayerTurn(getNextCommand())
			if (!battleField.player.exitCommandGiven && battleField.isBattling()) {
				val enemyBattleReport = battleField.playEnemyTurn()
			}
		}
		println(printBattleResults(battleField))
	}
	
	private def printBattleResults(battleField: Battlefield): String = {
		var results = ""
		if (battleField.player.isAlive() && !battleField.enemy.isAlive())
			results += battleField.getWinResults()
		results
	}
	
	private def getNextCommand(): String = {
		println()
		readLine("Next command: ")
	}



}