package tti.lv.airdockapp.web.dto

import com.google.gson.annotations.SerializedName
import java.util.*

class TaskSmallDTO (
        val id          : String,
        val description : String,
        val title       : String,

        @SerializedName("created") val creationDate : Date,
        @SerializedName("from")    val startDate    : Date,
        @SerializedName("to")      val endDate      : Date,

        val status : TaskDTO.Status
)