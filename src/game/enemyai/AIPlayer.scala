package game.enemyai

import game.enemyai.decisiontree.DecisionTreeValue
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.{AIAction, MovePlayer}

import scala.collection.mutable

class AIPlayer(val id: String) {

  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    var location = playerLocations

    if (location == null) {
      null
    }
    else if (location.value.playerId == playerId) {
      location.value

    }
    else {
      location = location.next
      locatePlayer(playerId, location)

    }
  }

  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    val currentLocation = locatePlayer(id, playerLocations)
    var location = playerLocations
    var closest: Double = 100000000
    var nearestPlayer = new PlayerLocation(1000000, 100000000, "")
    while (location != null) {
      val x1 = location.value.x
      val y1 = location.value.y
      val x2 = currentLocation.x
      val y2 = currentLocation.y

      val distance = math.sqrt(math.pow((x2 - x1), 2) + math.pow((y2 - y1), 2))

      if (location.value != currentLocation) {
        if (distance < closest) {
          closest = distance
          nearestPlayer = location.value
        }
      }
      location = location.next
    }
    nearestPlayer
  }

  def computePath(start: GridLocation, end: GridLocation): LinkedListNode[GridLocation] = {
    var playerPath: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](end, null)

    var gridX: Int = end.x - start.x
    var gridY: Int = end.y - start.y

    while (gridX != 0 || gridY != 0) {
      if (gridX > 0) {
        gridX -= 1
        playerPath = new LinkedListNode[GridLocation](new GridLocation(start.x + gridX, start.y + gridY), playerPath)
      }

      else if (gridX < 0) {
        gridX += 1
        playerPath = new LinkedListNode[GridLocation](new GridLocation(start.x + gridX, start.y + gridY), playerPath)
      }

      else if (gridY < 0) {
        gridY += 1
        playerPath = new LinkedListNode[GridLocation](new GridLocation(start.x + gridX, start.y + gridY), playerPath)
      }

      else if (gridY > 0) {
        gridY -= 1
        playerPath = new LinkedListNode[GridLocation](new GridLocation(start.x + gridX, start.y + gridY), playerPath)

      }
    }
    playerPath
  }


  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    var tree = decisionTree

    if (tree.value.check(gameState) < 0){
      makeDecision(gameState, tree.left)
    }

    else if(tree.value.check(gameState) > 0){
      makeDecision(gameState, tree.right)
    }

    else {
      tree.value.action(gameState)
    }
  }

  class Queue[A] {

    var front: LinkedListNode[A] = null
    var back: LinkedListNode[A] = null

    def empty(): Boolean = {
      front == null
    }

    def enqueue(a: A): Unit = {
      if (back == null) {
        this.back = new LinkedListNode[A](a, null)
        this.front = this.back
      } else {
        this.back.next = new LinkedListNode[A](a, null)
        this.back = this.back.next
      }
    }

    def dequeue(): A = {
      val toReturn = this.front.value
      this.front = this.front.next
      if (this.front == null) {
        this.back = null
      }
      toReturn
    }

  }

  def distanceAvoidWalls(gameState: AIGameState, start: GridLocation, end: GridLocation): Int = {
    val queue: Queue[Int] = new Queue()

    val startId = gameState.gridLocationToId(start)

    val endId = gameState.gridLocationToId(end)

    val lvl = gameState.levelAsGraph()

    queue.enqueue(startId)

    var dist: Map[Int, Int] = Map(startId -> 0)
    var covered: Set[Int] = Set(startId)

    while(!queue.empty()){
      val dQ = queue.dequeue()
      for ( nodesNear <- lvl.adjacencyList(dQ)){
        if(!covered.contains(nodesNear)){
          queue.enqueue(nodesNear)
          covered = covered + nodesNear
          dist = dist + (nodesNear->(dist(dQ)+1))
        }
      }
    }
    val distance = dist(endId)
    distance
  }



  def closestPlayerAvoidWalls(gameState: AIGameState): PlayerLocation = {
    closestPlayer(gameState.playerLocations)
  }

  // TODO: Replace this placeholder code with your own
  def getPath(gameState: AIGameState): LinkedListNode[GridLocation] = {
    computePath(locatePlayer(this.id, gameState.playerLocations).asGridLocation(), closestPlayerAvoidWalls(gameState).asGridLocation())
  }




}

