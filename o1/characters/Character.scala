package o1.characters

import o1.game._

class Character(val name: String, var startingArea: Area, val relationShip: String) {
	
	def getRelationShip: String = {
		this.relationShip
	}

}