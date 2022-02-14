package com.crowdstrike.crawler.shared.util

import com.crowdstrike.crawler.shared.repo.Status
import com.crowdstrike.crawler.shared.repo.Task
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern

/**
 * @author : Momo
 * @since : 23.01.22, Sun
 *
 **/
val containerUUID: UUID = UUID.randomUUID()
private val uuidPattern =
	Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$")

fun String.isValidUUID() = uuidPattern.matcher(this).matches()
fun UUID.isValid() = this.toString().isValidUUID()
fun LocalDate.isValid() = this.isBefore(LocalDate.now())
private val gson = Gson()
fun Any.toJson(): String = gson.toJson(this)
fun String.jsonToTask(): Task = gson.fromJson(this, Task::class.java)
fun UUID.toNewTask() = Task(UUID.randomUUID(), LocalDateTime.now(), this, Status.NEW, containerUUID)
