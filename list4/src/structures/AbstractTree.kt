package structures

import utility.CountingComparator

import java.io.File
import java.util.*

abstract class AbstractTree<T : Comparable<T>> : Tree<T> {

    protected interface Node<T> {
        val key: T
        val left: Node<T>?
        val right: Node<T>?
    }

    protected abstract val root: Node<T>?

    protected val comparator = CountingComparator(Comparator.naturalOrder<T>())
    var maximumNumberOfElements = 0
        private set
    var currentNumberOfElements = 0
        private set
    var numberOfInserts = 0
        private set
    var numberOfDeletions = 0
        private set
    var numberOfSearches = 0
        private set
    var numberOfLoads = 0
        private set
    var numberOfInOrders = 0
        private set
    val numberOfComparisons get() = comparator.count

    private fun updateNumberOfElements(change: Int) {
        currentNumberOfElements += change
        if (maximumNumberOfElements < currentNumberOfElements)
            maximumNumberOfElements = currentNumberOfElements
    }

    public final override fun insert(element: T) {
        if (insertImpl(element)) {
            updateNumberOfElements(1)
        }
    }

    protected abstract fun insertImpl(element: T): Boolean

    public final override fun delete(element: T) {
        if (deleteImpl(element)) {
            updateNumberOfElements(-1)
        }
    }

    protected abstract fun deleteImpl(element: T): Boolean

    override fun isEmpty(): Boolean {
        return currentNumberOfElements == 0
    }

    public final override fun search(element: T): Boolean {
        numberOfSearches++
        return searchImpl(element)
    }

    private fun searchImpl(element: T): Boolean {
        var currentNode: Node<T>? = root
        while (currentNode != null) {
            val compareResult = comparator.compare(element, currentNode.key)
            if (compareResult == 0) {
                return true
            }
            currentNode = if (compareResult < 0) {
                currentNode.left
            } else {
                currentNode.right
            }
        }
        return false
    }

    public final override fun load(file: File) {
        numberOfLoads++
        loadImpl(file)
    }

    protected abstract fun loadImpl(file: File)

    public final override fun inOrder(): List<T> {
        numberOfInOrders++
        return inOrderImpl()
    }

    override fun toString(): String {
        return buildString {
            val queue: Queue<Node<T>?> = LinkedList<Node<T>?>()
            queue.add(root)
            var nonNulls = 0
            var k = 0
            while (!queue.isEmpty() && nonNulls < currentNumberOfElements) {
                var n = queue.remove()
                append(" ")
                append(n.toString())
                append(" ")
                if (k + 2 and k + 1 == 0) {
                    append("\n")
                }
                if (n != null) {
                    nonNulls++
                }
                queue.add(n?.left)
                queue.add(n?.right)
                k++
            }
        }
    }

    private fun inOrderImpl(): List<T> {
        val resultList = mutableListOf<T>()
        orderNode(resultList, root)
        return resultList
    }

    private fun orderNode(list: MutableList<T>, node: Node<T>?) {
        if (node == null) return
        orderNode(list, node.left)
        list.add(node.key)
        orderNode(list, node.right)
    }
}
