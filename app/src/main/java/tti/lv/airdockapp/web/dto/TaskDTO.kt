package tti.lv.airdockapp.web.dto

import com.google.gson.annotations.SerializedName
import tti.lv.airdockapp.R
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
        LATER(false),
        SUSPENDED(true)
        ;

        companion object {
            fun assignableValues() =
                    values().toList()
                            .filter { it.isAssignable }
        }

        fun toPrettyString() =
                this.toString()
                        .toLowerCase()
                        .capitalize()
                        .replace("_", " ")

        fun getImgResource() =
                when(this) {
                    TaskDTO.Status.IN_PROGRESS -> R.mipmap.ic_status_progress
                    TaskDTO.Status.CANCELED    -> R.mipmap.ic_status_canceled
                    TaskDTO.Status.TODO        -> R.mipmap.ic_status_todo
                    TaskDTO.Status.DONE        -> R.mipmap.ic_status_done
                    TaskDTO.Status.LATER       -> R.mipmap.ic_status_later
                    TaskDTO.Status.SUSPENDED   -> R.mipmap.ic_status_suspended
                }
    }
}