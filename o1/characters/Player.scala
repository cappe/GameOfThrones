package o1.characters

import o1.game._

class Player(name: String, hp: Int, startingArea: Area) extends Character(name, hp, startingArea) {
	
	private var currentLocation = startingArea 
	
	def location = this.currentLocation

}