package o1.characters

import o1.game._

class Enemy(name: String, var hp: Int, startingArea: Area, relationShip: String, var hostage: Option[Relative]) extends Character(name, relationShip) {

	def freeHostage(relative: Relative): Option[Relative] = {
		var freeHostage = this.hostage
		this.hostage = None
		freeHostage
	}
	
}