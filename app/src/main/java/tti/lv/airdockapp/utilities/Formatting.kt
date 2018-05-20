package tti.lv.airdockapp.utilities

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import tti.lv.airdockapp.web.dto.TaskDTO
import java.text.SimpleDateFormat
import java.util.*

fun Date.toShortDateFormat() = SimpleDateFormat("dd.MM.yyyy").format(this)
fun Date.toDateWithTime() =  SimpleDateFormat("dd.MM.yyyy, h:mm a").format(this)

fun TaskDTO.idWithPrefix() = "Task-${this.id}"

