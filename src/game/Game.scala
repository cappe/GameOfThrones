package src.game

import src.characters._
import src.ui._
import src.game._
import src.items._

import scala.util.Random

class Game {

	/* Set areas */
	private val winterFell = new Area("Winterfell", "This is your home.")
	private val qarth = new Area("Qarth", "Evil and mystery place.")
	private val theWall = new Area("The Wall", "Cold and dark. One does not simply live here.")
	private val kingsLanding = new Area("King's Landing", "The King's territory. This is where the King lives and rules it all.")
	private val volantis = new Area("Volantis", "Place full of secrets.")
	private val castleBlack = new Area("Castle Black", "Stories are told that this place is cursed.")
	private val casterlyRock = new Area("Casterly Rock", "Watch out for every step! This place is rocky and cold.")
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
	kingsLanding.addEnemy(Some(joffrey))
	castleBlack.addEnemy(Some(ramsay))
	theWall.addEnemy(Some(whiteWalker))

	/* Set weapons */
	private val needle = new Weapon("Needle", "Arya's very first sword. Small but peppery.", 20)
	private val oathKeeper = new Weapon("OathKeeper", "A very fine lady once carried this weapon. It does what it promises.", 50)
	private val dragonGlass = new Weapon("Dragonglass", "Very rare but very powerful. Does a lot of damage.", 90)

	/* Add weapons to vector */
	private val weapons = Vector(needle, oathKeeper, dragonGlass)

	/* Set a player (Arya) */
	val player = new Player("Arya", 100, winterFell)

	/* Add all the weapons to random areas */
	for (weapon <- weapons) {
		this.areas(Random.nextInt(this.areas.size)).addItem(weapon)
	}

	def isCompleted = {
		this.player.location == this.home && this.allRelativesRescued && this.player.isAlive()
	}

	def allRelativesRescued: Boolean = {
		this.relatives.forall(p => this.player.rescuedRelative(p.fullName))
	}

	def addItemsToArea(area: Area, item: Item) = {
		area.addItem(item)
	}

	def welcomeMessage = "Welcome to the epic journey in the world of Game of Thrones.\n\n" +
						"In this game you are playing Arya and your mission is to save your relatives.\n" +
						"Some evil people are keeping them in prisons in different places. First try to find\n" +
						"at least one weapon to fight with and then attack your enemies as if it was your last day!\n" +
						"You can get started by typing 'help' to see all the commands available.\n\n" +
						"Good luck, Arya!"

	def goodByeMessage = {
		if (this.isCompleted) {
			"\nYou win! You saved all your relatives and\n" +
				"survived home alive. Nice work young lady!"
		} else if (!this.player.isAlive()) {
			"Game over!"
		} else {
			"User quit the game!"
		}
	}

	def isOver: Boolean = {
		!this.player.isAlive() || this.player.hasQuit() || this.isCompleted
	}

	def playTurn(command: String): String = {
		val action = new Action(command)
		val actionReport = action.execute(this.player)
		actionReport.getOrElse("Unknown command: \"" + command + "\".")
	}

	def getNewBattlefield(): Option[Battlefield] = {
		val player = this.player
		val enemy = this.player.getCurrentLocation().getEnemy()
		if (enemy.isDefined) {
			Some(new Battlefield(player, enemy.get))
		} else
			None
	}
}

object Game {
	val enemy = "Enemy"
	val relative = "Relative"

	val male = "Male"
	val female = "Female"
	val unknown = "Unknown"

	val battleFieldCommand = "fight"
}