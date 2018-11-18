package lesson3

import org.junit.jupiter.api.Tag
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.*

class BinaryTreeTest {
    private fun testAdd(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    @Tag("Example")
    fun testAddKotlin() {
        testAdd { createKotlinTree() }
    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testAdd { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            binarySet.remove(toRemove)
            println("Removing $toRemove from $list")
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(inn, element in binarySet,
                        "$element should be ${if (inn) "in" else "not in"} tree")
            }
            assertTrue(binarySet.checkInvariant())
        }
        //custom test
        val tree = KtBinaryTree<Int>()
        tree.add(9)
        tree.add(12)
        tree.add(7)
        tree.add(6)
        tree.add(1)
        tree.add(13)
        tree.add(20)
        tree.add(5)
        assertEquals(8, tree.size)
        tree.remove(20)
        tree.remove(7)
        assertFalse(tree.contains(20))
        assertFalse(tree.contains(7))
        assertTrue(tree.contains(9))
        assertEquals(6, tree.size)
        val tree1 = KtBinaryTree<Int>()
        tree1.add(10)
        tree1.add(10)
        tree1.add(10)
        assertEquals(1, tree1.size)
        assertTrue(tree1.contains(10))
        tree1.remove(10)
        assertEquals(0, tree1.size)
    }

    @Test
    @Tag("Normal")
    fun testRemoveKotlin() {
        testRemove { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
    }

    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next())
            }
        }
        //custom test
        val list = listOf(listOf(10), listOf(7, 8), listOf(8, 7), listOf(7, 9, 5, 6, 8))
        for (i in list) {
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in i) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next())
            }
        }
    }

    @Test
    @Tag("Normal")
    fun testIteratorKotlin() {
        testIterator { createKotlinTree() }
    }

    //custom test
    @Test
    fun testIterator2() {
        val tree = KtBinaryTree<Int>()
        tree.add(10)
        val treeIt = tree.iterator()
        try {
            treeIt.next()
            throw NoSuchElementException()
        } catch (ex: NoSuchElementException) {
        }
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(inn, element in binarySet,
                        "$element should be ${if (inn) "in" else "not in"} tree")
            }
            assertTrue(binarySet.checkInvariant())
        }
        //custom test
        val list = listOf(listOf(10, 9, 7, 5, 1), listOf(4, 7, 15, 10), listOf(7))
        for (i in list) {
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            treeSet.remove(7)
            println("Removing 7 from $list")
            val binaryIt = binarySet.iterator()
            while (binaryIt.hasNext()) {
                val element = binaryIt.next()
                print("$element ")
                if (element == 7) {
                    binaryIt.remove()
                }
            }
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of 7 from $i")
            assertEquals(treeSet.size, binarySet.size)
            for (element in i) {
                val inn = element != 7
                assertEquals(inn, element in binarySet,
                        "$element should be ${if (inn) "in" else "not in"} tree")
            }
            assertTrue(binarySet.checkInvariant())
        }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveKotlin() {
        testIteratorRemove { createKotlinTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
    }
}