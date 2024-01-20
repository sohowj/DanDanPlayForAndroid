package com.xyoye.common_component.source.inter

/**
 * Created by xyoye on 2021/11/14.
 *
 * 扩展资源，弹幕+字幕
 */

interface ExtraSource {
    fun getDanmuPath(): String?

    fun setDanmuPath(path: String)

    fun getEpisodeId(): String?

    fun setEpisodeId(id: String?)

    fun getSubtitlePath(): String?

    fun setSubtitlePath(path: String)
}