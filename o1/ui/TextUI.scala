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
	}

	private def getAreaInfo() = {
		val newArea = this.player.location
		if (newArea != this.currentArea) {
			this.currentArea = newArea
			this.printAreaInfo(this.currentArea)
		}
	}

	private def printAreaInfo(area: Area) = {
		println(area.fullDescription)
	}
	
	private def playTurn() = {
		println()
		val command = readLine("Next command: ")
		val turnReport = this.game.playTurn(command)
		if (!turnReport.isEmpty())
			println(turnReport)
	}

}