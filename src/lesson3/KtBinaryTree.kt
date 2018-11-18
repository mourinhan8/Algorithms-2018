package lesson3

import java.util.*
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
            root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean {
        val node = find(element)
        if (node == null || node.value != element)
            return false
        val right = node.right
        val left = node.left
        val parent = findParent(root!!, element)
        if (node == root) {
            if (right == null && left == null) root = null
            else if (right == null) {
                root = left
            } else if (left == null) {
                root = right
            } else {
                val temp = swapNode(left, right)
                root = temp
            }
        } else {
            val v = parent!!.value
            val a = element > v
            val b = element < v
            if (right == null && left == null) {
                if (a) parent.right = null
                if (b) parent.left = null
            } else if (right == null) {
                if (a) parent.right = left
                if (b) parent.left = left
            } else if (left == null) {
                if (a) parent.right = right
                if (b) parent.left = right
            } else {
                val temp = swapNode(left, right)
                if (a) parent.right = temp
                if (b) parent.left = temp
            }
        }
        size--
        return true
    }
    //Трудоемкость алгоритм - O(N logN) - N : количество node в дереве
    //Ресурсоемкость - O(1)

    private fun swapNode(nLeft: Node<T>, nRight: Node<T>): Node<T> {
        val min = minimumElement(nRight)
        val temp = Node(min)
        val minNode = find(min)
        temp.left = nLeft
        if (minNode == nRight) {
            temp.right = minNode.right
        } else {
            val rightOfMinNode = minNode!!.right
            val parentOfMinNode = findParent(root!!, min)
            parentOfMinNode!!.left = rightOfMinNode
            temp.right = nRight
        }
        return temp
    }

    //поиск наименьшее значение, который больше значения node
    private fun minimumElement(node: Node<T>): T {
        if (node.left == null) return node.value
        else return (minimumElement(node.left!!))
    }

    private fun findParent(node: Node<T>, v: T): Node<T>? {
        if (root!!.value == v) return null
        val left = node.left
        val right = node.right
        if (left != null && right != null)
            if (left.value == v || right.value == v) {
                return node
            }
        if (right == null && left != null)
            if (left.value == v) return node
        if (left == null && right != null) {
            if (right.value == v) return node
        }
        if (v < node.value) return findParent(node.left!!, v)
        else return findParent(node.right!!, v)
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
            root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    inner class BinaryTreeIterator : MutableIterator<T> {

        private var current: Node<T>? = null
        private var stack = Stack<Node<T>>()

        init {
            pushAll(root!!)
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private fun findNext(): Node<T>? {
            val temp = stack.pop()
            if (temp.right != null) {
                pushAll(temp.right!!)
            }
            return temp
        }
        //Трудоемкость алгоритм - O(1)
        //Ресурсоемкость - O(N)

        private fun pushAll(node: Node<T>) {
            if (node != null) {
                stack.push(node)
                if (node.left != null)
                    pushAll(node.left!!)
            }
        }

        override fun hasNext(): Boolean = !stack.isEmpty()

        override fun next(): T {
            current = findNext()
            return (current ?: throw NoSuchElementException()).value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            if (current != null) remove(current!!.value)
            else throw NoSuchElementException()
        }
        //Трудоемкость алгоритм - O(N logN)
        //Ресурсоемкость - O(N)
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
