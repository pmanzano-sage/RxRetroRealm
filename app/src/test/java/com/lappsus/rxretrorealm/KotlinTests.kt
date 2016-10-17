package com.lappsus.rxretrorealm

import org.junit.Assert.*
import org.junit.Test

/**
 * Aggregate operations for Kotlin collections:
 *
 * Iterable: The parent class. Any classes that inherit from this interface represent a sequence of elements we can iterate over.
 * MutableIterable: Iterables that support removing items during iteration.
 * Collection: This class represents a generic collection of elements. We get access to functions that return the size of the collection, whether the collection is empty, contains an item or a set of items. All the methods for this kind of collections are only to request data, because collections are immutable.
 * MutableCollection: a Collection that supports adding and removing elements. It provides extra functions such as add, remove or clear among others.
 * List: Probably the most used collection. It represents a generic ordered collection of elements. As it’s ordered, we can request an item by its position, using the get function.
 * MutableList: a List that supports adding and removing elements.
 * Set: an unordered collection of elements that doesn’t support duplicate elements.
 * MutableSet: a Set that supports adding and removing elements.
 * Map: a collection of key-value pairs. The keys in a map are unique, which means we cannot have two pairs with the same key in a map.
 * MutableMap: a Map that supports adding and removing elements.
 *
 * From http://antonioleiva.com/collection-operations-kotlin/
 */

class KotlinJunitTest {

    val list = listOf(1, 2, 3, 4, 5, 6)

    /**
     * Returns true if at least one element matches the given predicate.
     */
    @Test
    fun testAny() {
        assertTrue(list.any { it % 2 == 0 })
        assertFalse(list.any { it > 10 })
    }

    /**
     * Returns true if all the elements match the given predicate.
     */
    @Test
    fun testAll() {
        assertTrue(list.all { it < 10 })
        assertFalse(list.all { it % 2 == 0 })
    }

    /**
     * Returns the number of elements matching the given predicate.
     */
    @Test
    fun testCount() {
        assertEquals(3, list.count { it % 2 == 0 })
    }

    /**
     * Accumulates the value starting with an initial value and applying an operation from the first to the last element in a collection.
     */
    @Test
    fun testFold() {
        assertEquals(25, list.fold(4) { total, next -> total + next })
    }

    /**
     * Same as fold, but it goes from the last element to first.
     */
    @Test
    fun testFoldRight() {
        assertEquals(21, list.foldRight(0) { total, next -> total + next })
    }

    /**
     * Performs the given operation to each element.
     */
    @Test
    fun testForEach() {
        list.forEach(::println)
    }

    /**
     * Same as forEach, though we also get the index of the element.
     */
    @Test
    fun testForEachIndexed() {
        list.forEachIndexed { index, value -> println("position $index contains a $value") }
    }

    /**
     * Returns the largest element or null if there are no elements.
     */
    @Test
    fun testMax() {
        assertEquals(6, list.max())
    }

    /**
     * Returns the first element yielding the largest value of the given function or null if there are no elements.
     */
    @Test
    fun testMaxBy() {
        // The element whose negative is greater
        assertEquals(1, list.maxBy { -it })
    }

    /**
     * Returns the smallest element or null if there are no elements.
     */
    @Test
    fun testMin() {
        assertEquals(1, list.min())
    }


}
