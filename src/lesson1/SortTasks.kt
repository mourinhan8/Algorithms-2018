@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.util.*


/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
 * каждый на отдельной строке. Пример:
 *
 * 13:15:19
 * 07:26:57
 * 10:00:03
 * 19:56:14
 * 13:15:19
 * 00:40:31
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 00:40:31
 * 07:26:57
 * 10:00:03
 * 13:15:19
 * 13:15:19
 * 19:56:14
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    val result = File(outputName).bufferedWriter()
    val file = File(inputName).readLines().toTypedArray()
    val reg = Regex("(([01]\\d)|(2[0-4])):[0-5]\\d:[0-5]\\d")
    for (line in file) {
        if (!(reg matches line)) throw Exception()
    }
    insertionSort(file)
    for (line in file) {
        result.write(line + "\n")
    }
    result.close()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    val res = File(outputName).bufferedWriter()
    val file = File(inputName).readLines().toTypedArray().sortedArray()
    val reg = Regex("[А-Яа-я]+ [А-Яа-я]+ - [А-Яа-я]+ \\d+")
    val address = mutableSetOf<String>()
    val list = mutableListOf<List<String>>()
    for (i in file.indices) {
        if (!(reg matches file[i])) throw IllegalArgumentException()
        val sub = file[i].split(" - ")
        list.add(sub)
        address.add(sub[1])
    }
    val arrayAddress = address.toTypedArray().sortedArray()
    val listRes = mutableListOf<MutableList<String>>()
    for (n in arrayAddress) {
        val l = mutableListOf<String>()
        l.add(n)
        listRes.add(l)
    }
    for (i in arrayAddress.indices) {
        for (j in list.indices) {
            if (list[j][1] == arrayAddress[i]) {
                listRes[i].add(list[j][0])
            }
        }
    }
    for (i in 0 until listRes.size) {
        val str = StringBuilder()
        str.append(listRes[i][0] + " - ")
        for (j in 1 until listRes[i].size) {
            str.append(listRes[i][j])
            if (j != listRes[i].size - 1) str.append(", ")
        }
        res.write(str.toString() + "\n")
    }
    res.close()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */

fun sortTemperatures(inputName: String, outputName: String) {
    val res = File(outputName).bufferedWriter()
    val file = File(inputName).readLines()
    val list = ArrayList<Double>()
    for (line in file) {
        val temp = line.toDouble()
        if (temp < -273.0 || temp > 500.0) throw IllegalArgumentException()
        list.add(temp)
    }
    list.sort()
    for (i in list.indices) {
        res.write(list[i].toString() + "\n")
    }
    res.close()
}


/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    TODO()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

