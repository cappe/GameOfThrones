package o1.ui

import o1.game._
import o1.characters._
import o1.items._


object TextUI extends App {
	
	private val game = new Game
	private val player = game.player
	this.run()
	
	private def run() = {
		println(this.game.welcomeMessage)
		while (!this.game.isOver) {
			this.printAreaInfo()
			this.playTurn()
		}
	}
	
	private def printAreaInfo() = {
		val area = this.player.location
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