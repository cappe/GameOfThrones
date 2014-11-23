package o1.game

import scala.collection.mutable.Map
import o1.items._

class Area(var areaName: String, var areaDescription: String) {
	
	private val neighbors = Map[String, Area]()
	private val items = Map[String, Item]()
	
	def fullDescription = {
		var itemList = ""
//		if(!this.items.isEmpty) {
//			itemList = "\nYou see here: " + this.items.keys.mkString(" ")
//		}
//		val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
//		this.description + itemList + exitList
		itemList
	}

	def setNeighbors(exits: Vector[(String, Area)]) = {
		this.neighbors ++= exits
	}

}