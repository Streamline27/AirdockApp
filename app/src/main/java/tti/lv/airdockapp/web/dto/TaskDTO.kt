package tti.lv.airdockapp.web.dto

import com.google.gson.annotations.SerializedName
import java.util.*

class TaskDTO (
        val id          : String,
        val description : String,
        val title       : String,

        @SerializedName("created") val creationDate : Date,
        @SerializedName("from")    val startDate    : Date,
        @SerializedName("to")      val endDate      : Date,

        var status : Status,
        val workOrder : WorkOrderDTO
) {

    enum class Status(val isAssignable: Boolean) {
        IN_PROGRESS(true),
        CANCELED(false),
        TODO(true),
        DONE(true),
        LATER(false)
        ;

        companion object {
            fun assignableValues() =
                    values().toList()
                            .filter { it.isAssignable }
        }

    }
}