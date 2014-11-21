package o1.game

import o1.characters._
import o1.ui._
import o1.game._

class Game {
	
	private val winterFell = new Area("Winterfell", "Description of Winterfell")
	
	val player = new Player("Arya", 100, winterFell)
	
	def welcomeMessage = "You are playing Arya from Game of Thrones. Save your relatives!"
	
	def isOver: Boolean = {
		
		false
	}

}