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
	winterFell.setNeighbors(Vector("west" -> kingsLanding, "south" -> casterlyRock))
	qarth.setNeighbors(Vector("south" -> volantis, "east" -> kingsLanding))
	theWall.setNeighbors(Vector("south" -> kingsLanding))
	kingsLanding.setNeighbors(Vector("north" -> theWall, "east" -> winterFell, "south" -> castleBlack, "west" -> qarth))
	volantis.setNeighbors(Vector("north" -> qarth, "east" -> castleBlack))
	castleBlack.setNeighbors(Vector("north" -> kingsLanding, "east" -> casterlyRock, "west" -> volantis))
	casterlyRock.setNeighbors(Vector("north" -> winterFell, "west" -> castleBlack))
	
	/* Set characters */
	private val sansa = new Relative("Sansa Stark", Game.relative, Game.female)
//	private val catelyn = new Relative("Catelyn Stark", Game.relative, Game.female)
	private val robb = new Relative("Robb Stark", Game.relative, Game.male)
	
	private val relatives = Vector(sansa, robb)
	
	private val joffrey = new Enemy("Joffrey Baratheon", 100, kingsLanding, Game.enemy, Game.male)
	private val ramsay = new Enemy("Ramsay Bolton", 150, castleBlack, Game.enemy, Game.male)
	private val whiteWalker = new Enemy("Whitewalker", 200, theWall, Game.enemy, Game.unknown)
	
	/* Set hostages */	
	joffrey.setHostage(sansa)
	ramsay.setHostage(robb)
	
	/* Add characters to areas */
	kingsLanding.addCharacter(sansa, joffrey)
	castleBlack.addCharacter(robb, ramsay)
	theWall.addCharacter(whiteWalker)
	
	/* Set weapons */
	private val needle = new Weapon("Needle", "Description of Needle", 20)
	private val oathKeeper = new Weapon("OathKeeper", "Description of OathKeeper", 50)
	private val dragonGlass = new Weapon("Dragonglass", "Description of Dragonglass", 90)
	
	/* Add weapons to vector */
	private val weapons = Vector(needle, oathKeeper, dragonGlass)
	
	/* Set a player (Arya) */
	val player = new Player("Arya", 100, kingsLanding)
	
	/* Add all the weapons to random areas */
//	for(weapon <- weapons) {
//		this.areas(Random.nextInt(this.areas.size)).addItem(weapon)
//	}
	
	/* ---------------- FOR DEBUGGING PURPOSES ----------------------------- */
	
	winterFell.addItem(needle)
	
	
	
	/* -------------------- DEBUGGING PURPOSES END ------------------------- */
	
	
	def isComplete = {
		this.player.location == this.home && this.allRelativesRescued && this.player.isAlive()
	}
	
	def allRelativesRescued: Boolean = {
		this.relatives.forall(p => this.player.rescuedRelative(p.fullName))
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

object Game {
	val enemy = "Enemy"
	val relative = "Relative"
	
	val male = "Male"
	val female = "Female"
	val unknown = "Unknown"
}