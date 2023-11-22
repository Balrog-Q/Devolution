package devolution

import devolution.*
import devolution.helpers.*

import scala.collection.mutable.Map

trait Zone[thisSystem <: Zone[thisSystem]]:
	this: thisSystem =>

	private val neighbors = Map[String, thisSystem]()

	/** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
	def neighbor(direction: String) = this.neighbors.get(direction)

	/** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
	def setNeighbor(direction: String, neighbor: thisSystem) = 
		this.neighbors += direction -> neighbor
	
	/** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction–area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
	def setNeighbors(exists: Vector[(String, thisSystem)]) = 
		this.neighbors ++= exists

end Zone