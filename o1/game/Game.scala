package o1.game

import o1.characters._
import o1.ui._
import o1.game._

class Game {
	
	private val winterFell = new Area("Winterfell", "Description of Winterfell")
	private val qarth = new Area("Qarth", "Description of Qarth")
	private val theWall = new Area("The Wall", "Description of the Wall")
	private val kingsLanding = new Area("King's Landing", "Description of King's Landing")
	private val volantis = new Area("Volantis", "Description of Volantis")
	private val castleBlack = new Area("Castle Black", "Description of Castle Black")
	private val casterlyRock = new Area("Casterly Rock", "Description of Casterly Rock")
	
	winterFell.setNeighbors(Vector("West" -> kingsLanding, "South" -> casterlyRock))
	qarth.setNeighbors(Vector("South" -> volantis, "East" -> kingsLanding))
	theWall.setNeighbors(Vector("South" -> kingsLanding))
	kingsLanding.setNeighbors(Vector("North" -> theWall, "East" -> winterFell, "South" -> castleBlack, "West" -> qarth))
	volantis.setNeighbors(Vector("North" -> qarth, "East" -> castleBlack))
	castleBlack.setNeighbors(Vector("North" -> kingsLanding, "East" -> casterlyRock, "West" -> volantis))
	casterlyRock.setNeighbors(Vector("North" -> winterFell, "West" -> castleBlack))
	
	val player = new Player("Arya", 100, winterFell)
	
	def welcomeMessage = "You are playing Arya from Game of Thrones. Save your relatives!"
	
	def isOver: Boolean = {
		
		false
	}
	
	def playTurn(command: String): String = {
		val action = new Action(command)
		val actionReport = action.execute(this.player)
		actionReport.getOrElse("Unknown command: \"" + command + "\".")
	}

}