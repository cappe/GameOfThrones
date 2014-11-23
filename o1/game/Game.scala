package o1.game

import o1.characters._
import o1.ui._
import o1.game._
import o1.items._

import scala.util.Random

class Game {
	
	/* Set areas */
	private val winterFell = new Area("Winterfell", "Description of Winterfell")
	private val qarth = new Area("Qarth", "Description of Qarth")
	private val theWall = new Area("The Wall", "Description of the Wall")
	private val kingsLanding = new Area("King's Landing", "Description of King's Landing")
	private val volantis = new Area("Volantis", "Description of Volantis")
	private val castleBlack = new Area("Castle Black", "Description of Castle Black")
	private val casterlyRock = new Area("Casterly Rock", "Description of Casterly Rock")
	private val home = winterFell
	
	/* Add areas to vector */
	private val areas = Vector(winterFell, qarth, theWall, kingsLanding, volantis, castleBlack, casterlyRock)
	
	/* Add neighbors to areas */
	winterFell.setNeighbors(Vector("West" -> kingsLanding, "South" -> casterlyRock))
	qarth.setNeighbors(Vector("South" -> volantis, "East" -> kingsLanding))
	theWall.setNeighbors(Vector("South" -> kingsLanding))
	kingsLanding.setNeighbors(Vector("North" -> theWall, "East" -> winterFell, "South" -> castleBlack, "West" -> qarth))
	volantis.setNeighbors(Vector("North" -> qarth, "East" -> castleBlack))
	castleBlack.setNeighbors(Vector("North" -> kingsLanding, "East" -> casterlyRock, "West" -> volantis))
	casterlyRock.setNeighbors(Vector("North" -> winterFell, "West" -> castleBlack))
	
	/* Set characters */
	private val sansa = new Relative("Sansa Stark", kingsLanding, "Relative")
	private val catelyn = new Relative("Catelyn Stark", theWall, "Relative")
	private val robb = new Relative("Robb Stark", castleBlack, "Relative")
	
	private val relatives = Vector(sansa, catelyn, robb)
	
	private val joffrey = new Enemy("Joffrey Baratheon", 100, kingsLanding, "Enemy")
	private val ramsay = new Enemy("Ramsay Bolton", 150, castleBlack, "Enemy")
	private val whiteWalker = new Enemy("White Walker", 200, theWall, "Enemy")
	
	/* Add characters to areas */
	kingsLanding.addCharacter(sansa, joffrey)
	castleBlack.addCharacter(robb, ramsay)
	theWall.addCharacter(catelyn, whiteWalker)
	
	/* Set weapons */
	private val needle = new Weapon("Needle", "Description of Needle", 20)
	private val oathKeeper = new Weapon("OathKeeper", "Description of OathKeeper", 50)
	private val dragonGlass = new Weapon("Dragonglass", "Description of Dragonglass", 90)
	
	/* Add weapons to vector */
	private val weapons = Vector(needle, oathKeeper, dragonGlass)
	
	/* Add all the weapons to random areas */
	for(weapon <- weapons) {
		this.areas(Random.nextInt(this.areas.size)).addItem(weapon)
	}
	
	val player = new Player("Arya", 100, home)
	
	def isComplete = {
		this.player.location == this.home && this.allRelativesRescued
	}
	
	def allRelativesRescued: Boolean = {
		this.relatives.forall(p => this.player.has(p.name))
	}
	
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