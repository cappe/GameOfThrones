package o1.game

import o1.characters._
import o1.ui._
import o1.game._
import o1.items._

class Game {
	
	/* Set areas */
	private val winterFell = new Area("Winterfell", "Description of Winterfell")
	private val qarth = new Area("Qarth", "Description of Qarth")
	private val theWall = new Area("The Wall", "Description of the Wall")
	private val kingsLanding = new Area("King's Landing", "Description of King's Landing")
	private val volantis = new Area("Volantis", "Description of Volantis")
	private val castleBlack = new Area("Castle Black", "Description of Castle Black")
	private val casterlyRock = new Area("Casterly Rock", "Description of Casterly Rock")
	
	/* Add neighbors to areas */
	winterFell.setNeighbors(Vector("West" -> kingsLanding, "South" -> casterlyRock))
	qarth.setNeighbors(Vector("South" -> volantis, "East" -> kingsLanding))
	theWall.setNeighbors(Vector("South" -> kingsLanding))
	kingsLanding.setNeighbors(Vector("North" -> theWall, "East" -> winterFell, "South" -> castleBlack, "West" -> qarth))
	volantis.setNeighbors(Vector("North" -> qarth, "East" -> castleBlack))
	castleBlack.setNeighbors(Vector("North" -> kingsLanding, "East" -> casterlyRock, "West" -> volantis))
	casterlyRock.setNeighbors(Vector("North" -> winterFell, "West" -> castleBlack))
	
	/* Set relatives */
	private val sansa = new Relative("Sansa", kingsLanding)
	private val catelyn = new Relative("Catelyn", theWall)
	private val robb = new Relative("Robb", castleBlack)
	
	/* Add relatives to areas */
	kingsLanding.addCharacter(sansa)
	theWall.addCharacter(catelyn)
	castleBlack.addCharacter(robb)
	
	
	/* Set weapons */
	private val needle = new Weapon("Needle", "Description of Needle", 20)
	private val oathKeeper = new Weapon("OathKeeper", "Description of OathKeeper", 50)
	private val dragonGlass = new Weapon("Dragonglass", "Description of Dragonglass", 90)
	
	
	
	val player = new Player("Arya", 100, winterFell)
	
	def addItemsToArea(area: Area, item: Item) = {
		area.addItem(item)
	}
	
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