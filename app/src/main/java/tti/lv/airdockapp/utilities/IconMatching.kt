package tti.lv.airdockapp.utilities

import tti.lv.airdockapp.R
import tti.lv.airdockapp.web.dto.TaskDTO

fun TaskDTO.Status.getImgResource() =
        when(this) {
            TaskDTO.Status.IN_PROGRESS -> R.mipmap.ic_status_progress
            TaskDTO.Status.CANCELED    -> R.mipmap.ic_status_canceled
            TaskDTO.Status.TODO        -> R.mipmap.ic_status_todo
            TaskDTO.Status.DONE        -> R.mipmap.ic_status_done
            TaskDTO.Status.LATER       -> R.mipmap.ic_status_later
        }
