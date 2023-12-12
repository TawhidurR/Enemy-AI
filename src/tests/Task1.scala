package tests

import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import game.enemyai.AIPlayer
import game.enemyai.PlayerLocation
import org.scalatest._

class Task1 extends FunSuite {

  val EPSILON: Double = 0.001

  def compareDoubles(d1: Double, d2: Double): Boolean = {
    Math.abs(d1 - d2) < EPSILON
  }


//  test("Test 1") {
//    var location: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](new PlayerLocation(3, 3, "1"), null)
//
//    val AI: AIPlayer = new AIPlayer("1")
//
//    location = new LinkedListNode[PlayerLocation](
//      new PlayerLocation(5, 4, "3"),
//      location
//    )
//    location = new LinkedListNode[PlayerLocation](
//      new PlayerLocation(6, 4, "4"),
//      location
//    )
//    assert(AI.closestPlayer(location).playerId != location.value.playerId)
//  }


    test("compute path"){
      val AI: AIPlayer = new AIPlayer("AI1")
      val computed: LinkedListNode[GridLocation] = AI.computePath(new GridLocation(2,2), new GridLocation(3,3))
      assert(computed.value.x == 2)
      assert(computed.value.y == 2)
      assert((computed.next.value.x == 3 && computed.next.value.y == 2) || (computed.next.value.x == 2 && computed.next.value.y == 3))
      assert(computed.next.next.value.x == 3 && computed.next.next.value.y == 3)
      val computed2: LinkedListNode[GridLocation]= AI.computePath(new GridLocation(2,2), new GridLocation(0,0))
      assert(computed2.value.x == 2)
      assert(computed2.value.y == 2)
      assert((computed2.next.value.x == 2 && computed2.next.value.y == 1) || (computed2.next.value.x == 1 && computed2.next.value.y == 2))
      assert((computed2.next.next.value.x == 2 && computed2.next.next.value.y == 0) || (computed2.next.next.value.x == 0 && computed2.next.next.value.y == 2))

    }

  test("locate player") {
    val AI: AIPlayer = new AIPlayer("player1")

    var lst: LinkedListNode[PlayerLocation] = new LinkedListNode(new PlayerLocation(2.1, 2.6, "1"), null)
    lst = new LinkedListNode(new PlayerLocation(-8.0, -12.0, "3"), lst)
    lst = new LinkedListNode(new PlayerLocation(-10.0, 18.0, "5"), lst)
    lst = new LinkedListNode(new PlayerLocation(58.0, 19.0, "8"), lst)
    lst = new LinkedListNode(new PlayerLocation(42.0, 54.3, "8"), lst)

    val computed: PlayerLocation = AI.locatePlayer("5", lst)
    assert(compareDoubles(computed.x, -10.0))
    assert(compareDoubles(computed.y, 18.0))
    assert(computed.playerId == "5")
  }
  test("closest player") {
    val AI: AIPlayer = new AIPlayer("AI1")
    var lst: LinkedListNode[PlayerLocation] = new LinkedListNode(new PlayerLocation(2.7, 2.1, "AI1"), null)
    lst = new LinkedListNode(new PlayerLocation(-348.0, -132.0, "AI2"), lst)
    lst = new LinkedListNode(new PlayerLocation(5, 2, "AI5"), lst)
    lst = new LinkedListNode(new PlayerLocation(58.0, 19.0, "AI8"), lst)

    val computed: PlayerLocation = AI.closestPlayer(lst)
    assert(compareDoubles(computed.x, 5))
    assert(compareDoubles(computed.y, 2))
    assert(computed.playerId == "AI5")
  }

}
