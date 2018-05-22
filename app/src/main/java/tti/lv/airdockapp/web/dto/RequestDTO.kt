package tti.lv.airdockapp.web.dto

import tti.lv.airdockapp.R

data class RequestDTO(
        var id          : String,
        var title       : String,
        var description : String,
        var status      : Status = Status.DRAFT
) {
    enum class Status {
        PENDING,
        ACCEPTED,
        CANCELLED,
        DRAFT;

        fun toPrettyString() =
                this.toString()
                        .toLowerCase()
                        .capitalize()
                        .replace("_", " ")

        fun getImageResource() =
                when(this) {
                    RequestDTO.Status.CANCELLED -> R.mipmap.ic_status_canceled
                    RequestDTO.Status.DRAFT     -> R.mipmap.ic_status_draft
                    RequestDTO.Status.PENDING   -> R.mipmap.ic_status_pending
                    RequestDTO.Status.ACCEPTED  -> R.mipmap.ic_status_approved
                }
    }
}