package tests

import game.enemyai.{AIGameState, AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest.FunSuite

class Task3 extends FunSuite{

  var player: AIPlayer = new AIPlayer("1")
  var EPSILON = 0.001
  var gamestate: AIGameState = new AIGameState()

  gamestate.wallLocations = List(
    new GridLocation(3, 9),
    new GridLocation(3, 8),
    new GridLocation(3, 7),
    new GridLocation(3, 6),
    new GridLocation(3, 5)
  )
  gamestate.levelWidth = 30
  gamestate.levelHeight = 30
  var playerLocation = new LinkedListNode[PlayerLocation](new PlayerLocation(2, 8, "1"), null)
  playerLocation = new LinkedListNode[PlayerLocation](new PlayerLocation(4, 8, "2"), playerLocation)
  gamestate.playerLocations = playerLocation

test("Test 1 - testing if goes thru walls"){
  val nearPath: Int =
    player.distanceAvoidWalls(gamestate, new PlayerLocation(2, 8, "1").asGridLocation(), new GridLocation(4, 8))

  val wallFaze: Int =
    player.computePath(new PlayerLocation(2, 8, "1").asGridLocation(), new GridLocation(4, 8)).size()-1

  assert(Math.abs(wallFaze - 2) < EPSILON)
  assert(wallFaze < nearPath)
}


  test("Test 2 - testing no walls") {
    val gameState: AIGameState = new AIGameState()

    val playerLocation = new LinkedListNode[PlayerLocation](new PlayerLocation(2, 8, "1"), null)
    gameState.playerLocations = playerLocation

    gameState.levelWidth = 40
    gameState.levelHeight = 40

    val nearPath: Int =
      player.distanceAvoidWalls(gameState, new PlayerLocation(2, 8, "1").asGridLocation(), new GridLocation(4, 8))

    val wallFaze: Int =
      player.computePath(new PlayerLocation(2, 8, "1").asGridLocation(), new GridLocation(4, 8)).size()-1

    assert(Math.abs(nearPath - 2) < EPSILON)
    assert(Math.abs(wallFaze - 2) < EPSILON)
  }




  test("Test 3 - testing when theres two game states"){
    val gamestateTwo: AIGameState = new AIGameState()

    val playerLocation2 = new LinkedListNode[PlayerLocation](new PlayerLocation(2, 8, "1"), null)
    gamestateTwo.playerLocations = playerLocation2

    gamestateTwo.levelWidth = 50
    gamestateTwo.levelHeight = 50

    val nearPath: Int =
      player.distanceAvoidWalls(gamestateTwo, new PlayerLocation(2, 8, "1").asGridLocation(), new GridLocation(4, 8))

    val wallFaze: Int =
      player.computePath(new PlayerLocation(2, 8, "1").asGridLocation(), new GridLocation(4, 8)).size()-1

    assert(Math.abs(wallFaze - nearPath) < EPSILON)


  }

}
