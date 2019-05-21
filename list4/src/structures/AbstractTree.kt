package structures

import utility.CountingComparator
import java.io.*

import java.util.*
import java.util.function.Function

abstract class AbstractTree<T : Comparable<T>> : Tree<T> {

    protected interface Node<T> {
        var key: T
        val left: Node<T>?
        val right: Node<T>?
    }

    protected abstract val root: Node<T>?

    protected val comparator = CountingComparator(Comparator.naturalOrder<T>())
    var maximumNumberOfElements: Long = 0
        private set
    var currentNumberOfElements: Long = 0
        private set
    var numberOfInserts: Long = 0
        private set
    var numberOfDeletions: Long = 0
        private set
    var numberOfSearches: Long = 0
        private set
    var numberOfLoads: Long = 0
        private set
    var numberOfInOrders: Long = 0
        private set
    var numberOfNodeModifications: Long = 0
        private set
    val numberOfComparisons get() = comparator.count

    private fun updateNumberOfElements(change: Long) {
        currentNumberOfElements += change
        if (maximumNumberOfElements < currentNumberOfElements)
            maximumNumberOfElements = currentNumberOfElements
    }

    fun updateNumberOfNodeModifications(x: Long) {
        numberOfNodeModifications += x
    }

    fun resetStatistics() {
        maximumNumberOfElements = 0
        numberOfInserts = 0
        numberOfDeletions = 0
        numberOfSearches = 0
        numberOfLoads = 0
        numberOfInOrders = 0
        numberOfNodeModifications = 0
        comparator.resetCount()
    }

    final override fun insert(element: T) {
        numberOfInserts++
        if (insertImpl(element)) {
            updateNumberOfElements(1)
        }
    }

    protected abstract fun insertImpl(element: T): Boolean

    final override fun delete(element: T) {
        numberOfDeletions++
        if (deleteImpl(element)) {
            updateNumberOfElements(-1)
        }
    }

    protected abstract fun deleteImpl(element: T): Boolean

    override fun isEmpty(): Boolean {
        return currentNumberOfElements == 0L
    }

    final override fun search(element: T): Boolean {
        numberOfSearches++
        return searchImpl(element)
    }

    protected abstract fun searchImpl(element: T): Boolean

    final override fun load(file: File, fromString: Function<String, T>, delimiterPattern: String) {
        numberOfLoads++
        loadImpl(file, fromString, delimiterPattern)
    }

    @Throws(IOException::class)
    protected fun loadImpl(file: File, fromString: Function<String, T>, delimiterPattern: String) {
        if (file.canRead()) {
            try {
                val fileReader = FileReader(file)
                val bufferedReader = BufferedReader(fileReader)
                val scanner = Scanner(bufferedReader)
                scanner.useDelimiter(delimiterPattern) //"\\W+"
                var token: String
                while (scanner.hasNext()) {
                    token = scanner.next()
                    insert(fromString.apply(token))
                }
                bufferedReader.close()
            } catch (e: FileNotFoundException) {
                throw FileNotFoundException("Cannot find file" + file.name)
            } catch (e: IOException) {
                throw IOException()
            }

        }
    }

    final override fun inOrder(): List<T> {
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
                val n = queue.remove()
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

    protected open fun inOrderImpl(): List<T> {
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

    protected fun swapKeys(node1: Node<T>, node2: Node<T>) {
        val tmp = node1.key
        node1.key = node2.key
        node2.key = tmp
        updateNumberOfNodeModifications(2)
    }
}
